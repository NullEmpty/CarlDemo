package com.example.carldemo.tools;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.carldemo.audioplayer.AudioItem;

/**
 * @author Peichen Xu
 * 
 */
public class AudioUtil {

	public static List<AudioItem> getSdAudioItems(Context context) {
		ContentResolver resolver = context.getContentResolver();
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		String[] projection = new String[] { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.DISPLAY_NAME,
				MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.SIZE,
				MediaStore.Audio.Media.DURATION };
		String selection = MediaStore.Audio.Media.MIME_TYPE + " = ?";
		String[] selectionArgs = new String[] { "audio/mpeg" };
		String sortOrder = MediaStore.Audio.Media.DATE_MODIFIED + " desc ";
		Cursor cursor = resolver.query(uri, projection, selection,
				selectionArgs, sortOrder);
		if (cursor == null) {
			return null;
		}

		List<AudioItem> list = new ArrayList<AudioItem>();
		cursor.moveToFirst();
		do {
			AudioItem item = new AudioItem();
			item.setId(Cursors.getString(cursor, MediaStore.Audio.Media._ID));
			item.setName(Cursors.getString(cursor,
					MediaStore.Audio.Media.DISPLAY_NAME));
			item.setPath(Cursors.getString(cursor, MediaStore.Audio.Media.DATA));
			item.setSize(Cursors.getLong(cursor, MediaStore.Audio.Media.SIZE));
			item.setDuration(Cursors.getInt(cursor,
					MediaStore.Audio.Media.DURATION));

			list.add(item);
		} while (cursor.moveToNext());

		return list;
	}

}
