package com.tteam.insurance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	ImageView start;
	ImageView clear;
	ImageView clearCoordsFile;
	ImageView stopService;
	private Context context;
	private Intent serviceIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
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
					File f = new File(context.getFilesDir(), "track");
					f.delete();
					f.createNewFile();
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
				try {

				} catch (Exception e) {
					context.stopService(serviceIntent);
				}
			}
		});
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
			FileInputStream fis = openFileInput("track");
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
}
