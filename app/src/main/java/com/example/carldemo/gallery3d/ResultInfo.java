package com.example.carldemo.gallery3d;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcxu on 2016/2/27.
 */
public class ResultInfo {

    private List<AppInfo> mApplicationInfoList = new ArrayList<AppInfo>();
    private List<ItemInfo> mItemInfoList = new ArrayList<ItemInfo>();

    public void setApplicationInfoList(List<AppInfo> list) {
        if (mApplicationInfoList != null) {
            mApplicationInfoList.clear();
            mApplicationInfoList.addAll(list);
        }
    }

    public List<AppInfo> getApplicationInfoList() {
        return mApplicationInfoList;
    }

    public void setItemInfoList(List<ItemInfo> list) {
        if (mItemInfoList != null) {
            mItemInfoList.clear();
            mItemInfoList.addAll(list);
        }
    }

    public List<ItemInfo> getItemInfoList() {
        return mItemInfoList;
    }

    public AppInfo getAppInfo(int index) {
        return mApplicationInfoList.get(index);
    }

    public ItemInfo getItemInfo(int position) {
        return mItemInfoList.get(position);
    }

    public void addAppInfo(AppInfo appInfo) {
        if (mApplicationInfoList == null || mItemInfoList == null) {
            return;
        }
        mApplicationInfoList.add(appInfo);
        if (mItemInfoList.size() > 0) {
            ItemInfo itemInfo = mItemInfoList.get(mItemInfoList.size() - 1);
            if (itemInfo.getCount() < 4) {
                itemInfo.setCount(itemInfo.getCount() + 1);
            } else {
                ItemInfo itemInfo2 = new ItemInfo();
                itemInfo2.setStart(itemInfo.getStart() + 4);
                itemInfo2.setCount(1);
                itemInfo2.setType(ItemInfo.TYPE_3);

                mItemInfoList.add(itemInfo2);
            }
        } else {
            ItemInfo itemInfo3 = new ItemInfo();
            itemInfo3.setStart(0);
            itemInfo3.setCount(1);
            itemInfo3.setType(ItemInfo.TYPE_3);

            mItemInfoList.add(itemInfo3);
        }
    }

    public boolean removeAppInfo(String pkgName) {
        if (pkgName == null || pkgName.length() <= 0) {
            return false;
        }
        if (mApplicationInfoList == null) {
            return false;
        }
        boolean changed = false;
        for (AppInfo info : mApplicationInfoList) {
            if (pkgName.equals(info.intent.getComponent().getPackageName())) {
                mApplicationInfoList.remove(info);
                changed = true;
            }
        }
        if (changed) {
            setItemInfoList(ResultInfo.makeItemInfoList(mApplicationInfoList));
        }
        return changed;
    }

    public void updateAppInfo(String pkgName, AppInfo appInfo) {
        if (pkgName == null || pkgName.length() <= 0) {
            return;
        }
        for (AppInfo info : mApplicationInfoList) {
            if (pkgName.equals(info.intent.getComponent().getPackageName())) {
                info = appInfo;
                break;
            }
        }

    }

    public static List<ItemInfo> makeItemInfoList(List<AppInfo> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        List<ItemInfo> resultList = new ArrayList<ItemInfo>();
        int size = list.size();
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.setStart(0);
        itemInfo.setCount(2);
        itemInfo.setType(ItemInfo.TYPE_1);
        resultList.add(itemInfo);

        itemInfo = new ItemInfo();
        itemInfo.setStart(2);
        itemInfo.setCount(1);
        itemInfo.setType(ItemInfo.TYPE_2);
        resultList.add(itemInfo);

        itemInfo = new ItemInfo();
        itemInfo.setStart(3);
        itemInfo.setCount(1);
        itemInfo.setType(ItemInfo.TYPE_2);
        resultList.add(itemInfo);

        itemInfo = new ItemInfo();
        itemInfo.setStart(4);
        itemInfo.setCount(1);
        itemInfo.setType(ItemInfo.TYPE_2);
        resultList.add(itemInfo);

        int index = 5;
        while (size > index) {
            itemInfo = new ItemInfo();
            itemInfo.setType(ItemInfo.TYPE_3);
            itemInfo.setStart(index);
            int count = size - index >= 4 ? 4 : size - index;
            itemInfo.setCount(count);
            resultList.add(itemInfo);

            index += count;
        }

        return resultList;
    }
}
