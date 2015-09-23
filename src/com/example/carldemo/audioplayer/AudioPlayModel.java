package com.example.carldemo.audioplayer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.example.carldemo.tools.Utils;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.Handler;

/**
 * @author Peichen Xu
 *
 */
public class AudioPlayModel {
	
	private static AudioPlayModel mInstance;
	private MediaPlayer mPlayer;
	private Set<IProgressChangeListener> mSetListener = new HashSet<AudioPlayModel.IProgressChangeListener>();
	private Timer mTimer;
	private Handler mHandler = new Handler();
	
	public static interface IProgressChangeListener {
		void onProgress(long id, int progress);
	}
	
	public static interface IMediaPlayerListener {
		OnSeekCompleteListener onSetSeekCompleteListener();
		OnCompletionListener onSetCompletionListener();
	}
	
	private AudioPlayModel(){
		mPlayer = new MediaPlayer();
		mTimer = new Timer();
	}
	
	public static synchronized AudioPlayModel getInstance() {
		if (mInstance == null) {
			mInstance = new AudioPlayModel();
		}
		return mInstance;
	}
	
	private void startTimer() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer.purge();
		}
		mTimer = new Timer();
		mTimer.schedule(getTimerTask(), 0, 1000);
	}
	
	private void stopTimer() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer.purge();
			mTimer = null;
		}
	}
	
	private TimerTask getTimerTask() {
		return new TimerTask() {
			
			@Override
			public void run() {
				if (mHandler != null) {
					mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							if (mPlayer != null) {
								notifyProgress(mPlayer.getCurrentPosition());
							}
						}
					});
				}
			}
		};
	}
	
	public void addProgressListener(IProgressChangeListener l) {
		if (l != null) {
			mSetListener.add(l);
		}
	}
	
	public void removeProgressListener(IProgressChangeListener l) {
		if (l != null) {
			mSetListener.remove(l);
		}
	}
	
	private void notifyProgress(int progress) {
		if (Utils.isNullOrEmpty(mSetListener)) {
			return;
		}
		for (IProgressChangeListener l : mSetListener) {
			if (l == null) {
				continue;
			}
			l.onProgress(0, progress);
		}
	}
	
	public void setMediaPlayerListener(IMediaPlayerListener l) {
		if (l != null) {
			mPlayer.setOnCompletionListener(l.onSetCompletionListener());
			mPlayer.setOnSeekCompleteListener(l.onSetSeekCompleteListener());
		}
	}
	
	public void setDataSource(String path) {
		try {
			mPlayer.setDataSource(path);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void prepare() {
		try {
			mPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start() {
		mPlayer.start();
		startTimer();
	}
	
	public void pause() {
		mPlayer.pause();
		stopTimer();
	}
	
	public void stop() {
		mPlayer.stop();
	}
	
	public void reset() {
		mPlayer.reset();
	}
	
	public void seekTo(int msec) {
		mPlayer.seekTo(msec);
	}
	
	public boolean isPlaying() {
		return mPlayer.isPlaying();
	}
	
}
