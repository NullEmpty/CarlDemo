package com.example.carldemo.opengl;

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
 * @since: 2015-10-15
 */
public class ShapeL implements IDrawFrame {
	private Context mContext;
	
	private float[] mVertexs = {
		0,0,0,//face 1
		0,1,0,
		1,1,0,
		1,0,0,
		
		0,0,0,//face 2
		0,1,0,
		1,1,0,
		1,0,0,
		
		0,0,0,//face 3
		1,0,0,
		1,0,1,
		0,0,1,
		
		0,0,0,//face 4
		1,0,0,
		1,0,1,
		0,0,1
	};
	
	private short[] mIndices = {
		0,3,2,
		0,2,1,
		
		4,5,6,
		4,6,7,
		
		8,11,10,
		8,10,9,
		
		12,13,14,
		12,14,15
	};
	
	private float[] mTextures = {
		0,1,
		0,0,
		1,0,
		1,1,
		
		1,1,
		1,0,
		0,0,
		0,1,
		
		0,0,
		1,0,
		1,1,
		0,1,
		
		1,0,
		0,0,
		0,1,
		1,1
	};
	
	private FloatBuffer mVertexBuffer;
	private ShortBuffer mIndiceBuffer;
	private FloatBuffer mTextureBuffer;

	private int[] textureIds;
	
	public ShapeL(Context context) {
		mContext = context;
		
		mVertexBuffer = BufferUtil.toFloatBuffer(mVertexs);
		mIndiceBuffer = BufferUtil.toShortBuffer(mIndices);
		mTextureBuffer = BufferUtil.toFloatBuffer(mTextures);
	}

	/* (non-Javadoc)
	 * @see com.example.carldemo.opengl.IDrawFrame#onSurfaceCreated(javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		gl.glEnable(GL10.GL_TEXTURE_2D);
		textureIds = new int[1];
		gl.glGenTextures(1, textureIds, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIds[0]);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.t);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
	
		bitmap.recycle();
	}
	
	private int angle = 0;
	private float cx = 0;
	private float cy = 0;
	private float cz = 0;

	@Override
	public void draw(GL10 gl) {
		// TODO Auto-generated method stub
		
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIds[0]);
		
		gl.glTranslatef(1, -2, 0);
		gl.glRotatef(angle++, cx, cy, cz);
		cx += 0.3f;
		cy += 0.2f;
		cz += 0.4f;
		gl.glDrawElements(GL10.GL_TRIANGLES, mIndices.length, GL10.GL_UNSIGNED_SHORT, mIndiceBuffer);
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
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
