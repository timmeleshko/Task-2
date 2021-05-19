package by.senla.timmeleshko.task4.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task4.R
import by.senla.timmeleshko.task4.adapters.RecyclerViewAdapter
import by.senla.timmeleshko.task4.model.beans.DataWrapper
import by.senla.timmeleshko.task4.model.interfaces.DataListContract
import by.senla.timmeleshko.task4.model.network.DataListPresenter

class MainActivity : AppCompatActivity(), DataListContract.View {

    private lateinit var recyclerView: RecyclerView
    private val adapter = RecyclerViewAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.worksList)
        initListView()

        DataListPresenter(this).requestDataFromServer()
    }

    private fun initListView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    override fun setDataToRecyclerView(dataWrapper: DataWrapper) {
        dataWrapper.data?.works?.let { adapter.updateData(it) }
    }

    override fun onResponseFailure(throwable: Throwable?) {
        throwable?.printStackTrace()
    }
}