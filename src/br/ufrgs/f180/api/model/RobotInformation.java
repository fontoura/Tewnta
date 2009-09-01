package br.ufrgs.f180.api.model;

public class RobotInformation extends ElementInformation {
	protected String id;
	// dribbler and kicker
	protected Boolean dribbling;
	protected Boolean kicking;

	public Boolean getDribbling() {
		return dribbling;
	}

	public void setDribbling(Boolean dribbling) {
		this.dribbling = dribbling;
	}

	public Boolean getKicking() {
		return kicking;
	}

	public void setKicking(Boolean kicking) {
		this.kicking = kicking;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
