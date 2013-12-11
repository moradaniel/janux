package biz.janux.calendar;


import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Contains commonly used date utilities
 * @author David Fairchild
 */

public final class DateUtils
{
    /** the number of milliseconds in one day */
    public static final int MILLIS_PER_DAY = 24 * 60 * 60 * 1000;

    /**
     * Constructor
     */
    private DateUtils()
    {
    }
    
    /**
     * set the time component of the given date with the hours, minutes, and seconds
     * @param aDate the date parameter to modify
     * @param aHours the desired hours setting
     * @param aMinutes the desired minutes setting
     * @param aSeconds the desired seconds setting
     * @return the new date value with the desired time component
     */
    public static Date setTimeComponent(final Date aDate, final int aHours, final int aMinutes, final int aSeconds)
    {
        if (aDate instanceof Date)
        {
            final GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(aDate);
            cal.set(Calendar.HOUR_OF_DAY, aHours);
            cal.set(Calendar.MINUTE, aMinutes);
            cal.set(Calendar.SECOND, aSeconds);
            cal.set(Calendar.MILLISECOND, 0);
            return (cal.getTime());
        }
        else
        {
            return (null);
        }
    }


    /**
     * Returns a Date instance with the given date fields set.
     *
     * @param aYear the actual year value not an offset ie: 2004, 1997, etc.
     * @param aMonth valid range is 1 through 12
     * @param aDay the day of the month, valid range is 1 through 31
     * @return a Date instance with the given values
     */
    public static Date getDate(final int aYear, final int aMonth, final int aDay)
    {
        return (getDate(aYear, aMonth, aDay, 0, 0, 0));
    }

    
    /**
     * Returns a Date instance with the given date fields set
     * @param aYear the actual year value not an offset ie: 2004, 1997, etc.
     * @param aMonth valid range is 1 through 12
     * @param aDay the day of the month, valid range is 1 through 31
     * @param aHour valid range is 0 through 23
     * @param aMinute valid range is 0 through 59
     * @return a Date instance with the given values
     */
    public static Date getDate(final int aYear, final int aMonth, final int aDay, final int aHour, final int aMinute)
    {
        return (getDate(aYear, aMonth, aDay, aHour, aMinute, 0));
    }
    
    /**
     * Returns a Date instance with the given date fields set
     * @param aYear the actual year value not an offset ie: 2004, 1997, etc.
     * @param aMonth valid range is 1 through 12
     * @param aDay the day of the month, valid range is 1 through 31
     * @param aHour valid range is 0 through 23
     * @param aMinute valid range is 0 through 59
     * @param aSecond valid range is 0 through 59
     * @return a Date instance with the given values
     */
    public static Date getDate(final int aYear, final int aMonth, final int aDay, final int aHour, final int aMinute, final int aSecond)
    {
    	// year must be full 4 digit year (assume we won't need to deal with dates in the first century)
    	if (aYear < 100)
    	{
    		throw new IllegalArgumentException("Invalid year specified - " + aYear);
    	}
    	if ((aMonth < 1) || (aMonth > 12))
    	{
    		throw new IllegalArgumentException("Invalid month specified - " + aMonth);
    	}
    	if ((aDay < 0) || (aDay > 31))
    	{
    		throw new IllegalArgumentException("Invalid day specified - " + aDay);
    	}
    	if ((aHour < 0) || (aHour > 23))
    	{
    		throw new IllegalArgumentException("Invalid hour specified - " + aHour);
    	}
    	if ((aMinute < 0) || (aMinute > 59))
    	{
    		throw new IllegalArgumentException("Invalid minute specified - " + aMinute);
    	}
    	if ((aSecond < 0) || (aSecond > 59))
    	{
    		throw new IllegalArgumentException("Invalid second specified - " + aSecond);
    	}
    	
    	
    	
    	
        final GregorianCalendar cal = new GregorianCalendar();

        cal.set(Calendar.YEAR, aYear);
        cal.set(Calendar.MONTH, aMonth - 1); // calendar object wants zero-based months, January = 0
        cal.set(Calendar.DAY_OF_MONTH, aDay);
        cal.set(Calendar.HOUR_OF_DAY, aHour);
        cal.set(Calendar.MINUTE, aMinute);
        cal.set(Calendar.SECOND, aSecond);
        cal.set(Calendar.MILLISECOND, 0);

        return (cal.getTime());
    }

    

    /**
     * Increments a date
     *
     * @param aDate date to modify
     * @param aField date/time field to modify
     * @param aAmount amount to increment the given field
     * @return the new date value
     */
    public static Date incDate(final Date aDate, final int aField, final int aAmount)
    {
        if (aDate instanceof Date)
        {
            final GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(aDate);
            cal.add(aField, aAmount);
            return (cal.getTime());
        }
        else
        {
            return (null);
        }
    }


    /**
     * Returns true if the given dates are the same.  Ignores the time component
     * @param aDate1 first date to check
     * @param aDate2 second date to check
     * @return true if the given dates are the same regardless of time
     */    
    public static boolean isSameDate(final Date aDate1, final Date aDate2)
    {
        if ((aDate1 instanceof Date) && (aDate2 instanceof Date))
        {
            final GregorianCalendar cal1 = new GregorianCalendar();
            cal1.setTime(aDate1);

            final GregorianCalendar cal2 = new GregorianCalendar();
            cal2.setTime(aDate2);

            if ((cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
                    && (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))
                    && (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)))
            {
                return (true);
            }
        }

        return (false);
    }

    
    /**
     * Calculates the number of days between the two given dates
		 *
     * @param aStartDate
     * @param aStopDate
     * @return the whole number of complete days between the two dates provided
     */
	public static int numDaysBetween(final Date aStartDate, final Date aStopDate) 
	{
		if (aStartDate == null || aStopDate == null)
		{
			throw new IllegalArgumentException("Dates cannot be null");
		}
		
		// calculate the number of days on the assumption that there are a constant number of milliseconds per day
		final long start = aStartDate.getTime();
		final long stop = aStopDate.getTime();
		final long diff = stop - start;
		final long diffDays = diff/MILLIS_PER_DAY;
		int iDiffDays = (int )diffDays;

		// double check with the calendar
		final Calendar stopCal = new GregorianCalendar();
		stopCal.setTime(aStopDate);

		
		final Calendar checkCal = new GregorianCalendar();
		checkCal.setTime(aStartDate);
        checkCal.set(Calendar.HOUR_OF_DAY, 0);
        checkCal.set(Calendar.MINUTE, 0);
        checkCal.set(Calendar.SECOND, 0);
        checkCal.set(Calendar.MILLISECOND, 0);
		checkCal.add(Calendar.DATE, iDiffDays);
		
		if (checkCal.after(stopCal))
		{
			return iDiffDays;
		}
		
		final int MAX_TRIES = 1000;
		for (int i = 0; i < MAX_TRIES; i++)
		{
            if ((checkCal.get(Calendar.YEAR) == stopCal.get(Calendar.YEAR))
                    && (checkCal.get(Calendar.MONTH) == stopCal.get(Calendar.MONTH))
                    && (checkCal.get(Calendar.DAY_OF_MONTH) == stopCal.get(Calendar.DAY_OF_MONTH)))
            {
                return (iDiffDays);
            }
			
			iDiffDays++;
			checkCal.add(Calendar.DATE, 1);
		}
		
		throw new RuntimeException("Unable to calculate days between " + aStartDate + " and " + aStopDate);
		
	}
    
	/**
	 * Validates the start and end dates against the current date; both the start
	 * date and end date should be in the present, and the date range should be
	 * no longer than the max days provided; The dates used in the validation do
	 * not use any time component.
	 * 
	 * @param startDate    The start day of the date range
	 * @param endDate      The end day of the date range
	 * @param maxDays      The maximum number of days allowed from the start day.
	 */
	public static void validateDateRange(Date startDate, Date endDate, int maxDays)
	{
		validateDateRange(startDate, endDate, maxDays, false); 
	}
		   
	/**
	 * Validates the start and end dates against the current date; both the start
	 * date and end date should be in the present, and the date range should be
	 * no longer than the max days provided; The dates used in the validation do
	 * not use any time component.
	 * 
	 * @param startDate    The start day of the date range
	 * @param endDate      The end day of the date range
	 * @param maxDays      The maximum number of days allowed from the start day.
	 */
	public static void validateDateRange(Date startDate, Date endDate, int maxDays, boolean allowOpenEnd)
	{
		Calendar calendar = Calendar.getInstance();
	
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	    
		Date today = calendar.getTime();

		if (startDate.compareTo(today) < 0) {
			throw new IllegalArgumentException(
			    "From Date should be same or after today's date");
		}
		
		if (endDate.compareTo(today) < 0) {
			throw new IllegalArgumentException(
				"To Date should be same or after today's date");
		}
		
		if (endDate.compareTo(startDate) < 0) {
			throw new IllegalArgumentException(
				"To Date should be same or after From Date");
		}
		
		if (!allowOpenEnd || endDate.compareTo(DateRange.LATEST_DATE) < 0) {
			if (numDaysBetween(today, endDate) > maxDays) {
				throw new IllegalArgumentException(
					"To Date should not be more than " + maxDays + " days from today");
			}
		}
	}
}
