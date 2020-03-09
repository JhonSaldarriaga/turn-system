package ui;

import model.*;
import java.util.*;
import java.time.*;

public class Main {

	private Scanner scan;
	private TurnSystem ts;
	
	public static void main(String [] args) throws InterruptedException {
		Main main = new Main();
	}
	
	public Main() {
		scan = new Scanner(System.in);
		chooseBeggin();
	}
	
	public void menu() {
		
	}
	
	public void chooseBeggin() {
		System.out.println("Do yo want use the local time and local date?\n 1.YES\n 2.NO");
		boolean continueOption = true;
		int option = 0;
		do {
			try{
				option = Integer.parseInt(scan.nextLine());
				if(option!=1 && option!=2) {
					System.out.println("Invalid option");
				}else {
					continueOption = false;
				}
			} catch(NumberFormatException e) {
				System.out.println("ERROR: the option must be int, try again");
				continueOption = true;
			}
		} while(continueOption);
		
		switch(option) {
		case 1: ts = new TurnSystem();
		break;
		case 2: int [] date = setDate();
				LocalDate d = LocalDate.of(date[0], date[1], date[2]);
				LocalTime t = LocalTime.of(date[3], date[4], date[5]);
				ts = new TurnSystem(d, t);
		break;
		}
	}
	
	public int[] setDate() {
		LocalDateTime now = LocalDateTime.now();
		System.out.println("ACTUAL TIME: "+ now.getDayOfMonth() + "/" + now.getMonthValue() + "/" + now.getYear() + " --- " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond() + "\n*Remember, you cannot choose a date less than the current one\n");
		System.out.print("Type the year: ");
		int year = 0;
		boolean continueOption = true;
		do {
			try{
				year = Integer.parseInt(scan.nextLine());
				if(year>=now.getYear()) {
					continueOption = false;
				}else {
					System.out.println("ERROR: the year must be greater than or equal to the system");
				}
			} catch(NumberFormatException e) {
				System.out.println("ERROR: the year must be int, try again");
				continueOption = true;
			}
		} while(continueOption);
		
		System.out.print("Type the month: ");
		int month = 0;
		continueOption = true;
		do {
			try{
				month = Integer.parseInt(scan.nextLine());
				if(month>=13) {
					System.out.println("ERROR: the month must be between 1 and 12");
				}else {
					if(year==now.getYear()) {
						if(month>=now.getMonthValue()) {
							continueOption = false;
						}else {
							System.out.println("ERROR: the month must be greater than or equal to the system");
						}
					}else {
						continueOption = false;
					}
				}
			} catch(NumberFormatException e) {
				System.out.println("ERROR: the month must be int, try again");
				continueOption = true;
			}
		} while(continueOption);
		
		System.out.print("Type the day: ");
		int day = 0;
		continueOption = true;
		do {
			try{
				day = Integer.parseInt(scan.nextLine());
				if(month == 11 || month == 4 || month == 6 || month == 9) {
					if(day>=31) {
						System.out.println("ERROR: the day must be between 1 and 30");
					}else {
						if(year==now.getYear() && month==now.getMonthValue()) {
							if(day>=now.getDayOfMonth()) {
								continueOption = false;
							}else {
								System.out.println("ERROR: the day must be greater than or equal to the system");
							}
						}else {
							continueOption = false;
						}
					}
				}else {
					if(month == 2) {
						if(day>=29) {
							System.out.println("ERROR: the day must be between 1 and 28");
						}else {
							if(year==now.getYear() && month==now.getMonthValue()) {
								if(day>=now.getDayOfMonth()) {
									continueOption = false;
								}else {
									System.out.println("ERROR: the day must be greater than or equal to the system");
								}
							}else {
								continueOption = false;
							}
						}
					}else {
						if(day>=32) {
							System.out.println("ERROR: the day must be between 1 and 31");
						}else {
							if(year==now.getYear() && month==now.getMonthValue()) {
								if(day>=now.getDayOfMonth()) {
									continueOption = false;
								}else {
									System.out.println("ERROR: the day must be greater than or equal to the system");
								}
							}else {
								continueOption = false;
							}
						}
					}
				}
			} catch(NumberFormatException e) {
				System.out.println("ERROR: the day must be int, try again");
				continueOption = true;
			}
		} while(continueOption);
		
		System.out.print("Type the hour: ");
		int hour = 0;
		continueOption = true;
		do {
			try{
				hour = Integer.parseInt(scan.nextLine());
				if(hour>=24) {
					System.out.println("ERROR: the hour must be between 0 and 23");
				}else {
					if(now.getYear()==year && now.getMonthValue()==month && now.getDayOfMonth()==day) {
						if(hour>=now.getHour()) {
							continueOption = false;
						}else {
							System.out.println("ERROR: the time must be greater than or equal to the system");
						}
					}else {
						continueOption = false;	
					}
				}
			} catch(NumberFormatException e) {
				System.out.println("ERROR: the hour must be int, try again");
				continueOption = true;
			}
		} while(continueOption);
		
		System.out.print("Type the minute: ");
		int minute = 0;
		continueOption = true;
		do {
			try{
				minute = Integer.parseInt(scan.nextLine());
				if(minute>=60) {
					System.out.println("ERROR: the minute must be between 0 and 59");
				}else {
					if(now.getYear()==year && now.getMonthValue()==month && now.getDayOfMonth()==day && now.getHour()==hour) {
						if(minute>=now.getMinute()) {
							continueOption = false;
						}else {
							System.out.println("ERROR: the time must be greater than or equal to the system");
						}
					}else {
						continueOption = false;	
					}
				}
			} catch(NumberFormatException e) {
				System.out.println("ERROR: the minute must be int, try again");
				continueOption = true;
			}
		} while(continueOption);
		
		System.out.print("Type the seconds: ");
		int second = 0;
		continueOption = true;
		do {
			try{
				second = Integer.parseInt(scan.nextLine());
				if(second>=60) {
					System.out.println("ERROR: the second must be between 0 and 59");
				}else {
					if(now.getYear()==year && now.getMonthValue()==month && now.getDayOfMonth()==day && now.getHour()==hour && now.getMinute()==minute) {
						if(second>=now.getSecond()) {
							continueOption = false;
						}else {
							System.out.println("ERROR: the time must be greater than or equal to the system");
						}
					}else {
						continueOption = false;	
					}
				}
			} catch(NumberFormatException e) {
				System.out.println("ERROR: the second must be int, try again");
				continueOption = true;
			}
		} while(continueOption);
		
		int[] date = {year, month, day, hour, minute, second};
		
		return date;
	}
}
