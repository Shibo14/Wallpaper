package uz.khan.a4kwallpaper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.ViewHolder> {

    ArrayList<String>wallpaperArrayList;
    Context context;

    public WallpaperAdapter(ArrayList<String> wallpaperArrayList, Context context) {
        this.wallpaperArrayList = wallpaperArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_item,parent,false);

        return new  WallpaperAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(wallpaperArrayList.get(position)).into(holder.wallpaperIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,WallpaperActivity.class);
                i.putExtra("imgUrl",wallpaperArrayList.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallpaperArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView wallpaperIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wallpaperIV = itemView.findViewById(R.id.idImVWallpaper);

        }
    }
}
