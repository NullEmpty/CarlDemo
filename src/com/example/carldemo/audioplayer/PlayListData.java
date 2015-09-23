package com.example.carldemo.audioplayer;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.example.carldemo.tools.Utils;

/**
 * @author Peichen Xu
 *
 */
public class PlayListData {
	
	private static PlayListData mInstance;
	private List<AudioItem> mList;
	private PlayListData(){
		mList = new ArrayList<AudioItem>();
	}
	
	public static synchronized PlayListData getInstance() {
		if (mInstance == null) {
			mInstance = new PlayListData();
		}
		return mInstance;
	}
	
	public void setDataList(List<AudioItem> list) {
		mList = list;
	}
	
	public AudioItem getNext(AudioItem item) {
		if (Utils.isNullOrEmpty(mList)) {
			return null;
		}
		int index = mList.indexOf(item) + 1;
		Log.e("data", "index = " + index);
		if (index < 0 || index >= mList.size()) {
			index = 0;
		}
		return mList.get(index);
	}
	

}
