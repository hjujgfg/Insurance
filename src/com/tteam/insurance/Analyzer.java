package com.tteam.insurance;

import java.util.LinkedList;

import android.util.Log;
import android.widget.Toast;

public class Analyzer {

	// current several points for analysis
	public static LinkedList<Point> currentPoints = new LinkedList<Point>();
	private static double accelerationLimit = 0.0;

	/*
	 * e gets a new point. checks on the current list of points, whether there
	 * is an Incident here
	 * 
	 * @returns new incident, if there was one. null otherwise.
	 */
	public static Incident addPoint(double lat, double lng, SerializableTime t) {
		Point p;
		Incident res = null;
		if (currentPoints.size() > 2) {
			currentPoints.removeFirst();
			p = new Point(lat, lng, t, currentPoints.getLast());
			res = checkForAcceleration(currentPoints.getLast(), p);
			currentPoints.add(p);
		} else {
			p = new Point(lat, lng, t, null);
			currentPoints.add(p);
		}
		// TODO yo

		return res;
	}

	public static void setAccelerationLimit(String val) {
		try {
			accelerationLimit = Double.parseDouble(val);
			Toast t = Toast.makeText(MainActivity.context, "Success!",
					Toast.LENGTH_SHORT);
			t.show();
		} catch (Exception e) {
			Toast t = Toast.makeText(MainActivity.context, "Wrong value!",
					Toast.LENGTH_SHORT);
			t.show();
		}
	}

	/*
	 * checks acceleration
	 */
	private static Incident checkForAcceleration(Point a, Point b) {
		double acceleration = (b.Speed() - a.Speed())
				/ (b.Millis() - a.Millis() / 1000);
		Log.d("Millis", "Time a - b: " + (b.Millis() - a.Millis()) / 1000);
		Log.d("Speed", "Speed = " + b.Speed());

		if (Math.abs(acceleration) >= accelerationLimit) {
			return new Incident(b, acceleration, 0);
		}
		return null;
	}

	/*
	 * checks deceleration we do not need this one
	 */
	private static Incident checkForDeceleration() {
		return null;
	}

	/*
	 * checks cornering
	 */
	private static Incident checkForCornering() {
		return null;
	}

	/*
	 * distance between two coords in meters
	 */
	private static double gps2m(double lat_a, double lng_a, double lat_b,
			double lng_b) {
		double pk = (180 / 3.14169);

		double a1 = lat_a / pk;
		double a2 = lng_a / pk;
		double b1 = lat_b / pk;
		double b2 = lng_b / pk;

		double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
		double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
		double t3 = Math.sin(a1) * Math.sin(b1);
		double tt = Math.acos(t1 + t2 + t3);

		return 6366000 * tt;
	}
}
