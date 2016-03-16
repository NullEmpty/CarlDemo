package com.example.carldemo.gallery3d;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.royole.bwgallery.AbsGalleryLinearLayout;

/**
 * Created by pcxu on 2016/2/26.
 */
public class GalleryLinearLayout extends AbsGalleryLinearLayout {

    private static final String TAG = GalleryLinearLayout.class.getSimpleName();

    private CellView mCellView1;
    private CellView mCellView2;
    private CellView mCellView3;
    private CellView mCellView4;

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
    protected void onFinishInflate() {
        super.onFinishInflate();

        mCellView1 = (CellView) findViewWithTag("0");
        mCellView2 = (CellView) findViewWithTag("1");
        mCellView3 = (CellView) findViewWithTag("2");
        mCellView4 = (CellView) findViewWithTag("3");

    }

    public void setData(AppInfo info) {
        if (mCellView1 != null) {
            mCellView1.setAppinfo(info);
        }
    }

    public void setData(AppInfo info, AppInfo info2) {
        setData(info);
        if (mCellView2 != null) {
            mCellView2.setAppinfo(info2);
        }
    }

    public void setData(AppInfo info, AppInfo info2, AppInfo info3) {
        setData(info, info2);
        if (mCellView3 != null) {
            mCellView3.setAppinfo(info3);
        }

    }

    public void setData(AppInfo info, AppInfo info2, AppInfo info3, AppInfo info4) {
        setData(info, info2, info3);
        if (mCellView4 != null) {
            mCellView4.setAppinfo(info4);
        }

    }

}
