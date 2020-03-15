package customExceptions;
import java.util.ArrayList;

import model.*;

@SuppressWarnings("serial")
public class ArrayListEmptyException extends Exception{

	private ArrayList<User> users;
	private ArrayList<TypeTurn> typeOfTurns;
	
	public ArrayListEmptyException(ArrayList<User> users, ArrayList<TypeTurn> typeOfTurns){
		super("Some arrayList are empty.");
		
		this.users = users;
		this.typeOfTurns = typeOfTurns;
	}
	
	public String getProblem() {
		String message = "THE NEXT ARRAYLIST ARE EMPTY: ";
		int cont = 1;
		
		if(users.isEmpty()) {
			message+= "\n" + cont + ". Users.";
			cont++;
		}
		
		if(typeOfTurns.isEmpty()) {
			message+= "\n" + cont + ". Type of turns.";
		}
		
		return message;
	}
}
