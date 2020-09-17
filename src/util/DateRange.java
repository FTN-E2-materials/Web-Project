package util;

import java.time.DayOfWeek;
import java.util.Calendar;

import beans.model.other.Date;

public class DateRange {
	
	public Date startingDate;
	public Date endingDate;
	public int daySpan;
	
	public DateRange(Date start, Date end) {
		this.startingDate = start;
		this.endingDate = end;
		this.daySpan = endingDate.getDayOfYear() - startingDate.getDayOfYear() + 1;		// this wont work for december - january 
	}
	
	public boolean contains(Date date) {
		return (date.getYear() == startingDate.getYear() 
					&&  date.getYear() == endingDate.getYear()
					&&  date.getDayOfYear() >= startingDate.getDayOfYear()
					&&  date.getDayOfYear() <= endingDate.getDayOfYear());
	}
	
	public boolean isOnWeekend() {
		int startingDay = startingDate.calendar.get(Calendar.DAY_OF_WEEK);
		int endingDay = endingDate.calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println("Checking weekend...");
		System.out.println("Starting day is " + startingDay);
		System.out.println("Ending day is " + endingDay);
		if (startingDay == 6 || startingDay == 7) 
			if (endingDay == 1 ||  endingDay == 7 || endingDay == 2  || endingDay == 3) 
				if (endingDate.getDayOfYear() < startingDate.getDayOfYear() + 5) {   // If it spans the whole week, return false 
					System.out.println("It's on weekend");
					return true;
				}
		System.out.println("It's not on weekend");
		return false;
	}
}
