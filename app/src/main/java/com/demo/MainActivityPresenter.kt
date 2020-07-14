package com.demo

import android.util.Log
import com.demo.model.Comment
import com.demo.model.Photo
import com.demo.model.Post
import com.demo.retrofit.RetroClass
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers


class MainActivityPresenter(val view: MainActivityContract.View) : MainActivityContract.Presenter {

    val TAG = MainActivityPresenter::class.java.simpleName

    override fun callApiInPresenter() {
        DisposableManager.add(
            RetroClass.getApiService().allBrewaryList
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { brewaryList -> Log.i(TAG, "from api----->\n"+brewaryList.toString())
                        RxJavaRetrofitRoomSampleApplication.database?.let {
                            it.getBrewaryDao().deleteAll()
                            it.getBrewaryDao().insertAll(brewaryList)
                        }
                    },
                    { error -> Log.e(TAG, error.message) }
                )
        )
    }

    fun callThreeApisParallely(){
        val errorPhotoList : MutableList<Photo> = ArrayList()
        errorPhotoList.add(Photo(1,2,"a","b","c"))
        DisposableManager.add(
            Single.zip(RetroClass.getNormalApiService().allPhotos
                , RetroClass.getNormalApiService().firstPost,
                RetroClass.getNormalApiService().commentsForFirstPost,
                Function3<List<Photo>, Post, List<Comment>, List<String>> { type1, type2, type3 ->
                    val list : MutableList<String> = ArrayList()
                    list.add(type1[0].toString())
                    list.add(type2.toString())
                    list.add(type3[0].toString())
                    list
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {result ->
                        view.toggleProgressBar(false)
                        Log.i(TAG, "result->$result")},
                    {error ->
                        view.toggleProgressBar(false)
                        Log.i(TAG,"error->$error.message)")}
                )
        )
    }

    fun callThreeApisSequentially(){

        var list : MutableList<String> = ArrayList()

        DisposableManager.add(
            RetroClass.getNormalApiService().allPhotos
                .subscribeOn(Schedulers.io())
                .flatMap {photos ->
                    list.add(photos.toString())
                    RetroClass.getNormalApiService().firstPost }
                .flatMap {post ->
                    list.add(post.toString())
                    RetroClass.getNormalApiService().commentsForFirstPost }
                .map {response->
                    response}
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {result ->
                        list.add(result.toString())
                        view.toggleProgressBar(false)
                        //Log.i(TAG, "result->$result")
                        Log.i(TAG, "list->$list")
                    },
                    {error ->
                        view.toggleProgressBar(false)
                        Log.i(TAG,"error->$error.message)")}
                )
        )


    }

    override fun getDataFromDatabase(): Flowable<List<BrewaryModel>>? {
        return RxJavaRetrofitRoomSampleApplication.database?.let {
            it.getBrewaryDao().getAllBrewaries()
        }
    }

    override fun onActivityDestroy() {
        DisposableManager.dispose()
    }



}