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
	
	/** Check whether this date is in the future 
	 * @return true if date is in the future, false if it's in the past
	 */
	public boolean isFuture() {
		return (this.calendar.compareTo(Calendar.getInstance()) > 0);
	}
}
