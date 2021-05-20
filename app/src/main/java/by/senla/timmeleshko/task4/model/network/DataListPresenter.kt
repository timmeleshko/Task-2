package by.senla.timmeleshko.task4.model.network

import by.senla.timmeleshko.task4.model.DataListModel
import by.senla.timmeleshko.task4.model.beans.DataWrapper
import by.senla.timmeleshko.task4.model.interfaces.DataListContract

class DataListPresenter(
        private var dataListView: DataListContract.View?,
        private val dataListModel: DataListContract.Model? = DataListModel()
    ) : DataListContract.Presenter, DataListContract.Model.OnFinishedListener {

    override fun onDestroy() {
        dataListView = null
    }

    override fun requestDataFromServer() {
        dataListModel?.getDataList(this)
    }

    override fun onFinished(dataWrapper: DataWrapper?) {
        if (dataWrapper != null) {
            dataListView?.setDataToRecyclerView(dataWrapper)
        }
    }

    override fun onFailure(t: Throwable?) {
        dataListView?.onResponseFailure(t)
    }
}