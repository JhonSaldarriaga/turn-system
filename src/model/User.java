package model;

import java.util.*;

public class User {

	private String id;
	private String typeId;
	private String name;
	private String lastName;
	private String address;
	private String cell;
	private boolean attended;
	private boolean suspended;
	
	private ArrayList<Turn> turnsObtained;
	
	public User(String id, String typeId, String name, String lastName, String address, String cell, boolean attended,
			boolean suspended) {
		this.id = id;
		this.typeId = typeId;
		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.cell = cell;
		this.attended = attended;
		this.suspended = suspended;
		turnsObtained = new ArrayList<Turn>();
	}

	public boolean isAttended() {
		return attended;
	}

	public void setAttended(boolean attended) {
		this.attended = attended;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	public String getId() {
		return id;
	}

	public String getTypeId() {
		return typeId;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public String getCell() {
		return cell;
	}

	public ArrayList<Turn> getTurnsObtained() {
		return turnsObtained;
	}
}
