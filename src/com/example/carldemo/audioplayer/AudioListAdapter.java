package com.example.carldemo.audioplayer;

import java.util.ArrayList;
import java.util.List;

import com.example.carldemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Peichen Xu
 *
 */
public class AudioListAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<AudioItem> mList = new ArrayList<AudioItem>();
	public AudioListAdapter(Context context){
		mContext = context;
	}
	
	public void setList(List<AudioItem> list) {
		mList.clear();
		mList.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public AudioItem getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_audio_list, parent, false);
			holder = new ViewHolder();
			holder.txvName = (TextView)convertView.findViewById(R.id.txv_name);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		AudioItem item = getItem(position);
		holder.txvName.setText(item.getName());
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView txvName;
	}

}
