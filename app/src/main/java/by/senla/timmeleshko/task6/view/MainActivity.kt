package by.senla.timmeleshko.task6.view

import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.R
import by.senla.timmeleshko.task6.adapters.RecyclerViewAdapter
import by.senla.timmeleshko.task6.model.Constants.GRID_LAYOUT_COLUMNS_COUNT
import by.senla.timmeleshko.task6.model.network.DataViewModel
import com.rubensousa.decorator.ColumnProvider
import com.rubensousa.decorator.GridMarginDecoration
import com.rubensousa.decorator.LinearMarginDecoration

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val adapter = RecyclerViewAdapter(listOf())
    private val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.worksList)
        initListView()

        val model: DataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        model.getDataWrapper().observe(this) { dataWrapper ->
            dataWrapper.data?.works?.let {
                adapter.updateData(it)
            }
        }
    }

    private fun initListView() {
        recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, GRID_LAYOUT_COLUMNS_COUNT)
            addItemDecoration(GridMarginDecoration(
                verticalMargin = 16.px,
                horizontalMargin = 16.px,
                columnProvider = object : ColumnProvider {
                    override fun getNumberOfColumns(): Int = GRID_LAYOUT_COLUMNS_COUNT
                },
                orientation = RecyclerView.VERTICAL
            ))
            adapter = this@MainActivity.adapter
        }
    }
}