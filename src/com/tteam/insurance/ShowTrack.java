package com.tteam.insurance;

/*
 * Displays a map and our track
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class ShowTrack extends FragmentActivity {
	private LatLng myPosition = new LatLng(0, 0);
	private GoogleMap map;

	private ArrayList<LatLng> points;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_track);
		points = new ArrayList<LatLng>();

		fillPoints();
		// initialize map
		try {
			/*
			 * map = ((MapFragment) getFragmentManager()
			 * .findFragmentById(R.id.map)).getMap();
			 */
			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			Marker mypos;
			mypos = map.addMarker(new MarkerOptions().position(myPosition)
					.title("ME HERE"));
			// temporal govnokod!!!
			// if there is no coordinates of the track, go
			// to kremlin
			if (points.size() == 0) {
				points.add(new LatLng(55.7525, 37.6171));
			}
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(points.get(0), 15));
			map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
			drawPoints();
			/*
			 * Polyline line = map.addPolyline(new PolylineOptions()
			 * .add(myPosition, new LatLng(70, 70)).width(5) .color(Color.RED));
			 */
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		ImageView refreshBtn = (ImageView) findViewById(R.id.refresh_btn);
		refreshBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				points.clear();
				fillPoints();
				drawPoints();
				if (points.size() > 1) {
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(
							points.get(points.size() - 1), 15));
					map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000,
							null);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/*
	 * fill points arraylist from storage
	 */
	private void fillPoints() {
		try {
			FileInputStream fis = openFileInput("track");
			// StringBuffer str = new StringBuffer("");
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String tmp = br.readLine();

			while (tmp != "" || tmp != null) {
				try {
					String[] words = tmp.split(" ");
					if (words[0].equals("c")) {
						points.add(new LatLng(Double.parseDouble(words[1]),
								Double.parseDouble(words[2])));
					}
					tmp = br.readLine();
				} catch (NullPointerException e) {
					e.printStackTrace();
					break;
				}

			}
			/*
			 * byte[] buff = new byte[1024]; while (fis.read(buff) != -1) {
			 * str.append(new String(buff)); }
			 */

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * draw points on the map
	 */
	private void drawPoints() {
		for (int i = 0; i < points.size() - 1; i++) {
			map.addPolyline(new PolylineOptions()
					.add(points.get(i), points.get(i + 1)).width(2)
					.color(Color.RED));
		}
	}

}
