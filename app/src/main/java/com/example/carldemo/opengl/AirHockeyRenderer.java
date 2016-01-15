package com.example.carldemo.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.carldemo.R;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import static android.opengl.GLES20.*;

/**
 * @author: Peichen Xu
 * @since: 2015-10-19
 */
public class AirHockeyRenderer implements Renderer {
	
	private static final int POSITION_COMPONENT_COUNT = 2;
	
	private Context mContext;
	private int program;
	private static final String U_COLOR = "u_Color";
	private int uColorLocation;
	private static final String A_POSITION = "a_Position";
	private int aPositionLocation;

	private FloatBuffer vertexData;
	
	public AirHockeyRenderer(Context context) {
		mContext = context;
		
		float[] tableVertices = {
			0,0,
			9,14,
			0,14,
			
			0,0,
			9,0,
			9,14,
			
			0,7,
			9,7,
			
			4.5f,2,
			4.5f,12
		};
		vertexData = ByteBuffer.allocateDirect(tableVertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexData.put(tableVertices);
		vertexData.position(0);
		
	}

	/* (non-Javadoc)
	 * @see android.opengl.GLSurfaceView.Renderer#onSurfaceCreated(javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		glClearColor(0f, 0f, 0f, 0f);
		
		String vertexShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.simple_vertex_shader);
		String fragmentShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.simple_fragment_shader);
		
		int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
		int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
		program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
		
		ShaderHelper.validateProgram(program);
		
		glUseProgram(program);
		
		uColorLocation = glGetUniformLocation(program, U_COLOR);
		aPositionLocation = glGetAttribLocation(program, A_POSITION);
		glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
		glEnableVertexAttribArray(aPositionLocation);
	}

	/* (non-Javadoc)
	 * @see android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.microedition.khronos.opengles.GL10, int, int)
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		glViewport(0, 0, width, width);
	}

	/* (non-Javadoc)
	 * @see android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.microedition.khronos.opengles.GL10)
	 */
	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		glClear(GL_COLOR_BUFFER_BIT);
		glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
		glDrawArrays(GL_TRIANGLES, 0, 6);
		
		glUniform4f(uColorLocation, 1, 0, 0, 1);
		glDrawArrays(GL_LINES, 6, 2);
		
		glUniform4f(uColorLocation, 0, 0, 1, 1);
		glDrawArrays(GL_POINTS, 8, 1);
		
		glUniform4f(uColorLocation, 1, 0, 0, 1);
		glDrawArrays(GL_POINTS, 9, 1);

	}

}
