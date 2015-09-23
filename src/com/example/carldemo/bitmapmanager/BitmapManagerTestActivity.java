package com.example.carldemo.bitmapmanager;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.carldemo.R;
import com.example.carldemo.bitmapmanager.view.BitmapView;

/**
 * @author: Peichen Xu
 * @since: 2015-6-24
 */
public class BitmapManagerTestActivity extends Activity {

	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_bitmapmanager_activity);

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mListView = (ListView) findViewById(R.id.listview);
		List<String> list = new ArrayList<String>();
		list.addAll(getDataListVideo());
		list.addAll(getDataList());
		mListView.setAdapter(new MyAdapter(this, list));
	}
	
	private List<String> getDataList() {
//		File file = new File("/mnt/sdcard/DCIM/.thumbnails");
		File file = new File("/mnt/sdcard/DCIM/Camera");
		File[] files = file.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String filename) {
				// TODO Auto-generated method stub
				return filename.endsWith(".jpg");
			}
		});
		
		List<String> list = new ArrayList<String>();
		for (File f : files) {
			list.add(Scheme.FILE.wrap(f.getPath()));
		}
		return list;
	}
	
	private List<String> getDataListVideo() {
		List<String> list = new ArrayList<String>();
		VideoProvider videoProvider = new VideoProvider(this);
		List<MediaItem> itemList = videoProvider.getList();
		for(MediaItem item : itemList) {
			list.add(Scheme.VIDEO.wrap(item.getRes()));
		}
		
		return list;
	}

	public static class MyAdapter extends BaseAdapter {
		private List<String> list;
		private Context context;

		public MyAdapter(Context context, List<String> list) {
			this.context = context;
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public String getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_bitmapmanager_layout, parent, false);
				holder = new ViewHolder();
				holder.iv = (BitmapView) convertView.findViewById(R.id.iv);
				holder.txvName = (TextView) convertView.findViewById(R.id.txv_name);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			String url = getItem(position);
			Log.e("TAG", url);
			holder.iv.requestBitmap(url, url);
			holder.txvName.setText(url);
			return convertView;
		}

		public static class ViewHolder {
			BitmapView iv;
			TextView txvName;
		}

	}

}
