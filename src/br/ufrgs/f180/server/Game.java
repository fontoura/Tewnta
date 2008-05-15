package br.ufrgs.f180.server;


public class Game {
	private static Game instance = null;
	
	protected Game(){};

	public Game getInstance(){
		if(instance == null){
			instance = new Game();
		}
		return instance;
	}
}
