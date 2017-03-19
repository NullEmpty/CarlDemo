package com.example.carldemo.draw;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 2017/3/19.
 */

public class InnerLightView extends View {

    private Context mContext;
    private Paint mPaintLight;
    private Paint mPaint;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            postInvalidate();
            sendEmptyMessageDelayed(0, 60);
        }
    };
    public InnerLightView(Context context) {
        super(context);
        init(context);
    }

    public InnerLightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public InnerLightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mPaintLight = new Paint();
        setLayerType(View.LAYER_TYPE_SOFTWARE, mPaintLight);
        mPaintLight.setColor(Color.parseColor("#ddff0000"));
        mPaintLight.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);

        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLight(canvas);

        canvas.save();
        canvas.translate(500, 0);
        drawRect(canvas);
        canvas.restore();
    }

    private void drawLight(Canvas canvas) {
        RectF rectF = new RectF(100, 100, 400, 200);
        canvas.drawOval(rectF, mPaintLight);
    }

    private int r = 50;
    private int f = 1;
    private void drawRect(Canvas canvas) {

        LinearGradient lg = new LinearGradient(0, 0, 200, 0, Color.BLACK, Color.BLUE, Shader.TileMode.MIRROR);
        mPaint.setShader(lg);
        SweepGradient sg = new SweepGradient(300, 150, Color.BLACK, Color.BLUE);
        mPaint.setShader(sg);
        if (r > 200) {
            f = -1;
        } else if (r < 50) {
            f = 1;
        }
        r = r + f * 5;
        RadialGradient rg = new RadialGradient(300, 150, r, Color.BLACK, Color.BLUE, Shader.TileMode.MIRROR);
        mPaint.setShader(rg);
        RectF rectF = new RectF(100, 100, 500, 200);
        canvas.drawRoundRect(rectF, 30, 30, mPaint);
    }
}