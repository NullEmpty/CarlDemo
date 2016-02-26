package com.example.carldemo.gallery3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.carldemo.R;

/**
 * Created by pcxu on 2016/2/25.
 */
public class CellView extends RelativeLayout {
    private ImageView mIvIcon;
    private TextView mTxvName;

    public CellView(Context context) {
        super(context);
        init(context);
    }

    public CellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CellView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {

        LayoutInflater.from(context).inflate(R.layout.layout_cell, this);

        mIvIcon = (ImageView) findViewById(R.id.iv_icon);
        mTxvName = (TextView) findViewById(R.id.txv_name);

        setBackgroundResource(R.drawable.item_gallery_selector);
    }

    public void setIcon(Bitmap bmp) {
        mIvIcon.setImageBitmap(bmp);
    }

    public void setName(String name) {
        mTxvName.setText(name);
    }
}
