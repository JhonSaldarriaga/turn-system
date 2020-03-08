package ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {

	public static void main(String [] args) {
		LocalDate d = LocalDate.now();
		LocalTime t = LocalTime.now();
		LocalDateTime date = LocalDateTime.of(d, t);
		
		System.out.println(date.getDayOfWeek());
	}
}
