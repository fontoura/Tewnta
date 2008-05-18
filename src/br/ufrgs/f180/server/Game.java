package br.ufrgs.f180.server;

import br.ufrgs.f180.elements.Ball;
import br.ufrgs.f180.elements.GameField;
import br.ufrgs.f180.elements.Robot;
import br.ufrgs.f180.elements.Robot.Team;
import br.ufrgs.f180.gui.MainWindow;
import br.ufrgs.f180.math.Vector;

/**
 * 
 * @author Gabriel Detoni
 *
 */
public class Game {

	private static Game instance = null;
	
	/**
	 * Time interval on which the elements state will be updated in ms
	 */
	public static final int GAME_LOOP_INTERVAL = 5;

	private final double WIDTH = 600;
	private final double HEIGHT = 400;
	
	/**
	 * This is the visual container of this game.
	 */
	private MainWindow mainWindow;
	
	/**
	 * Game state variables
	 */
	private boolean gameRunning;
	private String nameTeamA;
	private String nameTeamB;
	private int scoreTeamA;
	private int scoreTeamB;
	private long elapsedTime;
	
	protected Game(){};

	public static Game getInstance(){
		if(instance == null){
			instance = new Game();
		}
		return instance;
	}

	public void resetGame(){
		gameRunning = false;
		nameTeamA = null;
		nameTeamB = null;
		scoreTeamA = 0;
		scoreTeamB = 0;
		elapsedTime = 0;
	}
	
	public void startGame(){
		gameRunning = true;
	}

	public void pauseGame(){
		gameRunning = false;
	}
	
	public void setUp(MainWindow window) throws Exception{
		this.mainWindow = window;
		mainWindow.setField(new GameField(window.getFootballFieldCanvas(), WIDTH, HEIGHT));
		setUpBall();
	}
	
	public void addPlayer(String teamId, String id, double x, double y) throws Exception{
		mainWindow.addRobot(id, x, y, getTeam(teamId));
	}
	

	private Team getTeam(String teamId) {
		return Team.valueOf(teamId);
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
		if(gameRunning){
			elapsedTime += d * 1000;
			if(mainWindow.getField() != null){
				mainWindow.getField().updateElementsState(d);
				ScoredGoal s = goalScored();
				switch (s) {
				case SCORED_LEFT:
					System.out.println("Goal Team A");
					gameRunning = false;
					setUpBall();
					scoreTeamB++;
					break;
				case SCORED_RIGHT:
					System.out.println("Goal Team B");
					gameRunning = false;
					setUpBall();
					scoreTeamA++;
					break;
				}
			}
		}
	}
	
	/**
	 * Put the Ball back into its initial position
	 */
	private void setUpBall() {
		try {
			mainWindow.getField().addElement(GameField.BALL_ELEMENT, new Ball(WIDTH / 2, HEIGHT / 2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public enum ScoredGoal {
		NONE,
		SCORED_LEFT,
		SCORED_RIGHT
	};
	
	public ScoredGoal goalScored() {
		
		Ball b = (Ball)mainWindow.getField().getElement(GameField.BALL_ELEMENT);
		double left = b.getPosition().getX() - b.getRadius();
		double right = b.getPosition().getX() + b.getRadius();
		if(left >= mainWindow.getField().getRightBound()){
			return ScoredGoal.SCORED_RIGHT;
		}
		if(right <= mainWindow.getField().getLeftBound()){
			return ScoredGoal.SCORED_LEFT;
		}
		return ScoredGoal.NONE;
	}

	public int getScoreTeamA() {
		return scoreTeamA;
	}

	public void setScoreTeamA(int scoreTeamA) {
		this.scoreTeamA = scoreTeamA;
	}

	public int getScoreTeamB() {
		return scoreTeamB;
	}

	public void setScoreTeamB(int scoreTeamB) {
		this.scoreTeamB = scoreTeamB;
	}

	public long getElapsedTime() {
		return elapsedTime;
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

	public boolean getGameRunning() {
		return gameRunning;
	}

	public String login(String teamName) throws Exception{
		if(nameTeamA == null){
			nameTeamA = teamName;
			return Team.A.toString();
		}
		else if(nameTeamB == null){
			nameTeamB = teamName;
			return Team.B.toString();
		}
		else {
			throw new Exception("All players already defined.");
		}
	}

	public String getNameTeamA() {
		return nameTeamA;
	}

	public String getNameTeamB() {
		return nameTeamB;
	}

	public Object getTeamName(Team team) {
		return Team.A.equals(team) ? nameTeamA : nameTeamB;
	}
}
