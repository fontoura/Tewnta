package br.ufrgs.wumpus.api.model;

public class GameOverException extends Exception {
	private static final long serialVersionUID = 176334674852991236L;
	private boolean wumpus;
	private boolean pit;

	public boolean isWumpus() {
		return wumpus;
	}

	public void setWumpus(boolean wumpus) {
		this.wumpus = wumpus;
	}

	public boolean isPit() {
		return pit;
	}

	public void setPit(boolean pit) {
		this.pit = pit;
	}

	public GameOverException(boolean wumpus, boolean pit) {
		this.wumpus = wumpus;
		this.pit = pit;
	}

	@Override
	public String getMessage() {
		return "Game is over.";
	}
}
