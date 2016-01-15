package com.example.carldemo.pickerview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * @author Peichen Xu
 * 
 */
public class PickerView extends ListView {

	private Context mContext;
	private float mCenterPosition;
	private Scroller mScroller;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			scrollToRightPosition();
		}
	};

	public PickerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public PickerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PickerView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		setOnScrollListener(mScrollListener);
		setDividerHeight(0);
		setDivider(null);

		mScroller = new Scroller(mContext);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mCenterPosition = getHeight() / 2f;
	}

	private boolean mScrolled = true;
	private OnScrollListener mScrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
			case SCROLL_STATE_FLING:
				break;
			case SCROLL_STATE_IDLE:
				if (!mScrolled) {
					mScrolled = true;
				}
				break;
			case SCROLL_STATE_TOUCH_SCROLL:
				break;
			default:
				break;
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			handleItemAnimation();
		}
	};

	@Override
	public boolean onTouchEvent(android.view.MotionEvent ev) {
		mScrolled = false;
		switch (ev.getAction()) {
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			scrollToRightPosition();
			break;

		case MotionEvent.ACTION_MOVE:
			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	};

	@Override
	public void setSelection(int position) {
		super.setSelection(position);
		postDelayed(new Runnable() {

			@Override
			public void run() {
				scrollToRightPosition();
			}
		}, 20);
	}

	private boolean scrollToRightPosition() {
		Log.e("TAG", "scrollToRightPosition");
		int distance = -1;
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View v = getChildAt(i);
			int top = v.getTop();
			int height = v.getHeight();

			if (top <= mCenterPosition && top + height >= mCenterPosition) {
				distance = (int) ((top + height / 2f) - mCenterPosition);
				Log.e("ACTION_UP", "i=" + i + ",top=" + top + ",height="
						+ height + ",cy=" + mCenterPosition);
				break;
			}
		}
		if (distance != 0) {
			smoothScrollBy(distance, 200);
			return false;
		}
		return true;
	}

	/**
	 * 处理Item动画
	 */
	private void handleItemAnimation() {
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View v = getChildAt(i);
			int top = v.getTop();
			int height = v.getHeight();
			int c = top + height / 2;
			float x = Math.abs(mCenterPosition - c) / mCenterPosition;
			float scale = 1 - x * x;
			// v.setScaleX(scale);
			v.setScaleY(scale);
			v.setAlpha(scale);
		}
	}

	private void scrollToPre() {
		if (!mScroller.isFinished()) {
			return;
		}
		int height = getChildAt(0).getHeight();
		smoothScrollBy(-height, 500);
	}

	private void scrollToNext() {
		if (!mScroller.isFinished()) {
			return;
		}
		int height = getChildAt(0).getHeight();
		smoothScrollBy(height, 500);
	}

	@Override
	public void smoothScrollBy(int distance, int duration) {
		super.smoothScrollBy(distance, duration);
		mScroller.forceFinished(true);
		mScroller.startScroll(0, 0, 0, 100, duration);
	}

	@Override
	public void computeScroll() {

		if (!mScroller.computeScrollOffset()) {
		}
		super.computeScroll();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			scrollToNext();
			return true;
		case KeyEvent.KEYCODE_VOLUME_UP:
			scrollToPre();
			return true;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			mHandler.removeMessages(0);
			mHandler.sendEmptyMessageDelayed(0, 100);
			return true;
		case KeyEvent.KEYCODE_VOLUME_UP:
			mHandler.removeMessages(0);
			mHandler.sendEmptyMessageDelayed(0, 100);
			return true;

		default:
			break;
		}
		return super.onKeyUp(keyCode, event);
	}
}
