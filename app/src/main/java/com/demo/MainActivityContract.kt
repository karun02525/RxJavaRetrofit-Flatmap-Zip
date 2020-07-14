package com.demo

import io.reactivex.Flowable

interface MainActivityContract {

    interface View{
        fun invokePresenterToCallApi()
        fun toggleProgressBar(enable : Boolean)
    }

    interface Presenter{
        fun callApiInPresenter()
        fun getDataFromDatabase() : Flowable<List<BrewaryModel>>?
        fun onActivityDestroy()
    }
}