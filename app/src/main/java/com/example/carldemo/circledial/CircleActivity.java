package com.example.carldemo.circledial;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carldemo.R;
import com.example.carldemo.circledial.CircleView.ISelectListener;

public class CircleActivity extends Activity {

	private Context mContext;
	private CircleView mCircleView;
	private TextView mTxvSelect;
	private static final int[] DRAWABLE_ID = new int[] {
			R.drawable.ic_launcher, R.drawable.circle, R.drawable.circle,
			R.drawable.circle, R.drawable.circle, R.drawable.circle };
	private ImageView[] mViews;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_circle_dial_activity);
		mContext = this;

		init();

		initItem();
		
		mCircleView.selectItem(4);
	}

	private void init() {
		mCircleView = (CircleView) findViewById(R.id.circle_view);
		mCircleView.setOnSelectListener(mSelectListener);
		mTxvSelect = (TextView) findViewById(R.id.txv_title);
	}

	private void initItem() {
		mViews = new ImageView[DRAWABLE_ID.length];
		LayoutInflater li = LayoutInflater.from(mContext);
		for (int i = 0; i < DRAWABLE_ID.length; i++) {
			View v = li
					.inflate(R.layout.item_circle_layout, mCircleView, false);
			ImageView iv = (ImageView) v.findViewById(R.id.iv);
			iv.setBackgroundResource(DRAWABLE_ID[i]);
			mCircleView.addView(v, i + 1);

			mViews[i] = iv;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return mCircleView.onKeyDown(keyCode, event);
	}

	private ISelectListener mSelectListener = new ISelectListener() {

		@Override
		public void onSelect(int index) {
			int count = mViews.length;
			for (int i = 0; i < count; i++) {
				View v = mViews[i];
				if (index == i) {
					v.setBackgroundResource(R.drawable.ic_launcher);
				} else {
					v.setBackgroundResource(R.drawable.circle);
				}
			}
			mTxvSelect.setText("select " + index);
		}
	};

}
