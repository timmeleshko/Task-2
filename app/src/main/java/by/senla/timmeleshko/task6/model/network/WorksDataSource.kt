package by.senla.timmeleshko.task6.model.network

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import by.senla.timmeleshko.task6.model.Constants.RECYCLER_VIEW_PAGE_SIZE
import by.senla.timmeleshko.task6.model.beans.MediaDto
import by.senla.timmeleshko.task6.model.beans.WorkDto
import by.senla.timmeleshko.task6.model.enums.State
import by.senla.timmeleshko.task6.model.interfaces.RetrofitService
import by.senla.timmeleshko.task6.model.retrofit.RetrofitClient
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class WorksDataSource(
    private val retrofitService: RetrofitService = RetrofitClient.retrofitService,
    private val compositeDisposable: CompositeDisposable,
    private var retryCompletable: Completable? = null,
    var state: MutableLiveData<State> = MutableLiveData()
) : PageKeyedDataSource<Int, WorkDto>() {

    private fun checkMediaId(worksList: List<WorkDto>, mediaList: List<MediaDto>) : List<WorkDto> {
        for (w in worksList) {
            for (m in mediaList) {
                if (m.media_id == w.media_id) {
                    w.media_dto = m
                }
            }
        }
        return worksList
    }

    override fun loadInitial(params: LoadInitialParams<Int>,
                             callback: LoadInitialCallback<Int, WorkDto>) {
        updateState(State.LOADING)
        compositeDisposable.add(retrofitService
            .getData(1, params.requestedLoadSize)
            .subscribe(
                { response ->
                    updateState(State.DONE)
                    checkMediaId(response.data?.works!!, response.data.media!!).let {
                        callback.onResult(it, null, RECYCLER_VIEW_PAGE_SIZE * 2)
                    }
                }
            ) {
                updateState(State.ERROR)
                setRetry { loadInitial(params, callback) }
            }
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, WorkDto>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            retrofitService
                .getData(params.key, params.requestedLoadSize)
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        checkMediaId(response.data?.works!!, response.data.media!!).let {
                            callback.onResult(it, params.key + RECYCLER_VIEW_PAGE_SIZE)
                        }
                    }
                ) {
                    updateState(State.ERROR)
                    setRetry { loadAfter(params, callback) }
                }
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, WorkDto>) {}

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}