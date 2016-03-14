package com.example.carldemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.carldemo.audioplayer.AudioListActivity;
import com.example.carldemo.bitmapmanager.BitmapManagerTestActivity;
import com.example.carldemo.circledial.CircleActivity;
import com.example.carldemo.circledial.DialActivity;
import com.example.carldemo.pickerview.PickerViewActivity;
import com.example.carldemo.view.TestViewActivity;

public class MainActivty extends Activity {
	
	private Context mContext;
	private MainGridAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);

		mContext = this;
		initView();

	}
	
	private void initView(){
		GridView gridView = (GridView) findViewById(R.id.gridview);
		mAdapter = new MainGridAdapter(this);
		mAdapter.setList(getContentList2());
		gridView.setAdapter(mAdapter);
		gridView.setOnItemClickListener(getItemClickListener());
	}
	
	private OnItemClickListener getItemClickListener() {
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.e("TT", "onClick:" + arg1);
				if (mAdapter == null) {
					return;
				}
				MainItem item = mAdapter.getItem(arg2);
				if (item == null) {
					return;
				}
				Intent intent = new Intent();
				intent.setClassName(mContext, item.getActivityName());
				mContext.startActivity(intent);
			}
		};
	}
	
	private List<MainItem> getContentList2() {
		List<MainItem> list = new ArrayList<MainItem>();
		PackageManager pm = getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory("carl.intent.category.SAMPLE_CODE");
		List<ResolveInfo> infoList = pm.queryIntentActivities(intent, 0);
		for (ResolveInfo info : infoList) {
			MainItem item = new MainItem();
			item.setTitleStr(info.loadLabel(pm).toString());
			item.setActivityName(info.activityInfo.name);
			
			list.add(item);
		}
		return list;
	}
	
	public void setRemindState() {
		Context ct = null;
		try {
			ct = mContext.createPackageContext("com.royole.setting",
					Context.CONTEXT_IGNORE_SECURITY);
			SharedPreferences sp = ct.getSharedPreferences("remind_refrence",
					Activity.MODE_WORLD_WRITEABLE + Activity.MODE_WORLD_WRITEABLE);
			sp.edit().putBoolean("gesture_refrence", false).commit();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getRemindState() {
		Context ct = null;
		try {
			ct = this.createPackageContext("com.royole.setting",
					Context.CONTEXT_IGNORE_SECURITY);
			SharedPreferences sp = ct.getSharedPreferences("remind_refrence",
					Activity.MODE_WORLD_READABLE | Activity.MODE_MULTI_PROCESS);
			return sp.getBoolean("gesture_refrence", true);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.e("TT", event.toString());
		return super.onTouchEvent(event);
	}

}
