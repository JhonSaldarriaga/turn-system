package model;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings("serial")
public class User implements Serializable{

	private String id;
	private String typeId;
	private String name;
	private String lastName;
	private String address;
	private String cell;
	private boolean attended;
	private Date suspended;
	private int Strike;
	
	private ArrayList<Turn> turnsObtained;
	
	public User(String id, String typeId, String name, String lastName, String address, String cell) {
		this.id = id;
		this.typeId = typeId;
		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.cell = cell;
		attended = false;
		suspended = null;
		turnsObtained = new ArrayList<Turn>();
		Strike = 0;
	}

	public void addTurn(Turn t) {
		turnsObtained.add(t);
	}
	
	public int getStrike() {
		return Strike;
	}

	public void setStrike(int strike) {
		Strike = strike;
	}

	public boolean isAttended() {
		return attended;
	}

	public void setAttended(boolean attended) {
		this.attended = attended;
	}

	public Date getSuspended() {
		return suspended;
	}

	public void setSuspended(Date suspended) {
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
	
	public String toString() {
		return "ID: " + id + "// NAME: " + name + " - LASTNAME: " + lastName;
	}
}
