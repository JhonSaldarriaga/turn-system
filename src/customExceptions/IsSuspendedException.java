package customExceptions;

import java.time.LocalDateTime;

import model.Date;

@SuppressWarnings("serial")
public class IsSuspendedException extends Exception{

	private Date date;
	
	public IsSuspendedException(Date date) {
		super("The user is suspended");
	}
	
	public String getMessage() {
		LocalDateTime showDate = date.getDateTime();
		return "The user is suspended until the day: " + showDate.getDayOfMonth() + "/" + showDate.getMonthValue() + "/" + showDate.getYear() + " --- " + showDate.getHour() + ":" + showDate.getMinute() + ":" + showDate.getSecond();
	}
}