package com.example.carldemo;

import android.view.View.OnClickListener;

/**
 * @author Peichen Xu
 *
 */
public class MainItem {

	
	private int titleId;
	private OnClickListener mClickListener;
	public int getTitleId() {
		return titleId;
	}
	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}
	public OnClickListener getClickListener() {
		return mClickListener;
	}
	public void setOnClickListener(OnClickListener mClickListener) {
		this.mClickListener = mClickListener;
	}
	
}
