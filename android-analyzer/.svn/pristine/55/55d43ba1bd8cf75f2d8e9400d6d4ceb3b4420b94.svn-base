package org.androidanalyzer.plugins.dummy.gui.cameraview;

import java.io.IOException;

import org.androidanalyzer.R;
import org.androidanalyzer.core.utils.Logger;
import org.androidanalyzer.plugins.dummy.gui.DummyGUIConstants;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;

public class CameraViewActivity extends Activity {

	private static final String DUMMY_GUI_INTENT = "org.androidanalyzer.plugins.dummy.guiplugin";
	private static final String TAG = "Analyzer-CameraViewActivity";
	private static final String ANSWER_NO = "No";
	private static final String ANSWER_YES = "Yes";
	private static final String SHOW = "Show";
	private static final int MESSAGE_DELAY = 5000;
	private Preview mPreview;
	private DrawOnTop mDraw;
	private AlertDialog.Builder dialog;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mPreview = new Preview(this);
		mDraw = new DrawOnTop(this);
		setContentView(mPreview);
		addContentView(mDraw, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		dialog = new AlertDialog.Builder(this);
		dialog.setTitle(DummyGUIConstants.CAMERA_VIEW_TITLE);
		dialog.setIcon(R.drawable.warning_icon_yellow);
		dialog.setMessage(DummyGUIConstants.DIALOG_MSG);
		dialog.setCancelable(false);
		dialog.setPositiveButton(DummyGUIConstants.DIALOG_ANSWER_YES, new DialogInterface.OnClickListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * android.content.DialogInterface.OnClickListener#onClick(android.content
			 * .DialogInterface, int)
			 */
			@Override
			public void onClick(DialogInterface dialog, int which) {
				sendBroadcast(ANSWER_YES);
				Logger.DEBUG(TAG, "YES  mPreview: " + mPreview);
				mPreview.surfaceDestroyed(mPreview.getHolder());
				mDraw.setVisibility(View.GONE);
				dialog.dismiss();
				CameraViewActivity.this.finish();
			}
		});
		dialog.setNegativeButton(DummyGUIConstants.DIALOG_ANSWER_NO, new DialogInterface.OnClickListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * android.content.DialogInterface.OnClickListener#onClick(android.content
			 * .DialogInterface, int)
			 */
			@Override
			public void onClick(DialogInterface dialog, int which) {
				sendBroadcast(ANSWER_NO);
				Logger.DEBUG(TAG, "NO  mPreview: " + mPreview);
				mPreview.surfaceDestroyed(mPreview.getHolder());
				mDraw.setVisibility(View.GONE);
				dialog.dismiss();
				CameraViewActivity.this.finish();
			}
		});
		new Thread(new DelayDialog(handler)).start();
		Logger.DEBUG(TAG, "Exit from onCreate method");
	}

	private void sendBroadcast(String answer) {
		Intent dummyGUIIntent = new Intent();
		dummyGUIIntent.setAction(DUMMY_GUI_INTENT);
		dummyGUIIntent.putExtra(DummyGUIConstants.ANSWER_KEY, answer);
		this.sendBroadcast(dummyGUIIntent);
		Logger.DEBUG(TAG, "Broadcast intent -" + dummyGUIIntent + " answer - " + answer);
	}

	private Handler handler = new Handler() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			if (bundle.containsKey(SHOW)) {
				dialog.show();
				super.handleMessage(msg);
			}
		};
	};

	class DrawOnTop extends View {

		private Canvas canvas;

		public DrawOnTop(Context context) {
			super(context);
		}

		public Canvas getCanvas() {
			return canvas;
		}

		public void refersh() {
			super.onDraw(this.canvas);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.view.View#onDraw(android.graphics.Canvas)
		 */
		@Override
		protected void onDraw(Canvas canvas) {
			this.canvas = canvas;
			Paint paint = new Paint();
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.RED);
			paint.setTextSize(18);
			canvas.drawText("Testing a plugin using GUI", 20, 20, paint);
			super.onDraw(canvas);
		}

	}

	class Preview extends SurfaceView implements SurfaceHolder.Callback {
		private static final String TAG = "Analyzer-Preview";
		SurfaceHolder mHolder;
		Camera mCamera;

		Preview(Context context) {
			super(context);
			// Install a SurfaceHolder.Callback so we get notified when the underlying
			// surface is created and destroyed.
			mHolder = getHolder();
			mHolder.addCallback(this);
			mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder
		 * )
		 */
		public void surfaceCreated(SurfaceHolder holder) {
			// The Surface has been created, acquire the camera and tell it where to
			// draw.
			mCamera = Camera.open();
			Logger.DEBUG(TAG, "Camera starts");
			try {
				mCamera.setPreviewDisplay(holder);
			} catch (IOException e) {
				mCamera.release();
				mCamera = null;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.
		 * SurfaceHolder )
		 */
		public void surfaceDestroyed(SurfaceHolder holder) {
			// Surface will be destroyed when we return, so stop the preview.
			// Because the CameraDevice object is not a shared resource, it's very
			// important to release it when the activity is paused.
			if (mCamera != null) {
				mCamera.stopPreview();
				mCamera.release();
				mCamera = null;
			}
			Logger.DEBUG(TAG, "Camera stops");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder
		 * , int, int, int)
		 */
		public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
			// Now that the size is known, set up the camera parameters and begin
			// the preview.
			Camera.Parameters parameters = mCamera.getParameters();
			parameters.setPreviewSize(w, h);
			mCamera.setParameters(parameters);
			mCamera.startPreview();
		}
	}

	/* Alert dialog will show after 5 seconds */
	class DelayDialog implements Runnable {
		private static final String TAG = "Analyzer-CameraViewActivity";
		private Handler handler;

		public DelayDialog(Handler handler) {
			this.handler = handler;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				Logger.DEBUG(TAG, "Sleeping...");
				Thread.sleep(MESSAGE_DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Message msg = handler.obtainMessage();
			Bundle bundle = new Bundle();
			bundle.putString(SHOW, "yes");
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	}
}
