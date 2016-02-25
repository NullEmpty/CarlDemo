package com.example.carldemo.opengl;

import android.view.KeyEvent;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author: Peichen Xu
 * @since: 2015-10-13
 */
public class Octagon implements IDrawFrame {
	
	private float A = 0.41f;
	private float[] mVertexArray = {
		0.0f, 0.0f, 0,
		-A, 1.0f, 0,
		-1.0f, A, 0,
		-1.0f, -A, 0,
		-A, -1.0f, 0,
		A, -1.0f, 0,
		1.0f, -A, 0,
		1.0f, A, 0,
		A, 1.0f, 0
	};
	private short[] mIndiceArray = {
			0,1,2,
			0,2,3,
			0,3,4,
			0,4,5,
			0,5,6,
			0,6,7,
			0,7,8,
			0,8,1
	};
	private float[] mColorArray = {
		1,1,1,1,
		0,1,1,1,
		1,0,1,1,
		1,1,0,1,
		0,0,1,1,
		1,0,0,1,
		0,1,0,1,
		0,0,0,1,
		0.5f,0.5f,0.5f,1
	};
	private FloatBuffer mColorBuffer;
	private FloatBuffer mVertexBuffer;
	private ShortBuffer mIndiceBuffer;
	
	public Octagon() {
		ByteBuffer vbb = ByteBuffer.allocateDirect(mVertexArray.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer = vbb.asFloatBuffer();
		mVertexBuffer.put(mVertexArray);
		mVertexBuffer.position(0);
		
		ByteBuffer ibb = ByteBuffer.allocateDirect(mIndiceArray.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		mIndiceBuffer = ibb.asShortBuffer();
		mIndiceBuffer.put(mIndiceArray);
		mIndiceBuffer.position(0);
		
		ByteBuffer cbb = ByteBuffer.allocateDirect(mColorArray.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		mColorBuffer = cbb.asFloatBuffer();
		mColorBuffer.put(mColorArray);
		mColorBuffer.position(0);
	}

	@Override
	public void draw(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
		
		gl.glDrawElements(GL10.GL_TRIANGLES, mIndiceArray.length, GL10.GL_UNSIGNED_SHORT, mIndiceBuffer);
		
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}

	/* (non-Javadoc)
	 * @see com.example.carldemo.opengl.IDrawFrame#onSurfaceCreated(javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		
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
		return false;
	}

}
