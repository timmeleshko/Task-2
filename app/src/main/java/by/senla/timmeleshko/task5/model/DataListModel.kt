package by.senla.timmeleshko.task5.model

import by.senla.timmeleshko.task5.model.beans.DataWrapper
import by.senla.timmeleshko.task5.model.interfaces.DataListContract
import by.senla.timmeleshko.task5.model.interfaces.DataListContract.Model.OnFinishedListener
import by.senla.timmeleshko.task5.model.interfaces.RetrofitServices
import by.senla.timmeleshko.task5.model.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataListModel(
    private val retrofitServices: RetrofitServices = RetrofitClient.retrofitService
) : DataListContract.Model {

    override fun getDataList(onFinishedListener: OnFinishedListener?) {
        retrofitServices.getWorksList().enqueue(object : Callback<DataWrapper?> {
            override fun onResponse(call: Call<DataWrapper?>?, response: Response<DataWrapper?>) {
                val data: DataWrapper? = response.body()
                onFinishedListener?.onFinished(data)
            }

            override fun onFailure(call: Call<DataWrapper?>?, t: Throwable) {
                onFinishedListener?.onFailure(t)
            }
        })
    }
}