package br.ufrgs.wumpus.elements;

public class Cell {
	private boolean wumpus;
	private boolean gold;
	private boolean pit;
	private boolean warrior;

	public boolean isWumpus() {
		return wumpus;
	}

	public void setWumpus(boolean wumpus) {
		this.wumpus = wumpus;
	}

	public boolean isGold() {
		return gold;
	}

	public void setGold(boolean gold) {
		this.gold = gold;
	}

	public boolean isPit() {
		return pit;
	}

	public void setPit(boolean pit) {
		this.pit = pit;
	}

	public void setWarrior(boolean warrior) {
		this.warrior = warrior;
	}

	public boolean isWarrior() {
		return warrior;
	}
}
