package util;

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
}
