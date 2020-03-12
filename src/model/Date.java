package model;

import java.time.*;

public class Date {

	private LocalDateTime dateTime;
	private LocalDate d;
	private LocalTime t;
	
	public Date(int hour, int minute, int second, int day, int month, int year) {
		d = LocalDate.of(year, month, day);
		t = LocalTime.of(hour, minute, second);
		dateTime = LocalDateTime.of(d, t);
	}
	
	public Date(LocalDate date, LocalTime time) {
		d = date;
		t = time;
		dateTime = LocalDateTime.of(d, t);
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	public LocalDate getDate() {
		return d;
	}
	
	public LocalTime getTime() {
		return t;
	}
	
	public void changeTime(int hour, int minute, int second) {
		t = LocalTime.of(hour, minute, second);
		dateTime = LocalDateTime.of(d, t);
	}
	
	public void changeDate(int day, int month, int year) {
		d = LocalDate.of(year, month, day);
		dateTime = LocalDateTime.of(d, t);
	}
	
	public String showAllDate() {
		return dateTime.getDayOfMonth() + "/" + dateTime.getMonthValue() + "/" + dateTime.getYear() + " --- TIME| " + dateTime.getHour() + ":" + dateTime.getMinute() + ":" + dateTime.getSecond();
	}
}
