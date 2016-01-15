/**
 * 
 */
package com.example.carldemo.pickerview;

import java.util.ArrayList;
import java.util.List;

import com.example.carldemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Peichen Xu
 *
 */
public class PickerAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<PickerItem> mList = new ArrayList<PickerItem>();
	
	public PickerAdapter(Context context, List<PickerItem> list) {
		mContext = context;
		mList = list;
	}

	@Override
	public int getCount() {
		return mList.size() * 10;
	}

	@Override
	public PickerItem getItem(int position) {
		if (position < 0) {
			position = mList.size() + position;
		} else if (position >= mList.size()) {
			position = position % mList.size();
		}
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_picker, parent, false);
			holder = new ViewHolder();
			holder.txvTitle = (TextView) convertView.findViewById(R.id.txv_title);
			holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		PickerItem item = getItem(position);
		holder.txvTitle.setText(item.getText());
		return convertView;
	}
	
	static class ViewHolder {
		TextView txvTitle;
		ImageView ivIcon;
		LinearLayout layout;
	}

}
