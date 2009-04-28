package br.ufrgs.f180.api;

import java.util.List;

import javax.jws.WebService;

import br.ufrgs.f180.api.model.BallInformation;
import br.ufrgs.f180.api.model.RobotInformation;

/**
 * 
 * @author Gabriel Detoni
 *
 */
@WebService
public interface Player {
	String echo(String msg);
	String login(String teamName) throws Exception;
	void logout(String teamId) throws Exception;
	void setPlayer(String teamId, String id, Double x, Double y) throws Exception;
	void setPlayerForce(String id, Double x, Double y) throws Exception;
	void setPlayerRotation(String id, Double force) throws Exception;
	List<RobotInformation> getRobotsFromTeam(String teamId);
	RobotInformation getPlayerInformation(String playerId);
	BallInformation getBallInformation();
}
