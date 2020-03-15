package model;

import java.time.*;

public class Date {

	private LocalDateTime dateTime;
	//private LocalDate d;
	//private LocalTime t;
	
	public Date(int hour, int minute, int second, int day, int month, int year) {
		dateTime = LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.of(hour, minute, second));
	}
	
	public Date(LocalDate date, LocalTime time) {
		dateTime = LocalDateTime.of(date, time);
	}
	
	public Date(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	public void setLocalDateTime(int hour, int minute, int second, int day, int month, int year) {
		dateTime = LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.of(hour, minute, second));
	}
	
	public void setLocalDateTime(LocalDate date, LocalTime time) {
		dateTime = LocalDateTime.of(date, time);
	}
	
	public void setLocalDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	//public LocalDate getDate() {
		//return d;
	//}
	
	//public LocalTime getTime() {
		//return t;
	//}
	
	//public void changeTime(int hour, int minute, int second) {
		//t = LocalTime.of(hour, minute, second);
		//dateTime = LocalDateTime.of(d, t);
	//}
	
	//public void changeDate(int day, int month, int year) {
		//d = LocalDate.of(year, month, day);
		//dateTime = LocalDateTime.of(d, t);
	//}
	
	public void changeDateTime(LocalDateTime dt) {
		dateTime = dt;
	}
	
	public String showAllDate() {
		return dateTime.getDayOfMonth() + "/" + dateTime.getMonthValue() + "/" + dateTime.getYear() + " --- TIME| " + dateTime.getHour() + ":" + dateTime.getMinute() + ":" + dateTime.getSecond();
	}
}
