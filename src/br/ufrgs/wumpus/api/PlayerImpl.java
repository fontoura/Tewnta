package br.ufrgs.wumpus.api;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import br.ufrgs.wumpus.api.model.CellInformation;
import br.ufrgs.wumpus.api.model.GameOverException;
import br.ufrgs.wumpus.elements.CellPerception;
import br.ufrgs.wumpus.server.Game;

/**
 * This is the implementation of the Player APIs.
 * 
 * Most information is gathered from the Game.java class.
 * 
 * @author Gabriel Detoni
 * 
 */
@WebService(endpointInterface = "br.ufrgs.wumpus.api.Player", serviceName = "Player")
public class PlayerImpl implements Player {

	private static Logger logger = Logger.getLogger(PlayerImpl.class);

	@Override
	public void grab() {
		Game.getInstance().grab();
	}

	@Override
	public void moveForward() {
		Game.getInstance().moveForward();
	}

	@Override
	public void rotateLeft() {
		Game.getInstance().rotateLeft();
	}

	@Override
	public void rotateRight() {
		Game.getInstance().rotateRight();
	}

	@Override
	public void shoot() {
		Game.getInstance().shoot();
	}

	@Override
	public CellInformation readCellInformation() throws GameOverException {

		CellPerception p = Game.getInstance().readCellInformation();
		CellInformation i = new CellInformation();
		i.setBreeze(p.isFreshAir());
		i.setImpact(p.isImpact());
		i.setRoar(p.isRoar());
		i.setShine(p.isShine());
		i.setStink(p.isSmell());
		return i;
	}

}
