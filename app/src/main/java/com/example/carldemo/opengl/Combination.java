package com.example.carldemo.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.view.KeyEvent;

/**
 * @author: Peichen Xu
 * @since: 2015-10-13
 */
public class Combination implements IDrawFrame {
	
	private Square mSquare;
	private Triangle mTriangle;
	private Octagon mOctagon;
	private Cylinder mCylinder;
	
	private Context mContext;
	private Cube mCube;
	private ShapeL mShapeL;
	
	public Combination(Context context) {
		mContext = context;
		
		mSquare = new Square(mContext);
		mTriangle = new Triangle(mContext);
		mOctagon = new Octagon();
		mCube = new Cube(mContext);
		mShapeL = new ShapeL(mContext);
		mCylinder = new Cylinder(mContext, 8,8,1,180);
		mCylinder.setzAngle(90);
	}

	@Override
	public void draw(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glPushMatrix();
		gl.glTranslatef(-1f, 0, 0);
		mSquare.draw(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(1, 0, 0);
//		gl.glScalef(0.5f, 0.5f, 0.5f);
		mTriangle.draw(gl);
		gl.glPopMatrix();
	
		
		gl.glPushMatrix();
		gl.glTranslatef(0, 3, 0);
		mOctagon.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslatef(-2, -3.5f, 0);
		gl.glScalef(1.5f, 1.5f, 1.5f);
		mCube.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		mShapeL.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 6);
		mCylinder.draw(gl);
		gl.glPopMatrix();

	}

	/* (non-Javadoc)
	 * @see com.example.carldemo.opengl.IDrawFrame#onSurfaceCreated(javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		mSquare.onSurfaceCreated(gl, config);
		mTriangle.onSurfaceCreated(gl, config);
		mCube.onSurfaceCreated(gl, config);
		mShapeL.onSurfaceCreated(gl, config);
		mCylinder.onSurfaceCreated(gl, config);

	}

	/* (non-Javadoc)
	 * @see com.example.carldemo.opengl.IDrawFrame#onSurfaceChanged(javax.microedition.khronos.opengles.GL10, int, int)
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		int rr = 0;
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			rr = 90;
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			rr = -90;
		}
		if (mCylinder != null) {
			mCylinder.setyAngle(100);
		}
		return true;
	}

}
