package by.senla.timmeleshko.task6.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.R
import by.senla.timmeleshko.task6.adapters.RecyclerViewAdapter
import by.senla.timmeleshko.task6.model.network.DataViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val adapter = RecyclerViewAdapter(listOf())

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
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }
}