package com.example.carldemo.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.example.carldemo.R;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

/**
 * Created by pcxu on 2016/2/25.
 */
public class Cylinder implements IDrawFrame{

    private FloatBuffer vertexBuffer;
    private FloatBuffer mTexture;//纹理缓冲
    private FloatBuffer myNormalBuffer;//法向量缓冲
    private int vCount;
    private float xAngle;
    private float yAngle;
    private float zAngle;

    private int mTextureId;
    private Context mContext;

    public Cylinder(Context context, float length,float radius,int blocks,int sectorBlocks)
    {
        mContext = context;
        float len=length/blocks;
        float sectorAngle=360/sectorBlocks;

        List<Float> arrayList=new ArrayList<Float>();
        ArrayList<Float> ial = new ArrayList<Float>();//法向量存放列表

        for(int i=0;i<blocks;i++)
        {
            for(float angle=360;angle>0;angle=angle-sectorAngle)
            {
//左上角顶点坐标
                float x1=-(length/2-i*len);
                float y1=(float) (radius*Math.sin(Math.toRadians(angle)));
                float z1=(float) (radius*Math.cos(Math.toRadians(angle)));
                float l1 = getVectorLength(x1, y1, y1);//模长
                float a1 = 0;//法向量规格化
                float b1 = y1 / l1;
                float c1 = z1 / l1;

//右上角顶点坐标
                float x2=x1+len;
                float y2=y1;
                float z2=z1;
                float l2 = getVectorLength(x2, y2, y2);//模长
                float a2 = 0;//法向量规格化
                float b2 = y2 / l2;
                float c2 = z2 / l2;

//右下角顶点坐标
                float x3=x2;
                float y3=(float) (radius*Math.sin(Math.toRadians(angle-sectorAngle)));
                float z3=(float) (radius*Math.cos(Math.toRadians(angle - sectorAngle)));
                float l3 = getVectorLength(x3, y3, y3);//模长
                float a3 = 0;//法向量规格化
                float b3 = y3 / l3;
                float c3 = z3 / l3;

//左下角顶点坐标
                float x4=x1;
                float y4=y3;
                float z4=z3;
                float l4 = getVectorLength(x4, y4, y4);//模长
                float a4 = 0;//法向量规格化
                float b4 = y4 / l4;
                float c4 = z4 / l4;

/*//第一条线
                arrayList.add(x1);
                arrayList.add(y1);
                arrayList.add(z1);

                arrayList.add(x2);
                arrayList.add(y2);
                arrayList.add(z2);

//第二条线
                arrayList.add(x2);
                arrayList.add(y2);
                arrayList.add(z2);

                arrayList.add(x4);
                arrayList.add(y4);
                arrayList.add(z4);

//第三条线
                arrayList.add(x4);
                arrayList.add(y4);
                arrayList.add(z4);

                arrayList.add(x1);
                arrayList.add(y1);
                arrayList.add(z1);

//第四条线

                arrayList.add(x2);
                arrayList.add(y2);
                arrayList.add(z2);

                arrayList.add(x3);
                arrayList.add(y3);
                arrayList.add(z3);

//第五条线

                arrayList.add(x3);
                arrayList.add(y3);
                arrayList.add(z3);

                arrayList.add(x4);
                arrayList.add(y4);
                arrayList.add(z4);*/

                /*arrayList.add(x1);
                arrayList.add(y1);
                arrayList.add(z1);
                arrayList.add(x4);
                arrayList.add(y4);
                arrayList.add(z4);
                arrayList.add(x2);
                arrayList.add(y2);
                arrayList.add(z2);

                arrayList.add(x4);
                arrayList.add(y4);
                arrayList.add(z4);
                arrayList.add(x3);
                arrayList.add(y3);
                arrayList.add(z3);
                arrayList.add(x2);
                arrayList.add(y2);
                arrayList.add(z2);


                ial.add(a1);
                ial.add(b1);
                ial.add(c1);//顶点对应的法向量
                ial.add(a4);
                ial.add(b4);
                ial.add(c4);
                ial.add(a2);
                ial.add(b2);
                ial.add(c2);

                ial.add(a4);
                ial.add(b4);
                ial.add(c4);
                ial.add(a3);
                ial.add(b3);
                ial.add(c3);
                ial.add(a2);
                ial.add(b2);
                ial.add(c2);*/


                arrayList.add(x2);
                arrayList.add(y2);
                arrayList.add(z2);
                arrayList.add(x3);
                arrayList.add(y3);
                arrayList.add(z3);
                arrayList.add(x4);
                arrayList.add(y4);
                arrayList.add(z4);

                arrayList.add(x2);
                arrayList.add(y2);
                arrayList.add(z2);
                arrayList.add(x4);
                arrayList.add(y4);
                arrayList.add(z4);
                arrayList.add(x1);
                arrayList.add(y1);
                arrayList.add(z1);




                ial.add(a2);
                ial.add(b2);
                ial.add(c2);

                ial.add(a3);
                ial.add(b3);
                ial.add(c3);
                ial.add(a4);
                ial.add(b4);
                ial.add(c4);

                ial.add(a2);
                ial.add(b2);
                ial.add(c2);
                ial.add(a4);
                ial.add(b4);
                ial.add(c4);
                ial.add(a1);
                ial.add(b1);
                ial.add(c1);//顶点对应的法向量

            }
        }
        int size=arrayList.size();
        vCount=size/3;
        float[] vertex=new float[size];
        for(int i=0;i<size;i++)
        {
            vertex[i]=arrayList.get(i);
        }

        ByteBuffer vB= ByteBuffer.allocateDirect(size * 4);
        vB.order(ByteOrder.nativeOrder());
        vertexBuffer=vB.asFloatBuffer();
        vertexBuffer.put(vertex);
        vertexBuffer.flip();
        vertexBuffer.position(0);

        //纹理
        float[] textures = generateTexCoor(sectorBlocks);
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        mTexture = tbb.asFloatBuffer();
        mTexture.put(textures);
        mTexture.position(0);

        //法向量
        float[] normals = new float[vCount * 3];
        for (int i = 0; i < vCount * 3; i++) {
            normals[i] = ial.get(i);
        }
        ByteBuffer ibb = ByteBuffer.allocateDirect(normals.length * 4);
        ibb.order(ByteOrder.nativeOrder());
        myNormalBuffer = ibb.asFloatBuffer();
        myNormalBuffer.put(normals);
        myNormalBuffer.position(0);

    }

    //法向量规格化，求模长度
    public float getVectorLength(float x, float y, float z) {
        float pingfang = x * x + y * y + z * z;
        float length = (float) Math.sqrt(pingfang);
        return length;
    }

    public void draw(GL10 gl)
    {
//        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
//        gl.glColor4f(0.2f, 0.2f, 0.2f, 0.2f);//设置绘制线的颜色
//        gl.glRotatef(xAngle, 1, 0, 0);
//        gl.glRotatef(yAngle, 0, 1, 0);
//        gl.glRotatef(zAngle, 0, 0, 1);
//        gl.glDrawArrays(GL10.GL_LINES, 0, vCount);



        gl.glRotatef(xAngle, 1, 0, 0);
        gl.glRotatef(yAngle, 0, 1, 0);
        gl.glRotatef(zAngle, 0, 0, 1);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
//        gl.glColor4f(0.2f, 0.2f, 0.2f, 0.2f);//设置绘制线的颜色

        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);//打开法向量缓冲
        gl.glNormalPointer(GL10.GL_FLOAT, 0, myNormalBuffer);//指定法向量缓冲

        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTexture);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制图像

        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭缓冲
        gl.glDisable(GL10.GL_TEXTURE_2D);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);


    }

    //自动切分纹理产生纹理数组的方法
    public float[] generateTexCoor(int bh) {
        float[] result = new float[bh * 6 * 2];
        float REPEAT = 1f;
        float sizeh = 2f / bh;//行数
        int c = 0;
        for (int i = 0; i < bh; i++) {
            //每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
            float t = i * sizeh;

            result[c++] = 0;
            result[c++] = t;

            result[c++] = 0;
            result[c++] = t + sizeh;

            result[c++] = REPEAT;
            result[c++] = t;

            result[c++] = 0;
            result[c++] = t + sizeh;

            result[c++] = REPEAT;
            result[c++] = t + sizeh;

            result[c++] = REPEAT;
            result[c++] = t;
        }
        return result;
    }

    //初始化纹理
    public int initTexture(Context context, GL10 gl, int drawableId)//textureId
    {
        //生成纹理ID
        int[] textures = new int[1];
        gl.glGenTextures(1, textures, 0);
        int currTextureId = textures[0];
        gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR_MIPMAP_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR_MIPMAP_LINEAR);
        ((GL11) gl).glTexParameterf(GL10.GL_TEXTURE_2D, GL11.GL_GENERATE_MIPMAP, GL10.GL_TRUE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);


        Bitmap bitmapTmp = BitmapFactory.decodeResource(mContext.getResources(), drawableId);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
        bitmapTmp.recycle();

        return currTextureId;
    }

    /**
     * @param xAngle the xAngle to set
     */
    public void setxAngle(float xAngle) {
        this.xAngle += xAngle;
    }

    /**
     * @param yAngle the yAngle to set
     */
    public void setyAngle(float yAngle) {
        this.yAngle += yAngle;
    }

    /**
     * @param zAngle the zAngle to set
     */
    public void setzAngle(float zAngle) {
        this.zAngle += zAngle;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mTextureId = initTexture(mContext, gl, R.drawable.t);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }


}
