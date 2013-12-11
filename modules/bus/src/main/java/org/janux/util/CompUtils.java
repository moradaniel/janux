package org.janux.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Contains common utilities for string comparisons, sorting, null checks, and date comparisons
 *
 * @author David Fairchild
 */
public final class CompUtils
{
    /**
     * Constructor
     */
    private CompUtils()
    {
        super();
    }
    
    
    /**
     * Returns true if the given string has no data in it.  Null strings, strings with zero length, and strings with
     * all blanks are considered "null" by this function
     * @param aInString String to check
     * @return true if the string is null or zero length
     */
    public static boolean isNull(final String aInString)
    {
        return (!notNull(aInString));
    }

    
    /**
     * Returns true if the given string has data in it.  Null strings, strings with zero length, and strings with all
     * blanks are considered "null" by this function
     * @param aInString String to check
     * @return true if the string has data in it
     */
    public static boolean notNull(final String aInString)
    {
        if (aInString instanceof String)
        {
            if (aInString.trim().length() > 0)
            {
                return (true);
            }
        }

        return (false);
    }

    /**
     * Returns true if the given strings are equal, null and "" strings are considered equivalent
     * @param aString1 first string to compare
     * @param aString2 second string to compare
     * @return true if the strings are equal or both are null
     */
    public static boolean strEquals(final String aString1, final String aString2)
    {
        if (notNull(aString1) && notNull(aString2))
        {
            return (aString1.trim().equals(aString2.trim()));
        }
        else if (isNull(aString1) && isNull(aString2))
        {
            return (true);
        }
        else
        {
            return (false);
        }
    }
    
    /**
     * Returns true if the given search string is contained in the array of values
     * @param aSearchString the string to search for
     * @param aValues the list of possible values
     * @return true if the search string is found
     */
    public static boolean containsValue(final String aSearchString, final String[] aValues)
    {
        if (aValues instanceof String[])
        {
            for (int i = 0; i < aValues.length; i++)
            {
                if (strEquals(aSearchString, aValues[i]))
                {
                    return (true);
                }
            }
        }
        
        return (false);
    }

    
    /**
     * Returns true if the given int value is contained in the array of values
     * @param aSearchValue the value to search for
     * @param aValues the list of possible values
     * @return true if the search value is found
     */
    public static boolean containsValue(final int aSearchValue, final int[] aValues)
    {
        if (aValues instanceof int[])
        {
            for (int i = 0; i < aValues.length; i++)
            {
                if (aSearchValue == aValues[i])
                {
                    return (true);
                }
            }
        }
        
        return (false);
    }

    

    /**
     * Returns true if the given Integers are equal, null Integers are considered equal
     *
     * @param aInteger1 first integer to compare
     * @param aInteger2 second integer to compare
     *
     * @return true if the value of the integers is the same or if both are null
     */
    public static boolean integerEquals(final Integer aInteger1, final Integer aInteger2)
    {
        if (aInteger1 instanceof Integer)
        {
            return (aInteger1.equals(aInteger2));
        }
        else if (aInteger2 instanceof Integer)
        {
            return (false);
        }
        else
        {
            return (true);
        }
    }

	/**
	 * Returns true if the Integer is positive
	 * 
	 * @param aInteger
	 * @return true if the Integer is positive
	 */
    public static boolean isPositive(final Integer aInteger)
    {
    	return ((aInteger != null) && (aInteger > 0));
    }

    /**
     * Returns true if the given BigDecimals are equal, nulls are considered equivalent
     *
     * @param aNumber1 DOCUMENT ME!
     * @param aNumber2 DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static boolean bigDecimalEquals(final BigDecimal aNumber1, final BigDecimal aNumber2)
    {
        if ((aNumber1 instanceof BigDecimal) && (aNumber2 instanceof BigDecimal))
        { // compare the two numbers
            if (aNumber1.scale() != aNumber2.scale())
            {
                final int iScale = Math.min(aNumber1.scale(), aNumber2.scale());
                if (aNumber1.scale() != iScale)
                {
                    final BigDecimal newNumber1 = aNumber1.setScale(iScale, BigDecimal.ROUND_HALF_UP);
                    return (newNumber1.equals(aNumber2));
                }
                else if (aNumber2.scale() != iScale)
                {
                    final BigDecimal newNumber2 = aNumber2.setScale(iScale, BigDecimal.ROUND_HALF_UP);
                    return (newNumber2.equals(aNumber1));
                }
            }

            return (aNumber1.equals(aNumber2));
        }
        else if (!(aNumber1 instanceof BigDecimal) && !(aNumber2 instanceof BigDecimal))
        { // both null, that's a match
            return (true);
        }
        else
        {
            return (false);
        }
    }

    /**
     * function for safely comparing two dates, even if they're null. The time components are ignored.
     *
     * @param aDate1 DOCUMENT ME!
     * @param aDate2 DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static boolean dateEquals(final Date aDate1, final Date aDate2)
    {
        if ((aDate1 instanceof Date) && (aDate2 instanceof Date))
        {
            final Calendar cal1 = new GregorianCalendar();
            cal1.setTime(aDate1);

            final Calendar cal2 = new GregorianCalendar();
            cal2.setTime(aDate2);

            if (cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR))
            {
                return (false);
            }

            if (cal1.get(Calendar.MONTH) != cal2.get(Calendar.MONTH))
            {
                return (false);
            }

            if (cal1.get(Calendar.DAY_OF_MONTH) != cal2.get(Calendar.DAY_OF_MONTH))
            {
                return (false);
            }

            return (true);
        }
        else if (!(aDate1 instanceof Date) && !(aDate2 instanceof Date))
        {
            return (true);
        }
        else
        {
            return (false);
        }
    }

    /**
     * function for safely comparing two date and times, even if they're null When comparing the time, only the hour
     * and minute are relevant.  Seconds and milliseconds are not compared.
     *
     * @param aDate1 DOCUMENT ME!
     * @param aDate2 DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static boolean dateTimeEquals(final Date aDate1, final Date aDate2)
    {
        if ((aDate1 instanceof Date) && (aDate2 instanceof Date))
        {
            final Calendar cal1 = new GregorianCalendar();
            cal1.setTime(aDate1);

            final Calendar cal2 = new GregorianCalendar();
            cal2.setTime(aDate2);

            if (cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR))
            {
                return (false);
            }

            if (cal1.get(Calendar.MONTH) != cal2.get(Calendar.MONTH))
            {
                return (false);
            }

            if (cal1.get(Calendar.DAY_OF_MONTH) != cal2.get(Calendar.DAY_OF_MONTH))
            {
                return (false);
            }

            if (cal1.get(Calendar.HOUR_OF_DAY) != cal2.get(Calendar.HOUR_OF_DAY))
            {
                return (false);
            }

            if (cal1.get(Calendar.MINUTE) != cal2.get(Calendar.MINUTE))
            {
                return (false);
            }

            //if ( cal1.get(Calendar.SECOND) != cal2.get(Calendar.SECOND) )
            //  return(false);
            return (true);
        }
        else if (!(aDate1 instanceof Date) && !(aDate2 instanceof Date))
        {
            return (true);
        }
        else
        {
            return (false);
        }
    }

    /**
     * function for safely comparing two times, even if they're null When comparing the time, only the hours, minutes,
     * and seconds are relevant.   Milliseconds are not compared.
     *
     * @param aDate1 DOCUMENT ME!
     * @param aDate2 DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static boolean timeEquals(final Date aDate1, final Date aDate2)
    {
        if ((aDate1 instanceof Date) && (aDate2 instanceof Date))
        {
            final Calendar cal1 = new GregorianCalendar();
            cal1.setTime(aDate1);

            final Calendar cal2 = new GregorianCalendar();
            cal2.setTime(aDate2);

            if (cal1.get(Calendar.HOUR_OF_DAY) != cal2.get(Calendar.HOUR_OF_DAY))
            {
                return (false);
            }

            if (cal1.get(Calendar.MINUTE) != cal2.get(Calendar.MINUTE))
            {
                return (false);
            }

            if (cal1.get(Calendar.SECOND) != cal2.get(Calendar.SECOND))
            {
                return (false);
            }

            return (true);
        }
        else if (!(aDate1 instanceof Date) && !(aDate2 instanceof Date))
        {
            return (true);
        }
        else
        {
            return (false);
        }
    }

    /**
     * Returns a Date instance with the given date fields set
     *
     * @param aYear the actual year value not an offset ie: 2004, 1997, etc.
     * @param aMonth valid range is 1 through 12
     * @param aDay the day of the month, valid range is 1 through 31
     *
     * @return DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public static Date getDate(final int aYear, final int aMonth, final int aDay) throws Exception
    {
        return (getDate(aYear, aMonth, aDay, 0, 0));
    }

    /**
     * DOCUMENT ME!
     *
     * @param aYear DOCUMENT ME!
     * @param aMonth DOCUMENT ME!
     * @param aDay DOCUMENT ME!
     * @param aHour DOCUMENT ME!
     * @param aMinute DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public static Date getDate(final int aYear, final int aMonth, final int aDay, final int aHour, final int aMinute)
        throws Exception
    {
        final GregorianCalendar cal = new GregorianCalendar();

        cal.set(Calendar.YEAR, aYear);
        cal.set(Calendar.MONTH, aMonth - 1); // calendar object wants zero-based months, January = 0
        cal.set(Calendar.DAY_OF_MONTH, aDay);
        cal.set(Calendar.HOUR_OF_DAY, aHour);
        cal.set(Calendar.MINUTE, aMinute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return (cal.getTime());
    }

    /**
     * Functions for sorting by display sequence
     *
     * @param aArray DOCUMENT ME!
     *
     * @throws ClassCastException DOCUMENT ME!
     */

    /*
     public static Sequenceable[] sortByDisplaySequence(final Map aMap) throws ClassCastException
     {
     final Collection collection = aMap.values();
     return( sortByDisplaySequence(collection) );
     }
     */
    /*
     public static Sequenceable[] sortByDisplaySequence(final Collection aCollection) throws ClassCastException
     {
     final Sequenceable[] seqArray = (Sequenceable[] )aCollection.toArray(new Sequenceable[0]);
     return( sortByDisplaySequence(seqArray) );
     }
     */
    /**
     * Sorts the sequenceable array by display order
     *
     * @param aArray DOCUMENT ME!
     *
     * @throws ClassCastException DOCUMENT ME!
     */
    /*
    public static void sortByDisplaySequence(final Sequenceable[] aArray) throws ClassCastException
    {
        Arrays.sort(aArray, new DisplaySequenceComparator());
    }
    */

    /**
     * Sorts the priceable array by price
     *
     * @param aArray DOCUMENT ME!
     *
     * @throws ClassCastException DOCUMENT ME!
     */
    /*
    public static void sortByPrice(final Priceable[] aArray) throws ClassCastException
    {
        Arrays.sort(aArray, new PriceComparator());
    }
    */
}

/**
 * compares another sequence
 */
/*
class DisplaySequenceComparator implements Comparator
{
    public int compare(final Object o1, final Object o2)
    {
        if (!(o1 instanceof Sequenceable) || !(o2 instanceof Sequenceable))
        {
            throw new ClassCastException();
        }

        final Sequenceable value1 = (Sequenceable) o1;
        final Sequenceable value2 = (Sequenceable) o2;
        return (value1.getDisplaySequence() - value2.getDisplaySequence());
    }
}
*/
/**
 * compares another price
 */
/*
class PriceComparator implements Comparator
{
    public int compare(final Object o1, final Object o2)
    {
        if (!(o1 instanceof Priceable) || !(o2 instanceof Priceable))
        {
            throw new ClassCastException();
        }

        final Priceable value1 = (Priceable) o1;
        final Priceable value2 = (Priceable) o2;
        final BigDecimal price1 = value1.getPrice();
        final BigDecimal price2 = value2.getPrice();

        if ((price1 instanceof BigDecimal) && (price2 instanceof BigDecimal))
        {
            return (price1.compareTo(price2));
        }
        else
        {
            return (0);
        }
    }

}
*/
