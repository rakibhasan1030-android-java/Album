package com.example.album.network;

import com.example.album.model.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetroServiceInterface {

    //https://jsonplaceholder.typicode.com/photos?albumId=1
    @GET("/photos")
    Call<List<Album>> getDataResponseFromAPI(@Query("albumId") int albumId);

}
