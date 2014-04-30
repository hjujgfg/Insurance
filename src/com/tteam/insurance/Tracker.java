package com.tteam.insurance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

public class Tracker extends Service {

	File ff;// = Environment.getExternalStorageDirectory();
	String path = "";// ff.getAbsolutePath() + "/track";
	Track t;

	LocationManager lm;
	LocationListener ll;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		ff = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath(), "track");
		// path = intent.getStringExtra("KEY1");
		String FILENAME = path;
		SerializableTime now = new SerializableTime();
		now.setToNow();
		t = new Track(now);
		String string = "t " + now.format2445() + "\n";

		FileOutputStream fos;
		try {
			// fos = openFileOutput(FILENAME, Context.MODE_APPEND);
			fos = new FileOutputStream(ff, true);
			fos.write(string.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		ll = new mylocationlistener();
		// get from gps
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300, 0, ll);
		// get from triangulation
		// lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 300, 0,
		// ll);

		return Service.START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		File f = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath(), "serializedTrack");
		FileOutputStream fos;
		lm.removeUpdates(ll);
		try {
			fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(t);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Point p1, p2;

	/*
	 * listens coordinate changes
	 */
	private class mylocationlistener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			if (location != null) {

				String FILENAME = path;
				SerializableTime now = new SerializableTime();
				now.setToNow();
				Log.d("LOCATION CHANGED",
						location.getLatitude() + " " + now.toMillis(true));
				Log.d("LOCATION CHANGED", location.getLongitude() + "");
				// generate string for writing to file

				String string = "c " + location.getLatitude() + " "
						+ location.getLongitude() + " " + now.format2445()
						+ "\n";
				String logger = "l " + location.getLatitude() + " "
						+ location.getLongitude() + " " + now.toMillis(true)
						+ " ";
				// add to track
				p2 = new Point(location.getLatitude(), location.getLongitude(),
						now, p1);
				logger += p2.Speed() + "\n";
				p1 = p2;
				// t.add(location.getLatitude(), location.getLongitude(), now);
				Incident inc = Analyzer.addPoint(location.getLatitude(),
						location.getLongitude(), now);
				String incidents = "";
				if (inc != null) {
					incidents = "i " + inc.Lat() + " " + inc.Lng() + "\n";
				}
				t.addIncident(inc);
				// String string = t.getLast().toString();
				FileOutputStream fos;
				try {
					// fos = openFileOutput(FILENAME, Context.MODE_APPEND);
					ff = new File(Environment.getExternalStorageDirectory()
							.getAbsolutePath(), "track");
					fos = new FileOutputStream(ff, true);
					fos.write(string.getBytes());
					fos.close();
					/*
					 * FileOutputStream incstr = openFileOutput("incidents",
					 * Context.MODE_APPEND);
					 */
					ff = new File(Environment.getExternalStorageDirectory()
							.getAbsolutePath(), "incidents");
					FileOutputStream incstr = new FileOutputStream(ff, true);
					incstr.write(incidents.getBytes());
					incstr.close();

					ff = new File(Environment.getExternalStorageDirectory()
							.getAbsolutePath(), "InsuranceLog");
					FileOutputStream log = new FileOutputStream(ff, true);
					log.write(logger.getBytes());
					log.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub

		return null;
	}

}
