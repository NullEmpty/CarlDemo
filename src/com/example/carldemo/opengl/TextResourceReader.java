package com.example.carldemo.opengl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.Resources.NotFoundException;

/**
 * @author: Peichen Xu
 * @since: 2015-10-19
 */
public class TextResourceReader {
	
	public static String readTextFileFromResource(Context context, int resourceId) {
		StringBuilder sb = new StringBuilder();
		try {
			InputStream inputStream = context.getResources().openRawResource(resourceId);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
