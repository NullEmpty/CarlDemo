package com.example.carldemo.audioplayer;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.carldemo.R;
import com.example.carldemo.audioplayer.AudioListModel.IAudioListModelListener;
import com.example.carldemo.tools.Utils;

/**
 * @author Peichen Xu
 *
 */
public class AudioListActivity extends Activity {
	
	private ListView mListView;
	private AudioListAdapter mAdapter;
	private Context mContext;
	private AudioListModel mListModel;
	
	private Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_audiolist_activity);
		mContext = this;
		initValues();
		initView();
		
		mListModel.startScanFromSd();
	}

	private void initValues() {
		mListModel = new AudioListModel(mContext, getAudioListModelListener());
	}

	private void initView() {
		mListView = (ListView) findViewById(R.id.listview);
		mListView.setOnItemClickListener(getItemClickListener());
	}
	
	private OnItemClickListener getItemClickListener() {
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mAdapter == null) {
					return;
				}
				AudioItem item = mAdapter.getItem(position);
				if (item == null) {
					return;
				}
				Intent intent = new Intent();
				intent.setClass(mContext, AudioDetailActivity.class);
				intent.putExtra(AudioDetailActivity.AUDIO_ITEM, AudioItem.toJson(item));
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		};
	}
	
	private IAudioListModelListener getAudioListModelListener() {
		return new IAudioListModelListener() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFinish(boolean isSucc, List<AudioItem> list) {
				if (mHandler != null) {
					mHandler.post(new ShowDataRunnable(list));
				}
			}
		};
	}
	
	private class ShowDataRunnable implements Runnable {
		private List<AudioItem> list;
		ShowDataRunnable(List<AudioItem> list){
			this.list = list;
		}
		
		@Override
		public void run() {
			if (showList(list)) {
				
			}
		}
	}
	
	private boolean showList(List<AudioItem> list) {
		if (Utils.isNullOrEmpty(list)) {
			return false;
		}
		if (mAdapter == null) {
			mAdapter = new AudioListAdapter(mContext);
			mAdapter.setList(list);
			mListView.setAdapter(mAdapter);
		} else {
			mAdapter.setList(list);
		}
		return true;
	}
	
	
}
