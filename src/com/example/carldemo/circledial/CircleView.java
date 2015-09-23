package com.example.carldemo.circledial;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.carldemo.R;

public class CircleView extends FrameLayout {

	private Context mContext;
	private float BR = 300;
	private float LR = 60;
	private float W = 720;
	private ISelectListener mSelectListener;
	private int mIndex = 0;

	public static interface ISelectListener {
		void onSelect(int index);
	}

	public CircleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public CircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public CircleView(Context context) {
		super(context);
		init(context, null);
	}

	private void init(Context context, AttributeSet attrs) {
		mContext = context;
		inflate(context, R.layout.layout_circle_view, this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		int index = mIndex;
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			selectItem(index + 1);
			return true;
		case KeyEvent.KEYCODE_VOLUME_UP:
			selectItem(index - 1);
			return true;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void setOnSelectListener(ISelectListener l) {
		mSelectListener = l;
	}

	public void selectItem(int index) {
		int count = getChildCount() - 1;
		index = index % count;
		if (index < 0) {
			index = index + count;
		}
		// Log.e("TAG", "index:" + index);
		if (mSelectListener != null) {
			mSelectListener.onSelect(index);
		}
		mIndex = index;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		W = getMeasuredWidth();
		int count = getChildCount();
		if (count <= 1) {
			return;
		}
		LR = getChildAt(1).getMeasuredWidth() / 2;
		View viewBgCircle = getChildAt(0);
		int bgCircleMeasureSpec = MeasureSpec.makeMeasureSpec(
				(int) (W - LR * 2), MeasureSpec.EXACTLY);
		viewBgCircle.measure(bgCircleMeasureSpec, bgCircleMeasureSpec);
		BR = (W - LR * 2) / 2;
		// Log.e("TAG", "W:" + W + ",LR=" + LR + ",BR=" + BR);

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {

		int count = getChildCount() - 1;
		// Log.e("TAG", "onLayout" + count);
		// super.onLayout(changed, left, top, right, bottom);
		if (count <= 0) {
			return;
		}
		View viewBgCircle = getChildAt(0);
		viewBgCircle.layout((int) LR, (int) LR, (int) (W - LR), (int) (W - LR));
		for (int i = 0; i < count; i++) {
			View v = getChildAt(i + 1);
			Rect rect = getChildRect(count, i);
			v.layout(rect.left, rect.top, rect.right, rect.bottom);
		}
	}

	private Rect getChildRect(int count, int index) {
		Rect rect = new Rect();
		double angle = 2 * Math.PI * index / count + Math.PI / 2;
		// Log.e("TAG", "angle=" + angle);
		double x = BR * Math.cos(angle);
		double y = BR * Math.sin(angle);
		// Log.e("TAG", "x=" + x + ",y=" + y);
		rect.left = (int) ((W / 2 + x) - LR);
		rect.top = (int) ((W / 2 - y) - LR);
		rect.right = (int) (rect.left + LR * 2);
		rect.bottom = (int) (rect.top + LR * 2);
		// Log.e("TAG", "l=" + rect.left + ",t=" + rect.top);
		return rect;
	}
}
