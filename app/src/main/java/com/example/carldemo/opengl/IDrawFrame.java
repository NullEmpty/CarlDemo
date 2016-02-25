package com.example.carldemo.opengl;

import android.view.KeyEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author: Peichen Xu
 * @since: 2015-10-13
 */
public interface IDrawFrame {
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config);
	public void draw(GL10 gl);
	public void onSurfaceChanged(GL10 gl, int width, int height);

	public boolean onKeyDown(int keyCode, KeyEvent event);

}
