package br.ufrgs.f180.server;

import br.ufrgs.f180.elements.Ball;
import br.ufrgs.f180.elements.GameField;
import br.ufrgs.f180.elements.Robot;
import br.ufrgs.f180.gui.MainWindow;
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
	
	private final double WIDTH = 600;
	private final double HEIGHT = 400;
	
	/**
	 * This is the visual container of this game.
	 */
	private MainWindow mainWindow;
	
	protected Game(){};

	public static Game getInstance(){
		if(instance == null){
			instance = new Game();
		}
		return instance;
	}

	public void setUp(MainWindow window) throws Exception{
		this.mainWindow = window;
		mainWindow.setField(new GameField(window.getFootballFieldCanvas(), WIDTH, HEIGHT));
		mainWindow.getField().addElement("BALL", new Ball(WIDTH / 2, HEIGHT / 2));
	}
	
	public void addPlayer(String id, double x, double y) throws Exception{
		mainWindow.addRobot(id, x, y);
	}

	public void setPlayerForce(String id, double x, double y) throws Exception{
		if(mainWindow.getField() != null){
			Robot r = (Robot) mainWindow.getField().getElement(id);
			r.setForce(new Vector(x, y));
		}
		else {
			throw new Exception("Cannot get element. Configure the mainWindow.getField() first");
		}
	}
	
	/**
	 * This is the method that updates the game state due a given time in milliseconds.
	 * Usually invoked in the main loop.
	 * @param d time in ms
	 */
	public void updateState(double d) {
		if(mainWindow.getField() != null){
			mainWindow.getField().updateElementsState(d);
		}
	}
}
