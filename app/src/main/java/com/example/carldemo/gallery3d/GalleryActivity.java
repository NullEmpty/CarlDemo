package com.example.carldemo.gallery3d;

import android.os.Bundle;
import android.app.Activity;

import com.example.carldemo.R;

public class GalleryActivity extends Activity {

    private MyGallery mGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mGallery = (MyGallery) findViewById(R.id.gallery);
        mGallery.setSpacing(80);
        mGallery.setFadingEdgeLength(0);
        GalleryAdapter mAdapter = new GalleryAdapter(this);
        mAdapter.setData(getRes());
        mGallery.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mGallery.setFocusable(true);
        mGallery.setFocusableInTouchMode(true);
        mGallery.requestFocus();
    }

    private int[] getRes() {
        return new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h,R.drawable.i,R.drawable.j,R.drawable.k,R.drawable.l};
    }

}
