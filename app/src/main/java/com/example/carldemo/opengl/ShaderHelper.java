package com.example.carldemo.opengl;

import static android.opengl.GLES20.*;
import android.util.Log;
/**
 * @author: Peichen Xu
 * @since: 2015-10-19
 */
public class ShaderHelper {
	
	private static final String TAG = ShaderHelper.class.getSimpleName();
	
	public static int compileShader(int type, String shaderCode) {
		final int shaderObjId = glCreateShader(type);
		
		if (shaderObjId == 0) {
			Log.w(TAG, "could not create new shader.");
			return 0;
		}
		glShaderSource(shaderObjId, shaderCode);
		
		glCompileShader(shaderObjId);
		final int[] compileStatus = new int[1];
		glGetShaderiv(shaderObjId, GL_COMPILE_STATUS, compileStatus, 0);
		if (compileStatus[0] == 0) {
			glDeleteShader(shaderObjId);
			Log.w(TAG, "compilation of shader failed.");
		}
		
		return shaderObjId;
	}
	
	public static int compileVertexShader(String shaderCode) {
		return compileShader(GL_VERTEX_SHADER, shaderCode);
	}
	
	public static int compileFragmentShader(String shaderCode) {
		return compileShader(GL_FRAGMENT_SHADER, shaderCode);
	}
	
	public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
		final int programObjId = glCreateProgram();
		if (programObjId == 0) {
			Log.w(TAG, "could not create new program.");
			return 0;
		}
		
		glAttachShader(programObjId, vertexShaderId);
		glAttachShader(programObjId, fragmentShaderId);
		glLinkProgram(programObjId);
		int[] linkStatus = new int[1];
		glGetProgramiv(programObjId, GL_LINK_STATUS, linkStatus, 0);
		if (linkStatus[0] == 0) {
			glDeleteProgram(programObjId);
			Log.w(TAG, "link of program failed.");
			return 0;
		}
		return programObjId;
	}
	
	public static boolean validateProgram(int programObjId) {
		glValidateProgram(programObjId);
		int[] validateStatus = new int[1];
		glGetProgramiv(programObjId, GL_VALIDATE_STATUS, validateStatus, 0);
		return validateStatus[0] != 0;
	}

}
