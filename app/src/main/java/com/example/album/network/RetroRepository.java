package com.example.album.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.album.model.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetroRepository {
    public static final String TAG = RetroRepository.class.getSimpleName();

    private RetroServiceInterface retroServiceInterface;

    public RetroRepository(RetroServiceInterface retroServiceInterface) {
        this.retroServiceInterface = retroServiceInterface;
    }

    public void makeApiCall(int albumId, MutableLiveData<List<Album>> liveData){

        Call<List<Album>> call = retroServiceInterface.getDataResponseFromAPI(albumId);
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {

                Log.v(TAG, String.valueOf(response));

                if (response.isSuccessful()){
                    assert response.body() != null;
                    liveData.postValue(response.body());
                    for (Album album : response.body()){
                        Log.v(TAG, String.valueOf(album.getTitle()));
                    }

                }else {
                    liveData.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                //Log.v(TAG, "onFailure");
            }
        });
    }

}
