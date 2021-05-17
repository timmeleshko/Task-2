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

    private lateinit var customAdapter: RecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.worksList)
        val dataLoader = object: DataLoader() {
            override fun onDataLoaded(result: ArrayList<Work>) {
                updateList(result)
            }
        }
        updateList(listOf())
        dataLoader.loadDataToList()
    }

    private fun updateList(list: List<Work>) {
        customAdapter = RecyclerViewAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = customAdapter
        customAdapter.notifyDataSetChanged()
    }
}