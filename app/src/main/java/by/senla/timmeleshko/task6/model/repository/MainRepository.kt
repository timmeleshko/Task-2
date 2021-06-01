package by.senla.timmeleshko.task6.model.repository

import android.content.Context
import android.support.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.room.Room
import by.senla.timmeleshko.task6.model.data.Listing
import by.senla.timmeleshko.task6.model.data.dto.MediaDto
import by.senla.timmeleshko.task6.model.data.dto.WorkDto
import by.senla.timmeleshko.task6.model.db.WorkDb
import by.senla.timmeleshko.task6.model.enums.State
import by.senla.timmeleshko.task6.model.interfaces.RetrofitService
import by.senla.timmeleshko.task6.model.repository.WorkBoundaryCallback.WorkBoundaryCallbackConstants.RECYCLER_VIEW_PAGE_SIZE
import by.senla.timmeleshko.task6.model.retrofit.RetrofitClient
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainRepository(
    private val context: Context,
    private val retrofitService: RetrofitService = RetrofitClient.retrofitService,
    private val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    private val ioExecutor: ExecutorService = Executors.newSingleThreadExecutor(),
    private val state: MutableLiveData<State> = MutableLiveData(),
    private val db: WorkDb = Room.databaseBuilder(context, WorkDb::class.java, "work-database").build()
) : WorkRepository {

    private fun checkMediaId(workDto: WorkDto, mediaList: List<MediaDto>) : WorkDto {
        for (media in mediaList) {
            if (media.media_id == workDto.media_id) {
                workDto.media_dto = media
            }
        }
        return workDto
    }

    private fun insertResultIntoDb(id: Int, body: RetrofitService.ListingResponse?) {
        body!!.data.let { works ->
            db.runInTransaction {
                val start = db.works().getNextIndex(id)
                val items = works.data?.works?.mapIndexed { index, workDto ->
                    works.data.media?.let { checkMediaId(workDto, it) }
                    workDto.indexInResponse = start + index
                    workDto
                }
                if (items != null) {
                    db.works().insert(items)
                }
            }
        }
    }

    @MainThread
    private fun refresh(id: Int): LiveData<State> {
        updateState(State.LOADING)
        compositeDisposable.add(retrofitService
            .getData(id + 1, RECYCLER_VIEW_PAGE_SIZE)
            .subscribe(
                { response ->
                    ioExecutor.execute {
                        db.runInTransaction {
                            db.works().deleteById(id)
                            insertResultIntoDb(id, response)
                        }
                        updateState(State.DONE)
                    }
                }
            ) {
                updateState(State.ERROR)
            }
        )
        return state
    }

    override fun getWorks(id: Int, pageSize: Int): Listing<WorkDto> {
        val boundaryCallback = WorkBoundaryCallback(
            ioExecutor = ioExecutor,
            id = id,
            handleResponse = this::insertResultIntoDb
        )
        val refreshTrigger = MutableLiveData<Unit?>()
        val refreshState = Transformations.switchMap(refreshTrigger) {
            refresh(id)
        }
        val livePagedList = LivePagedListBuilder(db.works().getAll(), pageSize)
            .setBoundaryCallback(boundaryCallback)
            .build()
        return Listing(livePagedList, state, refreshState,
            retry = {
                boundaryCallback.helper.retryAllFailed()
            },
            refresh = {
                refreshTrigger.value = null
            }
        )
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }
}