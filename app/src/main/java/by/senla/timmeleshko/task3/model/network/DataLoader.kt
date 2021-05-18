package by.senla.timmeleshko.task3.model.network

import by.senla.timmeleshko.task3.model.beans.DataWrapper
import by.senla.timmeleshko.task3.model.beans.Work
import by.senla.timmeleshko.task3.model.interfaces.RetrofitServices
import by.senla.timmeleshko.task3.model.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class DataLoader {

    private var worksList: ArrayList<Work> = ArrayList()

    abstract fun onDataLoaded(result: List<Work>)

    fun loadDataToList() {
        RetrofitClient.retrofitService.getWorksList().enqueue(object : Callback<DataWrapper> {

            override fun onFailure(call: Call<DataWrapper>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<DataWrapper>, response: Response<DataWrapper>) {
                worksList.clear()
                (response.body() as DataWrapper).data?.works?.let { worksList.addAll(it) }
                onDataLoaded(worksList)
            }
        })
    }
}