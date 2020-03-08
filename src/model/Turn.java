package model;
import java.time.*;

public class Turn {

	private char letter;
	private int number;
	
	private User user;
	private TypeTurn type;
	private Date date;
	
	public Turn(char le, int num, User us, TypeTurn ty, LocalDate d, LocalTime t) {
		letter = le;
		number = num;
		user = us;
		date = new Date(d, t);
		type = ty;
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
	
	public Date getDate() {
		return date;
	}
}
