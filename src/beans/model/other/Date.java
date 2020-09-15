package beans.model.other;

import java.util.Calendar;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import util.services.DateDeserializer;

@JsonDeserialize(using = DateDeserializer.class)
public class Date {
	public Calendar calendar;
	
	public Date(Long ticks) {
		if (ticks != -1) {
			calendar = Calendar.getInstance();
			calendar.setTimeInMillis(ticks);
		}
	}
	
	public Date(Date date) {
		this.calendar = Calendar.getInstance();
		this.calendar.setTimeInMillis(date.calendar.getTimeInMillis());
	}

	/** Adds or removes (+, -) the given amount of days to the current Date. 
	 * @param days
	 */
	public void addDays(int days) {
		calendar.add(Calendar.DATE, days);
	}
	
	/** Check whether this date is in the future 
	 * @return true if date is in the future, false if it's in the past
	 */
	public boolean isFuture() {
		return (this.calendar.compareTo(Calendar.getInstance()) > 0);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != Date.class)
			return false;
		
		Date otherDate = (Date)obj;
		return this.calendar.get(Calendar.YEAR) == otherDate.calendar.get(Calendar.YEAR)  
					&& this.calendar.get(Calendar.DAY_OF_YEAR) == otherDate.calendar.get(Calendar.DAY_OF_YEAR);
	}
	
	/** Checks whether the date of this Date is after the date of the otherDate (argument) */
	public boolean greaterThan(Date otherDate) {
		return this.calendar.compareTo(otherDate.calendar) > 0;
	}
	
	@Override
	public String toString() {
		return calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
	}
	
	public int getYear() {
		return this.calendar.get(Calendar.YEAR);
	}
	
	public int getDayOfYear() {
		return this.calendar.get(Calendar.DAY_OF_YEAR);
	}
}
