package com.tteam.insurance;

import java.util.LinkedList;

public class Analyzer {

	// current several points for analysis
	public static LinkedList<Point> currentPoints;

	/*
	 * gets a new point. checks on the current list of points, whether there is
	 * an Incident here
	 * 
	 * @returns new incident, if there was one. null otherwise.
	 */
	public Incident addPoint(Point p) {
		currentPoints.removeLast();
		currentPoints.add(p);
		// TODO yo
		return null;
	}

	/*
	 * checks acceleration
	 */
	private Incident checkForAcceleration() {
		return null;
	}

	/*
	 * checks deceleration
	 */
	private Incident checkForDeceleration() {
		return null;
	}

	/*
	 * checks cornering
	 */
	private Incident checkForCornering() {
		return null;
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
}
