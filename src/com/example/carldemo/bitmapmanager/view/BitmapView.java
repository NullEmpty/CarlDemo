package com.example.carldemo.bitmapmanager.view;

import com.example.carldemo.bitmapmanager.AbsBitmapLoadRunnable;
import com.example.carldemo.bitmapmanager.BitmapRuntime;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * @author: Peichen Xu
 * @since: 2015-6-24
 */
public class BitmapView extends ImageView {

	private static final String TAG = BitmapView.class.getSimpleName();

	private String mUri;
	private String mPathName;

	public BitmapView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public BitmapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public BitmapView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void requestBitmap(String uri, String path) {
		if (TextUtils.isEmpty(uri)) {
			setImageBitmap(null);
			return;
		}
		if (!uri.equals(mUri)) {
			mUri = uri;
			mPathName = path;
			setImageBitmap(null);

			Bitmap bitmap = BitmapRuntime.getInstance().requestBitmap(mUri, mPathName,
					new BitmapRunnable(mUri));
			if (bitmap != null && !bitmap.isRecycled()) {
				setImageBitmap(bitmap);
			}
		}

	}

	private class BitmapRunnable extends AbsBitmapLoadRunnable {

		public BitmapRunnable(String uri) {
			super(uri);
		}

		@Override
		public void onBitmapLoad(String uri, Bitmap bitmap) {
			if (TextUtils.isEmpty(uri)) {
				return;
			}
			if (bitmap == null || bitmap.isRecycled()) {
				return;
			}
			if (uri.equals(mUri)) {
				setImageBitmap(bitmap);
			} else {
				Log.e(TAG, "url != mUrl");
			}
		}

	}

}
