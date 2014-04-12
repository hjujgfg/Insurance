package com.tteam.insurance;

import android.text.format.Time;

public class Point {
	private double latitude, longitude;
	private Time time;
	private double speed;

	public Point(double latitude, double longitude, Time t, Time prevT,
			double prevLa, double prevLo) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.time = t;
		speed = countSpeed(latitude, longitude, prevLa, prevLo, t, prevT);
	}

	/*
	 * 1 - current point; 2 - previous
	 */
	private double countSpeed(double x1, double y1, double x2, double y2,
			Time t1, Time t2) {
		double res;
		long millis1 = t1.toMillis(true);
		long millis2 = t2.toMillis(true);
		res = Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
		res /= (millis1 - millis2);
		return res;
	}

}
