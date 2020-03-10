package customExceptions;

import model.Turn;

@SuppressWarnings("serial")
public class ExistActiveTurnException extends Exception{

	private Turn turn;
	
	public ExistActiveTurnException(Turn turn) {
		super("an active Turn already exist");
		this.turn = turn;
	}
	
	public String getTurn() {
		return turn.getLetter() + turn.getNumber();
	}
	
	public String getType() {
		return "Type: " + turn.getType().getType() + ". Duration: " + turn.getType().getDuration();
	}
	
	public String getUser() {
		return "User with id: " + turn.getUser().getId();
	}
}
