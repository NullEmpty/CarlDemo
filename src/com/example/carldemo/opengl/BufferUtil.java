package com.example.carldemo.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * @author: Peichen Xu
 * @since: 2015-10-14
 */
public class BufferUtil {
	
	private BufferUtil(){
		
	}
	
	public static ShortBuffer toShortBuffer(short[] array) {
		ByteBuffer bb = ByteBuffer.allocateDirect(array.length * 2);
		bb.order(ByteOrder.nativeOrder());
		ShortBuffer sbb = bb.asShortBuffer();
		sbb.put(array);
		sbb.position(0);
		return sbb;
	}
	
	public static FloatBuffer toFloatBuffer(float[] array) {
		ByteBuffer bb = ByteBuffer.allocateDirect(array.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fbb = bb.asFloatBuffer();
		fbb.put(array);
		fbb.position(0);
		return fbb;
	}
	
}
