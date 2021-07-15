package by.senla.timmeleshko.task6.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.senla.timmeleshko.task6.model.api.DataApi
import by.senla.timmeleshko.task6.model.dto.Data
import by.senla.timmeleshko.task6.model.dto.FilterDto
import by.senla.timmeleshko.task6.model.dto.GeneralData
import by.senla.timmeleshko.task6.view.FiltersViewModel.FiltersViewModelConstants.FILTER_SALEST
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FiltersViewModel(
    private val dataApi: DataApi
) : ViewModel() {

    object FiltersViewModelConstants {
        const val FILTER_SALEST = "salest"
    }

    private val data: MutableLiveData<Data> by lazy {
        MutableLiveData<Data>().also {
            loadData()
        }
    }

    fun getData(): LiveData<Data> {
        return data
    }

    private fun iterateFilters(list: List<GeneralData>?, filtersList: List<FilterDto>?) {
        list?.forEach { a ->
            filtersList?.stream()
                ?.filter { b -> b.uri == a.uri }
                ?.forEach { c -> c.name = a.name }
        }
    }

    private fun loadFilters(data: Data) : Data {
        iterateFilters(data.techniques, data.filters)
        iterateFilters(data.styles, data.filters)
        iterateFilters(data.genres, data.filters)
        iterateFilters(data.types, data.filters)
        iterateFilters(data.materials, data.filters)
        data.filters = data.filters?.filter { a -> a.uri != null && !a.uri.contains(FILTER_SALEST) }
        return data
    }

    private fun loadData() {
        try {
            viewModelScope.launch {
                val asyncData = getDataFromServerAsync()
                data.value = asyncData.data?.let { loadFilters(it) }
            }
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }
    }

    private suspend fun getDataFromServerAsync() = withContext(Dispatchers.IO) {
        dataApi.getDataForFilterAsync()
    }
}