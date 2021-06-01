package by.senla.timmeleshko.task6.model.interfaces

import by.senla.timmeleshko.task6.model.data.dto.DataWrapperDto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("works.search?extends=works.alt_media_ids,works.media_id,works.counters,works.properties,works.collection_id,works.infos,works.description,filters.uri,works.aset_ids,works.artist_ids&count=30&artist_id=739&order=default&")
    fun getData(
        @Query("offset") offset: Int = 0,
        @Query("count") count: Int = 25
    ): Observable<ListingResponse>

    class ListingResponse(val data: DataWrapperDto)
}