package by.senla.timmeleshko.task6.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.senla.timmeleshko.task6.model.ServiceLocator

class FilterViewModelFactory(
    private val serviceLocator: ServiceLocator
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return FiltersViewModel(serviceLocator) as T
    }
}