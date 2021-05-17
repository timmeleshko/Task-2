package by.senla.timmeleshko.task3.model.common

import by.senla.timmeleshko.task3.model.interfaces.RetrofitServices
import by.senla.timmeleshko.task3.model.retrofit.RetrofitClient

object Common {
    private const val BASE_URL = "https://api.arthive.com/v2.0/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(
            RetrofitServices::class.java)
}