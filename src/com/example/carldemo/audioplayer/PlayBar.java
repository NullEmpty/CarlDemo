package com.example.carldemo.audioplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.carldemo.R;

/**
 * 播放条
 * 
 * @author Peichen Xu
 *
 */
public class PlayBar extends LinearLayout{
	
	private Context mContext;
	private TextView mTxvTimeCur;
	private TextView mTxvTimeTotal;
	private ProgressBar mPb;
	private Button mBtnPre;
	private Button mBtnNext;
	private Button mBtnPlay;
	private IPlayBarListener mPlayBarListener;
	
	public interface IPlayBarListener {
		OnClickListener onSetPbClkLsn();
		OnClickListener onSetPreClkLsn();
		OnClickListener onSetPlayClkLsn();
		OnClickListener onSetNextClkLsn();
		void onPbTouch(float progress);
	}

	public PlayBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PlayBar(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.layout_audio_playbar, this);
		mTxvTimeCur = (TextView) findViewById(R.id.txv_time_cur);
		mTxvTimeTotal = (TextView) findViewById(R.id.txv_time_total);
		mPb = (ProgressBar) findViewById(R.id.pb);
		mBtnPre = (Button) findViewById(R.id.btn_pre);
		mBtnNext = (Button) findViewById(R.id.btn_next);
		mBtnPlay = (Button) findViewById(R.id.btn_play);
	}
	
	public void setViewClickListener(IPlayBarListener l) {
		if (l == null) {
			return;
		}
		mPlayBarListener = l;
		mPb.setOnClickListener(l.onSetPbClkLsn());
		mPb.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
					float x = event.getX();
					float w = v.getWidth();
					float progress = x / w;
//					Log.e("PlayBar", "x=" + x+ ",w=" + w + ",p=" + progress);
					if (mPlayBarListener != null) {
						mPlayBarListener.onPbTouch(progress);
					}
					break;

				default:
					break;
				}
				return false;
			}
		});
		mBtnPre.setOnClickListener(l.onSetPreClkLsn());
		mBtnPlay.setOnClickListener(l.onSetPlayClkLsn());
		mBtnNext.setOnClickListener(l.onSetNextClkLsn());
	}
	
	public void setTimeCur(String s) {
		mTxvTimeCur.setText(s);
	}
	
	public void setTimeTotal(String s) {
		mTxvTimeTotal.setText(s);
	}
	
	public void setProgress(int progress) {
		mPb.setProgress(progress);
	}
	
	public void showPlaying(boolean playing) {
		
	}

}
