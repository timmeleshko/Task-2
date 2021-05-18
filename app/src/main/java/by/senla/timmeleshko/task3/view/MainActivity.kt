package by.senla.timmeleshko.task3.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task3.R
import by.senla.timmeleshko.task3.adapters.RecyclerViewAdapter
import by.senla.timmeleshko.task3.model.beans.Work
import by.senla.timmeleshko.task3.model.network.DataLoader

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val adapter = RecyclerViewAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.worksList)
        initListView()

        val dataLoader = object: DataLoader() {
            override fun onDataLoaded(result: List<Work>) {
                adapter.updateData(result)
            }
        }
        dataLoader.loadDataToList()
    }

    private fun initListView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }
}