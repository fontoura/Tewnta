package br.ufrgs.f180.server;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

import br.ufrgs.f180.elements.Ball;
import br.ufrgs.f180.elements.GameField;
import br.ufrgs.f180.elements.Robot;
import br.ufrgs.f180.math.Vector;

/**
 * 
 * @author Gabriel Detoni
 *
 */
public class Game {

	//Time interval on which the elements state will be updated in ms
	public static final int GAME_LOOP_INTERVAL = 5;

	private static Game instance = null;
	
	private GameField field;
	private final double WIDTH = 600;
	private final double HEIGHT = 400;
	
	protected Game(){};

	public static Game getInstance(){
		if(instance == null){
			instance = new Game();
		}
		return instance;
	}

	public void setUpField(Canvas canvas){
		field = new GameField(canvas, WIDTH, HEIGHT);
		field.addElement("BALL", new Ball(WIDTH / 2, HEIGHT / 2));
	}
	
	public void addPlayer(String id, double x, double y) throws Exception{
		if(field != null){
			Robot r = new Robot(x, y);
			field.addElement(id, r);
		}
		else {
			throw new Exception("Cannot add element. Configure the field first");
		}
	}

	public void setPlayerForce(String id, double x, double y) throws Exception{
		if(field != null){
			Robot r = (Robot) field.getElement(id);
			r.setForce(new Vector(x, y));
		}
		else {
			throw new Exception("Cannot get element. Configure the field first");
		}
	}
	
	public GameField getField() {
		return field;
	}

	/**
	 * This is the method that updates the game state due a given time in milliseconds.
	 * Usually invoked in the main loop.
	 * @param d time in ms
	 */
	public void updateState(double d) {
		if(field != null){
			field.updateElementsState(d);
		}
	}

	/**
	 * Draws this Game into a Graphics controller. This methor is to be invoked everytime you want to update 
	 * the field state in the screen. Usually inside the main loop of the program.
	 * @param gc
	 */
	public void draw(GC gc) {
		if(field != null) field.draw(gc);
		
	}
}
