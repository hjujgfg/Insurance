package com.tteam.insurance;

import android.util.Log;
import android.widget.Toast;

public class Incident {
	private Point location;
	String type;
	double accelaration;
	double angle;

	public Incident(Point loc, double acc, double angle) {
		this.location = loc;
		Toast t = Toast.makeText(MainActivity.context, loc.toString()
				+ " acc: " + acc + "\n", Toast.LENGTH_LONG);
		t.show();
		if (acc != 0) {
			if (acc > 0) {
				type = "acceleration";
			} else {
				type = "brake";
			}
			accelaration = acc;
		}
		if (angle != 0) {
			this.angle = angle;
			type = "corner";
		}
		Log.d("New Incident", loc.Speed() + " : " + acc);
	}

	public double Lat() {
		return location.Lat();
	}

	public double Lng() {
		return location.Lng();
	}
}
