package com.example.carldemo.opengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * @author: Peichen Xu
 * @since: 2015-10-13
 */
public class OpenGlTest2 extends Activity {
	
	private GLSurfaceView mGlSurfaceView;
	private TestRender mTestRender;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mGlSurfaceView = new GLSurfaceView(getApplicationContext());
		mTestRender = new TestRender(new Combination(getApplicationContext()));
		mTestRender.setBgColor(1, 1, 1);
		mGlSurfaceView.setRenderer(mTestRender);
		setContentView(mGlSurfaceView);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mGlSurfaceView.onPause();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mGlSurfaceView.onResume();
	}
	
	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		mGlSurfaceView.queueEvent(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mTestRender != null) {
					mTestRender.setBgColor(event.getX() / mGlSurfaceView.getWidth(), event.getY() / mGlSurfaceView.getHeight(), 0);
				}
			}
		});
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {

		mGlSurfaceView.queueEvent(new Runnable() {
			@Override
			public void run() {
				if (mTestRender != null) {
					mTestRender.onKeyDown(keyCode, event);
				}
			}
		});
		return true;
	}
}
