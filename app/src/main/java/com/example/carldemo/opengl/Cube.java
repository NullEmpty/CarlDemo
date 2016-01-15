package com.example.carldemo.opengl;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import com.example.carldemo.R;

/**
 * @author: Peichen Xu
 * @since: 2015-10-14
 */
public class Cube implements IDrawFrame {
	
	private Context mContext;
	
	private float[] mVertxts = {
		0,0,0,
		0,0,1,
		1,0,1,
		1,0,0,
		0,1,0,
		0,1,1,
		1,1,1,
		1,1,0
	};
	
	private short[] mIndices = {
		0,3,2,
		0,2,1,
		0,4,7,
		0,7,3,
		0,1,5,
		0,5,4,
		6,7,4,
		6,4,5,
		6,5,1,
		6,1,2,
		6,2,3,
		6,3,7
	};
	
	private float[] mColors = {
		1,1,1,1,
		0,1,1,1,
		1,0,1,1,
		1,1,0,1,
		0,1,0,1,
		0,0,1,1,
		1,0,0,1,
		0,0,0,1
	};
	
	private float[] mTextures = {
			0,1,
			0,1,
			1,1,
			1,1,
			0,0,
			0,0,
			1,0,
			1,0
	};
	
	private FloatBuffer mVertexBuffer;
	private ShortBuffer mIndiceBuffer;
	private FloatBuffer mColorBuffer;
	private FloatBuffer mTextureBuffer;
	private int mTextureId;
	private Bitmap mBitmap;
	
	public Cube(Context context) {
		mContext = context;
		
		mVertexBuffer = BufferUtil.toFloatBuffer(mVertxts);
		mIndiceBuffer = BufferUtil.toShortBuffer(mIndices);
		mColorBuffer = BufferUtil.toFloatBuffer(mColors);
		mTextureBuffer = BufferUtil.toFloatBuffer(mTextures);
	}

	/* (non-Javadoc)
	 * @see com.example.carldemo.opengl.IDrawFrame#onSurfaceCreated(javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		gl.glEnable(GL10.GL_TEXTURE_2D);
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		mTextureId = textures[0];
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.t2);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
		mBitmap.recycle();
	}
	
	private int angle = 0;

	/* (non-Javadoc)
	 * @see com.example.carldemo.opengl.IDrawFrame#draw(javax.microedition.khronos.opengles.GL10)
	 */
	@Override
	public void draw(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
//		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
//		gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
		
		gl.glRotatef(angle++, 1, 2, 1);
		gl.glDrawElements(GL10.GL_TRIANGLES, mIndices.length, GL10.GL_UNSIGNED_SHORT, mIndiceBuffer);
		
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);

	}

	/* (non-Javadoc)
	 * @see com.example.carldemo.opengl.IDrawFrame#onSurfaceChanged(javax.microedition.khronos.opengles.GL10, int, int)
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub

	}

}
