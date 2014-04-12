package com.tteam.insurance;

import java.io.Serializable;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;

public class Track implements Parcelable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Point> coords;
	ArrayList<Incident> incidents;
	String startTime;
	String finishTime;

	public Track(Time t) {
		coords = new ArrayList<Point>();
		incidents = new ArrayList<Incident>();
		startTime = t.format2445();
		finishTime = "";
	}

	private Track(Parcel p) {
		p.readList(coords, getClass().getClassLoader());
		p.readList(incidents, getClass().getClassLoader());
		startTime = p.readString();
		finishTime = p.readString();
	}

	public void endTrack(Time t) {
		finishTime = t.format2445();
	}

	/*
	 * add new coordinate
	 */
	public void add(double latitude, double longitude, Time t) {
		// if it not the first one
		if (coords.size() > 1) {
			coords.add(new Point(latitude, longitude, t, coords.get(coords
					.size() - 1)));
			// first point of the track
		} else {
			coords.add(new Point(latitude, longitude, t, null));
		}
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeList(coords);
		dest.writeList(incidents);
		dest.writeString(startTime);
		dest.writeString(finishTime);
	}

	public static final Parcelable.Creator<Track> CREATOR = new Creator<Track>() {

		@Override
		public Track[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Track[size];
		}

		@Override
		public Track createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Track(source);
		}
	};

}
