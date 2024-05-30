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
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        if (byteArray != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            touchImageView.setImageBitmap(bmp);
        }
    }
}
