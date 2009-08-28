package br.ufrgs.f180.elements;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import br.ufrgs.f180.math.Point;
import br.ufrgs.f180.math.Vector;
import br.ufrgs.f180.resources.GameProperties;

import com.cloudgarden.resource.SWTResourceManager;

/**
 * 
 * @author Gabriel Detoni
 *
 */
public class Robot extends MovingElement {
	
	private static final double ROBOT_MAX_VELOCITY = (double)GameProperties.getDoubleValue("robot.max.velocity") * 100;
	private static final double SPOT_SIZE = 2.5;
	private double radius;
	private Vector front;
	private String id;

	/**
	 * True means the force will be displayed as an arrow inside the robot
	 */
	private boolean displayForce = false;
	/**
	 * True means the colored balls will be displayed inside the robot
	 */
	private boolean displayMarks = true;
	/**
	 * True means the colored balls will be displayed inside the robot
	 */
	private boolean displayName = false;

	public String getId() {
		return id;
	}

	public enum Team{
		A("A"),
		B("B");
		private String value;
		
		Team(String str){
			value = str;
		}
		
		public String toString(){
			return value;
		}
	}
	private Team team;
	
	public Robot(Point position, Team team){
		this.setAngle(0);
		this.setRotationForce(0);
		this.setRotationVelocity(0);
		this.setMass(5);
		this.setForce(new Vector(0, 0));
		this.setVelocity(new Vector(0, 0));
		this.setPosition(position);
		//this.setId(id);
		//this.setFrente(getPosition()+, y);
		this.team = team;
		
	}
	

	public Robot(double x, double y, Team team, double mass, double radius, String id){
		this(new Point(x, y), team);
		this.setMass(mass);
		this.setRadius(radius);
		this.setId(id);
		
	}
	
	@Override
	public void draw(GC gc) {
		Color old = gc.getBackground();
		Color backgroundColor = SWTResourceManager.getColor(0, 0, 0);
		gc.setBackground(backgroundColor);
		gc.fillOval(realx(position.getX() - radius), realy(position.getY() + radius), scalex(radius * 2), scaley(radius * 2));

		drawMarks(gc);
		drawName(gc);
		drawForce(gc);
		drawSelection(gc);

		gc.setBackground(old);
		
	}

	/**
	 * Draws yellow halo around object
	 * @param gc
	 */
	private void drawSelection(GC gc) {
		if(isSelected()){
			Color old = gc.getForeground();
			Color color = SWTResourceManager.getColor(200, 200, 0);
			gc.setForeground(color);
			gc.drawOval(realx(position.getX() - radius), realy(position.getY() + radius), scalex(radius * 2), scaley(radius * 2));
			gc.setForeground(old);
		}
	}


	private void drawForce(GC gc) {
		if(isDisplayForce()) {
			double x1 = getPosition().getX();
			double y1 = getPosition().getY();
			double x2 = getPosition().getX()+ getForce().getX();
			double y2 = getPosition().getY() + getForce().getY();
			Color old = gc.getForeground();
			Color foregroundColor = SWTResourceManager.getColor(0, 255, 55);
			gc.setForeground(foregroundColor);
			gc.drawLine(realx(x1), realy(y1), realx(x2), realy(y2));
			gc.setForeground(old);
		}
		
	}


	private void drawName(GC gc) {
		if(isDisplayName()) {
			gc.drawString(getId(), realx(getPosition().getX() - getRadius()), realy(getPosition().getY() - getRadius()), true);
		}
	}


	private void drawMarks(GC gc) {
		if(isDisplayMarks()) {
			Color centerColor = Team.A.equals(team) ? SWTResourceManager.getColor(0, 0, 200) : SWTResourceManager.getColor(200, 200, 0);
			drawSpot(gc, new Vector(0, 0), centerColor);
			
			if (Team.A.equals(team))
			{
				Color foregroundColor = SWTResourceManager.getColor(255, 0, 128);
				drawSpot(gc, new Vector(5.5, 0), foregroundColor);
	
				Color cian = SWTResourceManager.getColor(128, 255, 255);
				Color lt_green = SWTResourceManager.getColor(128, 255, 0);
				
				switch (getIndex())
				{
					case 1:
						drawSpot(gc, new Vector(-5.5, 0), cian);
						break;
					case 2:
						drawSpot(gc, new Vector(-5.5, 0), lt_green);
						break;					
					case 3:
						drawSpot(gc, new Vector(0, -5.5), cian);
						break;					
					case 4:
						drawSpot(gc, new Vector(0, -5.5), lt_green);
						break;									
				}
	
			}
		}
	}


	private void drawSpot(GC gc, Vector spotPositionAbsolute, Color color){
		
		//Rotate the spot to the actual angle
		Vector spotPosition = spotPositionAbsolute.rotate(this.angle);
		
		Color old = gc.getBackground();
		gc.setBackground(color);
		gc.fillOval(realx(position.getX()+ spotPosition.getX() - SPOT_SIZE), realy(position.getY() - spotPosition.getY() + SPOT_SIZE), scalex(SPOT_SIZE * 2), scaley(SPOT_SIZE * 2));
		gc.setBackground(old);
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

	@Override
	public double getRadius() {
		return radius;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public void setFront(int x, int y) {
		this.front.setX(x);
		this.front.setY(y);
	}
	
	public Vector getFront() {
		return this.front;
		
	}
	
	// seta índice do robo
	public void setId(String newId)
	{
		this.id = newId;
	}
	
	//retorna índice do robô
	
	public int getIndex()
	{
		
		return id.charAt(id.length()-1) - '0';
	}	

	public boolean isDisplayForce() {
		return displayForce;
	}


	public void setDisplayForce(boolean displayForce) {
		this.displayForce = displayForce;
	}


	public boolean isDisplayMarks() {
		return displayMarks;
	}


	public void setDisplayMarks(boolean displayMarks) {
		this.displayMarks = displayMarks;
	}


	public boolean isDisplayName() {
		return displayName;
	}


	public void setDisplayName(boolean displayName) {
		this.displayName = displayName;
	}

	@Override
	public void setVelocity(Vector velocity) {
		if(velocity.module() > ROBOT_MAX_VELOCITY){
			velocity = velocity.normalize().multiply(ROBOT_MAX_VELOCITY);
		}
		super.setVelocity(velocity);
	}
}
