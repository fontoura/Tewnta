package br.ufrgs.f180.elements;

import org.eclipse.swt.graphics.GC;

import br.ufrgs.f180.math.Point;
import br.ufrgs.f180.math.Vector;

public class WallCollisionPoint extends MovingElement {

	/**
	 * Simulates a point in the wall with infinite mass
	 * @param position
	 */
	public WallCollisionPoint(Point position) {
		this.setMass(Double.MAX_VALUE);
		this.setForce(new Vector(0, 0));
		this.setVelocity(new Vector(0, 0));
		this.setPosition(position);
	}
	
	@Override
	public void draw(GC gc) {
	}

	@Override
	public double getRadius() {
		return 1;
	}

	@Override
	public int realx(double x) {
		return 0;
	}

	@Override
	public int realy(double y) {
		return 0;
	}

}
