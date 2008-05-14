package br.ufrgs.f180.elements;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import com.cloudgarden.resource.SWTResourceManager;

import br.ufrgs.f180.math.Cartesian;
import br.ufrgs.f180.math.Vector;

public abstract class Motor extends MovingElement {

	public void calculatePosition(double timeElapsed, Cartesian center) {
		Vector compositeAcceleration = getAcceleration().sum(getCentripetForce(center));
		velocity = velocity.sum(compositeAcceleration.multiply(timeElapsed));
		
		position.setX(position.getX() + velocity.multiply(timeElapsed).getX());
		position.setY(position.getY() + velocity.multiply(timeElapsed).getY());
		
		//double cos = velocity 
	}

	public Vector getCentripetForce(Cartesian center){
		double actionRadius = (Robot.RADIUS / 2) * 3; 
		//Ac = V^2 / r * u
		Vector ca = getVelocity().multiply(getVelocity());
		ca = ca.divide(actionRadius);
		Vector normal = new Vector(position, center);
		ca = ca.multiply(normal);
		return ca;
	}

	private Vector direction;
	
	public Motor(double x, double y){
		this.setMass(1);
		this.setForce(new Vector(0, 0));
		this.setVelocity(new Vector(1, 0));
		this.setPosition(new Cartesian(x, y));
		this.direction = direction;
	}

	@Override
	public void draw(GC gc) {
		Color old = gc.getForeground();
		Color c = SWTResourceManager.getColor(50, 10, 200);
		gc.setForeground(c);
		gc.drawOval(realx(position.getX() - 1), realy(position.getY() - 1), realx(2), realy(2));
		gc.setForeground(old);
	}

	@Override
	public int realx(double x) {
		return (int)(x * field.getScale_x());
	}

	@Override
	public int realy(double y) {
		return (int)(y * field.getScale_y());
	}

	public Vector getDirection() {
		return direction;
	}

	public void setDirection(Vector direction) {
		this.direction = direction;
	}
}
