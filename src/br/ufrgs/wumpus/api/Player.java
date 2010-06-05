package br.ufrgs.wumpus.api;

import javax.jws.WebService;

import br.ufrgs.wumpus.api.model.CellInformation;
import br.ufrgs.wumpus.api.model.GameOverException;

/**
 * This is the main API provided to players to connect and communicate with the
 * simulator. It's purpose is to provide the same sort of functionality a
 * "sensor" and a "motor" components would do in the Wumpus game. The APIs are
 * published as a web-service and are available as soon as the system starts up.
 * 
 * 
 * API's are documented individually below.
 * 
 * @author Gabriel Detoni
 * 
 */
@WebService
public interface Player {
	/**
	 * Moves forward.
	 */
	public void moveForward();

	/**
	 * Rotates 90 degrees counterclockwise.
	 */
	public void rotateLeft();

	/**
	 * Rotates 90 degrees clockwise.
	 */
	public void rotateRight();

	/**
	 * Shoots an arrow forward until hits Wumpus or a wall.
	 */
	public void shoot();

	/**
	 * Grabs an object.
	 */
	public void grab();

	/**
	 * Reads the information from a given cell.
	 * 
	 * @return
	 * @throws GameOverException 
	 */
	public CellInformation readCellInformation() throws GameOverException;

}
