package br.ufrgs.f180.elements;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import br.ufrgs.f180.math.Point;
import br.ufrgs.f180.math.Vector;

import com.cloudgarden.resource.SWTResourceManager;

/**
 * 
 * @author Gabriel Detoni
 *
 */
public class Robot extends MovingElement {
	
	private static final double SPOT_SIZE = 2.5;
	private double radius;
	private Vector frente;
	private String id;

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
		
		/*		
		@Override
		public void valueOf(String str){
			for (Team team : Team.values()) {
				if(team.value.equals(str)) return team;
			}
			return null;
		}
		*/
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
		gc.fillOval(realx(position.getX() - radius), realy(position.getY() - radius), realx(radius * 2), realy(radius * 2));
		Color centerColor = Team.A.equals(team) ? SWTResourceManager.getColor(0, 0, 200) : SWTResourceManager.getColor(200, 200, 0);


		drawSpot(gc, new Vector(0, 0), centerColor);
		
		if (Team.A.equals(team))
		{
			Color foregroundColor = SWTResourceManager.getColor(255, 0, 128);
			drawSpot(gc, new Vector(5.5, 0), foregroundColor);

			Color cian = SWTResourceManager.getColor(128, 255, 255);
			Color lt_green = SWTResourceManager.getColor(128, 255, 0);
			
			switch (getId())
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
		gc.setBackground(old);
		
	}

	private void drawSpot(GC gc, Vector spotPositionAbsolute, Color color){
		
		//Rotate the spot to the actual angle
		Vector spotPosition = spotPositionAbsolute.rotate(this.angle);
		
		Color old = gc.getBackground();
		gc.setBackground(color);
		gc.fillOval(realx(position.getX()+ spotPosition.getX() - SPOT_SIZE), realy(position.getY() + spotPosition.getY() - SPOT_SIZE), realx(SPOT_SIZE * 2), realy(SPOT_SIZE * 2));
		gc.setBackground(old);
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
	
	public void setFrente(int x, int y) {
		this.frente.setX(x);
		this.frente.setY(y);
	}
	
	public Vector getFrente() {
		return this.frente;
		
	}
	
	// seta índice do robo
	public void setId(String newId)
	{
		this.id = newId;
	}
	
	//retorna índice do robô
	
	public int getId()
	{
		
		return id.charAt(id.length()-1) - '0';
	}	
	
}
