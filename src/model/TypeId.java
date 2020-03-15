package model;

import java.io.Serializable;

public enum TypeId implements Serializable{

	CD("citizenship_card"),
	ID("identity_card"),
	CR("civil_registration"),
	PP("passport"),
	FC("foreigner_id");
	
	private String type;
	
	private TypeId(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
