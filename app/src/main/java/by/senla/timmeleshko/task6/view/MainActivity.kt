package by.senla.timmeleshko.task6.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.R
import by.senla.timmeleshko.task6.adapters.WorksListAdapter
import by.senla.timmeleshko.task6.model.Constants.GRID_LAYOUT_COLUMNS_COUNT
import by.senla.timmeleshko.task6.model.Constants.HORIZONTAL_COLUMN_MARGIN
import by.senla.timmeleshko.task6.model.Constants.VERTICAL_COLUMN_MARGIN
import by.senla.timmeleshko.task6.model.enums.State
import by.senla.timmeleshko.task6.model.network.WorksListViewModel
import by.senla.timmeleshko.task6.utils.dpToPx
import com.rubensousa.decorator.ColumnProvider
import com.rubensousa.decorator.GridMarginDecoration

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: WorksListViewModel
    private lateinit var worksListAdapter: WorksListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.worksList)
        viewModel = ViewModelProvider(this).get(WorksListViewModel::class.java)
        initAdapter()
        initState()
    }

    private fun initAdapter() {
        val gridLayoutManager = GridLayoutManager(this@MainActivity, GRID_LAYOUT_COLUMNS_COUNT)
        worksListAdapter = WorksListAdapter { viewModel.retry() }
        recyclerView.apply {
            layoutManager = gridLayoutManager
            addItemDecoration(GridMarginDecoration(
                verticalMargin = dpToPx(VERTICAL_COLUMN_MARGIN),
                horizontalMargin = dpToPx(HORIZONTAL_COLUMN_MARGIN),
                columnProvider = object : ColumnProvider {
                    override fun getNumberOfColumns(): Int = GRID_LAYOUT_COLUMNS_COUNT
                },
                orientation = RecyclerView.VERTICAL
            ))
            adapter = this@MainActivity.worksListAdapter
        }
        viewModel.worksList.observe(this, {
            worksListAdapter.submitList(it)
        })
    }

    private fun initState() {
        viewModel.getState().observe(this, { state ->
            findViewById<ProgressBar>(R.id.progressBar).visibility =
                if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                worksListAdapter.setState(state ?: State.DONE)
            }
        })
    }
}