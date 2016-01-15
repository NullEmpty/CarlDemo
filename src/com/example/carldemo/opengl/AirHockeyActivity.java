package com.example.carldemo.opengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

/**
 * @author: Peichen Xu
 * @since: 2015-10-19
 */
public class AirHockeyActivity extends Activity {
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		GLSurfaceView glSurfaceView = new GLSurfaceView(this);
		glSurfaceView.setEGLContextClientVersion(2);
		glSurfaceView.setRenderer(new AirHockeyRenderer(this));
		setContentView(glSurfaceView);
	}

}
