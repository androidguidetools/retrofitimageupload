package com.retrofitdemo.retrofitlearnigtopics.rest;

import com.retrofitdemo.retrofitlearnigtopics.model.ImageUploadModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by KP on 4/11/2018.
 */

public interface ApiInterface3 {

    @FormUrlEncoded
    @POST("imageupload.php")
    Call<ImageUploadModel> uploadImage(@Field("title")String title,@Field("image")String image);
}
