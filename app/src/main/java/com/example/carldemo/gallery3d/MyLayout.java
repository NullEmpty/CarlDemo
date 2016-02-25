package com.example.carldemo.gallery3d;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

/**
 * Created by pcxu on 2016/2/22.
 */
public class MyLayout extends LinearLayout {

    private int mCenterX;
    private Camera mCamera;
    private int mMaxAngle = 90;
    private int mMaxZoom = -120;

    public MyLayout(Context context) {
        super(context);
        init();
    }

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.setStaticTransformationsEnabled(true);
//        this.setChildrenDrawingOrderEnabled(true);

        mCamera = new Camera();
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        int selectIndex = i;//getSelectedItemPosition() - getFirstVisiblePosition();
        if (selectIndex < 0) {
            return i;
        }
        if (i < selectIndex) {
            return i;
        } else if (i >= selectIndex) {
            return childCount - i - 1 + selectIndex;
        } else {
            return i;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = getCenterX();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private int getCenterX() {
        return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
    }

    private int getCenterxOfView(View v) {
        return v.getLeft() + v.getWidth()  / 2;
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        super.getChildStaticTransformation(child, t);

        final int childCenter = getCenterxOfView(child);
        final int childWidth = child.getWidth();

        int rotationAngle = 0;
        t.clear();
        t.setTransformationType(Transformation.TYPE_MATRIX);

        Log.e("TAG", "childCenter=" + childCenter + ",mCenterX=" + mCenterX);
        if (childCenter == mCenterX) {
            transformImageBitmap(child, t, 0);
        } else {
            rotationAngle = (int)(((float)(mCenterX - childCenter) / (childWidth * 6)) * mMaxAngle);

            if (Math.abs(rotationAngle) > mMaxAngle) {
                rotationAngle = (rotationAngle < 0) ? - mMaxAngle : mMaxAngle;
            }

            transformImageBitmap(child, t, rotationAngle);
        }

        if (Build.VERSION.SDK_INT > 15) {
//            child.postInvalidate();
        }
        return true;
    }

    private void transformImageBitmap(View child, Transformation t, int rotationAngle) {

        mCamera.save();

        final Matrix imageMatrix = t.getMatrix();
        final int h = child.getHeight();
        final int w = child.getWidth();
        final int rotation = Math.abs(rotationAngle);

//        if (rotation < mMaxAngle) {
//            float zoom = (float)(mMaxZoom - rotation * 1.5f);
//            mCamera.translate(0, 0, zoom);
//        } else {
//            mCamera.translate(0, 0, mMaxZoom);
//        }
        float zoom = (float)(mMaxZoom - rotation * 1.5f);
        mCamera.translate(0, 0, zoom);

        mCamera.rotateY(rotationAngle);
        mCamera.getMatrix(imageMatrix);

        imageMatrix.postTranslate(w / 2, h / 2);
        imageMatrix.preTranslate(-w / 2, -h / 2);

        mCamera.restore();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);

        int count = getChildCount();
        for (int i = 0; i < count; i ++) {
            getChildAt(i).layout(i * 150, t, (i ) * 150 + 100, b);
        }
    }
}
