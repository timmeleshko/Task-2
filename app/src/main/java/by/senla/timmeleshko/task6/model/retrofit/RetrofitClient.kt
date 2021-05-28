package by.senla.timmeleshko.task6.model.retrofit

import android.util.Log
import by.senla.timmeleshko.task6.model.Constants.INFO
import by.senla.timmeleshko.task6.model.interfaces.RetrofitService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://api.arthive.com/v2.0/"
    val retrofitService: RetrofitService
        get() = getClient().create(RetrofitService::class.java)

    private fun getClient(): Retrofit {
        Log.i(INFO, BASE_URL)
        if (retrofit == null) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}