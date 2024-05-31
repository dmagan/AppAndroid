package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.ortiz.touchview.TouchImageView;

public class FullscreenImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_image);

        TouchImageView touchImageView = findViewById(R.id.touchImageView);
        String imagePath = getIntent().getStringExtra("image_path");
        if (imagePath != null) {
            Bitmap bmp = BitmapFactory.decodeFile(imagePath);
            touchImageView.setImageBitmap(bmp);
        }
    }
}
