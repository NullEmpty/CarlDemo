package com.example.carldemo.bitmapmanager;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

/**
 * @author: Peichen Xu
 * @since: 2015-6-24
 */
public abstract class AbsLoadBitmapTask implements Runnable {
	private static final String TAG = AbsLoadBitmapTask.class.getSimpleName();

	private String mUri;
	private String mPath;
	private AbsBitmapLoadRunnable mLoadRunnable;
	private Handler mMainLoopHandler;

	public AbsLoadBitmapTask(String uri, String path, AbsBitmapLoadRunnable runnable,
			Handler handler) {
		mUri = uri;
		mPath = path;
		mLoadRunnable = runnable;
		mMainLoopHandler = handler;
	}

	@Override
	public void run() {
		BitmapManager bitmapManager = BitmapManager.getInstance();
		Bitmap bitmap = bitmapManager.getBitmapFromMemory(mUri);
		if (bitmap != null) {
			notifyLoadBitmap(bitmap);
			return;
		}

		bitmap = tryLoadBitmap(mUri, mPath);
		if (bitmap != null) {
			bitmapManager.addBitmapToMemory(mUri, bitmap);
			notifyLoadBitmap(bitmap);
		} else {
			Log.e(TAG, "try load bitmap fail !!!->" + mUri);
		}
	}

	protected abstract Bitmap tryLoadBitmap(String uri, String path);

	private void notifyLoadBitmap(Bitmap bitmap) {
		if (mLoadRunnable == null || mMainLoopHandler == null) {
			return;
		}
		if (bitmap != null) {
			mLoadRunnable.setBitmap(bitmap);
			mMainLoopHandler.post(mLoadRunnable);
		}
	}

}
