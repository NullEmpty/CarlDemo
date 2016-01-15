package com.example.carldemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

/**
 * @author: Peichen Xu
 * @since: 2015-9-23
 */
public class TestActivity extends Activity {
	
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyThread thread = new MyThread("thread-1");
		thread.start();
		mHandler = new Handler(thread.getLooper(), thread);
		
		Message message = mHandler.obtainMessage(0, Thread.currentThread().getName());
		mHandler.sendMessage(message);
	}
	
	public static class MyThread extends HandlerThread implements Callback {
		public MyThread(String name) {
			super(name);
		}

		@Override
		public boolean handleMessage(Message msg) {
			Log.e("TAG", "my=" + Thread.currentThread().getName() + ", rec=" + (String)msg.obj);
			return false;
		}
	}
	
	public class MyThread2 extends Thread {
		
		@Override
		public void run() {
			Looper.prepare();
			mHandler = new Handler() {
				public void handleMessage(Message msg) {
					Log.e("TAG", "receive msg");
				};
			};
			Looper.loop();
		}
	}
	
	@Override
	protected void onDestroy() {
		if (mHandler != null) {
			mHandler.removeMessages(0);
		}
		super.onDestroy();
	}

}
