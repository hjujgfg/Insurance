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
}
