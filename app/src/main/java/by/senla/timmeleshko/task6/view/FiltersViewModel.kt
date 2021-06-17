package by.senla.timmeleshko.task6.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.senla.timmeleshko.task6.model.api.DataApi
import by.senla.timmeleshko.task6.model.dto.*
import by.senla.timmeleshko.task6.view.FiltersViewModel.FiltersViewModelConstants.FILTER_SALEST
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FiltersViewModel(
    private val api: DataApi = DataApi.create()
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

    private fun loadFilters(data: Data) : Data {
        data.techniques?.forEach { a ->
            data.filters?.stream()
                ?.filter { b -> b.uri == a.uri }
                ?.forEach { c -> c.name = a.name }
        }
        data.styles?.forEach { a ->
            data.filters?.stream()
                ?.filter { b -> b.uri == a.uri }
                ?.forEach { c -> c.name = a.name }
        }
        data.genres?.forEach { a ->
            data.filters?.stream()
                ?.filter { b -> b.uri == a.uri }
                ?.forEach { c -> c.name = a.name }
        }
        data.types?.forEach { a ->
            data.filters?.stream()
                ?.filter { b -> b.uri == a.uri }
                ?.forEach { c -> c.name = a.name }
        }
        data.materials?.forEach { a ->
            data.filters?.stream()
                ?.filter { b -> b.uri == a.uri }
                ?.forEach { c -> c.name = a.name }
        }
        data.filters = data.filters?.filter { a -> a.uri != null && !a.uri.contains(FILTER_SALEST) }
        return data
    }

    private fun loadData() {
        api.getDataForFilter()
            .subscribeOn(Schedulers.io())
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