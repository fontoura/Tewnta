package br.ufrgs.f180.math;

public class Cartesian {
	protected Double x;
	protected Double y;

	public Cartesian(double x, double y){
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
	
}
