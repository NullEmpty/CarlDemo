package com.example.carldemo.gallery3d;

import android.os.Bundle;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.carldemo.R;

public class Layout3dActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyLayout mLayout = new MyLayout(this);

        addChild(mLayout);
        setContentView(mLayout);
    }

    private void addChild(ViewGroup vg) {
        int[] res = new int[] {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h,R.drawable.i,R.drawable.j,R.drawable.k,R.drawable.l};

        for (int i = 0; i < res.length; i ++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(res[i]);
            vg.addView(iv, 100, 100);
        }
    }

}
