package br.ufrgs.f180.api;

import javax.jws.WebService;

/**
 * 
 * @author Gabriel Detoni
 *
 */
@WebService
public interface Player {
	String echo(String msg); 
	void login(String id, Double x, Double y) throws Exception;
	void setPlayerForce(String id, Double x, Double y) throws Exception;
}
