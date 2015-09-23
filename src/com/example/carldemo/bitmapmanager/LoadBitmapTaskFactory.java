package com.example.carldemo.bitmapmanager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.util.Log;

/**
 * @author: Peichen Xu
 * @since: 2015-6-24
 */
public class LoadBitmapTaskFactory {

	private static final String TAG = LoadBitmapTaskFactory.class
			.getSimpleName();

	private LoadBitmapTaskFactory() {

	}

	public static AbsLoadBitmapTask getTask(String uri, String path,
			AbsBitmapLoadRunnable runnable, Handler handler) {
		AbsLoadBitmapTask task = null;
		Scheme scheme = Scheme.ofUri(uri);
		switch (scheme) {
		case FILE:
			task = new LoadBitmapTaskForFile(uri, path, runnable, handler);
			break;
		case VIDEO:
			task = new LoadBitmapTaskForVideo(uri, path, runnable, handler);
			break;
		case HTTP:
			task = new LoadBitmapTaskForNet(uri, path, runnable, handler);
			break;
		case UNKNOWN:
			task = new LoadBitmapTaskForUnknown(uri, path, runnable, handler);
			break;
			
		default:
			task = new LoadBitmapTaskForUnknown(uri, path, runnable, handler);
			break;
		}
		return task;
	}

	public static class LoadBitmapTaskForFile extends AbsLoadBitmapTask {

		public LoadBitmapTaskForFile(String uri, String path,
				AbsBitmapLoadRunnable l, Handler handler) {
			super(uri, path, l, handler);
		}

		@Override
		protected Bitmap tryLoadBitmap(String uri, String path) {
			String url = Scheme.FILE.crop(uri);
			Options options = new Options();
			options.inSampleSize = 4;
			Bitmap bitmap = BitmapFactory.decodeFile(url, options);
			return bitmap;
		}
	}

	public static class LoadBitmapTaskForVideo extends AbsLoadBitmapTask {

		public LoadBitmapTaskForVideo(String uri, String path,
				AbsBitmapLoadRunnable l, Handler handler) {
			super(uri, path, l, handler);
		}

		@Override
		protected Bitmap tryLoadBitmap(String uri, String path) {
			String url = Scheme.VIDEO.crop(uri);
			return getVideoThumbnail1(url);
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

	}
	
	public static class LoadBitmapTaskForNet extends AbsLoadBitmapTask {

		public LoadBitmapTaskForNet(String uri, String path,
				AbsBitmapLoadRunnable runnable, Handler handler) {
			super(uri, path, runnable, handler);
		}

		@Override
		protected Bitmap tryLoadBitmap(String uri, String path) {
			// TODO Auto-generated method stub
			//1,load bitmap from sdcard
			//2,if bitmap is not exists in sdcard, then load bitmap from net
			//3,save the bitmap in sdcard after load bitmap from net
			return null;
		}
		
	}

	public static class LoadBitmapTaskForUnknown extends AbsLoadBitmapTask {

		public LoadBitmapTaskForUnknown(String uri, String path,
				AbsBitmapLoadRunnable runnable, Handler handler) {
			super(uri, path, runnable, handler);
		}

		@Override
		protected Bitmap tryLoadBitmap(String uri, String path) {
			Log.e(TAG, "Scheme is unknown, uri-->" + uri);
			return null;
		}

	}
}
