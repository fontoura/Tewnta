package br.ufrgs.wumpus.api.model;

import java.io.Serializable;


/**
 * API abstraction of the CellPerception object.
 * 
 * @author Gabriel
 *
 */
public class CellInformation implements Serializable{
	private static final long serialVersionUID = -8019702689096203165L;
	private boolean stink;
	private boolean shine;
	private boolean breeze;
	private boolean roar;
	private boolean impact;

	public boolean isStink() {
		return stink;
	}

	public void setStink(boolean stink) {
		this.stink = stink;
	}

	public boolean isShine() {
		return shine;
	}

	public void setShine(boolean shine) {
		this.shine = shine;
	}

	public boolean isBreeze() {
		return breeze;
	}

	public void setBreeze(boolean breeze) {
		this.breeze = breeze;
	}

	public boolean isRoar() {
		return roar;
	}

	public void setRoar(boolean roar) {
		this.roar = roar;
	}

	public boolean isImpact() {
		return impact;
	}

	public void setImpact(boolean impact) {
		this.impact = impact;
	}

}
