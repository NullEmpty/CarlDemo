package com.example.carldemo.gallery3d;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by pcxu on 2016/2/26.
 */
public class GalleryLinearLayout extends LinearLayout {

    private static final String TAG = GalleryLinearLayout.class.getSimpleName();
    public GalleryLinearLayout(Context context) {
        super(context);
    }

    public GalleryLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        Log.e(TAG, "onFocusChanged" + gainFocus + "," + direction + "," + previouslyFocusedRect.toString());
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    private String mIndex = "0";
    public void setIndex(String index) {
        mIndex = index;
    }

    @Override
    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        if (direction == View.FOCUS_LEFT) {

        }
        Log.e(TAG, "onRequestFocusInDescendants " + direction + "," + (previouslyFocusedRect == null ? "null" : previouslyFocusedRect.toString()));
        findViewWithTag(mIndex).requestFocus(direction, previouslyFocusedRect);

        mIndex = "0";
        return true;
//        return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);

    }
}
