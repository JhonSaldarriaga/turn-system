package model;

import java.util.ArrayList;
import java.util.Collections;

import customExceptions.*;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.io.*;
@SuppressWarnings("serial")
public class TurnSystem implements Serializable{

	public static final int WAIT_TIME = 15;
	public static final String DATABASE_NAME = "data/USER_DATA.csv";//firstName,lastName,number,Country
	public static final String REPORTTURNS = "data/TURNS_REPORT";
	public static final String REPORTUSERS = "data/USERS_REPORT";
	
	private ArrayList<Turn> turns;
	private ArrayList<TypeTurn> typesOfTurns;
	private ArrayList<User> users;
	private ArrayList<String> randomsUsersData;
	private Date date;
	private long secondDifference;
	private Alphabet[] alphabet;
	private TypeId[] typeId;
	private long generateId;
	
	public TurnSystem() throws IOException {
		alphabet = Alphabet.values();
		typeId = TypeId.values();
		date = new Date(LocalDate.now(), LocalTime.now());
		turns = new ArrayList<Turn>();
		typesOfTurns = new ArrayList<TypeTurn>();
		users = new ArrayList<User>();
		randomsUsersData = new ArrayList<String>();
		generateId = 0;
		readUsers();
	}
	
	public TurnSystem(LocalDate d, LocalTime t) throws IOException {
		alphabet = Alphabet.values();
		typeId = TypeId.values();
		date = new Date(d, t);
		turns = new ArrayList<Turn>();
		typesOfTurns = new ArrayList<TypeTurn>();
		users = new ArrayList<User>();
		setDifferences();
		randomsUsersData = new ArrayList<String>();
		generateId = 0;
		readUsers();
	}
	
	/**
	 * Este método genera la diferencia entre la hora actual del computador, y la hora actual de TurnSystem.
	 *<b>pos:</b> El atributo secondDifferences se ha actualizado.<br>
	 */
	private void setDifferences() {
		LocalDateTime now = LocalDateTime.now();
		now = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute(), now.getSecond(), 0);
		LocalDateTime system = date.getDateTime();
		Duration duration = Duration.between(now, system);
		
		secondDifference = duration.getSeconds();
	}
	
	/**
	 * Este método actualiza la hora actual del sistema, "la refresca".
	 * <b>pre:</b> La relacion date debe de estar inicializada.<br>
	 * <b>pos:</b> La relacion date ha sido actualizada.<br>
	 */
	public void upgradeTheTime() {
		LocalDateTime now = LocalDateTime.now();
		now = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute(), now.getSecond(), 0);
		now = now.plus(secondDifference, ChronoUnit.SECONDS);
		
		date.setLocalDateTime(now);
	}
	
	/**
	 * Este método edita la hora actual del sistema.
	 * <b>pre:</b> La relacion date debe de estar inicializada.<br>
	 * <b>pos:</b> La relacion date ha sido actualizada.<br>
	 * @param d Objeto del tipo LocalDate.
	 * @param t Objeto del tipo LocalTime.
	 */
	public void editDate(LocalDate d, LocalTime t) {
		date.setLocalDateTime(d, t);
		setDifferences();
	}
	
	/**
	 * Este método permite mostrar la hora y fecha actual del programa con un formato especifico.
	 * @return Un string con la fecha y hora.
	 */
	public String showDateTime() {
		LocalDateTime showDate = date.getDateTime();
		return showDate.getDayOfMonth() + "/" + showDate.getMonthValue() + "/" + showDate.getYear() + " --- " + showDate.getHour() + ":" + showDate.getMinute() + ":" + showDate.getSecond();
	}
	
	/**
	 * Este método añade un nuevo tipo de turno al arrayList TypeTurn.
	 * <b>pre:</b> El arrayList TypeTurn debe de estar inicializado.<br>
	 * <b>pos:</b> Se ha añadido un nuevo elemento al arrayList de turn.<br>
	 * @param du float con la duracion en minutos del tipo de turno.
	 * @param ty String con el nombre del tipo de turno.
	 * @throws TypeTurnExistException
	 */
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
	
	public String showAllUsers() {
		String message = "";
		for(int i = 0; i<users.size(); i++) {
			message += users.get(i).toString() + "\n";
		}
		
		return message;
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
	
	public String showAllActiveTurns() {
		int cont = 1;
		String message = "";
		for(int i = 0; i<turns.size(); i++) {
			if(!turns.get(i).getUser().isAttended()) { 
				message += + cont + ". " + turns.get(i).getLetter() + turns.get(i).getNumber() + " --- " + "User ID: " + turns.get(i).getUser().getId() + "/Type: " + turns.get(i).getType().getType() + " --- Duration: " + turns.get(i).getType().getDuration() + "///DATE: " + turns.get(i).getDate().showAllDate() + "\n";
				cont++;
			}
		}
		
		return message;
	}
	
	/**
	 * Este metodo permite buscar a un usuario en su arrayList y retornar su posicion.
	 * <b>pre:</b> El arrayList (users) debe de estar inicializado.<br>
	 * @param id Número de identificación del usuario a buscar. id != null.
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
	
	private boolean isSuspended(String id) {
		int n = searchPersonPosition(id);
		User user = users.get(n);
		if(user.getSuspended()!=null) {
			if(user.getSuspended().getDateTime().isBefore(date.getDateTime())) {
				user.setSuspended(null);
				user.setStrike(0);
				return false;
			}else
				return true;
		}else
			return false;
	}
	
	public String attendTurns() {
		String message = "The turns attended was: ";
		if(!turns.isEmpty()) {
			LocalDateTime now = date.getDateTime();
			Duration d = Duration.between(turns.get(0).getDate().getDateTime(), now);
			int i = 0;
			int randomNumber;
			while(!d.isNegative() && i<turns.size()) {
				turns.get(i).getUser().setAttended(true);
				User userOfTurn = users.get(searchPersonPosition(turns.get(i).getUser().getId()));
				randomNumber = (int)(Math.random()*2)+1;
				if(randomNumber == 1) {
					userOfTurn.setStrike(0);
				}else {
					if(randomNumber == 2) {
						userOfTurn.setStrike(userOfTurn.getStrike() + 1);
						if(userOfTurn.getStrike() == 2) {
							Date dateSuspended = new Date(now.plusDays(2));
							userOfTurn.setSuspended(dateSuspended);
						}
					}
				}
				
				message += turns.get(i).getLetter() + turns.get(i).getNumber() +  " --- Was attended at: " + turns.get(i).getDate().showAllDate() + "\n";
				
				i++;
				d = Duration.between(turns.get(i).getDate().getDateTime(), now);
			}
			if(i==0) {
				message += "NEITHER";
			}
		}else {
			message = null;
		}
		
		return message;
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
	 * @throws IsSuspendedException 
	 */
	public void assignTurn(String id, TypeTurn t) throws UserNotRegisterException, ExistActiveTurnException, IsSuspendedException{
		if(searchPersonPosition(id)==-1) {
			throw new UserNotRegisterException(id);
		}
		else {
			if(isSuspended(id)) {
				throw new IsSuspendedException(users.get(searchPersonPosition(id)).getSuspended());
			}else {
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
								
							LocalDateTime d = getTimeForTheNewTurn();
							Turn turn = new Turn(letter, position, users.get(searchPersonPosition(id)), t, d);
							turns.add(turn);
							users.get(searchPersonPosition(id)).addTurn(turn);
							
						}else {
							if(Integer.parseInt(position)<99 && Integer.parseInt(position)>=9) {
								position = ""+(Integer.parseInt(position)+1);
								
								
								LocalDateTime d = getTimeForTheNewTurn();
								Turn turn = new Turn(letter, position, users.get(searchPersonPosition(id)), t, d);
								turns.add(turn);
								users.get(searchPersonPosition(id)).addTurn(turn);
								
							}
							else {
								if(Integer.parseInt(position)<9) {
									position = "0"+(Integer.parseInt(position)+1);
									
									LocalDateTime d = getTimeForTheNewTurn();
									Turn turn = new Turn(letter, position, users.get(searchPersonPosition(id)), t, d);
									turns.add(turn);
									users.get(searchPersonPosition(id)).addTurn(turn);
									
								}
							}
						}
					}else {
						LocalDateTime d = date.getDateTime();
						Turn turn = new Turn('A', "00", users.get(searchPersonPosition(id)), t, d);
						turns.add(turn);
						users.get(searchPersonPosition(id)).addTurn(turn);
					}
				}
			}
		}
	}
	
	public LocalDateTime getTimeForTheNewTurn() {
		Turn lastTurn = turns.get(turns.size()-1);
		LocalDateTime lastTurnLD = lastTurn.getDate().getDateTime();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime newTurn = null;
		
		if(now.isBefore(lastTurnLD)) {
			long seconds = (long) ((turns.get(turns.size()-1).getType().getDuration() * 60) + WAIT_TIME);
			newTurn = lastTurnLD.plus(seconds, ChronoUnit.SECONDS);
		}else {
			if(Duration.between(lastTurnLD, now).getSeconds()>((lastTurn.getType().getDuration()*60)+15)) {
				newTurn = now.plus(WAIT_TIME, ChronoUnit.SECONDS);
			}else {
				long seconds = (long)((lastTurn.getType().getDuration() * 60) + WAIT_TIME);
				newTurn = now.plus(seconds, ChronoUnit.SECONDS);
			}
		}
		
		return newTurn;
	}
	
	
	public void sortUsersByName() {
		
		for(int i=0; i<users.size()-1; i++) {
			User menor = users.get(i);
			int cual = i;
			for(int j=i+1; j<users.size();j++) {
				if(users.get(j).getName().compareTo(menor.getName())<0) {
					menor = users.get(j);
					cual =  j;
				}
			}
			User temp = users.get(i);
			users.set(i, menor);
			users.set(cual, temp);
		}
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
	
	private void readUsers() throws IOException {
		File file = new File(DATABASE_NAME);
		if(file.exists()) {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			boolean empty = false;
			String line = "";
			while(!empty) {
				line = br.readLine();
				if(line==null){
					empty = true;
				}else {
					randomsUsersData.add(line);
				}
			}
			
			br.close();
		}
	}
	
	public String generateTurnsUntilADay(int n) throws ArrayListEmptyException, UserNotRegisterException, ExistActiveTurnException, IsSuspendedException {
		LocalDateTime until = date.getDateTime().plusDays(n);
		LocalDateTime lastTurnD;
		if(!(typesOfTurns.isEmpty() || users.isEmpty())) {
			if(turns.isEmpty()) {
				lastTurnD = date.getDateTime();
			}else
				lastTurnD = turns.get(turns.size()-1).getDate().getDateTime();

			if(lastTurnD.isBefore(until)) {
				int cont = 0;
				while(lastTurnD.isBefore(until) && cont!=users.size()) {
					int randomNumUser = (int) (Math.random()*(users.size()-1));
					String id = users.get(randomNumUser).getId();
					if(!isSuspended(id) && existActiveTurn(id)==-1) {
						int randomNumType = (int)(Math.random()*(typesOfTurns.size()-1));
						assignTurn(id, typesOfTurns.get(randomNumType));
					}else
						cont ++;
					
					lastTurnD = turns.get(turns.size()-1).getDate().getDateTime();
					
				}
				return "Successful.";
			}else
				return "Turns already exist for the next " + n + " days";
		}else
			throw new ArrayListEmptyException(users, typesOfTurns);
	}
	
	public void generateRandomUsers(int n) {
		while(n!=0) {
			int randomNumber = (int) (Math.random()*(randomsUsersData.size()-1));
			String line = randomsUsersData.get(randomNumber);
			String[] lines = line.split(",");
			int randomNumberType = (int) (Math.random()*(typeId.length-1));
			User e = new User(getRandomId(), typeId[randomNumberType].getType(), lines[0], lines[1], lines[3], lines[2]);
			users.add(e);
			n--;
		}
	}
	
	public String getRandomId() {
		String number = null;
		boolean different = false;
		while(!different) {
			long id = generateId;
			generateId += 1;
			
			number = ""+id;
			
			if((number).length()<10) {
				while((number).length()<10) {
					number+=0;
				}
			}
			
			if(searchPersonPosition(number)==-1) {
				different = true;
			}
		}
		
		return number;
	}
	
	public void generateReportTurnText(String id) throws IOException {
		User user = users.get(searchPersonPosition(id));
		
		File file = new File(REPORTTURNS + "_" + user.getId()+".txt");
		if(!file.exists()) {
			file.createNewFile();
		}
		
		PrintWriter pr = new PrintWriter(file);
		pr.println("|ALL THE TURNS THAT A USER: " + user.getId() + " HAS REQUESTED|");
		pr.println("=====================================================");
		
		ArrayList<Turn> turnsObtained = user.getTurnsObtained();
		for(int i = 0; i < turnsObtained.size(); i++) {
			pr.println(turnsObtained.get(i).toString());
		}
		
		pr.close();
	}
	
	public String generateReportTurnShow(String id) {
		String message = "";
		User user = users.get(searchPersonPosition(id));
		message += "|ALL THE TURNS THAT A USER: " + user.getId() + " HAS REQUESTED|\n";
		message += "=====================================================\n";
		
		ArrayList<Turn> turnsObtained = user.getTurnsObtained();
		for(int i = 0; i < turnsObtained.size(); i++) {
			message += turnsObtained.get(i).toString() +"\n";
		}
		
		return message;
	}
	
	public void generateReportUsersText(char letter, String number) throws IOException {

		ArrayList<User> usersTurns = new ArrayList<User>();
		
		for(int i = 0; i<turns.size(); i++) {
			if(turns.get(i).getLetter() == letter && turns.get(i).getNumber().equals(number)) {
				usersTurns.add(turns.get(i).getUser());
			}
		}
		
		File file = new File(REPORTTURNS + "_" + letter + number +".txt");
		if(!file.exists()) {
			file.createNewFile();
		}
		
		PrintWriter pr = new PrintWriter(file);
		pr.println("|GENERATE A REPORT WITH ALL THE PEOPLE WHO HAVE COME TO HAVE A SPECIFIC TURN: "+letter+number+"|");
		pr.println("===================================================================================");
		
		for(int i = 0; i < usersTurns.size(); i++) {
			pr.println(usersTurns.get(i).toString());
		}
		
		pr.close();
	}
	
	public String generateReportUsersShow(char letter, String number) throws IOException {

		ArrayList<User> usersTurns = new ArrayList<User>();
		
		String message = "";
		
		for(int i = 0; i<turns.size(); i++) {
			if(turns.get(i).getLetter() == letter && turns.get(i).getNumber().equals(number)) {
				usersTurns.add(turns.get(i).getUser());
			}
		}
		
		message += "|GENERATE A REPORT WITH ALL THE PEOPLE WHO HAVE COME TO HAVE A SPECIFIC TURN: "+letter+number+"|\n";
		message += "===================================================================================\n";
		
		for(int i = 0; i < usersTurns.size(); i++) {
			message += usersTurns.get(i).toString() + "\n";
		}
		
		return message;
	}
	
	//--------------------------------------
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
	
	public Alphabet[] getAlphabet() {
		return alphabet;
	}

}
