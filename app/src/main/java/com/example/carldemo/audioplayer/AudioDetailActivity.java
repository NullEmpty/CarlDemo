package com.example.carldemo.audioplayer;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.carldemo.R;
import com.example.carldemo.audioplayer.AudioPlayModel.IMediaPlayerListener;
import com.example.carldemo.audioplayer.AudioPlayModel.IProgressChangeListener;
import com.example.carldemo.audioplayer.PlayBar.IPlayBarListener;
import com.example.carldemo.tools.Utils;

/**
 * @author Peichen Xu
 * 
 */
public class AudioDetailActivity extends Activity {

	public static final String AUDIO_ITEM = "AudioDetailActivity.audio.item";
	private PlayBar mPlayBar;
	private AudioPlayModel mPlayModel;
	private TextView mTxvName;
	private AudioItem mItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_audiodetail_activity);

		initValues();
		initViews();

		initItem();
	}

	private void initValues() {
		Intent intent = getIntent();
		String json = intent.getStringExtra(AUDIO_ITEM);
		mItem = AudioItem.fromJson(json);
		if (mItem == null) {
			finish();
			return;
		}
		
		mPlayModel = AudioPlayModel.getInstance();
		mPlayModel.setMediaPlayerListener(mMediaPlayerListener);
		mPlayModel.setDataSource(mItem.getPath());
		mPlayModel.prepare();
	}

	private void initViews() {
		mPlayBar = (PlayBar) findViewById(R.id.playbar);
		mTxvName = (TextView) findViewById(R.id.txv_title);

		mPlayBar.setViewClickListener(getPlayBarClickListener());
		
	}
	
	private void initItem() {
		mTxvName.setText(mItem.getName());
		mPlayBar.setTimeCur(Utils.getFormatPlayTime(0));
		mPlayBar.setTimeTotal(Utils.getFormatPlayTime(mItem.getDuration()));
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (mPlayModel != null) {
			mPlayModel.addProgressListener(mProgressChangeListener);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mPlayModel != null) {
			mPlayModel.removeProgressListener(mProgressChangeListener);
		}
	}
	
	@Override
	protected void onDestroy() {
		if (mPlayModel != null) {
			mPlayModel.stop();
		}
		super.onDestroy();
	}
	
	private void playNext() {
		AudioItem item = PlayListData.getInstance().getNext(mItem);
		mItem = item;
		initItem();
		mPlayModel.reset();
		mPlayModel.setDataSource(mItem.getPath());
		mPlayModel.prepare();
		mPlayModel.start();
	}
	
	public IMediaPlayerListener mMediaPlayerListener = new IMediaPlayerListener() {
		
		@Override
		public OnSeekCompleteListener onSetSeekCompleteListener() {
			// TODO Auto-generated method stub
			return new OnSeekCompleteListener() {
				
				@Override
				public void onSeekComplete(MediaPlayer mp) {
				}
			};
		}
		
		@Override
		public OnCompletionListener onSetCompletionListener() {
			// TODO Auto-generated method stub
			return new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					Log.e("detail", "onComple");
					playNext();
				}
			};
		}
	};

	public IProgressChangeListener mProgressChangeListener = new IProgressChangeListener() {

		@Override
		public void onProgress(long id, int progress) {
			// TODO Auto-generated method stub
			if (mPlayBar != null) {
//				Log.e("TAG", "PROGRESS=" + progress);
				mPlayBar.setProgress((int)(progress * 100f / mItem.getDuration()));
				mPlayBar.setTimeCur(Utils.getFormatPlayTime(progress));
			}
		}
	};

	public IPlayBarListener getPlayBarClickListener() {
		return new IPlayBarListener() {

			@Override
			public OnClickListener onSetPreClkLsn() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public OnClickListener onSetPlayClkLsn() {
				// TODO Auto-generated method stub
				return new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (mPlayModel.isPlaying()) {
							mPlayModel.pause();
						} else {
							mPlayModel.start();
						}
					}
				};
			}

			@Override
			public OnClickListener onSetPbClkLsn() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public OnClickListener onSetNextClkLsn() {
				// TODO Auto-generated method stub
				return new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						playNext();
					}
				};
			}

			@Override
			public void onPbTouch(float progress) {
				// TODO Auto-generated method stub
				if (mPlayModel != null) {
					mPlayModel.seekTo((int)(progress * mItem.getDuration()));
				}
				if (mPlayBar != null) {
					mPlayBar.setProgress((int)(progress * 100));
				}
			}
		};
	}

}
