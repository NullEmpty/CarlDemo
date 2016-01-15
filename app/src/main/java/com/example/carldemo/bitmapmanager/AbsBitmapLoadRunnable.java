package com.example.carldemo.bitmapmanager;

import android.graphics.Bitmap;

/**
 * @author: Peichen Xu
 * @since: 2015-6-24
 */
public abstract class AbsBitmapLoadRunnable implements Runnable {

	private String mUri;
	private Bitmap mBitmap;
	public AbsBitmapLoadRunnable(String uri) {
		mUri = uri;
	}
	
	public final void setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
	}
	
	@Override
	public void run() {
		if (mBitmap != null) {
			onBitmapLoad(mUri, mBitmap);
		}
	}
	
	public abstract void onBitmapLoad(String uri, Bitmap bitmap);

}
