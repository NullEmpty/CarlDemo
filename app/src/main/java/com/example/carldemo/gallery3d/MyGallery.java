package com.example.carldemo.gallery3d;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Transformation;
import android.widget.Gallery;

/**
 * Created by pcxu on 2016/2/19.
 */
public class MyGallery extends Gallery {

    private int mCenterX;
    private Camera mCamera;
    private int mMaxAngle = 45;
    private int mMaxZoom = -120;

    public MyGallery(Context context) {
        super(context);
        init();
    }

    public MyGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.setStaticTransformationsEnabled(true);
        this.setChildrenDrawingOrderEnabled(true);

        mCamera = new Camera();
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        int selectIndex = getSelectedItemPosition() - getFirstVisiblePosition();
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
        return v.getLeft() + v.getWidth() / 2;
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        super.getChildStaticTransformation(child, t);

        final int childCenter = getCenterxOfView(child);
        final int childWidth = child.getWidth();

        int rotationAngle = 0;
        t.clear();
        t.setTransformationType(Transformation.TYPE_MATRIX);

        if (childCenter == mCenterX) {
            transformImageBitmap(child, t, 0);
        } else {
            rotationAngle = (int) (((float) (mCenterX - childCenter) / (childWidth * 6)) * mMaxAngle);

            if (Math.abs(rotationAngle) > mMaxAngle) {
                rotationAngle = (rotationAngle < 0) ? -mMaxAngle : mMaxAngle;
            }

            transformImageBitmap(child, t, rotationAngle);
        }

        if (Build.VERSION.SDK_INT > 15) {
            child.postInvalidate();
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
        float zoom = (float) (mMaxZoom - rotation * 1.5f);
        mCamera.translate(0, 0, zoom);

        mCamera.rotateY(rotationAngle);
        mCamera.getMatrix(imageMatrix);

        imageMatrix.postTranslate(w / 2, h / 2);
        imageMatrix.preTranslate(-w / 2, -h / 2);

        mCamera.restore();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {

                View v = getFocusedChild();
//                Log.e("'dispatchKeyEventv", v.toString());
                View vv = ((ViewGroup) v).getFocusedChild();
                if (vv != null && vv instanceof ViewGroup) {
                    vv = ((ViewGroup) vv).getFocusedChild();//focused cellview
                }
                if (vv != null) {
//                    Log.e("'dispatchKeyEventvv", vv.toString());

                    int direction = event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT ? View.FOCUS_LEFT : View.FOCUS_RIGHT;
                    View next = FocusFinder.getInstance().findNextFocus((ViewGroup) v, vv, direction);
                    if (next != null) {//item内部移动
                        next.requestFocus();
                        return true;
                    } else {//跨item
                        GalleryAdapter adapter = (GalleryAdapter)getAdapter();
                        int selectP = getSelectedItemPosition();
                        int type = adapter.getItemViewType(selectP);

                        int nextP = (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT ? selectP - 1 : selectP + 1);
                        int nextType = adapter.getItemViewType(nextP);
                        if (nextType == GalleryAdapter.ITEM_TYPE_3 && type == GalleryAdapter.ITEM_TYPE_3) {
                            String index = (String) vv.getTag();
                            next = FocusFinder.getInstance().findNextFocus(this, v, direction);
//                            Log.e("TAG", "selectP=" + selectP + ",type=" + type);
                            next = next.getParent() instanceof GalleryLinearLayout ? (ViewGroup)next.getParent() : (ViewGroup)next.getParent().getParent();
                            if ("0".equals(index)) {
                                Log.e("TAGG", next.toString() + ",tag=" + next.getTag());
                                super.dispatchKeyEvent(event);
//                                next.findViewWithTag("1").requestFocus(direction);

                                ((GalleryLinearLayout)next).setIndex("1");
                                return true;
                            } else if ("1".equals(index)) {
                                super.dispatchKeyEvent(event);
                                ((GalleryLinearLayout)next).setIndex("0");
                                return true;
                            } else if ("2".equals(index)) {
                                super.dispatchKeyEvent(event);
                                ((GalleryLinearLayout)next).setIndex("3");
                                return true;

                            } else if ("3".equals(index)) {
                                super.dispatchKeyEvent(event);
                                ((GalleryLinearLayout)next).setIndex("2");
                                return true;
                            }
                        }

                    }
                }

            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect
            previouslyFocusedRect) {
        Log.e("MyGallery", gainFocus + ",");
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    @Override
    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        if (direction == View.FOCUS_LEFT) {

        }
        Log.e("MyGallery", "onRequestFocusIn" + direction + "," + (previouslyFocusedRect == null ? " null" : previouslyFocusedRect.toString()));
        return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
    }
}
