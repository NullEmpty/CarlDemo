package com.example.carldemo.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.carldemo.R;
import com.example.carldemo.tools.FastBlurUtil;

public class BitmapBlurActivity extends Activity {

    private ImageView mIv;
    private RelativeLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_blur);

        mLayout = (RelativeLayout) findViewById(R.id.activity_bitmap_blur);
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIv.setImageBitmap(null);
            }
        });

        mIv = (ImageView) findViewById(R.id.iv);
        mIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int scaleRatio = 10;
                int blurRadius = 8;
                Bitmap originBitmap = getOrigBitmap();
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(originBitmap,
                        originBitmap.getWidth() / scaleRatio,
                        originBitmap.getHeight() / scaleRatio,
                        false);
                Bitmap blurBitmap = FastBlurUtil.doBlur(scaledBitmap, blurRadius, true);
                mIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mIv.setImageBitmap(blurBitmap);
            }
        });

    }

    private Bitmap getOrigBitmap() {
        int[] positions = new int[2];
        mIv.getLocationOnScreen(positions);
        int w = mIv.getWidth();
        int h = mIv.getHeight();
        Log.e("TAG", String.format("%d,%d,%d,%d", positions[0], positions[1], w, h));

        int[] layoutL = new int[2];
        mLayout.getLocationOnScreen(layoutL);
        mLayout.setDrawingCacheEnabled(true);
        mLayout.buildDrawingCache();
        Bitmap cache = mLayout.getDrawingCache();
        Bitmap bmp = Bitmap.createBitmap(cache, positions[0] - layoutL[0], positions[1] - layoutL[1], w, h);
        return bmp;
    }

}
