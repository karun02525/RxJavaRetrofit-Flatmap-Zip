package com.demo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityContract.View {


    var TAG = MainActivity::class.java.simpleName
    lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityPresenter = MainActivityPresenter(this)
        toggleProgressBar(true)
        //invokePresenterToCallApi()

        callMultipleApiParallalyFromPresenter()
        //callMultipleApiInSequenceFromPresenter()

        mainActivityPresenter.getDataFromDatabase()?.let {
            mainActivityPresenter.getDataFromDatabase()!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {brewaryList -> Log.i(TAG,"from database----->\n"+brewaryList.toString())},
                    {error -> Log.e(TAG,error.message)}
                )
        }

    }

    private fun callMultipleApiInSequenceFromPresenter() {
        mainActivityPresenter.callThreeApisSequentially()
    }

    override fun toggleProgressBar(enable : Boolean){
        if(enable)
            progress_bar.visibility = View.VISIBLE
        else
            progress_bar.visibility = View.GONE

    }

    private fun callMultipleApiParallalyFromPresenter() {
        mainActivityPresenter.callThreeApisParallely()
    }

    override fun invokePresenterToCallApi() {
        mainActivityPresenter.callApiInPresenter()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivityPresenter.onActivityDestroy()
    }
}
