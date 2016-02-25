package com.example.carldemo.gallery3d;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.carldemo.R;

/**
 * Created by pcxu on 2016/2/19.
 */
public class GalleryAdapter extends BaseAdapter {

    private int ITEM_TYPE_1 = 0;
    private int ITEM_TYPE_2 = 1;
    private int ITEM_TYPE_3 = 2;

    private Context mContext;
    private int[] mResIds = new int[10];

    public GalleryAdapter(Context context) {
        mContext = context;
    }

    public void setData(int[] data) {
        mResIds = data;
    }

    @Override
    public int getCount() {
        return mResIds.length;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_1;
        } else if (position == 1 || position == 2 || position == 3) {
            return ITEM_TYPE_2;
        } else {
            return ITEM_TYPE_3;
        }
    }

    @Override
    public Integer getItem(int position) {
        return mResIds[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int itemType = getItemViewType(position);
        if (itemType == ITEM_TYPE_1) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gallery1, parent, false);

            return convertView;
        } else if (itemType == ITEM_TYPE_2) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gallery2, parent, false);
            }
            return convertView;
        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gallery3, parent, false);
            }
        }
        return convertView;
    }
}
