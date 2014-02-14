package com.tteam.insurance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	ImageView start;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final File f = new File(this.getFilesDir(), "track");
		Intent i = new Intent(this, Tracker.class);
		// potentially add data to the intent
		i.putExtra("KEY1", "track");
		this.startService(i);

		try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		start = (ImageView) findViewById(R.id.imageView1);
		start.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
