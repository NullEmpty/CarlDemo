package com.royole.bwgallery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by pcxu on 2016/2/26.
 */
public class AbsGalleryLinearLayout extends LinearLayout {

    private static final String TAG = AbsGalleryLinearLayout.class.getSimpleName();
    private String mIndex = "0";
    private int mType = ItemType.ITEM_TYPE_2;

    private PaintFlagsDrawFilter mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    public AbsGalleryLinearLayout(Context context) {
        super(context);
    }

    public AbsGalleryLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsGalleryLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (findViewWithTag("3") != null) {
            mType = ItemType.ITEM_TYPE_3;
        } else if (findViewWithTag("1") != null) {
            mType = ItemType.ITEM_TYPE_1;
        } else {
            mType = ItemType.ITEM_TYPE_2;
        }
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
//        Log.e(TAG, "onFocusChanged" + gainFocus + "," + direction + "," + previouslyFocusedRect.toString());
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    public void setIndex(String index) {
        mIndex = index;
    }

    @Override
    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        if (mType == ItemType.ITEM_TYPE_3) {
            findViewWithTag(mIndex).requestFocus(direction, previouslyFocusedRect);

            mIndex = "0";
            return true;
        }
        return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.setDrawFilter(mPaintFlagsDrawFilter);
        super.dispatchDraw(canvas);
    }
}
