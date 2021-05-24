package by.senla.timmeleshko.task6.model.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.senla.timmeleshko.task6.model.beans.DataWrapper
import by.senla.timmeleshko.task6.model.interfaces.RetrofitServices
import by.senla.timmeleshko.task6.model.retrofit.RetrofitClient
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DataViewModel(
    private val retrofitServices: RetrofitServices = RetrofitClient.retrofitService
) : ViewModel() {

    private val dataWrapper: MutableLiveData<DataWrapper> by lazy {
        MutableLiveData<DataWrapper>().also {
            loadData()
        }
    }

    fun getDataWrapper(): LiveData<DataWrapper> {
        return dataWrapper
    }

    private fun loadData() {
        retrofitServices.getWorksList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<DataWrapper?> {

                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {}

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onNext(t: DataWrapper) {
                    dataWrapper.value = t
                }
            })
    }
}