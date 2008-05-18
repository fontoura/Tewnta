package br.ufrgs.f180.elements;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import br.ufrgs.f180.math.Cartesian;
import br.ufrgs.f180.math.Vector;

import com.cloudgarden.resource.SWTResourceManager;

public class Ball extends MovingElement {
	public static final double RADIUS = 5;
	
	public Ball(double x, double y){
		this.setMass(1);
		this.setForce(new Vector(0, 0));
		this.setVelocity(new Vector(800, 800));
		this.setPosition(new Cartesian(x, y));
	}

	@Override
	public void draw(GC gc) {
		Color old = gc.getForeground();
		Color c = SWTResourceManager.getColor(255, 200, 0);
		gc.setForeground(c);
		gc.drawOval(realx(position.getX() - RADIUS), realy(position.getY() - RADIUS) , realx(RADIUS * 2), realy(RADIUS * 2) );
		gc.setForeground(old);
		//int y1 = realy(position.getY());
		//int y2 = getField().relativeRealy(realy(position.getY() + velocity.getY()));
		//gc.drawLine(realx(position.getX()), y1, realx(position.getX() + velocity.getX()), y2);
	}

	@Override
	public int realx(double x) {
		return (int)(x * field.getScale_x());
	}

	@Override
	public int realy(double y) {
		return (int)(y * field.getScale_y());
	}

	@Override
	public double getRadius() {
		return RADIUS;
	}
	
}
