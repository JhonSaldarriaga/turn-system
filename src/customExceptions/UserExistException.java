package customExceptions;

public class UserExistException extends Exception{

	public UserExistException(String id) {
		super("User with id: " + id + "\nIs already registered in the system");
	}
}
