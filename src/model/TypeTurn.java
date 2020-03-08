package model;

public class TypeTurn {

	private float duration;
	private String type;
	
	public TypeTurn(float du, String ty) {
		duration = du;
		type = ty;
	}

	public float getDuration() {
		return duration;
	}

	public String getType() {
		return type;
	}
}
