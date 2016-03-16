package com.example.carldemo.gallery3d;

/**
 * Created by pcxu on 2016/2/27.
 */
public class ItemInfo {
    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    public static final int TYPE_3 = 3;

    private int mType = 2;
    private int mStart;
    private int mCount;

    public void setType(int type) {
        mType = type;
    }

    public int getType() {
        return mType;
    }

    public void setStart(int position) {
        mStart = position;
    }

    public int getStart() {
        return mStart;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public int getCount() {
        return mCount;
    }

    @Override
    public String toString() {
        return "[" + mStart + "," + mCount + "]";
    }
}
