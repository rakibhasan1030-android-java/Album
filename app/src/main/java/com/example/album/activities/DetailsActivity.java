package com.example.album.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.album.R;
import com.example.album.model.Album;
import com.example.album.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    public static final String TAG = DetailsActivity.class.getSimpleName();

    //viwes
    private TextView titleTv;
    private ImageView coverIv, profileIv;

    private int position;
    private List<Album> albumList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //bind viwes
        titleTv = findViewById(R.id.details_activity_title_tv);
        coverIv = findViewById(R.id.details_activity_cover_iv);
        profileIv = findViewById(R.id.details_activity_profile_iv);

        position = getIntent().getIntExtra(Constants.CLICKED_LIST_POSITION, 0);
        albumList = getIntent().getParcelableArrayListExtra(Constants.CLICKED_LIST);

        configureViews(position, albumList);
    }

    private void configureViews(int position, List<Album> albumList) {
        titleTv.setText(albumList.get(position).getTitle());
        String coverURL = albumList.get(position).getUrl();
        String thumbURL = albumList.get(position).getThumbnailUrl();
        Picasso.get().load(thumbURL).placeholder(R.drawable.no_image).into(profileIv);
        Picasso.get().load(coverURL).placeholder(R.drawable.no_image).into(coverIv);
    }

}