package br.ufrgs.wumpus.server;

import org.apache.log4j.Logger;

import br.ufrgs.wumpus.api.model.CellInformation;
import br.ufrgs.wumpus.api.model.GameOverException;
import br.ufrgs.wumpus.elements.CellPerception;
import br.ufrgs.wumpus.elements.GameField;
import br.ufrgs.wumpus.gui.MainWindow;

/**
 * 
 * @author Gabriel Detoni
 * 
 */
public class Game {

	private static Logger logger = Logger.getLogger(Game.class);

	private static Game instance = null;

	/**
	 * Time interval on which the elements state will be updated in ms
	 */
	public static final int GAME_LOOP_INTERVAL = 500;

	/**
	 * This is the visual container of this game.
	 */
	private MainWindow mainWindow;

	/**
	 * Game state variables
	 */
	private boolean gameRunning;
	private double elapsedTime;

	protected Game() {
	};

	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}

	public void resetGame() {
		gameRunning = false;
		elapsedTime = 0;
	}

	public void startGame() {
		gameRunning = true;
	}

	public void pauseGame() {
		gameRunning = false;
	}

	public void setUp(MainWindow window) throws Exception {
		this.mainWindow = window;
		mainWindow.setField(new GameField(window.getFootballFieldCanvas()));
	}


	/**
	 * This is the method that updates the game state due a given time in
	 * milliseconds. Usually invoked in the main loop.
	 * 
	 * @param d
	 *            time in ms
	 */
	public void updateState(double d) {

		if (mainWindow.getField() != null) {
			double timeInterval = 0;
			if (gameRunning) {
				timeInterval = d;
			}
			elapsedTime += timeInterval * 1000d;
		}
	}

	public long getElapsedTime() {
		return (long)elapsedTime;
	}

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public boolean isGameRunning() {
		return gameRunning;
	}

	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}
	
	
	public void grab() {
		// TODO Auto-generated method stub
	}

	public void moveForward() {
		mainWindow.getField().moveWarriorForward();
	}

	public CellPerception readCellInformation() throws GameOverException {
		if (mainWindow.getField() != null) {
			CellPerception p = mainWindow.getField().getCellPerception();
			return p;
		}
		return null;
	}

	public void rotateLeft() {
		mainWindow.getField().rotateWarriorLeft();		
	}

	public void rotateRight() {
		mainWindow.getField().rotateWarriorRight();		
		
	}

	public void shoot() {
		// TODO Auto-generated method stub
	}
	
}
