package by.senla.timmeleshko.task3.model.retrofit

import android.util.Log
import by.senla.timmeleshko.task3.model.Constants.URL
import by.senla.timmeleshko.task3.model.interfaces.RetrofitServices
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var retrofit: Retrofit? = null

    private const val BASE_URL = "https://api.arthive.com/v2.0/"
    val retrofitService: RetrofitServices
    get() = getClient().create(RetrofitServices::class.java)

    private fun getClient(): Retrofit {
        Log.i(URL, BASE_URL)
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}