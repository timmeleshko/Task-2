package by.senla.timmeleshko.task4.model.interfaces

import by.senla.timmeleshko.task4.model.beans.DataWrapper

interface DataListContract {

    interface Model {
        interface OnFinishedListener {
            fun onFinished(dataWrapper: DataWrapper?)
            fun onFailure(t: Throwable?)
        }
        fun getDataList(onFinishedListener: OnFinishedListener?)
    }

    interface View {
        fun setDataToRecyclerView(dataWrapper: DataWrapper)
        fun onResponseFailure(throwable: Throwable?)
    }

    interface Presenter {
        fun onDestroy()
        fun requestDataFromServer()
    }
}