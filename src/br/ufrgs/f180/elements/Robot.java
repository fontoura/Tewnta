package br.ufrgs.f180.elements;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import br.ufrgs.f180.math.Cartesian;
import br.ufrgs.f180.math.Vector;

import com.cloudgarden.resource.SWTResourceManager;

public class Robot extends MovingElement {
	
	public static final double RADIUS = 20;
	
	public Robot(double x, double y){
		this.setMass(5);
		this.setForce(new Vector(0, 0));
		this.setVelocity(new Vector(0, 0));
		this.setPosition(new Cartesian(x, y));
		
/*		double angle = 2 * Math.PI / NUM_MOTORS; 
		for(double i = 0; i < NUM_MOTORS; i++){
			double ypos = (Math.sin(angle * i) * RADIUS) + y; 
			double xpos = (Math.cos(angle * i) * RADIUS) + x;
			motors.add(new Motor(xpos,ypos));
		}	
*/
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
