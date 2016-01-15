package com.example.carldemo.bitmapmanager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

public class VideoProvider {
	private Context context;

	public VideoProvider(Context context) {
		this.context = context;
	}

	public List<MediaItem> getList() {
		List<MediaItem> list = null;
		if (context != null) {
			Cursor cursor = context.getContentResolver().query(
					MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null,
					null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
			if (cursor != null) {
				list = new ArrayList<MediaItem>();
				while (cursor.moveToNext()) {
					int id = cursor.getInt(cursor
							.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
					String title = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
					String album = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM));
					String artist = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
					String displayName = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
					String mimeType = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
					String path = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
					int duration = cursor
							.getInt(cursor
									.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
					long size = cursor
							.getLong(cursor
									.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
					Bitmap bitmap = getVideoThumbnail(path);
					// Bitmap bitmap = getVideoThumbnail2(path,
					// 210,120,MediaStore.Images.Thumbnails.MINI_KIND);
					MediaItem video = new MediaItem(id, title, album, artist,
							displayName, mimeType, path, size, duration, bitmap);
					list.add(video);

				}
				cursor.close();
			}
		}
		return list;
	}

	private Bitmap getVideoThumbnail(String filePath) {
		Bitmap bitmap = null;

		if (bitmap == null) {
			bitmap = getVideoThumbnail1(filePath);
		}
		if (bitmap != null && bitmap.getWidth() > 300) {
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, 208, 117, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		}
		return bitmap;

	}

	private Bitmap getVideoThumbnail1(String filePath) {
		Bitmap bitmap = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			retriever.setDataSource(filePath);
			bitmap = retriever.getFrameAtTime(-1,
					MediaMetadataRetriever.OPTION_NEXT_SYNC);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			try {
				retriever.release();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	private Bitmap getVideoThumbnail2(String videoPath, int width, int height,
			int kind) {
		Bitmap bitmap = null;
		// ��ȡ��Ƶ������ͼ
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

}