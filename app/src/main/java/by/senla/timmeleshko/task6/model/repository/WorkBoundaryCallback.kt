package by.senla.timmeleshko.task6.model.repository

import androidx.annotation.MainThread
import androidx.paging.PagedList
import by.senla.timmeleshko.task6.model.data.dto.WorkDto
import by.senla.timmeleshko.task6.model.helpers.PagingRequestHelper
import by.senla.timmeleshko.task6.model.interfaces.RetrofitService
import by.senla.timmeleshko.task6.model.repository.WorkBoundaryCallback.WorkBoundaryCallbackConstants.RECYCLER_VIEW_PAGE_SIZE
import by.senla.timmeleshko.task6.model.retrofit.RetrofitClient
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

class WorkBoundaryCallback(
    private val ioExecutor: Executor,
    val helper: PagingRequestHelper = PagingRequestHelper(ioExecutor),
    private val retrofitService: RetrofitService = RetrofitClient.retrofitService,
    private val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    private val handleResponse: (Int, RetrofitService.ListingResponse?) -> Unit,
    private val id: Int
) : PagedList.BoundaryCallback<WorkDto>() {

    object WorkBoundaryCallbackConstants {
        const val RECYCLER_VIEW_PAGE_SIZE = 10
    }

    private fun insertItemsIntoDb(listingResponse: RetrofitService.ListingResponse, it: PagingRequestHelper.Request.Callback) {
        ioExecutor.execute {
            handleResponse(id, listingResponse)
            it.recordSuccess()
        }
    }


    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { helper ->
            retrofitService.getData()
            compositeDisposable.add(
                retrofitService
                    .getData(1, RECYCLER_VIEW_PAGE_SIZE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({ createWebserviceCallback(helper) }) {}
            )
        }
    }

    @MainThread
    override fun onItemAtEndLoaded(workDto: WorkDto) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { helper ->
            compositeDisposable.add(
                retrofitService
                    .getData(workDto.id + 1, RECYCLER_VIEW_PAGE_SIZE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({ createWebserviceCallback(helper) }) {}
            )
        }
    }

    override fun onItemAtFrontLoaded(workDto: WorkDto) {}

    private fun createWebserviceCallback(it: PagingRequestHelper.Request.Callback) : Observer<RetrofitService.ListingResponse> {
        return object : Observer<RetrofitService.ListingResponse> {

                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {}

                override fun onError(e: Throwable) {
                    it.recordFailure(e)
                }

                override fun onNext(t: RetrofitService.ListingResponse) {
                    insertItemsIntoDb(t, it)
                }
            }
    }
}