package com.example.carldemo.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/3/12.
 */

public class WaveView extends View {

    private Paint mPaint = null;
    private Point mPointS;
    private Point mPointE;
    private Point mPointA;

    private int mCenter = 1100;
    private int[] mFlag = new int[]{2,-3,1,-2,-2, 3, 1};
    private List<Point> mPoints = new ArrayList<>();
    private List<Point> mPointMids = new ArrayList<>();
    private List<Point> mPointMidMids = new ArrayList<>();
    private List<Point> mPointControls = new ArrayList<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            refresh();
            sendEmptyMessageDelayed(0, 80);
        }
    };
    public WaveView(Context context) {
        super(context);
        init(context);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);

        mPointS = new Point(100, 500);
        mPointE = new Point(500, 500);
        mPointA = new Point(250, 800);

        initPoints();
        initMidPoints(mPoints);
        initMidMidPoints(mPointMids);
        initControlPoints(mPoints, mPointMids, mPointMidMids);
    }

    public void start() {
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0, 80);
    }

    public void stop() {
        mHandler.removeMessages(0);
    }

    public void refresh() {
        for (int j = 0; j < mPoints.size(); j ++) {
            Point point = mPoints.get(j);
            point.y  = point.y - 20* mFlag[j];
            if (Math.abs(point.y - mCenter) > 150) {
                mFlag[j] *= -1;
            }
        }
        initMidPoints(mPoints);
        initMidMidPoints(mPointMids);
        initControlPoints(mPoints, mPointMids, mPointMidMids);
        postInvalidate();
    }

    public void progress(int i) {
        for (Point p : mPoints) {
            p.y += i;
        }
        mCenter += i;
    }

    private void initPoints() {
        mPoints.add(new Point(0, 1200));
        mPoints.add(new Point(150, 1200));
        mPoints.add(new Point(300, 1000));
        mPoints.add(new Point(550, 1150));
        mPoints.add(new Point(750, 1100));
        mPoints.add(new Point(900, 1250));
        mPoints.add(new Point(1080, 1200));

    }

    private void initMidPoints(List<Point> points) {
        if (points == null || points.isEmpty()) {
            return;
        }
        mPointMids.clear();
        Point midPoint;
        for (int i = 0; i < points.size() - 1; i ++) {
            midPoint = new Point((int)(points.get(i).x + points.get(i + 1).x)/2, (int)(points.get(i).y + points.get(i + 1).y)/2 );
            mPointMids.add(midPoint);
        }
    }

    private void initMidMidPoints(List<Point> midPoints) {
        if (midPoints == null || midPoints.isEmpty()) {
            return;
        }
        mPointMidMids.clear();
        Point midMidPoint;
        for (int i = 0; i < midPoints.size() - 1; i ++) {
            midMidPoint = new Point((int)(midPoints.get(i).x + midPoints.get(i + 1).x)/2, (int)(midPoints.get(i).y + midPoints.get(i + 1).y)/2 );
            mPointMidMids.add(midMidPoint);
        }
    }

    private void initControlPoints(List<Point> points, List<Point> midPoints, List<Point> midmidPoints) {
        mPointControls.clear();
        for (int i = 1; i < points.size() - 1; i ++) {
            Point beforePoint = new Point();
            Point afterPoint = new Point();
            beforePoint.x = points.get(i).x - midmidPoints.get(i-1).x + midPoints.get(i - 1).x;
            beforePoint.y= points.get(i).y - midmidPoints.get(i-1).y + midPoints.get(i - 1).y;
            afterPoint.x = points.get(i).x - midmidPoints.get(i-1).x + midPoints.get(i).x;
            afterPoint.y = points.get(i).y - midmidPoints.get(i-1).y + midPoints.get(i).y;
            mPointControls.add(beforePoint);
            mPointControls.add(afterPoint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        RectF rect = new RectF(100, 100, 1000, 200);
        path.addRect(rect, Path.Direction.CW);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mPaint);

        path.reset();
        path.moveTo(mPointS.x, mPointS.y);
        path.quadTo(mPointA.x, mPointA.y, mPointE.x, mPointE.y);
        canvas.drawPath(path, mPaint);

        drawWave(canvas);
    }

    private void drawWave(Canvas canvas) {
        Path path = new Path();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        for (Point p : mPoints) {
            canvas.drawCircle(p.x, p.y, 5, mPaint);
            if (path.isEmpty()) {
                path.moveTo(p.x, p.y);
            } else {
                path.lineTo(p.x, p.y);
            }
        }
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        canvas.drawPath(path, mPaint);
        path.reset();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        for (Point p : mPointMids) {
            canvas.drawCircle(p.x, p.y, 5, mPaint);
        }
        mPaint.setColor(Color.YELLOW);
        for (Point p : mPointMidMids) {
            canvas.drawCircle(p.x, p.y, 5, mPaint);
        }
        mPaint.setColor(Color.GREEN);
        for (Point p : mPointControls) {
            canvas.drawCircle(p.x, p.y, 5, mPaint);
        }
        mPaint.setColor(Color.parseColor("#77000000"));
        mPaint.setStyle(Paint.Style.STROKE);

        for (int i = 0; i < mPoints.size(); i ++) {
            if (i == 0) {
                path.moveTo(mPoints.get(i).x, mPoints.get(i).y);
                path.quadTo(mPointControls.get(i).x, mPointControls.get(i).y, mPoints.get(i+1).x, mPoints.get(i+1).y);
                Log.e("WaveView", mPoints.get(0).toString() + mPoints.get(1).toString() + mPointControls.get(0).toString());
            } else if (i < mPoints.size() - 2) {
                path.cubicTo(mPointControls.get(i * 2 - 1).x, mPointControls.get(i * 2 - 1).y, mPointControls.get(i * 2).x, mPointControls.get(i * 2).y, mPoints.get(i + 1).x, mPoints.get(i+1).y);
            } else if (i == mPoints.size() - 2) {
                path.moveTo(mPoints.get(i).x, mPoints.get(i).y);
                path.quadTo(mPointControls.get(mPointControls.size() - 1).x, mPointControls.get(mPointControls.size() - 1).y, mPoints.get(i + 1).x, mPoints.get(i+1).y);
            }
        }
        path.lineTo(1080, 1920);
        path.lineTo(0, 1920);
        path.lineTo(mPoints.get(0).x, mPoints.get(0).y);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, mPaint);
    }
}
