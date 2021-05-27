package by.senla.timmeleshko.task6.model.network

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import by.senla.timmeleshko.task6.model.beans.WorkDto
import by.senla.timmeleshko.task6.model.interfaces.RetrofitService
import by.senla.timmeleshko.task6.model.retrofit.RetrofitClient
import io.reactivex.disposables.CompositeDisposable

class WorksDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val retrofitService: RetrofitService = RetrofitClient.retrofitService,
    val worksDataSourceLiveData: MutableLiveData<WorksDataSource> = MutableLiveData<WorksDataSource>()
) : DataSource.Factory<Int, WorkDto>() {

    override fun create(): DataSource<Int, WorkDto> {
        val newsDataSource = WorksDataSource(retrofitService, compositeDisposable)
        worksDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}