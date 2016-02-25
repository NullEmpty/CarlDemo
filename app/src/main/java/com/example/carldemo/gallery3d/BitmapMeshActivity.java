/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.carldemo.gallery3d;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.util.FloatMath;

import com.example.carldemo.R;

public class BitmapMeshActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SampleView(this));
    }

    private static class SampleView extends View {
        private static final int WIDTH = 20;
        private static final int HEIGHT = 20;
        private static final int COUNT = (WIDTH + 1) * (HEIGHT + 1);

        private final Bitmap mBitmap;
        private final float[] mVerts = new float[COUNT*2];
        private final float[] mOrig = new float[COUNT*2];

        private final Matrix mMatrix = new Matrix();
        private final Matrix mInverse = new Matrix();

        private static void setXY(float[] array, int index, float x, float y) {
            array[index*2 + 0] = x;
            array[index*2 + 1] = y;
        }

        public SampleView(Context context) {
            super(context);
            setFocusable(true);
            mBitmap = BitmapFactory.decodeResource(getResources(),
                                                     R.drawable.beach);

            float w = mBitmap.getWidth();
            float h = mBitmap.getHeight();
            // construct our mesh
            int index = 0;
            for (int y = 0; y <= HEIGHT; y++) {
                float fy = h * y / HEIGHT;
                for (int x = 0; x <= WIDTH; x++) {
                    float fx = w * x / WIDTH;
                    setXY(mVerts, index, fx, fy);
                    setXY(mOrig, index, fx, fy);
                    index += 1;
                }
            }

            mMatrix.setTranslate(10, 10);
            mMatrix.invert(mInverse);

            test();
        }

        private void test() {
            float pi = 3.14f;
            float jiaodu = pi * 0.9f;
            int R = (int)(mBitmap.getWidth() / jiaodu);
            float a = jiaodu / WIDTH;


            float cx_index = (WIDTH + 0) / 2f;
            float cy_index = (HEIGHT + 0) / 2f;
            float cx = (mVerts[((int)(cx_index - 1)) * 2] + mVerts[((int)(cx_index + 1)) * 2]) / 2;
            for (int i = 0; i < COUNT; i ++) {


                int index_x = i % (WIDTH + 1);
                float index_x2 = Math.abs(index_x - cx_index);
                float bian = (float)(R * Math.sin(index_x2 * a / 2) * 2);
                float px = (float) (bian * Math.cos(pi / 2 - (pi - (index_x2)* a) / 2));
                mVerts[i * 2] = cx + (index_x - cx_index > 0 ? px : -px);

                float dy = 300 / (index_x2 + 1);
                mVerts[i * 2 + 1] += dy;

                Log.e("TAG", "bian=" + bian + "   cx=" + cx + " y=" + mVerts[i * 2 + 1]);

            }
        }

        @Override protected void onDraw(Canvas canvas) {
            canvas.drawColor(0xFFCCCCCC);

            canvas.concat(mMatrix);
            canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, mVerts, 0,
                                  null, 0, null);
        }

        private void warp(float cx, float cy) {
            final float K = 10000;
            float[] src = mOrig;
            float[] dst = mVerts;
            for (int i = 0; i < COUNT*2; i += 2) {
                float x = src[i+0];
                float y = src[i+1];
                float dx = cx - x;
                float dy = cy - y;
                float dd = dx*dx + dy*dy;
                float d = FloatMath.sqrt(dd);
                float pull = K / (dd + 0.000001f);

                pull /= (d + 0.000001f);
             //   android.util.Log.d("skia", "index " + i + " dist=" + d + " pull=" + pull);

                if (pull >= 1) {
                    dst[i+0] = cx;
                    dst[i+1] = cy;
                } else {
                    dst[i+0] = x + dx * pull;
                    dst[i+1] = y + dy * pull;
                }
            }
        }

        private int mLastWarpX = -9999; // don't match a touch coordinate
        private int mLastWarpY;

        @Override public boolean onTouchEvent(MotionEvent event) {
            float[] pt = { event.getX(), event.getY() };
            mInverse.mapPoints(pt);

            int x = (int)pt[0];
            int y = (int)pt[1];
            if (mLastWarpX != x || mLastWarpY != y) {
                mLastWarpX = x;
                mLastWarpY = y;
                warp(pt[0], pt[1]);
                invalidate();
            }
            return true;
        }
    }
}

