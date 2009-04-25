package br.ufrgs.f180.model;

import br.ufrgs.f180.math.Point;

public class ElementInformation {
	protected Point position;
	protected Double angle;

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Double getAngle() {
		return angle;
	}

	public void setAngle(Double angle) {
		this.angle = angle;
	}
}
