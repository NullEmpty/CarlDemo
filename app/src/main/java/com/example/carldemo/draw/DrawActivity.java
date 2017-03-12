package com.example.carldemo.draw;

import android.app.Activity;
import android.os.Bundle;

import com.example.carldemo.R;

public class DrawActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_draw);
        WaveView mWaveView = new WaveView(this);
        setContentView(mWaveView);
    }
}
