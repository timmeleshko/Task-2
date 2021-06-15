package by.senla.timmeleshko.task6.model.api

import android.util.Log
import by.senla.timmeleshko.task6.model.Constants
import by.senla.timmeleshko.task6.model.dto.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface DataApi {
    @GET("works.search?count_filters=20&extends=works.media_id,works.counters,works.properties,works.collection_id,works.infos,works.description,filters.uri,works.aset_ids,works.artist_ids,works.media_id&with_filters=y&filter_prefixes=salest,genre,style,tech&artist_id=65")
    suspend fun getData(
        @Query("offset") offset: String? = null,
        @Query("count") count: Int = 25,
        @Query("uris") uris: String
    ): ListingResponse

    class ListingResponse(val data: ListingData)

    class ListingData(
            val works: List<WorkDto>,
            val media: List<MediaDto>?,
            val filters: List<FilterDto>?,
            val techniques: List<TechniqueDto>?,
            val styles: List<StyleDto>?,
            val genres: List<GenreDto>?,
            val types: List<TypeDto>?,
            val materials: List<MaterialDto>?
    )

    companion object {
        private const val BASE_URL = "https://api.arthive.com/v2.0/"
        fun create(): DataApi {
            Log.i(Constants.INFO, BASE_URL)
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DataApi::class.java)
        }
    }
}