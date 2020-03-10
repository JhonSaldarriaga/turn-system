package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import customExceptions.*;

import java.time.*;

public class TurnSystem implements Serializable{

	public static final int WAIT_TIME = 15;
	public static final String DATEBASE_NAME = "*";
	public static final String DATEBASE_LASTNAME = "*";
	
	private Turn actualTurn;
	private ArrayList<Turn> turns;
	private ArrayList<TypeTurn> typesOfTurns;
	private ArrayList<User> users;
	private Date date;
	private int[] differences;//year, month, day, hour, minutes, second.
	private Alphabet[] alphabet;
	
	public TurnSystem() {
		alphabet = Alphabet.values();
		date = new Date(LocalDate.now(), LocalTime.now());
		actualTurn = new Turn('A', "00", null, null, LocalDate.now(), LocalTime.now());
		turns = new ArrayList<Turn>();
		typesOfTurns = new ArrayList<TypeTurn>();
		users = new ArrayList<User>();
		differences = new int[6];
	}
	
	public TurnSystem(LocalDate d, LocalTime t) {
		alphabet = Alphabet.values();
		date = new Date(d, t);
		actualTurn = new Turn('A', "00", null, null, d, t);
		turns = new ArrayList<Turn>();
		typesOfTurns = new ArrayList<TypeTurn>();
		users = new ArrayList<User>();
		differences = new int[6];
		setDifferences();
	}
	
	public void setDifferences() {
			LocalDateTime now = LocalDateTime.now();
			Period period = Period.between(LocalDate.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth()), date.getDate());
			differences[0] = period.getYears();
			differences[1] = period.getMonths();
			differences[2] = period.getDays();
			differences[3] = date.getTime().getHour() - now.getHour();
			differences[4] = date.getTime().getMinute() - now.getMinute();
			differences[5] = date.getTime().getSecond() -now.getSecond();
			differences[5] += differences[4]*60 + differences[3]*60*60;
	}
	
	public void upgradeTheTime() {
		LocalDate dateNow = LocalDate.now();
		LocalTime timeNow = LocalTime.now();
		int newSecond = timeNow.getSecond();
		int newMinute = timeNow.getMinute();
		int newHour = timeNow.getHour();
		int newDay = 0;
		int newMonth = 0;
		int newYear = 0;
		

		if(differences[5]<0) {
			int d = differences[5];
			while(d!=0) {
				newSecond --;
				d++;
				if(newSecond==-1) {
					newMinute --;
					newSecond=59;
				}
				if(newMinute==-1) {
					newHour --;
					newMinute = 59;
				}if(newHour==-1) {
					newHour = 23;
					newDay--;
				}
			}
		}else {
			int d = differences[5];
			while(d!=0) {
				newSecond ++;
				d--;
				if(newSecond==60) {
					newMinute ++;
					newSecond=0;
				}
				if(newMinute==60) {
					newHour ++;
					newMinute = 0;
				}if(newHour==24) {
					newHour = 0;
					newDay++;
				}
			}
		}
		
		newMonth = dateNow.getMonthValue() +  differences[1];
		if(newMonth>=13) {
			newMonth = newMonth - 12;
			newYear++;
		}
		
		newDay += dateNow.getDayOfMonth() + differences[2];
		if(newMonth == 11 || newMonth == 4 || newMonth == 6 || newMonth == 9) {
			if(newDay>=31) {
				newDay = newDay - 30;
				newMonth++;
			}
		}else {
			if(newMonth == 2) {
				if(newDay>=29) {
					newDay = newDay - 28;
					newMonth++;
				}
			}else {
				if(newDay>=32) {
					newDay = newDay - 31;
					newMonth++;
				}
			}
		}
		if(newMonth>=13) {
			newMonth = newMonth - 12;
			newYear++;
		}
		
		newYear += dateNow.getYear() + differences[0];
		
		date.changeTime(newHour, newMinute, newSecond);
		date.changeDate(newDay, newMonth, newYear);
	}
	
	public void editDate(LocalDate d, LocalTime t) {
		date.changeDate(d.getDayOfMonth(), d.getMonthValue(), d.getYear());
		date.changeTime(t.getHour(), t.getMinute(), t.getSecond());
		setDifferences();
	}
	
	public String showDateTime() {
		LocalDateTime showDate = date.getDateTime();
		return showDate.getDayOfMonth() + "/" + showDate.getMonthValue() + "/" + showDate.getYear() + " --- " + showDate.getHour() + ":" + showDate.getMinute() + ":" + showDate.getSecond();
	}
	
	public void addTypeTurn(float du, String ty) throws TypeTurnExistException {
		TypeTurn t = searchTypeTurn(ty);
		if(t==null) {
			typesOfTurns.add(new TypeTurn(du, ty));
			Collections.sort(typesOfTurns);
		}else {
			throw new TypeTurnExistException(du, t.getDuration(), ty);
		}
	}
	
	public TypeTurn searchTypeTurn(String ty) {
		Collections.sort(typesOfTurns);
		int low = 0;
		int high = typesOfTurns.size()-1;
		
		while(low<=high)
		   {
		     int mid=(low+high)/2;
		     if(typesOfTurns.get(mid).getType().compareTo(ty)<0){
		         low=mid+1;
		     }else{	
		    	 if(typesOfTurns.get(mid).getType().compareTo(ty)>0)
		    		 high=mid-1;
		    	 else{
			         return typesOfTurns.get(mid);
			     }
		     }
		   }
		   return null;        
	}
	
	public String showAllTypeTurns() {
		int cont = 1;
		String message = "";
		if(!typesOfTurns.isEmpty()) {
			for(int i = 0; i<typesOfTurns.size(); i++) {
				message += + cont + ". " + typesOfTurns.get(i).getType() + ".\n";
				cont++;
			}
			
			return message;
		}else
			return null;
	}
	
	/**
	 * Este metodo permite buscar a un usuario en su arrayList y retornar su posicion.
	 * <b>pre:</b> El arrayList (users) debe de estar inicializado.<br>
	 * @param id Número de cédula del usuario a buscar. id != null.
	 * @return Retorna la posicion en donde se encuentra el usuario en el ArrayList, si no lo encontró, retornará -1.
	 */
	public int searchPersonPosition(String id) {
		int position = -1;
		for(int i = 0; i<users.size(); i++) {
			if(users.get(i).getId().equals(id)) {
				position = i;
				break;
			}
		}
		
		return position;
	}
	
	/**
	 * Este metodo permite añadir un nuevo usuario al programa.
	 * <b>pre:</b> El ArrayList(users) debe de estar inicializado.<br>
	 * <b>pos:</b> El ArrayList(users) se ha actualizado con un nuevo usuario.<br>
	 * @param id Id del usuario a registrar. id != null.
	 * @param typeId Tipo de documento de identificación del usuario a registrar.
	 * @param name Nombre del usuario a registrar.
	 * @param lastName Apellido del usuario a registrar.
	 * @param address Dirección del usuario a registrar
	 * @param cell Numero de telefono del usuario a registrar.
	 * @throws UserExistException Si el usuario ya está registrado en el programa, salta la excepcion.
	 */
	public void addUser(String id, String typeId, String name, String lastName, String address, String cell) throws UserExistException {
		
		int add = searchPersonPosition(id);
		
		if(add == -1) {
			users.add(new User(id, typeId, name, lastName, address, cell));
		}
		else {
			throw new UserExistException(id);
		}
	}
	
	/**
	 *Este metodo permite asignarle un turno a un Usuario, el turno es necesariamente consecutivo al ultimo ingresado en el ArrayList(turns).
	 *<b>pre:</b> El ArrayList(turns) debe de estar inicializado.<br>
	 *<b>pre:</b> El ArrayList(users) debe de estar inicializado.<br>
	 *<b>pre:</b> El usuario al que se le va asignar el turno debe de existir en el programa.<br>
	 *<b>pos:</b> Se ha añadido un nuevo Turno al ArrayList(turns), consecutivo al ultimo ingresado.<br>
	 * @param id Id del usuario al que se le asignará el turno. id != null.
	 * @return un objeto Turno el cual será diferente de null si se le quiere asignar a un turno a un usuario que ya tiene un turno anteriormente, en este caso se le retornará el turno que posee.
	 * @throws UserNotRegisterException Esta excepción se lanza cuando se le intenta asignar un turno a un usuario que no existe en el programa.
	 * @throws ExistActiveTurnException 
	 */
	public void assignTurn(String id, TypeTurn t) throws UserNotRegisterException, ExistActiveTurnException{
		if(searchPersonPosition(id)==-1) {
			throw new UserNotRegisterException(id);
		}
		else {
			if(existActiveTurn(id)!=-1)
				throw new ExistActiveTurnException(turns.get(existActiveTurn(id)));
			else {
				if(turns.size()>=1) {
					char letter = turns.get(turns.size()-1).getLetter();
					String position = turns.get(turns.size()-1).getNumber();
					
					if(Integer.parseInt(position)==99) {
						position = "00";
						if(letter!='Z') {
							for(int i=0; i<alphabet.length; i++) {
								if(alphabet[i].getLetter()==letter) {
									letter=alphabet[i+1].getLetter();
									break;
								}
							}
						}else
							letter='A';
							
						LocalDateTime date = assignDateAtTurn();
						
						turns.add(new Turn(letter, position, users.get(searchPersonPosition(id)), t, LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth()), LocalTime.of(date.getHour(), date.getMinute(), date.getSecond())));
						
					}else {
						if(Integer.parseInt(position)<99 && Integer.parseInt(position)>=9) {
							position = ""+(Integer.parseInt(position)+1);
							
							
							LocalDateTime date = assignDateAtTurn();
							turns.add(new Turn(letter, position, users.get(searchPersonPosition(id)), t, LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth()), LocalTime.of(date.getHour(), date.getMinute(), date.getSecond())));
							
						}
						else {
							if(Integer.parseInt(position)<9) {
								position = "0"+(Integer.parseInt(position)+1);
								
								LocalDateTime date = assignDateAtTurn();
								
								turns.add(new Turn(letter, position, users.get(searchPersonPosition(id)), t, LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth()), LocalTime.of(date.getHour(), date.getMinute(), date.getSecond())));
								
							}
						}
					}
				}
				else {
					LocalDateTime date = this.date.getDateTime();
					turns.add(new Turn('A', "00", users.get(searchPersonPosition(id)), t, LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth()), LocalTime.of(date.getHour(), date.getMinute(), date.getSecond())));
				}
			}
		}
	}
	
	public LocalDateTime assignDateAtTurn() {
		LocalDate dateNow = turns.get(turns.size()-1).getDate().getDate();
		LocalTime timeNow = turns.get(turns.size()-1).getDate().getTime();
		int newSecond = 0;
		int newMinute = 0;
		int newHour = 0;
		int newDay = 0;
		int newMonth = 0;
		int newYear = 0;
		
		newSecond = timeNow.getSecond() + WAIT_TIME;
		if(newSecond >= 60) {
			newSecond = newSecond - 60;
			newMinute++;
		}
		
		newMinute += timeNow.getMinute() + turns.get(turns.size()-1).getType().getDuration();
		if(newMinute >= 60) {
			newMinute = newMinute - 60;
			newHour++;
		}
		
		newHour += timeNow.getHour();
		if(newHour>=24) {
			newHour = newHour - 24;
			newDay ++;
		}
		
		newMonth = dateNow.getMonthValue();
		if(newMonth>=13) {
			newMonth = newMonth - 12;
			newYear++;
		}
		
		newDay += dateNow.getDayOfMonth();
		if(newMonth == 11 || newMonth == 4 || newMonth == 6 || newMonth == 9) {
			if(newDay>=31) {
				newDay = newDay - 30;
				newMonth++;
			}
		}else {
			if(newMonth == 2) {
				if(newDay>=29) {
					newDay = newDay - 28;
					newMonth++;
				}
			}else {
				if(newDay>=32) {
					newDay = newDay - 31;
					newMonth++;
				}
			}
		}
		if(newMonth>=13) {
			newMonth = newMonth - 12;
			newYear++;
		}
		
		newYear += dateNow.getYear();
		
		LocalDate d = LocalDate.of(newYear, newMonth, newDay);
		LocalTime t = LocalTime.of(newHour, newMinute, newSecond);
		
		return LocalDateTime.of(d, t);
	}
	//Con comparator y comparable.
	public void sortUsersByName() {
		
	}

	public int existActiveTurn(String id) {
		boolean found = false;
		int position = -1;
		
		for(int i=0; i<turns.size() && found == false; i++) {
			if(turns.get(i).getUser().getId().equals(id) && !turns.get(i).getUser().isAttended()) {
				found = true;
				position = i;
			}
		}
		
		return position;
	}

	//--------------------------------------
	
	public static String getDatebaseName() {
		return DATEBASE_NAME;
	}

	public static String getDatebaseLastname() {
		return DATEBASE_LASTNAME;
	}

	public Turn getActualTurn() {
		return actualTurn;
	}

	public ArrayList<Turn> getTurns() {
		return turns;
	}

	public ArrayList<TypeTurn> getTypesOfTurns() {
		return typesOfTurns;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public Date getDate() {
		return date;
	}

	public int[] getDifferences() {
		return differences;
	}

	public Alphabet[] getAlphabet() {
		return alphabet;
	}
}
