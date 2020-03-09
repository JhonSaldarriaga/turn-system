package customExceptions;

public class TypeTurnExistException extends Exception{

	private float duration1;
	private float duration2;
	private String type;
	
	public TypeTurnExistException(float du1, float du2, String ty) {
		super("The type already exist");
		type = ty;
		duration1 = du1;
		duration2 = du2;
	}
	
	public String getProblem() {
		return "The TypeOfTurn: " + type + "already exist.\n" + "The time exist is: " + duration1 + ".\nAnd the time you wanted asign: " + duration2;
	}
}
