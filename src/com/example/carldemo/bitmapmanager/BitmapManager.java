package com.example.carldemo.bitmapmanager;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * @author: Peichen Xu
 * @since: 2015-6-24
 */
public class BitmapManager {
	
	private LruCache<String, Bitmap> mCache;
	private static BitmapManager mInstance;

	private BitmapManager() {
		int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
		mCache = new LruCache<String, Bitmap>(maxSize) {

			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}
			
		};
		
	}
	
	public synchronized static BitmapManager getInstance() {
		if (mInstance == null) {
			mInstance = new BitmapManager();
		}
		return mInstance;
	}
	
	public Bitmap getBitmapFromMemory(String url) {
		return mCache.get(url);
	}
	
	public void addBitmapToMemory(String url, Bitmap bitmap) {
		if (url != null && bitmap != null) {
			mCache.put(url, bitmap);
		}
	}
	
	public void onDestroy() {
		mCache.evictAll();
	}
}
