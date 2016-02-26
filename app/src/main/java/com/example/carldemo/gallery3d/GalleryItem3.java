package com.example.carldemo.gallery3d;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.carldemo.R;

/**
 * Created by pcxu on 2016/2/26.
 */
public class GalleryItem3 extends LinearLayout {
    public GalleryItem3(Context context) {
        super(context);
        init(context);
    }

    public GalleryItem3(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GalleryItem3(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_gallery3, this);
    }
}
