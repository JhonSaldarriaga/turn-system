package ui;

import model.*;
import java.util.*;

import customExceptions.UserNotRegisterException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.*;

public class Main {

	private Scanner scan;
	private TurnSystem ts;
	private boolean firstTime;
	
	public static void main(String [] args){
		Main main = new Main();
		main.menu();
	}
	
	public Main() {
		scan = new Scanner(System.in);
		firstTime = true;
		chooseBeggin();
	}
	
	public void menu() {
		
		boolean continueOption;
		int option = 0;
		boolean finish = false;
		System.out.println("-----------------------");
		System.out.println("     W E L C O M E     ");
		System.out.println("-----------------------");
		System.out.println(ts.showDateTime()+"\n");
		while(!finish) {
			System.out.println("1. Add user.\n2. Create new typeTurn.\n3. Assign turn.\n4. Attend all the turns until the actuality.\n5. Change actual date and time.\n6. Generate a report with all the turns that a user has requested.\n7. Generate a report with all the people who have come to have a specific turn.\n8. Generate random users.\n9. Generate random turns");
			continueOption = true;
			do {
				try{
					option = Integer.parseInt(scan.nextLine());
					continueOption = false;
				} catch(NumberFormatException e) {
					System.out.println("ERROR: the option must be int, try again");
					continueOption = true;
				}
			} while(continueOption);
			
			switch(option) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			case 9:
				break;
			default: System.out.println("Invalid option");
				break;
			}
			
			System.out.println("-----------------------");
		}
	}
	
	/**
	 * Este m�todo solo se ejecuta en el constructor del Main. Permite escojer al usuario entre inicializar TurnSystem(ts) con la fecha actual del sistema, o con una establecida por el usuario.
	 */
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
		case 2: int [] date = getDate();
				LocalDate d = LocalDate.of(date[0], date[1], date[2]);
				LocalTime t = LocalTime.of(date[3], date[4], date[5]);
				ts = new TurnSystem(d, t);
		break;
		}
	}
	
	/**
	 * Este metodo pide al usuario los datos de una nueva fecha y tiempo y los recolecta. No permite que estos datos de fecha sean menores a la fecha actual de TurnSystem(ts).
	 * <b>pos:</b> Se han pedido los datos al usuario de manera correcta.<br>
	 * @return Array de tipo int con los datos recojidos.
	 */
	public int[] getDate() {
		LocalDateTime now;
		if(firstTime) {
			now = LocalDateTime.now();
			firstTime = false;
		}else {
			now = ts.getDate().getDateTime();
		}
		System.out.println("ACTUAL TIME: "+ now.getDayOfMonth() + "/" + now.getMonthValue() + "/" + now.getYear() + " --- " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond() + "\n*Remember, you cannot choose a date less than the current one\n");
		int year = getYear(now);
		int month = getMonth(now, year);
		int day = getDay(now, year, month);
		int hour = getHour(now, year, month, day);
		int minute = getMinute(now, year, month, day, hour);
		int second = getSecond(now, year, month, day, hour, minute);
		
		int[] date = {year, month, day, hour, minute, second};
		
		return date;
	}
	
	public int getYear(LocalDateTime now) {
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
		
		return year;
	}
	
	public int getMonth(LocalDateTime now, int year) {
		System.out.print("Type the month: ");
		int month = 0;
		boolean continueOption = true;
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
		
		return month;
	}
	
	public int getDay(LocalDateTime now, int year, int month) {
		System.out.print("Type the day: ");
		int day = 0;
		boolean continueOption = true;
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
		
		return day;
	}
	
	public int getHour(LocalDateTime now, int year, int month, int day) {
		System.out.print("Type the hour: ");
		int hour = 0;
		boolean continueOption = true;
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
		
		return hour;
	}
	
	public int getMinute(LocalDateTime now, int year, int month, int day, int hour ) {
		System.out.print("Type the minute: ");
		int minute = 0;
		boolean continueOption = true;
		do {
			try{
				minute = Integer.parseInt(scan.nextLine());
				if(minute>=60) {
					System.out.println("ERROR: the minute must be between 0 and 59");
				}else {
					if(now.getYear()==year && now.getMonthValue()==month && now.getDayOfMonth()==day && now.getHour()==hour) {
						if(minute>now.getMinute()) {
							continueOption = false;
						}else {
							System.out.println("ERROR: the time must be greater than the system");
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
		
		return minute;
	}
	
	public int getSecond(LocalDateTime now, int year, int month, int day, int hour, int minute) {
		System.out.print("Type the seconds: ");
		int second = 0;
		boolean continueOption = true;
		do {
			try{
				second = Integer.parseInt(scan.nextLine());
				if(second>=60) {
					System.out.println("ERROR: the second must be between 0 and 59");
				}else {
					if(now.getYear()==year && now.getMonthValue()==month && now.getDayOfMonth()==day && now.getHour()==hour && now.getMinute()==minute) {
						if(second>now.getSecond()) {
							continueOption = false;
						}else {
							System.out.println("ERROR: the time must be greater than the system");
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
		
		return second;
	}
}
