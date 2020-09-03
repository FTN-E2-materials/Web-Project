package beans.model;

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
}
