package by.senla.timmeleshko.task6.model.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import by.senla.timmeleshko.task6.model.interfaces.WorkRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class WorksViewModel(
    private val repository: WorkRepository
) : ViewModel() {

    companion object {
        const val RECYCLER_VIEW_PAGE_SIZE = 10
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val works = MutableStateFlow("0")
            .flatMapLatest { repository.worksOfData(it, RECYCLER_VIEW_PAGE_SIZE) }
            .cachedIn(viewModelScope)
}
