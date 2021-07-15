package by.senla.timmeleshko.task6.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.senla.timmeleshko.task6.model.api.DataApi

class FilterViewModelFactory(
    private val dataApi: DataApi
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return FiltersViewModel(dataApi) as T
    }
}