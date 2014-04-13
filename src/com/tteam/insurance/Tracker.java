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
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;

public class Tracker extends Service {

	String path = "track";
	Track t;
	LocationManager lm;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		// path = intent.getStringExtra("KEY1");
		String FILENAME = "track";
		Time now = new Time();
		now.setToNow();
		t = new Track(now);
		String string = "t " + now.format2445() + "\n";

		FileOutputStream fos;
		try {
			fos = openFileOutput(FILENAME, Context.MODE_APPEND);
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
		LocationListener ll = new mylocationlistener();
		// get from gps
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0, ll);
		// get from triangulation
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5, 0, ll);

		return Service.START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		File f = new File(getFilesDir(), "serializedTrack");
		FileOutputStream fos;
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

	/*
	 * listens coordinate changes
	 */
	private class mylocationlistener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			if (location != null) {
				Log.d("LOCATION CHANGED", location.getLatitude() + "");
				Log.d("LOCATION CHANGED", location.getLongitude() + "");
				String FILENAME = path;
				Time now = new Time();
				now.setToNow();
				// generate string for writing to file
				String string = "c " + location.getLatitude() + " "
						+ location.getLongitude() + " " + now.format2445()
						+ "\n";
				// add to track
				t.add(location.getLatitude(), location.getLongitude(), now);

				FileOutputStream fos;
				try {
					fos = openFileOutput(FILENAME, Context.MODE_APPEND);
					fos.write(string.getBytes());
					fos.close();
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
