package com.royole.bwgallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class MirrorItemView extends FrameLayout{
	private View mContentView;
	protected boolean mHasReflection = true;
	private static int REFHEIGHT = -1;
	public static Paint RefPaint=null;
	
	private Bitmap mReflectBitmap;
	private Canvas mReflectCanvas;

	private PaintFlagsDrawFilter mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG| Paint.FILTER_BITMAP_FLAG);
	public MirrorItemView(Context context) {
		super(context);

		init(context);
	}

	public MirrorItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MirrorItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		setClickable(true);
		if(REFHEIGHT == -1) {
			REFHEIGHT = 36 * 3;//getResources().getDimensionPixelSize(R.dimen.mirror_ref_height);
		}
		if(RefPaint==null){
			RefPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			LinearGradient shader = new LinearGradient(
					0,
					0,
					0,
					REFHEIGHT,
					0x70FFFFFF,
					0x00000000,
					Shader.TileMode.CLAMP);
			RefPaint.setShader(shader);
			RefPaint.setShader(new LinearGradient(0, 0,
					0, REFHEIGHT,
					new int[] {0x99FFFFFF, 0x66FFFFFF, 0x0500000,0x00000000},
					new float[] {0.0f, 0.1f, 0.6f,1.0f}, Shader.TileMode.CLAMP));

			RefPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
		}

		this.setClickable(true);
	}

//	public void setContentView(View view, ViewGroup.LayoutParams lp){
//		mContentView = view;
//		//mContentView.setFocusable(false);
//		//setFocusable(true);
//		addView(view,lp);
//	}
	
	public View getContentView(){
		return mContentView;
	}
	
	public void setReflection(boolean ref){
		mHasReflection = ref;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mContentView = getChildAt(0);
	}

//	@Override
//    public boolean performClick() {
//        return mContentView.performClick();
//    }
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
        if(mHasReflection)
        {
            if(mReflectBitmap==null){
            	mReflectBitmap = Bitmap.createBitmap(mContentView.getWidth(), REFHEIGHT, Bitmap.Config.ARGB_8888);
            	mReflectCanvas = new Canvas(mReflectBitmap);
            }     
            drawReflection(mReflectCanvas,mContentView);

            canvas.save();
            int dy = mContentView.getBottom();
            int dx = mContentView.getLeft();
            canvas.translate(dx, dy);
            canvas.drawBitmap(mReflectBitmap, 0, 0, null);
            canvas.restore();
        }
	}
	
	public Bitmap getReflectBitmap(){
		return mReflectBitmap;
	}
	
	public void drawReflection(Canvas canvas,View view){
		canvas.save();
		canvas.clipRect(0, 0, view.getWidth(), REFHEIGHT);
		canvas.save();
		canvas.scale(1, -1);
		canvas.translate(0, -view.getHeight());
		view.draw(canvas);
		canvas.restore();         
		canvas.drawRect(0, 0, view.getWidth(), REFHEIGHT, RefPaint); 
		canvas.restore();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.setDrawFilter(mPaintFlagsDrawFilter);
		super.onDraw(canvas);
	}
}
