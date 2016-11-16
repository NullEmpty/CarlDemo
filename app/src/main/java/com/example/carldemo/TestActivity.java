package com.example.carldemo;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

/**
 * @author: Peichen Xu
 * @since: 2015-9-23
 */
public class TestActivity extends Activity {

	private Handler mHandler;
	private PendingIntent mPermissionIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		TextView txv = new TextView(this);
		txv.setText("hello world");
		layout.addView(txv);
		ImageView iv = new ImageView(this);
		iv.setImageResource(R.drawable.beach);
		layout.addView(iv);
		Button btn1 = new Button(this);
		btn1.setText("BUTTON_1");
		btn1.setWidth(500);
		layout.addView(btn1, 500, 200);
		Button btn2 = new Button(this);
		btn2.setText("BUTTON_2");
		layout.addView(btn2);
		layout.setRotationY(180);
		setContentView(layout);
		MyThread thread = new MyThread("thread-1");
		thread.start();
		mHandler = new Handler(thread.getLooper(), thread);

		Message message = mHandler.obtainMessage(0, Thread.currentThread().getName());
		mHandler.sendMessage(message);


		UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
		HashMap<String, UsbDevice> map = manager.getDeviceList();
		Log.e("TAG", "map=" + map);
		UsbDevice androidDevice = null;
		for (UsbDevice d : map.values()) {
			if (d.getVendorId() == 8921) {
				androidDevice = d;
			}
		}
		if (androidDevice != null) {

			UsbDeviceConnection conn = manager.openDevice(androidDevice);
		}


		mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
		IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
		registerReceiver(mUsbReceiver, filter);

	}

	public static class MyThread extends HandlerThread implements Callback {
		public MyThread(String name) {
			super(name);
		}

		@Override
		public boolean handleMessage(Message msg) {
			Log.e("TAG", "my=" + Thread.currentThread().getName() + ", rec=" + (String) msg.obj);
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
				}

				;
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

	private static final String ACTION_USB_PERMISSION =
			"com.android.example.USB_PERMISSION";
	private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (ACTION_USB_PERMISSION.equals(action)) {
				synchronized (this) {
					UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

					if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
						if (device != null) {
							//call method to set up device communication
							Log.d("TAG", "call method to set up device communication" + device);
						}
					} else {
						Log.d("TAG", "permission denied for device " + device);
					}
				}
			}
		}
	};

}