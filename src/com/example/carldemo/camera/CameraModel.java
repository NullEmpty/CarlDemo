package com.example.carldemo.camera;

import java.io.IOException;
import java.util.List;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * @author: Peichen Xu
 * @since: 2015-12-22
 */
public class CameraModel {

	private static CameraModel mInstance;
	private Camera mCamera;
	private boolean mIsPreviewing = false;
	private ICameraListener mCameraListener;
	private int mNum;
	private int mCurCameraId;
	private SurfaceHolder mHolder;
	
	public interface ICameraListener {
		ShutterCallback getShutterCallback();
		PictureCallback getRawPictureCallback();
		PictureCallback getJpegPictureCallback();
	}

	private CameraModel() {
		mNum = Camera.getNumberOfCameras();
		CameraInfo cameraInfo = new CameraInfo();
		for (int i = 0; i < mNum; i ++) {
			Camera.getCameraInfo(i, cameraInfo);
			if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
				mCurCameraId = i;
			}
		}
	}

	public synchronized static CameraModel getInstance() {
		if (mInstance == null) {
			mInstance = new CameraModel();
		}
		return mInstance;
	}
	
	public void setCameraListener(ICameraListener l) {
		mCameraListener = l;
	}
	
	public void setPreviewDisplay(SurfaceHolder holder) throws IOException {
		mHolder = holder;
		if (mCamera == null) {
			return;
		}
		mCamera.setPreviewDisplay(holder);
	}

	public void openCamera() {
		mCamera = Camera.open();

	}
	
	public Camera getCamera() {
		return mCamera;
	}

	public void startPreview() {
		if (mIsPreviewing) {
			return;
		}
		if (mCamera == null) {
			return;
		}
		Parameters parameters = mCamera.getParameters();
		parameters.setPreviewSize(1920,1080);
//		parameters.setPreviewFormat(ImageFormat.JPEG);
//		parameters.setPictureSize(1080, 1920);
		List<String> focusModes = parameters.getSupportedFocusModes();
		Log.e("CameraModel", "mode->" + focusModes);
		
		parameters.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
		mCamera.setParameters(parameters);

		mCamera.startPreview();

		mIsPreviewing = true;
	}

	public void stopPreview() {
		if (mCamera == null) {
			return;
		}
		mCamera.stopPreview();

	}

	public void takePicture() {
		mIsPreviewing = false;
		if (mCamera != null) {
			if (mCameraListener != null) {
				mCamera.takePicture(mCameraListener.getShutterCallback(),
						mCameraListener.getRawPictureCallback(),
						mCameraListener.getJpegPictureCallback());
			} else {
				mCamera.takePicture(null, null, null);
			}

		}
	}
	
	public void switchCamera() throws IOException {
		if (mNum < 2) {
			return;
		}
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
		
		mCurCameraId = (mCurCameraId + 1) % mNum;
		mCamera = Camera.open(mCurCameraId);
		mCamera.setPreviewDisplay(mHolder);
		mIsPreviewing = false;
		startPreview();
	}
	
	public void releaseCamera() {
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}
}
