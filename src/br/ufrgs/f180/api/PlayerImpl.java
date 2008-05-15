package br.ufrgs.f180.api;

import javax.jws.WebService;

import br.ufrgs.f180.math.Cartesian;

@WebService(endpointInterface = "br.ufrgs.f180.api.Player", serviceName = "Player")
public class PlayerImpl implements Player {

	@Override
	public String echo(String msg) {
		System.out.println("echo: " + msg);
		return msg;
	}

	@Override
	public void login(Long id, Cartesian position) {

	}

}
