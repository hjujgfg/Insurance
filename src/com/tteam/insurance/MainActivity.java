package com.tteam.insurance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	ImageView start;
	ImageView clear;
	ImageView clearCoordsFile;
	ImageView stopService;
	EditText accelerationLimit;
	// TODO public only for TESTING
	public static Context context;
	private Intent serviceIntent;
	TextView accView, decView, speedView, statusView, altSpeedView;

	boolean isBound;

	Messenger mService;
	final Messenger mMessenger = new Messenger(new IncomingHandler());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		isBound = false;
		// file for coords
		final File f = new File(this.getFilesDir(), "track");
		try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// here we start a service which obtains coords
		serviceIntent = new Intent(this, Tracker.class);
		// potentially add data to the intent
		serviceIntent.putExtra("KEY1", "track");
		this.startService(serviceIntent);

		//
		// left android - write coords from file to the screen
		start = (ImageView) findViewById(R.id.imageView1);
		start.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				displayCoords();
			}
		});
		// right android - start map activity
		clear = (ImageView) findViewById(R.id.imageView2);
		clear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mapIntent = new Intent(context, ShowTrack.class);
				startActivity(mapIntent);
			}
		});
		clearCoordsFile = (ImageView) findViewById(R.id.clear_andro);
		clearCoordsFile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				context.stopService(serviceIntent);
				try {
					// File f = new File(context.getFilesDir(), "track");
					File f = new File(
							Environment.getExternalStorageDirectory(), "track");
					f.delete();
					f.createNewFile();

					File ff = new File(Environment
							.getExternalStorageDirectory(), "incidents");
					ff.delete();
					ff.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				context.startService(serviceIntent);
				displayCoords();
			}
		});
		stopService = (ImageView) findViewById(R.id.stop_andro);
		stopService.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doUnbindService();
				context.stopService(serviceIntent);

			}
		});
		accelerationLimit = (EditText) findViewById(R.id.accelerationLimit);
		accelerationLimit
				.setOnLongClickListener(new View.OnLongClickListener() {

					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						Analyzer.setAccelerationLimit(accelerationLimit
								.getText().toString());
						return false;
					}
				});
		accView = (TextView) findViewById(R.id.accView);
		decView = (TextView) findViewById(R.id.decView);
		speedView = (TextView) findViewById(R.id.speedView);
		statusView = (TextView) findViewById(R.id.statusView);
		altSpeedView = (TextView) findViewById(R.id.sSpeed);
		doBindService();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void displayCoords() {
		String FILENAME = "hello_file";
		String string = "hello world!";
		TextView tv = (TextView) findViewById(R.id.textView1);
		try {
			// FileInputStream fis = openFileInput("track");
			File f = new File(Environment.getExternalStorageDirectory(),
					"track");
			FileInputStream fis = new FileInputStream(f);
			StringBuffer str = new StringBuffer("");
			byte[] buff = new byte[1024];
			while (fis.read(buff) != -1) {
				str.append(new String(buff));
			}
			tv.setText(str.toString());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void doBindService() {
		bindService(serviceIntent, sConnection, Context.BIND_AUTO_CREATE);
		isBound = true;
		statusView.setText("binding");
	}

	void doUnbindService() {
		if (isBound) {
			if (mService != null) {
				try {
					Message msg = Message
							.obtain(null, Tracker.MSG_UNREG_CLIENT);
					msg.replyTo = mMessenger;
					mService.send(msg);
				} catch (RemoteException e) {

				}
				unbindService(sConnection);
				isBound = false;
				statusView.setText("Unbinding");
			}
		}
	}

	class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Tracker.MSG_ACC:
				accView.setText(msg.getData().getString("acc"));
				break;
			case Tracker.MSG_DECC:
				decView.setText(msg.getData().getString("dec"));
				break;
			case Tracker.MSG_SPEED:
				speedView.setText(msg.getData().getString("speed"));
				break;
			case Tracker.MSG_SPEED2:
				altSpeedView.setText(msg.getData().getString("speed2"));
			default:
				super.handleMessage(msg);
			}
		}
	}

	private ServiceConnection sConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			mService = null;
			statusView.setText("Disconnected");
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mService = new Messenger(service);
			statusView.setText("Attached");
			try {
				Message msg = Message.obtain(null, Tracker.MSG_REG_CLIENT);
				msg.replyTo = mMessenger;
				mService.send(msg);
			} catch (RemoteException e) {

			}
		}
	};
}
