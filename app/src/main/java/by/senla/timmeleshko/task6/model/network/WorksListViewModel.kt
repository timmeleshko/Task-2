package by.senla.timmeleshko.task6.model.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import by.senla.timmeleshko.task6.model.Constants.RECYCLER_VIEW_PAGE_SIZE
import by.senla.timmeleshko.task6.model.beans.WorkDto
import by.senla.timmeleshko.task6.model.enums.State
import by.senla.timmeleshko.task6.model.interfaces.RetrofitService
import by.senla.timmeleshko.task6.model.retrofit.RetrofitClient
import io.reactivex.disposables.CompositeDisposable

class WorksListViewModel(
    private val retrofitService: RetrofitService = RetrofitClient.retrofitService,
    private val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    private val worksDataSourceFactory: WorksDataSourceFactory = WorksDataSourceFactory(compositeDisposable, retrofitService),
    private val config: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(RECYCLER_VIEW_PAGE_SIZE)
        .setInitialLoadSizeHint(RECYCLER_VIEW_PAGE_SIZE * 2)
        .setEnablePlaceholders(false)
        .build(),
    var worksList: LiveData<PagedList<WorkDto>> = LivePagedListBuilder(worksDataSourceFactory, config).build()
) : ViewModel() {

    fun getState(): LiveData<State> = Transformations.switchMap(
        worksDataSourceFactory.worksDataSourceLiveData, WorksDataSource::state)

    fun retry() {
        worksDataSourceFactory.worksDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return worksList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}