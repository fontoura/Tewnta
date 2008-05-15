package br.ufrgs.f180.api;

import javax.jws.WebService;

import br.ufrgs.f180.math.Cartesian;

@WebService
public interface Player {
	String echo(String msg); 
	void login(Long id, Cartesian position);
}
