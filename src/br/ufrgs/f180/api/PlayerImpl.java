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
	public void login(String id, Double x, Double y) throws Exception {
		Game.getInstance().addPlayer(id, x, y);
	}

	@Override
	public void setPlayerForce(String id, Double x, Double y) throws Exception {
		Game.getInstance().setPlayerForce(id, x, y);
	}

}
