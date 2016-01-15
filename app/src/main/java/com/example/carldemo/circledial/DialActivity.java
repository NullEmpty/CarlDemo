package com.example.carldemo.circledial;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * @author: Peichen Xu
 * @since: 2015-5-26
 */
public class DialActivity extends Activity {
	
	private DialView mDialView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mDialView = new DialView(this);
		setContentView(mDialView);
		
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		mDialView.onTouchEvent(event);
		
		return true;
	}

}
