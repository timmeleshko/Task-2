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
//    private val adapter = RecyclerViewAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.worksList)
//        initListView()

        val dataLoader = object: DataLoader() {
            // в качестве типа аргументов и обьявлений переменных лучше использовать более общий тип класса или интерфейса
            // в данном случае правильней указать тип result: List<Work>
            // нужно избегать указание конкретных реализаций
            override fun onDataLoaded(result: ArrayList<Work>) {
                updateList(result)
//                adapter.updateData(result)
            }
        }
        updateList(listOf())
        dataLoader.loadDataToList()
    }

    // не совсем удачный вариант инициализации RecyclerView
    private fun updateList(list: List<Work>) {
        customAdapter = RecyclerViewAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = customAdapter
        customAdapter.notifyDataSetChanged()
    }

    // более правильный вариант
//    private fun initListView() {
//        recyclerView.apply {
//            layoutManager = LinearLayoutManager(this@MainActivity)
//            adapter = this@MainActivity.adapter
//        }
//    }
}