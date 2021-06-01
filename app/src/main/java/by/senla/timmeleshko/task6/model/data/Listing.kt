package by.senla.timmeleshko.task6.model.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import by.senla.timmeleshko.task6.model.enums.State

data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val state: LiveData<State>,
    val refreshState: LiveData<State>,
    val refresh: () -> Unit,
    val retry: () -> Unit
)
