package by.senla.timmeleshko.task6.view

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import by.senla.timmeleshko.task6.model.dto.WorkDto
import by.senla.timmeleshko.task6.model.repository.WorkRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class WorksViewModel(
        repository: WorkRepository,
        private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val RECYCLER_VIEW_PAGE_SIZE = 10
        const val KEY = "uri"
        const val DEFAULT_URI = ""
    }

    init {
        if (!savedStateHandle.contains(KEY)) {
            savedStateHandle.set(KEY, DEFAULT_URI)
        }
    }

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val works = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty() },
        savedStateHandle.getLiveData<String>(KEY)
            .asFlow()
            .flatMapLatest { repository.worksOfData(it, RECYCLER_VIEW_PAGE_SIZE) }
            .cachedIn(viewModelScope)
    ).flattenMerge(2)

    private fun shouldShowWork(uri: String) = savedStateHandle.get<String>(KEY) != uri

    fun showWork(uri: String) {
        if (!shouldShowWork(uri)) {
            return
        }
        clearListCh.offer(Unit)
        savedStateHandle.set(KEY, uri)
    }
}
