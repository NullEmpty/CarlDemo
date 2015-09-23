package com.example.carldemo.pickerview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.carldemo.R;

/**
 * @author Peichen Xu
 *
 */
public class PickerViewActivity extends Activity {
	
	private PickerView mPickerView;
	private TextView mTxvCenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_picker_view);
		
		initValue();
		initView();
	}

	private void initValue() {
		// TODO Auto-generated method stub
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		mPickerView = (PickerView) findViewById(R.id.picker_view);
		mTxvCenter = (TextView) findViewById(R.id.txv_center);
		
		List<PickerItem> list = getContentList();
		PickerAdapter mAdapter = new PickerAdapter(this, list);
		mPickerView.setAdapter(mAdapter);
		mPickerView.setSelection(mAdapter.getCount() / 2);
		
	}
	
	private List<PickerItem> getContentList() {
		List<PickerItem> list = new ArrayList<PickerItem>();
		
		for (int i = 0; i < 20; i ++) {
			PickerItem item = new PickerItem();
			item.setText("向天再借五百年" + i);
			list.add(item);
		}
		return list;
	}
	
}
