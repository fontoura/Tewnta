package br.ufrgs.f180.api;

import java.util.List;

import javax.jws.WebService;

import br.ufrgs.f180.api.model.BallInformation;
import br.ufrgs.f180.api.model.RobotInformation;
import br.ufrgs.f180.server.Game;

/**
 * This is the implementation of the Player APIs. 
 * 
 * Most information is gathered from the Game.java class.
 * 
 * @author Gabriel Detoni
 *
 */
@WebService(endpointInterface = "br.ufrgs.f180.api.Player", serviceName = "Player")
public class PlayerImpl implements Player {

	@Override
	public String echo(String msg) {
		System.out.println("echo: " + msg);
		return msg;
	}

	@Override
	public void setPlayer(String teamId, String id, Double x, Double y) throws Exception {
		Game.getInstance().addPlayer(teamId, id, x, y);
	}

	@Override
	public void setPlayerForce(String id, Double x, Double y) throws Exception {
		Game.getInstance().setPlayerForce(id, x, y);
	}

	@Override
	public String login(String teamName) throws Exception {
		return Game.getInstance().login(teamName);
	}

	@Override
	public void setPlayerRotation(String id, Double force) throws Exception {
		Game.getInstance().setPlayerRotation(id, force);
	}

	@Override
	public void logout(String teamId) throws Exception {
		
		Game.getInstance().logout(teamId);
	}

	@Override
	public BallInformation getBallInformation() {
		return Game.getInstance().getBallInformation();
	}

	@Override
	public List<RobotInformation> getRobotsFromTeam(String teamId) {
		return Game.getInstance().getRobotsFromTeam(teamId);
	}

	@Override
	public RobotInformation getPlayerInformation(String playerId) {
		return Game.getInstance().getPlayerInformation(playerId);
	}

	@Override
	public void setPlayerVelocity(String id, Double x, Double y)
			throws Exception {
		System.out.println("Set Velocity" + x + " " + y);
		Game.getInstance().setPlayerVelocity(id, x, y);		
	}

	@Override
	public void setPlayerRotationVelocity(String id, Double velocity)
			throws Exception {
		Game.getInstance().setPlayerRotationVelocity(id, velocity);		
	}

}
