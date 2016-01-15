package com.example.carldemo.bitmapmanager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

/**
 * @author: Peichen Xu
 * @since: 2015-6-24
 */
public class BitmapRuntime {

	private ThreadPoolExecutor mExecutor = null;
	private Handler mHandler = new Handler(Looper.getMainLooper());

	private BitmapManager mBitmapManager;
	private static BitmapRuntime mRuntime;

	private BitmapRuntime() {
		mBitmapManager = BitmapManager.getInstance();
		mExecutor = new ThreadPoolExecutor(2, 3, 3, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(200),
				new RejectedExecutionHandler() {

					@Override
					public void rejectedExecution(Runnable r,
							ThreadPoolExecutor executor) {
						// TODO Auto-generated method stub

					}
				});
	}

	public synchronized static BitmapRuntime getInstance() {
		if (mRuntime == null) {
			mRuntime = new BitmapRuntime();
		}
		return mRuntime;
	}

	public Bitmap requestBitmap(String uri, String path,
			AbsBitmapLoadRunnable runnable) {
		if (TextUtils.isEmpty(uri)) {
			return null;
		}
		Bitmap bitmap = mBitmapManager.getBitmapFromMemory(uri);
		if (bitmap != null) {
			return bitmap;
		}

		mExecutor.submit(LoadBitmapTaskFactory.getTask(uri, path, runnable,
				mHandler));

		return null;
	}

}
