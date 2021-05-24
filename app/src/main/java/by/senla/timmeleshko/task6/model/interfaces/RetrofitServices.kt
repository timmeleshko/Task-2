package by.senla.timmeleshko.task6.model.interfaces

import by.senla.timmeleshko.task6.model.beans.DataWrapper
import io.reactivex.Observable
import retrofit2.http.GET

interface RetrofitServices {
    @GET("works.search?offset=0&extends=works.alt_media_ids,works.media_id,works.counters,works.properties,works.collection_id,works.infos,works.description,filters.uri,works.aset_ids,works.artist_ids&count=30&artist_id=739&order=default&")
    fun getWorksList(): Observable<DataWrapper>
}