package by.senla.timmeleshko.task5.model

import by.senla.timmeleshko.task5.model.beans.DataWrapper
import by.senla.timmeleshko.task5.model.interfaces.DataListContract
import by.senla.timmeleshko.task5.model.interfaces.DataListContract.Model.OnFinishedListener
import by.senla.timmeleshko.task5.model.interfaces.RetrofitServices
import by.senla.timmeleshko.task5.model.retrofit.RetrofitClient
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DataListModel(
    private val retrofitServices: RetrofitServices = RetrofitClient.retrofitService
) : DataListContract.Model {

    override fun getDataList(onFinishedListener: OnFinishedListener?) {
        retrofitServices.getWorksList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<DataWrapper?> {

                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {}

                override fun onError(e: Throwable) {
                    onFinishedListener?.onFailure(e)
                }

                override fun onNext(t: DataWrapper) {
                    onFinishedListener?.onFinished(t)
                }
            })
    }
}