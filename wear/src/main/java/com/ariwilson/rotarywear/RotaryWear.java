package com.ariwilson.rotarywear;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class RotaryWear extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FrameLayout frame_layout = (FrameLayout) findViewById(R.id.frame_layout);
    }
}
