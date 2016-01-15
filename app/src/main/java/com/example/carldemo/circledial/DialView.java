package com.example.carldemo.circledial;

import com.example.carldemo.R;
import com.example.carldemo.circledial.WheelMenu.WheelChangeListener;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author: Peichen Xu
 * @since: 2015-5-26
 */
public class DialView extends FrameLayout {

	private static final int[] DRAWABLE_ID = new int[] {
			R.drawable.item_circle_img, R.drawable.item_circle_img,
			R.drawable.item_circle_img, R.drawable.item_circle_img,
			R.drawable.item_circle_img, R.drawable.item_circle_img };

	private static final String[] TITLE = new String[] { "视频", "设置", "局域网",
			"推送", "图片", "音乐" };

	private Context mContext;
	private View[] mViews;
	private CircleView mCircleView;
	private WheelMenu mWheelMenu;
	private TextView mTxvSelect;
	
	public DialView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public DialView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public DialView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.layout_dial_view, this);

		mCircleView = (CircleView) findViewById(R.id.circle_view);
		mWheelMenu = (WheelMenu) findViewById(R.id.wheelmenu);
		mWheelMenu.setDivCount(6);
		mWheelMenu.setWheelImage(R.drawable.circle_hl);
		mWheelMenu.setWheelChangeListener(mWheelChangeListener);
		
		mTxvSelect = (TextView) findViewById(R.id.txv_select);

		initItemView();
		
		mWheelChangeListener.onSelectionChange(0);

	}

	private WheelChangeListener mWheelChangeListener = new WheelChangeListener() {

		@Override
		public void onSelectionChange(int selectedPosition) {
			Log.e("TAG", "onSelectionChange:" + selectedPosition);
			for (int i = 0; i < DRAWABLE_ID.length; i++) {
				mViews[i].setSelected(selectedPosition == i);
				
				mTxvSelect.setText(TITLE[selectedPosition]);
			}
		}
	};

	private void initItemView() {
		mViews = new View[DRAWABLE_ID.length];
		LayoutInflater li = LayoutInflater.from(mContext);
		for (int i = 0; i < DRAWABLE_ID.length; i++) {
			View v = li
					.inflate(R.layout.item_circle_layout, mCircleView, false);
			ImageView iv = (ImageView) v.findViewById(R.id.iv);
			iv.setBackgroundResource(DRAWABLE_ID[i]);
			mCircleView.addView(v);

			TextView txv = (TextView) v.findViewById(R.id.txv_title);
			txv.setText(TITLE[i]);

			mViews[i] = v;
		}
	}

	public void setHighLightAlpha(float alpha) {
		mWheelMenu.setAlpha(alpha);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			setHighLightAlpha(0.3f);
		} else if (action == MotionEvent.ACTION_UP) {
			setHighLightAlpha(1);
		}
		return mWheelMenu.onTouchEvent(event);
	}

}
