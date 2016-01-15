package com.example.carldemo.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.carldemo.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

/**
 * @author: Peichen Xu
 * @since: 2015-10-13
 */
public class Triangle implements IDrawFrame {
	
	private Context mContext;
	
	private float[] mVertexArray = {
			0,1,1,
			-1.0f, 0.0f, 0.0f,
			1, 0, 0.0f,
			0.0f, 1.73f, 0.0f
	};
	private FloatBuffer mVertexBuffer;
	
	private short[] mIndiceArray = {
		0, 1, 2,
		0,2,3,
		0,3,1,
		1,3,2
	};
	private ShortBuffer mIndiceBuffer;
	
	private float[] mColorArray = {
		1,1,1,1,
		0,1,1,1,
		1,0,1,1,
		1,1,0,1
	};
	private FloatBuffer mColorBuffer;
	
	private float[] mTextureCoordinates = {
		0,0,
		0,1,
		1,1,
		1,0
	};
	
	private FloatBuffer mTextureBuffer;
	private int mTextureId;
	private Bitmap mBitmap;
	
	public Triangle(Context context) {
		mContext = context;
		
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
		
		ByteBuffer tbb = ByteBuffer.allocateDirect(mTextureCoordinates.length * 4);
		tbb.order(ByteOrder.nativeOrder());
		mTextureBuffer = tbb.asFloatBuffer();
		mTextureBuffer.put(mTextureCoordinates);
		mTextureBuffer.position(0);
		
	}
	
	private int angle = 0;

	@Override
	public void draw(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glRotatef(angle ++, 1, 2, 1);
		
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
		
		gl.glDrawElements(GL10.GL_TRIANGLES, mIndiceArray.length, GL10.GL_UNSIGNED_SHORT, mIndiceBuffer);
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
		
	}

	/* (non-Javadoc)
	 * @see com.example.carldemo.opengl.IDrawFrame#onSurfaceCreated(javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.t);

		gl.glEnable(GL10.GL_TEXTURE_2D);
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		mTextureId = textures[0];
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
		mBitmap.recycle();
	}

	/* (non-Javadoc)
	 * @see com.example.carldemo.opengl.IDrawFrame#onSurfaceChanged(javax.microedition.khronos.opengles.GL10, int, int)
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		
	}

}
