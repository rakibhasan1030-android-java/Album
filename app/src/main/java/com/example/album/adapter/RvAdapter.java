package com.example.album.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.album.R;
import com.example.album.model.Album;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {
    public static final String TAG = RvAdapter.class.getSimpleName();


    private final List<Album> albumList;
    private final OnAlbumClickListener onAlbumClickListener;
    private final Context context;

    public RvAdapter(Context context, List<Album> albumList, OnAlbumClickListener onAlbumClickListener) {
        this.albumList = albumList;
        this.onAlbumClickListener = onAlbumClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public RvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_row_item,
                parent, false);
        return new MyViewHolder(view, onAlbumClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.MyViewHolder holder, int position) {

        String url = albumList.get(position).getThumbnailUrl();
        ((MyViewHolder) holder).title.setText(albumList.get(position).getTitle());
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.no_image)
                //  .error(R.drawable.user_placeholder_error)
                .into(holder.thumb);

    }

    @Override
    public int getItemCount() {
        Log.v(TAG, String.valueOf(albumList.size()));
        return albumList.size();
    }

    public interface OnAlbumClickListener {
        void onAlbumClick(int position, View view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        OnAlbumClickListener onAlbumClickListener;

        CircleImageView thumb;
        TextView title;

        public MyViewHolder(@NonNull View itemView, OnAlbumClickListener onAlbumClickListener) {
            super(itemView);
            this.onAlbumClickListener = onAlbumClickListener;
            thumb = itemView.findViewById(R.id.rv_row_iv);
            title = itemView.findViewById(R.id.rv_row_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAlbumClickListener.onAlbumClick(getAdapterPosition(), view);
                }
            });

        }
    }

}
