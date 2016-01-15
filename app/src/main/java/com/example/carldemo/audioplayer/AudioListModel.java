package com.example.carldemo.audioplayer;

import java.util.List;

import com.example.carldemo.tools.AudioUtil;
import com.example.carldemo.tools.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class AudioListModel {
	
	private Context mContext;
	private IAudioListModelListener mListener;
	
	private Task mTaskSd;
	
	public static interface IAudioListModelListener {
		public void onStart();
		public void onFinish(boolean isSucc, List<AudioItem> list);
	}
	
	public AudioListModel(Context context, IAudioListModelListener l) {
		mContext = context;
		mListener = l;
	}
	
	public void startScanFromSd() {
		mTaskSd = new Task();
		mTaskSd.execute();
	}
	
	
	private class Task extends AsyncTask<Void, Void, List<AudioItem>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mListener != null) {
				mListener.onStart();
			}
		}

		@Override
		protected List<AudioItem> doInBackground(Void... params) {
			List<AudioItem> list = AudioUtil.getSdAudioItems(mContext);
//			if (!Utils.isNullOrEmpty(list)) {
//				for (AudioItem item : list) {
//					Log.e("TAG", item.toString());
//				}
//			}
			return list;
		}

		@Override
		protected void onPostExecute(List<AudioItem> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (mListener != null) {
				mListener.onFinish(true, result);
			}
			
			PlayListData.getInstance().setDataList(result);
		}
		
	}
	
}
