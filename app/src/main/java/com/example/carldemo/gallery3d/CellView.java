package com.example.carldemo.gallery3d;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;
import com.example.carldemo.R;
import com.royole.bwgallery.ICellView;

/**
 * Created by pcxu on 2016/2/25.
 */
public class CellView extends ViewAnimator implements ICellView{
    private static final int ITEM_TYPE_1 = 1;
    private static final int ITEM_TYPE_2 = 2;
    private static final int ITEM_TYPE_3 = 3;

    private static final int[] WIDTH_ICON = {80, 140, 80};
    private static final int[] SPACE_TEXT_ICON = {30, 70, 30};

    private AppInfo mAppInfo;
    private ImageView mIvIcon;
    private TextView mTxvName;
    private ImageView mIvAddTip;
    private PaintFlagsDrawFilter mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CellView(Context context) {
        super(context);
        init(context);
    }

    public CellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CellView);
        int itemType = a.getInt(R.styleable.CellView_item_type, ITEM_TYPE_3);

        itemType = (itemType >= ITEM_TYPE_1 && itemType <= ITEM_TYPE_3) ? itemType : ITEM_TYPE_3;
        int index = itemType - 1;
        ViewGroup.LayoutParams lp_icon = mIvIcon.getLayoutParams();
        lp_icon.width = WIDTH_ICON[index];
        lp_icon.height = WIDTH_ICON[index];
        mIvIcon.setLayoutParams(lp_icon);
        MarginLayoutParams lp_name = (MarginLayoutParams)mTxvName.getLayoutParams();
        lp_name.topMargin = SPACE_TEXT_ICON[index];
        mTxvName.setLayoutParams(lp_name);

        a.recycle();
    }

//    public CellView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        init(context);
//    }

    private void init(Context context) {

        LayoutInflater.from(context).inflate(R.layout.layout_cell, this);

        mIvIcon = (ImageView) findViewById(R.id.iv_icon);
        mIvIcon.setEnabled(false);
        mTxvName = (TextView) findViewById(R.id.txv_name);
        mTxvName.setEnabled(false);

        mIvAddTip = (ImageView) findViewById(R.id.iv_add_tip);
        mIvAddTip.setEnabled(false);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("CLICK", "tag=" + v.getTag());
            }
        });

        mPaint.setColor(Color.parseColor("#0f0e2d"));
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
    }

    public void setAppinfo(AppInfo info) {
        mAppInfo = info;
        if (info == null) {
            return;
        }
        if (info.itemType == AppInfo.TYPE_ADD_TIP) {
            setDisplayedChild(1);
            setBackgroundResource(R.drawable.item_add_bg_selector);
        } else {
            setDisplayedChild(0);
            mIvIcon.setImageDrawable(info.icon);
            mTxvName.setText(info.title);
            setBackgroundResource(R.drawable.item_bg_selector);


            Drawable d = mIvIcon.getDrawable();
            if (BitmapDrawable.class.isInstance(d)) {
                d.setFilterBitmap(true);
                ((BitmapDrawable)d).setAntiAlias(true);
            }
        }

    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        if (gainFocus) {//用于改变选中文字颜色
            mTxvName.setEnabled(true);
            mIvIcon.setEnabled(true);
            mIvAddTip.setEnabled(true);
        } else {
            mTxvName.setEnabled(false);
            mIvIcon.setEnabled(false);
            mIvAddTip.setEnabled(false);
        }
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.setDrawFilter(mPaintFlagsDrawFilter);
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(mPaintFlagsDrawFilter);

        super.onDraw(canvas);
        canvas.drawLine(0, 0, getWidth(), 0, mPaint);
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), mPaint);
    }

    @Override
    public boolean isVisible() {
        return mAppInfo != null;
    }
}
