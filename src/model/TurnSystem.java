package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import customExceptions.TypeTurnExistException;
import customExceptions.UserExistException;
import customExceptions.UserNotRegisterException;

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
			Period period = Period.between(LocalDate.now(), date.getDate());
			differences[0] = period.getYears();
			differences[1] = period.getMonths();
			differences[2] = period.getDays();
			differences[3] = date.getTime().getHour() - LocalTime.now().getHour();
			differences[4] = date.getTime().getMinute() - LocalTime.now().getMinute();
			differences[5] = date.getTime().getSecond() - LocalTime.now().getSecond();
	}
	
	public void upgradeTheTime() {
		LocalDate dateNow = LocalDate.now();
		LocalTime timeNow = LocalTime.now();
		int newSecond = 0;
		int newMinute = 0;
		int newHour = 0;
		int newDay = 0;
		int newMonth = 0;
		int newYear = 0;
		
		newSecond = timeNow.getSecond() + differences[5];
		if(newSecond >= 60) {
			newSecond = newSecond - 60;
			newMinute++;
		}
		
		newMinute += timeNow.getMinute() + differences[4];
		if(newMinute >= 60) {
			newMinute = newMinute - 60;
			newHour++;
		}
		
		newHour += timeNow.getHour() + differences[3];
		if(newHour>=24) {
			newHour = newHour - 24;
			newDay ++;
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
	
	public Date getDate() {
		return date;
	}
}
