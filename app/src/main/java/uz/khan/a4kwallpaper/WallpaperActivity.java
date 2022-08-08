package uz.khan.a4kwallpaper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;
import java.util.Objects;

public class WallpaperActivity extends AppCompatActivity {

    ImageView wallpaperIV;
    Button setWallpaper;
    String imgUrl;

    WallpaperManager wallpaperManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wallpaper);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//
//        }
        wallpaperIV = findViewById(R.id.idIVWalpapers);
        setWallpaper = findViewById(R.id.idBtnSetWallpaper);

        imgUrl = getIntent().getStringExtra("imgUrl");
        Glide.with(this).load(imgUrl).into(wallpaperIV);

        wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Glide.with(WallpaperActivity.this).asBitmap().load(imgUrl).listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Toast.makeText(WallpaperActivity.this, "Fail to load wallpapers...", Toast.LENGTH_SHORT).show();

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        try {
                            wallpaperManager.setBitmap(resource);

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(WallpaperActivity.this, "Fail to set wallpapers...", Toast.LENGTH_SHORT).show();

                        }
                        return false;
                    }
                }).submit();
                FancyToast.makeText(WallpaperActivity.this, "Wallpaper Set to home Screen", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

            }
        });


    }
}