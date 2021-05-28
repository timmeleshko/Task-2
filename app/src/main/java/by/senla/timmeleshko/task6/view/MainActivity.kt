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
import by.senla.timmeleshko.task6.adapters.WorksListAdapter.WorksListAdapterConstants.DATA_VIEW_TYPE
import by.senla.timmeleshko.task6.adapters.WorksListAdapter.WorksListAdapterConstants.FOOTER_VIEW_TYPE
import by.senla.timmeleshko.task6.model.enums.State
import by.senla.timmeleshko.task6.model.network.WorksListViewModel
import by.senla.timmeleshko.task6.utils.dpToPx
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.COLUMNS_COUNT
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.COLUMNS_COUNT_EMPTY
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.HORIZONTAL_COLUMN_MARGIN
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.VERTICAL_COLUMN_MARGIN
import com.rubensousa.decorator.GridSpanMarginDecoration

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: WorksListViewModel
    private lateinit var worksListAdapter: WorksListAdapter

    object MainActivityConstants {
        const val COLUMNS_COUNT = 2
        const val COLUMNS_COUNT_EMPTY = 1
        const val VERTICAL_COLUMN_MARGIN = 16
        const val HORIZONTAL_COLUMN_MARGIN = 16
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.worksList)
        viewModel = ViewModelProvider(this).get(WorksListViewModel::class.java)
        initAdapter()
        initState()
    }

    private fun initAdapter() {
        worksListAdapter = WorksListAdapter { viewModel.retry() }
        val gridLayoutManager = GridLayoutManager(this@MainActivity, COLUMNS_COUNT)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (worksListAdapter.getItemViewType(position)) {
                    DATA_VIEW_TYPE -> COLUMNS_COUNT_EMPTY
                    FOOTER_VIEW_TYPE -> COLUMNS_COUNT
                    else -> COLUMNS_COUNT
                }
            }
        }
        recyclerView.apply {
            layoutManager = gridLayoutManager
            addItemDecoration(
                GridSpanMarginDecoration(
                    verticalMargin = dpToPx(VERTICAL_COLUMN_MARGIN),
                    horizontalMargin = dpToPx(HORIZONTAL_COLUMN_MARGIN),
                    gridLayoutManager = gridLayoutManager
                )
            )
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