/**
 * 
 */
package com.example.carldemo.tools;

import android.database.Cursor;

/**
 * @author Peichen Xu
 *
 */
public class Cursors {
	
	public static String getString(Cursor cursor, String columnName) {
		return cursor.getString(cursor.getColumnIndex(columnName));
	}
	
	public static long getLong(Cursor cursor, String columnName) {
		return cursor.getLong(cursor.getColumnIndex(columnName));
	}
	
	public static int getInt(Cursor cursor, String columnName) {
		return cursor.getInt(cursor.getColumnIndex(columnName));
	}

}
