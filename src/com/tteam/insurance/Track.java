package com.tteam.insurance;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;

import com.google.android.gms.maps.model.LatLng;

public class Track implements Parcelable {

	ArrayList<LatLng> coords;
	ArrayList<LatLng> incidents;
	String startTime;
	String finishTime;

	public Track(Time t) {
		coords = new ArrayList<LatLng>();
		incidents = new ArrayList<LatLng>();
		startTime = t.format2445();
		finishTime = "";
	}

	private Track(Parcel p) {
		p.readList(coords, getClass().getClassLoader());
		p.readList(incidents, getClass().getClassLoader());
		startTime = p.readString();
		finishTime = p.readString();
	}

	/*
	 * add new coordinate
	 */
	public void add(LatLng point) {
		coords.add(point);
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
