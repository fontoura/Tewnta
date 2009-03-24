package br.ufrgs.f180.api;

import javax.jws.WebService;

import br.ufrgs.f180.server.Game;

/**
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

}
