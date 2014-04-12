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
		res = Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
		res /= (millis1 - millis2);
		return res;
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
