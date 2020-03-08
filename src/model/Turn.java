package model;

public class Turn {

	private char letter;
	private int number;
	
	private User user;
	private TypeTurn type;
	
	public Turn(char le, int num) {
		letter = le;
		number = num;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public char getLetter() {
		return letter;
	}

	public int getNumber() {
		return number;
	}

	public TypeTurn getType() {
		return type;
	}
}
