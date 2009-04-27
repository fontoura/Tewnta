package br.ufrgs.f180.model;

import br.ufrgs.f180.math.Point;
import br.ufrgs.f180.math.Vector;

public class ElementInformation {
	protected Point position;
	protected Double angle;
	protected Vector velocity;

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

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
