package ui;

import model.*;
import java.util.*;

import customExceptions.*;
import java.io.*;
import java.time.*;

public class Main {

	private Scanner scan;
	private TurnSystem ts;
	private boolean firstTime;
	private TypeId typeId;
	private  static final String TS_DATA = "data/TS.obj";
	
	public static void main(String [] args) throws ClassNotFoundException{
		Main main = new Main();
		main.menu();
		//main.pruebas();
	}
	
	public Main() throws ClassNotFoundException {
		scan = new Scanner(System.in);
		load();
		if(firstTime) {
			chooseBeggin();	
		}
	}
	
	public void pruebas() throws IOException{
		for(int i = 0; i<100; i++) {
			int random = (int) (Math.random()*(100-1));	
			System.out.println(random);
		}
	}
	
	public void menu() {
		boolean continueOption;
		int option = 0;
		boolean finish = false;
		System.out.println("-----------------------");
		System.out.println("     W E L C O M E     ");
		System.out.println("-----------------------");
		while(!finish) {
			System.out.println(ts.showDateTime()+"\n");
			System.out.println("1. Add user.\n2. Create new typeTurn.\n3. Assign turn.\n4. Show all Active turn.\n5. Show all register users.\n6. Attend all the turns until the actuality.\n7. Change actual date and time.\n8. Generate a report with all the turns that a user has requested.\n9. Generate a report with all the people who have come to have a specific turn.\n10. Generate random users.\n11. Generate turns until a day specify for the user.\n12. Exit.");
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
			case 1: addUser();
				break;
			case 2: createTypeTurn();
				break;
			case 3: assignTurn();
				break;
			case 4: if(ts.showAllActiveTurns().equals("")){
						System.out.println("Theres not exist turns yet.");
					}else {
						System.out.println(ts.showAllActiveTurns());
					}
				break;
				case 5:if(ts.showAllUsers().equals("")) {
							System.out.println("Users not exist yet");
						}else {
							ts.sortUsersByName();
							System.out.println(ts.showAllUsers());
						}
				break;
			case 6: if(ts.attendTurns()==null) 
						System.out.println("Theres not exist turns yet.");
					else
						System.out.println(ts.attendTurns());
				break;
			case 7: editDate();
				break;
			case 8: generateReportTurns();
				break;
			case 9: generateReportUsers();
				break;
			case 10:generateRandomPeople();
				break;
			case 11: generateTurns();
				break;
			case 12:System.out.println("Bye.");
					finish = true;
					save();
				break;
			default: System.out.println("Invalid option");
				break;
			}
			
			System.out.println("-----------------------");
			ts.upgradeTheTime();
		}
	}
	
	public void save() {
		File file = new File(TS_DATA);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		try {
			FileOutputStream fo = new FileOutputStream(file);
			ObjectOutputStream oo = new ObjectOutputStream(fo);
			oo.writeObject(ts);
			oo.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@SuppressWarnings("resource")
	public void load() throws ClassNotFoundException {
		File file = new File(TS_DATA);
		if(file.exists()) {
			firstTime = false;
			try {
				FileInputStream fi = new FileInputStream(TS_DATA);
				ObjectInputStream oi;
				oi = new ObjectInputStream(fi);
				ts = (TurnSystem)oi.readObject();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}else
			firstTime = true;
	}
	
	public void generateReportUsers() {
		System.out.println("Do you want generate document report or show in the screen?");
		int option = 0;
		boolean continueOption = true;
		do {
			try{
				System.out.println("1. GENERATE DOCUMENT.\n2. SHOW.");
				option = Integer.parseInt(scan.nextLine());
				continueOption = false;
			} catch(NumberFormatException e) {
				System.out.println("ERROR: the option must be int, try again");
				continueOption = true;
			}
		} while(continueOption);
		
		System.out.println("Type letter");
		char letter = scan.nextLine().charAt(0);
		String number = scan.nextLine();
		if(option == 1) {
			try {
				ts.generateReportUsersText(letter, number);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}else {
			try {
				System.out.println(ts.generateReportUsersShow(letter, number));
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void generateReportTurns() {
		System.out.println("Do you want generate document report or show in the screen?");
		int option = 0;
		boolean continueOption = true;
		do {
			try{
				System.out.println("1. GENERATE DOCUMENT.\n2. SHOW.");
				option = Integer.parseInt(scan.nextLine());
				continueOption = false;
			} catch(NumberFormatException e) {
				System.out.println("ERROR: the option must be int, try again");
				continueOption = true;
			}
		} while(continueOption);
		
		System.out.println("Type the id");
		String id = scan.nextLine();
		if(option == 1) {
			if(ts.searchPersonPosition(id)!=-1) {
				try {
					ts.generateReportTurnText(id);
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}else
				System.out.println("User not exist");
		}else {
			if(ts.searchPersonPosition(id)!=-1) {
				System.out.println(ts.generateReportTurnShow(id));
			}else
				System.out.println("User not exist");
		}
	}
	
	public void generateTurns() {
		
		System.out.println("for how many days do you want to generate new turns?");
		int option = 0;
		boolean continueOption = true;
		do {
			try{
				option = Integer.parseInt(scan.nextLine());
				continueOption = false;
			} catch(NumberFormatException e) {
				System.out.println("ERROR: the option must be int, try again");
				continueOption = true;
			}
		} while(continueOption);
		
			try {
				System.out.println(ts.generateTurnsUntilADay(option));
			} catch (ArrayListEmptyException e) {
				System.out.println("ERROR" + e.getProblem());
			} catch (UserNotRegisterException e) {
				e.printStackTrace();
			} catch (ExistActiveTurnException e) {
				e.printStackTrace();
			} catch (IsSuspendedException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Este metodo es una de las opciones del menu de TurnManager, permite añadir un usuario al programa TurnManager(tm).
	 * <b>pre:</b> El TurnManager(tm) debe de estar inicializado.<br>
	 * <b>pos:</b> Se ha añadido un nuevo usuario al programa TurnManager(tm).<br>
	 */
	public void addUser() {
		System.out.println("Type name");
		String name = scan.nextLine();
		System.out.println("Type lastname");
		String lastName = scan.nextLine();
		System.out.println("Type address");
		String address = scan.nextLine();
		System.out.println("Type cell");
		String cell = scan.nextLine();
		System.out.println("Choose an option for type id: ");
		System.out.println("");
		String typeId = null;
		boolean choose = false;
		boolean continueOption;
		int option = 0;
		int cont = 1;
		while(!choose) {
			for(TypeId type : TypeId.values()) {
				System.out.println(cont+". "+type.getType());
				cont++;
			}
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
			case 1:	typeId = this.typeId.CD.getType();
					choose = true;
				break;
			case 2: typeId = this.typeId.CR.getType();
					choose = true;
				break;
			case 3: typeId = this.typeId.FC.getType();
					choose = true;
				break;	
			case 4: typeId = this.typeId.ID.getType();
					choose = true;
				break;
			case 5:	typeId =this.typeId.PP.getType();
					choose = true;
				break;
			default: System.out.println("not valid option. Please, type a valid option");
				break;
			}
		}
		
		System.out.println("Type number id");
		String numberId = scan.nextLine();

		try {
			if(!name.equals("") && !lastName.equals("") && !numberId.equals("")) {
				try{
					ts.addUser(numberId, typeId, name, lastName, address, cell);
					System.out.println("User has been added");
				}catch (UserExistException e) {
					System.out.println(e.getMessage());
				}
			}
			else
				throw new MandatoryParameterNotTypeException(name, lastName, numberId, typeId);
			
		}catch (MandatoryParameterNotTypeException e) {
			System.out.println(e.getMessage()+"\n"+e.getProblem());
		}
	}
	
	/**
	 * Este metodo es una de las opciones del menu de TurnManager, permite asignar un turno a un usuario de TurnManager(tm).
	 * <b>pre:</b> El TurnManager(tm) debe de estar inicializado.<br>
	 * <b>pos:</b> Se ha asignado un turno a un usuario existente en el programa TurnManager(tm).<br>
	 */
	public void assignTurn() {
		if(ts.showAllTypeTurns()!=null) {
			System.out.println("Type the number id of the person you want to register");
			String id = scan.nextLine();
			
			int option = 0;
			boolean continueOption = true;
			do {
				try{
					System.out.println(ts.showAllTypeTurns());
					option = Integer.parseInt(scan.nextLine());
					if(option>ts.getTypesOfTurns().size() || option<1) {
						System.out.println("Invalid option, the number must be in the list.");
					}else {
						continueOption = false;
					}
				} catch(NumberFormatException e) {
					System.out.println("ERROR: the option must be int, try again");
					continueOption = true;
				}
			} while(continueOption);
			
			try {
				ts.assignTurn(id, ts.getTypesOfTurns().get(option-1));
				System.out.println("Turn added correctly");
			} catch (UserNotRegisterException e) {
				System.out.println(e.getMessage());
			} catch (ExistActiveTurnException e) {
				System.out.println("The turn already exist:\n" + "Turn: " + e.getTurn() + " --- " + e.getType());
			}catch (IsSuspendedException e) {
				System.out.println(e.getMessage());
			}
		}else {
			System.out.println("The types of the turns do not exist yet. Please, create the types of turns");
		}
	}
	
	public void createTypeTurn() {
		System.out.println("Type the name of the type");
		String type = scan.nextLine();
		System.out.println("Type the duration of the type turn");
		float du = 0;
		boolean continueOption = true;
		do {
			try{
				du = Float.parseFloat(scan.nextLine());
				continueOption = false;
			} catch(NumberFormatException e) {
				System.out.println("ERROR: the option must be float, try again");
				continueOption = true;
			}
		} while(continueOption);
		
		try {
			ts.addTypeTurn(du, type);
			System.out.println("The type has been added.");
		}catch(TypeTurnExistException e) {
			System.out.println(e.getProblem());
		}
	}
	
	public void editDate() {
		int [] date = getDate();
		LocalDate d = LocalDate.of(date[0], date[1], date[2]);
		LocalTime t = LocalTime.of(date[3], date[4], date[5]);
		ts.editDate(d, t);
	}
	
	public void generateRandomPeople() {
		System.out.println("How many people do you want to generate?");
		boolean continueOption = true;
		int option = 0;
		do {
			try{
				option = Integer.parseInt(scan.nextLine());
				if((""+option).length()>9) {
					System.out.println("Invalid option, MAX number is 100000");
				}else {
					continueOption = false;
				}
			} catch(NumberFormatException e) {
				System.out.println("ERROR: the option must be int, try again");
				continueOption = true;
			}
		} while(continueOption);
		
		ts.generateRandomUsers(option);
		System.out.println("Successful");
	}
	
	/**
	 * Este método solo se ejecuta en el constructor del Main. Permite escojer al usuario entre inicializar TurnSystem(ts) con la fecha actual del sistema, o con una establecida por el usuario.
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
		case 1: try {
				ts = new TurnSystem();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		break;
		case 2: int [] date = getDate();
				LocalDate d = LocalDate.of(date[0], date[1], date[2]);
				LocalTime t = LocalTime.of(date[3], date[4], date[5]);
			try {
				ts = new TurnSystem(d, t);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
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
