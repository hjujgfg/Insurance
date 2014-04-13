package com.tteam.insurance;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;

public class Point implements Parcelable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double latitude, longitude;
	private Time time;
	private double speed;

	/*
	 * deprecated? lal
	 */
	public Point(double latitude, double longitude, Time t, Time prevT,
			double prevLa, double prevLo) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.time = t;
		speed = countSpeed(latitude, longitude, prevLa, prevLo, t, prevT);
	}

	public Point(double latitude, double longitude, Time t, Point previous) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.time = t;
		if (previous != null)
			speed = countSpeed(latitude, longitude, previous.latitude,
					previous.longitude, t, previous.time);
		else
			speed = 0;
	}

	/*
	 * constuctor for parcel
	 */
	private Point(Parcel p) {
		latitude = p.readDouble();
		longitude = p.readDouble();
		speed = p.readDouble();
		time = new Time();
		time.set(p.readLong());
	}

	/*
	 * 1 - current point; 2 - previous
	 */
	private double countSpeed(double x1, double y1, double x2, double y2,
			Time t1, Time t2) {
		double res;
		long millis1 = t1.toMillis(true);
		long millis2 = t2.toMillis(true);
		res = gps2m(x1, y1, x2, y2);
		res /= (millis1 - millis2);
		return res;
	}

	/*
	 * distance between two coords in meters
	 */
	private double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
		dest.writeDouble(speed);
		dest.writeLong(time.toMillis(true));
	}

	public static final Parcelable.Creator<Point> CREATOR = new Creator<Point>() {

		@Override
		public Point[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Point[size];
		}

		@Override
		public Point createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Point(source);
		}
	};

}
