package br.ufrgs.f180.elements;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import br.ufrgs.f180.math.Cartesian;
import br.ufrgs.f180.math.Vector;

import com.cloudgarden.resource.SWTResourceManager;

/**
 * 
 * @author Gabriel Detoni
 *
 */
public class Robot extends MovingElement {
	
	public static final double RADIUS = 20;

	public Robot(Cartesian position){
		this.setMass(5);
		this.setForce(new Vector(0, 0));
		this.setVelocity(new Vector(0, 0));
		this.setPosition(position);
		
	}
	
	public Robot(double x, double y){
		this(new Cartesian(x, y));
	}
	
	@Override
	public void draw(GC gc) {
		Color old = gc.getForeground();
		Color c = SWTResourceManager.getColor(50, 50, 200);
		gc.setForeground(c);
		gc.drawOval(realx(position.getX() - RADIUS), realy(position.getY() - RADIUS), realx(RADIUS * 2), realy(RADIUS * 2));
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

	@Override
	public double getRadius() {
		return RADIUS;
	}
}
