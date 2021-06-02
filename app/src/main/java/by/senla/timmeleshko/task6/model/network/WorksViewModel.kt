package by.senla.timmeleshko.task6.model.network

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import by.senla.timmeleshko.task6.model.beans.WorkDto
import by.senla.timmeleshko.task6.model.repository.WorkRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class WorksViewModel(
    private val repository: WorkRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val KEY_WORK_ID = "work_id"
        const val DEFAULT_WORK_ID = "4405"
        const val RECYCLER_VIEW_PAGE_SIZE = 10
    }

    init {
        if (!savedStateHandle.contains(KEY_WORK_ID)) {
            savedStateHandle.set(KEY_WORK_ID, DEFAULT_WORK_ID)
        }
    }

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val works = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty() },
        savedStateHandle.getLiveData<String>(KEY_WORK_ID)
            .asFlow()
            .flatMapLatest { repository.worksOfData(it, RECYCLER_VIEW_PAGE_SIZE) }
            .cachedIn(viewModelScope)
    ).flattenMerge(2)

    fun shouldShowWorkId(work_id: String) = savedStateHandle.get<String>(KEY_WORK_ID) != work_id

    fun showWork(work_id: String) {
        if (!shouldShowWorkId(work_id)) return
        clearListCh.offer(Unit)
        savedStateHandle.set(KEY_WORK_ID, work_id)
    }
}
