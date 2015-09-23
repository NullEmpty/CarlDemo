package com.example.carldemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

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
		
		Log.e("TAG", "r:" + getRemindState());
		setRemindState();
		Log.e("TAG", "r:" + getRemindState());
	}
	
	private void initView(){
		GridView gridView = (GridView) findViewById(R.id.gridview);
		mAdapter = new MainGridAdapter(this);
		mAdapter.setList(getContentList());
		gridView.setAdapter(mAdapter);
		gridView.setOnItemClickListener(getItemClickListener());
	}
	
	private OnItemClickListener getItemClickListener() {
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (mAdapter == null) {
					return;
				}
				MainItem item = mAdapter.getItem(arg2);
				if (item == null) {
					return;
				}
				OnClickListener clickListener = item.getClickListener();
				if (clickListener != null) {
					clickListener.onClick(arg1);
				}
			}
		};
	}
	
	private List<MainItem> getContentList() {
		List<MainItem> list = new ArrayList<MainItem>();
		MainItem itemAudioPlayer = new MainItem();
		itemAudioPlayer.setTitleId(R.string.title_audioplayer);
		itemAudioPlayer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mContext, AudioListActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		list.add(itemAudioPlayer);
		
		MainItem itemPicker = new MainItem();
		itemPicker.setTitleId(R.string.title_pickerview);
		itemPicker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mContext, PickerViewActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		list.add(itemPicker);
		
		MainItem itemCircleDial = new MainItem();
		itemCircleDial.setTitleId(R.string.title_circledial);
		itemCircleDial.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mContext, CircleActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		list.add(itemCircleDial);
		
		MainItem itemDial = new MainItem();
		itemDial.setTitleId(R.string.title_dial);
		itemDial.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mContext, DialActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		list.add(itemDial);
		
		MainItem itemBmpM = new MainItem();
		itemBmpM.setTitleId(R.string.title_bmpmanager);
		itemBmpM.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mContext, BitmapManagerTestActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		list.add(itemBmpM);
		
		MainItem itemView = new MainItem();
		itemView.setTitleId(R.string.title_view);
		itemView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mContext, TestViewActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		list.add(itemView);
		
		MainItem itemTest = new MainItem();
		itemTest.setTitleId(R.string.title_test);
		itemTest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mContext, TestActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		list.add(itemTest);
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

}
