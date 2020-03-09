package model;
import java.time.*;

public class Turn implements Comparable<Turn>{

	private char letter;
	private String number;
	
	private User user;
	private TypeTurn type;
	private Date date;
	
	public Turn(char le, String num, User us, TypeTurn ty, LocalDate d, LocalTime t) {
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

	public String getNumber() {
		return number;
	}

	public TypeTurn getType() {
		return type;
	}
	
	public Date getDate() {
		return date;
	}

	@Override
	public int compareTo(Turn t) {
		LocalDateTime d = date.getDateTime();
		return d.compareTo(t.getDate().getDateTime());
	}
}
