package by.senla.timmeleshko.task6.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import by.senla.timmeleshko.task6.model.repository.WorkBoundaryCallback.WorkBoundaryCallbackConstants.RECYCLER_VIEW_PAGE_SIZE
import by.senla.timmeleshko.task6.model.repository.WorkRepository

class MainViewModel(
    private val repository: WorkRepository
) : ViewModel() {

    private val id = MutableLiveData<Int>()
    private val repoResult = Transformations.map(id) {
        repository.getWorks(it, RECYCLER_VIEW_PAGE_SIZE)
    }
    val works = Transformations.switchMap(repoResult) {
        it.pagedList
    }
    val state = Transformations.switchMap(repoResult) {
        it.state
    }
    val refreshState = Transformations.switchMap(repoResult) {
        it.refreshState
    }

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun retry() {
        val listing = repoResult.value
        listing?.retry?.invoke()
    }

    fun currentWork(): Int? = id.value
}