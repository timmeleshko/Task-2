package by.senla.timmeleshko.task3.model.network

import by.senla.timmeleshko.task3.model.beans.DataWrapper
import by.senla.timmeleshko.task3.model.beans.Work
import by.senla.timmeleshko.task3.model.common.Common
import by.senla.timmeleshko.task3.model.interfaces.RetrofitServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class DataLoader(
    private var service: RetrofitServices = Common.retrofitService
) {

    private var worksList: ArrayList<Work> = ArrayList()

    abstract fun onDataLoaded(result: ArrayList<Work>)

    fun loadDataToList() {
        service.getWorksList().enqueue(object : Callback<DataWrapper> {

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