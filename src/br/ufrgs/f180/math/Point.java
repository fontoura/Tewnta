package br.ufrgs.f180.math;

public class Point {
	protected Double x;
	protected Double y;

	public Point() {
		this.x = Double.valueOf(0);
		this.y = Double.valueOf(0);
	}
	
	public Point(Point p){
		this.x = Double.isNaN(p.getX()) ? 0 : x;
		this.y = Double.isNaN(p.getY()) ? 0 : y;
	}
	
	public Point(double x, double y){
		this.x = Double.isNaN(x) ? 0 : x;
		this.y = Double.isNaN(y) ? 0 : y;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public Point sum(Point p){
		return new Point(p.getX() + x, p.getY() + y);
	}
}
