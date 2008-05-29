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
	
	private double radius;

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
	
	public Robot(Cartesian position, Team team){
		this.setMass(5);
		this.setForce(new Vector(0, 0));
		this.setVelocity(new Vector(0, 0));
		this.setPosition(position);
		this.team = team;
		
	}
	
	public Robot(double x, double y, Team team, double mass, double radius){
		this(new Cartesian(x, y), team);
		this.setMass(mass);
		this.setRadius(radius);
		
	}
	
	@Override
	public void draw(GC gc) {
		Color old = gc.getForeground();
		Color c = Team.A.equals(team) ? SWTResourceManager.getColor(50, 50, 200) : SWTResourceManager.getColor(200, 50, 50);
		gc.setForeground(c);
		gc.drawOval(realx(position.getX() - radius), realy(position.getY() - radius), realx(radius * 2), realy(radius * 2));
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
}
