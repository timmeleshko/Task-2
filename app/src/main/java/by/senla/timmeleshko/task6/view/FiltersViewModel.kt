package by.senla.timmeleshko.task6.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.senla.timmeleshko.task6.model.ServiceLocator
import by.senla.timmeleshko.task6.model.dto.Data
import by.senla.timmeleshko.task6.model.dto.DataWrapper
import by.senla.timmeleshko.task6.model.dto.FilterDto
import by.senla.timmeleshko.task6.model.dto.GeneralData
import by.senla.timmeleshko.task6.view.FiltersViewModel.FiltersViewModelConstants.FILTER_SALEST
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class FiltersViewModel(
    private val serviceLocator: ServiceLocator
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

    // не нужно в одном проекте использовать разные подходы для асинхронной обработки данных
    // если применяешь корутины то используй только корутины, если RX то только RX
    private fun loadData() {
        serviceLocator.getDataApi().getDataForFilter()
            .subscribeOn(serviceLocator.getScheduler())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<DataWrapper?> {

                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {}

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onNext(t: DataWrapper) {
                    data.value = t.data?.let { loadFilters(it) }
                }
            })
    }
}