package com.demo.retrofit;

import com.demo.BrewaryModel;
import com.demo.model.Comment;
import com.demo.model.Photo;
import com.demo.model.Post;

import io.reactivex.Single;
import retrofit2.http.GET;

import java.util.List;

public interface ApiClient {

    @GET("/breweries")
    Single<List<BrewaryModel>> getAllBrewaryList();

    @GET("/photo")
    Single<List<Photo>> getAllPhotos();

    @GET("/posts/1")
    Single<Post> getFirstPost();

    @GET("/posts/1/comments")
    Single<List<Comment>> getCommentsForFirstPost();

}



//https://api.openbrewerydb.org/breweries