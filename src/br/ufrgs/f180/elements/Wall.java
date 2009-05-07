package br.ufrgs.f180.elements;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import br.ufrgs.f180.math.Point;

import com.cloudgarden.resource.SWTResourceManager;


public class Wall implements VisualElement {

	public enum CollisionSide{
		NORMAL,
		REVERSE,
		BOTH;
	};
	
	private GameField field;
	private final double y0;
	private final double y1;
	private final double x0;
	private final double x1;
	private final CollisionSide collisionSide; 
	
	public Wall(double x0, double y0, double x1, double y1, CollisionSide collisionSide){
		this.y0 = y0;
		this.y1 = y1;
		this.x0 = x0;
		this.x1 = x1;
		this.collisionSide = collisionSide;
	}
	
	@Override
	public void draw(GC gc) {
		Color old = gc.getForeground();
		Color c = SWTResourceManager.getColor(250, 250, 0);
		gc.setForeground(c);
		gc.drawLine(realx(x0), realy(y0), realx(x1), realy(y1));
		
//		//Demonstration of the collision calculations
//		Wall n = this;
//		c = SWTResourceManager.getColor(250, 0, 0);
//		gc.setForeground(c);
//		Point pt = new Point(100, 100);
//		gc.drawOval(realx(pt.getX()), realy(pt.getY()), 2, 2);
//		c = SWTResourceManager.getColor(250, 250, 250);
//		gc.setForeground(c);
//		Point p = n.perpendicularProjection(pt);
//		gc.drawOval(realx(p.getX()), realy(p.getY()), 2, 2);
		
		gc.setForeground(old);
		
	}

	@Override
	public int scalex(double x) {
		return field.scalex(x);
	}

	@Override
	public int scaley(double y) {
		return field.scaley(y);
	}

	@Override
	public int realx(double x) {
		return field.realx(x);
	}

	@Override
	public int realy(double y) {
		return field.realy(y);
	}

	public double getY0() {
		return y0;
	}

	public double getY1() {
		return y1;
	}

	public double getX0() {
		return x0;
	}

	public double getX1() {
		return x1;
	}

	public GameField getField() {
		return field;
	}

	public void setField(GameField field) {
		this.field = field;
	}

	public CollisionSide getCollisionSide() {
		return collisionSide;
	}

	/**
	 * Calculates the 2D distance between a point and a line
	 * @param x
	 * @param y
	 * @return
	 */
	public double distanceFrom(Point point) {
		return distanceFrom(point.getX(), point.getY());
	}
	
	/**
	 * Calculates the 2D distance between a point and a line
	 * @param x
	 * @param y
	 * @return
	 */
	public double distanceFrom(double x, double y) {
		double p1 = (findLinearInterpolationCoheficientA() * x) + (findLinearInterpolationCoheficientB() * y) + findLinearInterpolationCoheficientC();
		double p2 = Math.pow(findLinearInterpolationCoheficientA(), 2) + Math.pow(findLinearInterpolationCoheficientB(), 2);
		double p3 = Math.abs(p1) / Math.sqrt(p2);
		return p3;
	}
	
	/**
	 * Linear interpolation to ax + by + c = 0
     * a = (y1 - y0) / (x1 - x0)
     * b = 1
     * c = y0 - (a * x0)
	 * @return a
	 */
	private double findLinearInterpolationCoheficientA(){
		return (this.y1 - this.y0) / (this.x1 - this.x0);	
	}

	/**
	 * Linear interpolation to ax + by = c
     * a = (y1 - y0) / (x1 - x0)
     * b = 1
     * c = y0 - (a * x0)
	 * @return b
	 */
	private double findLinearInterpolationCoheficientB(){
		return 1;	
	}

	/**
	 * Linear interpolation to ax + by = c
     * a = (y1 - y0) / (x1 - x0)
     * b = 1
     * c = y0 - (a * x0)
	 * @return c
	 */
	private double findLinearInterpolationCoheficientC(){
		return y0 - (findLinearInterpolationCoheficientA() * this.x0);	
	}

	/**
	 * Return the position where a perpendicular line from the position passed will intersect the wall
	 */
	public Point perpendicularProjection(Point position) {
		//Equation for the wall
		double a0 = findLinearInterpolationCoheficientA();
		double b0 = findLinearInterpolationCoheficientB(); 
		double c0 = findLinearInterpolationCoheficientC();
		
		//Equation for the perpendicular point
		double a1 = -1.0 / findLinearInterpolationCoheficientA();
		double b1 = 1; 
		double c1 = position.getY() - (a1 * position.getX()) ;
		
		double x;
		double y;
		
		//Check for infinite tangent
		if(a0 == 0){ //horizontal line
			x = position.getX();
			y = this.y0;
		}
		else if(a0 == Double.NEGATIVE_INFINITY || a0 == Double.POSITIVE_INFINITY){ //Vertical line
			x = this.x0;
			y = position.getY();
		}
		else{
			//Normal line
			x = (-(-c1/b1)+(-c0/b0)) / ((-a1/b1)-(-a0/b0));
			y = (a1 * x + c1) / b1;
		}
		return new Point(x, y);
	}
	
	public static void main(String[] args) {
//		Wall n = new Wall(0, 0, 4.33, 2.5, CollisionSide.BOTH);
//		Point pt = new Point(5.77, 0);
		Wall n = new Wall(0, 10, 100, 10, CollisionSide.BOTH);
		Point pt = new Point(5.77, 0);
		Point p = n.perpendicularProjection(pt);
		System.out.println("Distance: " + n.distanceFrom(pt));
		System.out.println("Projection: x = " + p.getX());
		System.out.println("Projection: y = " + p.getY());
		
	}
}
