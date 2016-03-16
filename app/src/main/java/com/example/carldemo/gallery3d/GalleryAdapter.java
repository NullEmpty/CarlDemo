package com.example.carldemo.gallery3d;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.carldemo.R;
import com.royole.bwgallery.MirrorItemView;

/**
 * Created by pcxu on 2016/2/19.
 */
public class GalleryAdapter extends BaseAdapter {

    public static int ITEM_TYPE_1 = 0;
    public static int ITEM_TYPE_2 = 1;
    public static int ITEM_TYPE_3 = 2;

    private Context mContext;
    private ResultInfo mResultInfo;

    public GalleryAdapter(Context context) {
        mContext = context;
    }

    public void setData(ResultInfo info) {
        mResultInfo = info;

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mResultInfo == null || mResultInfo.getItemInfoList() == null) {
            return 0;
        }
        return mResultInfo.getItemInfoList().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mResultInfo == null) {
            return ITEM_TYPE_2;
        }

        ItemInfo info = getItem(position);
        if (info.getType() == ItemInfo.TYPE_1) {
            return ITEM_TYPE_1;
        } else if (info.getType() == ItemInfo.TYPE_2) {
            return ITEM_TYPE_2;
        } else {
            return ITEM_TYPE_3;
        }

    }

    public AppInfo getInfoFromIndex(int position) {
        return mResultInfo.getAppInfo(position);
    }

    @Override
    public ItemInfo getItem(int position) {
        return mResultInfo == null ? null : mResultInfo.getItemInfoList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemInfo itemInfo = getItem(position);
        int itemType = getItemViewType(position);

        MirrorItemView layout = null;
        if (convertView == null) {
            Log.e("TAG", "adapter new!!!!!!!!!");
            if (itemType == ITEM_TYPE_1) {
                layout = (MirrorItemView) LayoutInflater.from(mContext).inflate(R.layout.item_gallery1, parent, false);
            } else if (itemType == ITEM_TYPE_2) {
                layout = (MirrorItemView) LayoutInflater.from(mContext).inflate(R.layout.item_gallery2, parent, false);
            } else {
                layout = (MirrorItemView) LayoutInflater.from(mContext).inflate(R.layout.item_gallery3, parent, false);
            }
        } else {
            layout = (MirrorItemView) convertView;
            Log.e("TAG", "adapter reuse!!!!!!!!!");
        }
        int count = itemInfo.getCount();
        int start = itemInfo.getStart();
        GalleryLinearLayout galleryLayout = (GalleryLinearLayout) layout.getChildAt(0);
        if (count == 1) {
            galleryLayout.setData(mResultInfo.getAppInfo(start));
        } else if (count == 2) {
            galleryLayout.setData(mResultInfo.getAppInfo(start), mResultInfo.getAppInfo(start + 1));
        } else if (count == 3) {
            galleryLayout.setData(mResultInfo.getAppInfo(start), mResultInfo.getAppInfo(start + 1), mResultInfo.getAppInfo(start + 2));
        } else if (count == 4) {
            galleryLayout.setData(mResultInfo.getAppInfo(start), mResultInfo.getAppInfo(start + 1), mResultInfo.getAppInfo(start + 2), mResultInfo.getAppInfo(start + 3));
        }
        return layout;

    }
}
