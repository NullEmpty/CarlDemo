package com.example.carldemo.view;

import com.example.carldemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * @author: Peichen Xu
 * @since: 2015-6-29
 */
public class TestViewActivity extends Activity {
	
	private RoyoleVolumControlBar mBar;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_view_activity);
		
		mBar = (RoyoleVolumControlBar) findViewById(R.id.volumecontrol);
		mBar.setCurrentProgress(50);
	}
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			mBar.down();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			mBar.up();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
