package com.example.album.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.album.R;
import com.example.album.adapter.RvAdapter;
import com.example.album.model.Album;
import com.example.album.util.Constants;
import com.example.album.viewmodel.MainActivityViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements RvAdapter.OnAlbumClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    //views
    private TextView albumCounterTv;
    private AppCompatButton prevBtn, nextBtn;

    private RecyclerView mainRecyclerView;
    private RvAdapter rvAdapter;
    private int counter = 1;


    private List<Album> albumList;

    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViewModel(counter);
        albumCounterTv.setText(String.valueOf(counter));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find views
        mainRecyclerView = findViewById(R.id.mainRv);
        albumCounterTv = findViewById(R.id.main_album_counter_tv);
        prevBtn = findViewById(R.id.main_prev_btn);
        nextBtn = findViewById(R.id.main_next_btn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter < 101) {
                    counter++;
                    initViewModel(counter);
                    albumCounterTv.setText(String.valueOf(counter));
                } else {
                    Toast.makeText(MainActivity.this, "Sorry, No more album!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (counter > 0) {
                    counter--;
                    initViewModel(counter);
                    albumCounterTv.setText(String.valueOf(counter));
                } else {
                    Toast.makeText(MainActivity.this, "Sorry, No album Found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initRecyclerView(List<Album> albums) {
        rvAdapter = new RvAdapter(this, albums, this);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mainRecyclerView.setAdapter(rvAdapter);
    }


    private void initViewModel(int counter) {
        MainActivityViewModel viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.makeApiCall(counter);
        viewModel.getLiveData().observe(this, new Observer<List<Album>>() {
            @Override
            public void onChanged(List<Album> albums) {
                if (albums != null) {
                    initRecyclerView(albums);
                    setAlbumList(albums);
                    // rvAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Error getting data!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onAlbumClick(int position, View view) {
      //  Toast.makeText(this, "Position : " + position, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, DetailsActivity.class)
                .putParcelableArrayListExtra(Constants.CLICKED_LIST, (ArrayList<? extends Parcelable>) albumList)
                .putExtra(Constants.CLICKED_LIST_POSITION, position));

/*        Intent intent=new Intent(getApplicationContext(),DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.CLICKED_LIST_POSITION, position);
        bundle.putParcelableArrayList(Constants.CLICKED_LIST, albumList);
        intent.putExtras(bundle);
        this.startActivity(intent);*/

    }

}