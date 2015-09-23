package com.example.carldemo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainGridAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<MainItem> mList = new ArrayList<MainItem>();
	public MainGridAdapter(Context context) {
		mContext = context;
	}
	
	public void setList(List<MainItem> itemList) {
		mList.addAll(itemList);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public MainItem getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder =  null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_grid, parent, false);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.txv_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MainItem item = getItem(position);
		holder.tvTitle.setText(item.getTitleId() <= 0 ? "0" : mContext.getString(item.getTitleId()));
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView tvTitle;
	}

}
