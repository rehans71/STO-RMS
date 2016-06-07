package com.sto.rms.utils;

import java.util.Calendar;
import java.util.Date;

public class CommonUtils
{

	public static Date buildDate(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
    	calendar.clear();
    	calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
	}
	
	public static Date buildDate(int year, int month, int date, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
    	calendar.clear();
    	calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
	}
	
	public static Date getStartOfDate(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date getEndOfDate(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	public static Date getStartOfMonth(Date date)
	{
		Calendar c = Calendar.getInstance();      
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1); 
		return c.getTime();
	}

	public static Date getEndOfMonth(Date date)
	{
		Calendar c = Calendar.getInstance();      
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH)); 
		return c.getTime();
	}

	public static int getNumberOfDaysBetween(Date startDate, Date endDate)
	{
		Calendar c1 = Calendar.getInstance();      
		c1.setTime(startDate);
		int start = c1.get(Calendar.DATE);
		
		Calendar c2 = Calendar.getInstance();      
		c2.setTime(endDate);
		int end = c2.get(Calendar.DATE);
		
		return end - start +1;
	}
}
