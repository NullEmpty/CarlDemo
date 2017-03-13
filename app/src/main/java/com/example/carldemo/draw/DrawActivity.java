package com.example.carldemo.draw;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import com.example.carldemo.R;

public class DrawActivity extends Activity {

    private WaveView mWaveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_draw);
        mWaveView = new WaveView(this);
        setContentView(mWaveView);

        mWaveView.setOnTouchListener(mTouchListener);
        mWaveView.start();
    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float x=event.getX();
            float y = event.getY();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (mWaveView != null) {
                    mWaveView.progress(y > 1000 ? 50 : -50);
                }
            }
            return false;
        }
    };
}
