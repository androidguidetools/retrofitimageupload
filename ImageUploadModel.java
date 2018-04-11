package com.retrofitdemo.retrofitlearnigtopics.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by KP on 4/11/2018.
 */

public class ImageUploadModel {

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String image;

    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }
}
