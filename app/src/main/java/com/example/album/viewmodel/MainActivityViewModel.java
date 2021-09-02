package com.example.album.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.album.model.Album;
import com.example.album.network.RetroRepository;
import com.example.album.network.RetroServiceInterface;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainActivityViewModel extends ViewModel {
    public static final String TAG = MainActivityViewModel.class.getSimpleName();



    MutableLiveData<List<Album>> liveData;

    @Inject
    RetroServiceInterface retroServiceInterface;

    @Inject
    public MainActivityViewModel() {
        liveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Album>> getLiveData(){
        return liveData;
    }

    public void makeApiCall(int albumId){

        RetroRepository retroRepository = new RetroRepository(retroServiceInterface);
        retroRepository.makeApiCall(albumId, liveData);
    }

}
