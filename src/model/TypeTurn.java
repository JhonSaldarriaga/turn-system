package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TypeTurn implements Comparable<TypeTurn>, Serializable{

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

	@Override
	public int compareTo(TypeTurn t2) {
		return type.compareTo(t2.getType());
	}
}
