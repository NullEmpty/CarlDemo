package com.example.carldemo.camera;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.carldemo.R;
import com.example.carldemo.camera.CameraModel.ICameraListener;

/**
 * @author: Peichen Xu
 * @since: 2015-12-21
 */
public class CameraActivity extends Activity {
	private static final String TAG = CameraActivity.class.getSimpleName();

	private View mRootView;
	private SurfaceView mSurfaceView;
	private Button mBtnTake;
	private SurfaceHolder mHolder;
	private CameraModel mCameraModel;

	private Button mBtnVideo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mRootView = LayoutInflater.from(this).inflate(
				R.layout.layout_camera_preview, null, false);
		setContentView(mRootView);

		initViews();

		mCameraModel = CameraModel.getInstance();
		mCameraModel.setCameraListener(mCameraListener);
	}

	private void initViews() {
		mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		mHolder = mSurfaceView.getHolder();
		mHolder.addCallback(mCallback);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mBtnTake = (Button) findViewById(R.id.btn_take);
		mBtnTake.setOnClickListener(mTakeClickListener);
		Button mBtnSwitch = (Button) findViewById(R.id.btn_switch);
		mBtnSwitch.setOnClickListener(mSwitchClickLsn);
		mBtnVideo = (Button) findViewById(R.id.btn_video);
		mBtnVideo.setOnClickListener(mVideoClickLsn);
	}

	@Override
	protected void onResume() {
		super.onResume();

		mCameraModel.openCamera();
		mRootView.requestLayout();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			mCameraModel.setPreviewDisplay(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mCameraModel.releaseCamera();
	}

	private SurfaceHolder.Callback mCallback = new Callback() {

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			mCameraModel.stopPreview();
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				mCameraModel.setPreviewDisplay(holder);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			mCameraModel.startPreview();
		}
	};

	private OnClickListener mTakeClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mCameraModel.takePicture();
		}
	};

	private OnClickListener mSwitchClickLsn = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				mCameraModel.switchCamera();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	private OnClickListener mVideoClickLsn = new OnClickListener() {

		private boolean mStart = false;
		private MediaRecorder mRecorder = null;

		@Override
		public void onClick(View v) {
			if (!mStart) {
				mRecorder = new MediaRecorder();
				try {
					mCameraModel.getCamera().unlock();
					mRecorder.setCamera(mCameraModel.getCamera());
					mRecorder
							.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
					mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

					// Set output file format
					mRecorder
							.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

					// 这两项需要放在setOutputFormat之后
					mRecorder
							.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);

					mRecorder.setVideoSize(1920, 1080);
					mRecorder.setVideoFrameRate(20);
					mRecorder.setPreviewDisplay(mHolder.getSurface());
					mRecorder.setOutputFile("/sdcard/"
							+ System.currentTimeMillis() + ".mp4");
					mRecorder.prepare();
					mRecorder.start();
 					
					mStart = true;
					mBtnVideo.setText("stop");
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				mRecorder.stop();
				mStart = false;
				mBtnVideo.setText("start");
			}
		}
	};

	private ICameraListener mCameraListener = new ICameraListener() {

		@Override
		public ShutterCallback getShutterCallback() {
			// TODO Auto-generated method stub
			return new ShutterCallback() {

				@Override
				public void onShutter() {
					// TODO Auto-generated method stub
					Log.e(TAG, "onShutter");
				}
			};
		}

		@Override
		public PictureCallback getRawPictureCallback() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PictureCallback getJpegPictureCallback() {
			return new PictureCallback() {

				@Override
				public void onPictureTaken(byte[] data, Camera camera) {
					Bitmap b = null;
					if (data != null) {
						b = BitmapFactory.decodeByteArray(data, 0, data.length);
					}
					if (b != null) {
						String path = "/sdcard/" + System.currentTimeMillis()
								+ ".jpg";
						BufferedOutputStream bos;
						try {
							bos = new BufferedOutputStream(
									new FileOutputStream(path));
							b.compress(CompressFormat.JPEG, 100, bos);
							bos.flush();
							bos.close();
							Log.e(TAG, "picture save!");
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					mCameraModel.startPreview();
				}
			};
		}
	};

}
