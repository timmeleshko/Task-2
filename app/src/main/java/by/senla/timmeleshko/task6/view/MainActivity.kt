package by.senla.timmeleshko.task6.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.R
import by.senla.timmeleshko.task6.adapters.RecyclerViewAdapter
import by.senla.timmeleshko.task6.model.Constants.GRID_LAYOUT_COLUMNS_COUNT
import by.senla.timmeleshko.task6.model.network.DataViewModel
import by.senla.timmeleshko.task6.utils.dpToPx
import com.rubensousa.decorator.ColumnProvider
import com.rubensousa.decorator.GridMarginDecoration

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val adapter = RecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.worksList)
        initListView()

        val model: DataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        model.getDataWrapper().observe(this) { dataWrapper ->
            dataWrapper.data?.let {
                adapter.updateData(it)
            }
        }
    }

    private fun initListView() {
        recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, GRID_LAYOUT_COLUMNS_COUNT)
            addItemDecoration(GridMarginDecoration(
                verticalMargin = dpToPx(16),
                horizontalMargin = dpToPx(16),
                columnProvider = object : ColumnProvider {
                    override fun getNumberOfColumns(): Int = GRID_LAYOUT_COLUMNS_COUNT
                },
                orientation = RecyclerView.VERTICAL
            ))
            adapter = this@MainActivity.adapter
        }
    }
}