package model;
import java.time.*;

public class Turn implements Comparable<Turn>{

	private char letter;
	private String number;
	
	private User user;
	private TypeTurn type;
	private Date date;
	
	public Turn(char le, String num, User us, TypeTurn ty, LocalDateTime dt) {
		letter = le;
		number = num;
		user = us;
		date = new Date(dt);
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

	public String getNumber() {
		return number;
	}

	public TypeTurn getType() {
		return type;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String toString() {
		return letter + number + " --- " + "Type: " + type.getType() + "// Date: " + date.showAllDate();
	}
	
	@Override
	public int compareTo(Turn t) {
		LocalDateTime d = date.getDateTime();
		return d.compareTo(t.getDate().getDateTime());
	}
}
