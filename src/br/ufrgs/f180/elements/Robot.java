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
	
	public Robot(Cartesian position, Team team){
		this.setMass(5);
		this.setForce(new Vector(0, 0));
		this.setVelocity(new Vector(0, 0));
		this.setPosition(position);
		//this.setId(id);
		//this.setFrente(getPosition()+, y);
		this.team = team;
		
	}
	

	public Robot(double x, double y, Team team, double mass, double radius, String id){
		this(new Cartesian(x, y), team);
		this.setMass(mass);
		this.setRadius(radius);
		this.setId(id);
		
	}
	
	@Override
	public void draw(GC gc) {
		Color old = gc.getBackground();
		Color fundo = SWTResourceManager.getColor(0, 0, 0);
		gc.setBackground(fundo);
		gc.fillOval(realx(position.getX() - radius), realy(position.getY() - radius), realx(radius * 2), realy(radius * 2));
		Color centro = Team.A.equals(team) ? SWTResourceManager.getColor(0, 0, 200) : SWTResourceManager.getColor(200, 200, 0);
		gc.setBackground(centro);
		
		double tamMarca = 2.5;

		gc.fillOval(realx(position.getX() - tamMarca ), realy(position.getY() - tamMarca), realx(tamMarca * 2), realy(tamMarca * 2));
		
		if (Team.A.equals(team))
		{
			Color frente = SWTResourceManager.getColor(255, 0, 128);
			gc.setBackground(frente);
			gc.fillOval(realx(position.getX() + 5.5 - tamMarca), realy(position.getY() - tamMarca), realx(tamMarca * 2), realy(tamMarca * 2));
			Color ciano = SWTResourceManager.getColor(128, 255, 255);
			Color verdinho = SWTResourceManager.getColor(128, 255, 0);
			
			switch (getId())
			{
				case 1:
					gc.setBackground(ciano);
					gc.fillOval(realx(position.getX() - 5.5 - tamMarca), realy(position.getY() - tamMarca), realx(tamMarca * 2), realy(tamMarca * 2));
					break;
				case 2:
					gc.setBackground(verdinho);
					gc.fillOval(realx(position.getX() - 5.5 - tamMarca), realy(position.getY() - tamMarca), realx(tamMarca * 2), realy(tamMarca * 2));
					break;					
				case 3:
					gc.setBackground(ciano);
					gc.fillOval(realx(position.getX() - tamMarca), realy(position.getY() - 5.5 - tamMarca), realx(tamMarca * 2), realy(tamMarca * 2));
					break;					
				case 4:
					gc.setBackground(verdinho);
					gc.fillOval(realx(position.getX() - tamMarca), realy(position.getY() - 5.5 - tamMarca), realx(tamMarca * 2), realy(tamMarca * 2));
					break;									
			}
		}
		
		
		
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
