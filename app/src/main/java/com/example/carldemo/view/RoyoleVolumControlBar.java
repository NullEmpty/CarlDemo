package com.example.carldemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.carldemo.R;

public class RoyoleVolumControlBar extends View {
	private int mFirstColor;

	private int mSecondColor;
	private int mCircleWidth;
	private Paint mPaint;
	private int mCurrentCount = 0;

	private int mTotalAngle = 260;
	private int mSplitSize;
	private int mTextSize = 30;
	private int mCount;

	private Rect mRect;

	public RoyoleVolumControlBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RoyoleVolumControlBar(Context context) {
		this(context, null);
	}

	/**
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public RoyoleVolumControlBar(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.RoyoleVolumControlBar, defStyle, 0);
		int n = a.getIndexCount();

		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.RoyoleVolumControlBar_firstColor:
				mFirstColor = a.getColor(attr, Color.GREEN);
				break;
			case R.styleable.RoyoleVolumControlBar_secondColor:
				mSecondColor = a.getColor(attr, Color.CYAN);
				break;
			case R.styleable.RoyoleVolumControlBar_bg:
				break;
			case R.styleable.RoyoleVolumControlBar_circleWidth:
				mCircleWidth = a.getDimensionPixelSize(attr, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20,
								getResources().getDisplayMetrics()));
				break;
			case R.styleable.RoyoleVolumControlBar_dotCount:
				mCount = a.getInt(attr, 20);
				break;
			case R.styleable.RoyoleVolumControlBar_splitSize:
				mSplitSize = a.getDimensionPixelSize(attr, 3);
				break;
			case R.styleable.RoyoleVolumControlBar_textSize:
				mTextSize = a.getDimensionPixelSize(attr, 30);
				break;
			}
		}
		a.recycle();
		mPaint = new Paint();
		mRect = new Rect();
	}

	@Override
	protected void onDraw(Canvas canvas) {

		mPaint.setStrokeWidth(mCircleWidth);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
		int centre = getWidth() / 2;
		int radius = centre - mCircleWidth / 2;

		mRect.left = 0;
		mRect.top = 0;
		mRect.bottom = getHeight();
		mRect.right = getWidth();
		drawOval(canvas, centre, radius);

		mPaint.setStrokeWidth(0);
		mPaint.setColor(mSecondColor);
		mPaint.setTextSize(mTextSize);
		mPaint.setTypeface(Typeface.DEFAULT_BOLD);
		mPaint.setTextAlign(Align.CENTER);
		int percent = (int) (((float) mCurrentCount / (float) 50) * 100);
		FontMetrics fm = mPaint.getFontMetrics();
		int textHeight = (int) (Math.ceil(fm.descent - fm.ascent) + 2);

		canvas.drawText(percent + "%", centre, centre + textHeight * 1 / 3,
				mPaint);
	}

	/**
	 * 
	 * @param canvas
	 * @param centre
	 * @param radius
	 */
	private void drawOval(Canvas canvas, int centre, int radius) {
		float itemSize = (mTotalAngle * 1.0f - mCount * mSplitSize) / mCount;

		RectF oval = new RectF(centre - radius, centre - radius, centre
				+ radius, centre + radius);

		mPaint.setColor(mFirstColor);
		for (int i = 0; i < mCount; i++) {
			if (i < mCurrentCount) {
				mPaint.setColor(mSecondColor);
			} else {
				mPaint.setColor(mFirstColor);
			}
			canvas.drawArc(oval, 140 + i * (itemSize + mSplitSize), itemSize,
					false, mPaint);
		}
	}

	public void up() {
		mCurrentCount = mCurrentCount + 3;
		if (mCurrentCount > mCount) {
			mCurrentCount = mCount;
		}
		postInvalidate();
	}

	public void down() {
		mCurrentCount = mCurrentCount - 3;
		if (mCurrentCount < 0) {
			mCurrentCount = 0;
		}
		postInvalidate();
	}

	public void setCurrentProgress(int progress) {
		mCurrentCount = progress;
		postInvalidate();
	}

}
