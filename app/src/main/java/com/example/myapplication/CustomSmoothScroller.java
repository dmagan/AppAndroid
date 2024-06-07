package com.example.myapplication;

import android.content.Context;
import android.util.DisplayMetrics;
import androidx.recyclerview.widget.LinearSmoothScroller;

public class CustomSmoothScroller extends LinearSmoothScroller {

    public CustomSmoothScroller(Context context) {
        super(context);
    }

    @Override
    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return 0.3f; // تنظیم سرعت اسکرول. مقدار کمتر به معنی سرعت بیشتر است.
    }
}
