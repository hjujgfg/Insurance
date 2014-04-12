package com.tteam.insurance;

public class Incident {
	private Point location;
	String type;
	double accelaration;
	double angle;

	public Incident(Point loc, double acc, double angle) {
		this.location = loc;
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
	}
}
