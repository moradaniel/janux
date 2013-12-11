package biz.janux.calendar;

import org.janux.util.CompUtils;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.io.Serializable;

/**
 * Used to contain information about the days of the week that are valid.
 *
 * @author Philippe Paravicini, David Fairchild
 * @version $Revision: 1.4 $ - $Date: 2006-11-14 01:16:55 $
 *
 * @see DateRange
 */
public class DayOfWeekCalendar implements Serializable, Cloneable
{	private static final long serialVersionUID = 9097803254214871721L;	/** unique code identifying this calendar */
    private String m_code;
    /** description of calendar */
    private String m_description;
    /** sunday flag */
    private boolean m_isSunValid;
    /** monday flag */
    private boolean m_isMonValid;
    /** tuesday flag */
    private boolean m_isTueValid;
    /** wednesday flag */
    private boolean m_isWedValid;
    /** thursday flag */
    private boolean m_isThuValid;
    /** friday flag */
    private boolean m_isFriValid;
    /** saturday flag */
    private boolean m_isSatValid;

    // flags for bitmapped days of week
    /** DOCUMENT ME! */
    public static final int SUNDAY_VALID = 0x40;
    /** DOCUMENT ME! */
    public static final int MONDAY_VALID = 0x20;
    /** DOCUMENT ME! */
    public static final int TUESDAY_VALID = 0x10;
    /** DOCUMENT ME! */
    public static final int WEDNESDAY_VALID = 0x08;
    /** DOCUMENT ME! */
    public static final int THURSDAY_VALID = 0x04;
    /** DOCUMENT ME! */
    public static final int FRIDAY_VALID = 0x02;
    /** DOCUMENT ME! */
    public static final int SATURDAY_VALID = 0x01;
    /** DOCUMENT ME! */
    public static final int ALLWEEK_VALID = (SUNDAY_VALID | MONDAY_VALID | TUESDAY_VALID | WEDNESDAY_VALID
        | THURSDAY_VALID | FRIDAY_VALID | SATURDAY_VALID);
    /** DOCUMENT ME! */
    public static final String ALLWEEK_VALID_STRING = "1111111";

    /**
     * Constructor
     */
    public DayOfWeekCalendar()
    {
        m_isSunValid = true;
        m_isMonValid = true;
        m_isTueValid = true;
        m_isWedValid = true;
        m_isThuValid = true;
        m_isFriValid = true;
        m_isSatValid = true;
    }

    /**
     * the calendar code
     *
     * @param aCode DOCUMENT ME!
     */
    public void setCode(final String aCode)
    {
        m_code = aCode;
    }

    /**
     * the calendar code
     *
     * @return DOCUMENT ME!
     */
    public String getCode()
    {
        return (m_code);
    }

    /**
     * the calendar description
     *
     * @param aDescription DOCUMENT ME!
     */
    public void setDescription(final String aDescription)
    {
        m_description = aDescription;
    }

    /**
     * the calendar description
     *
     * @return DOCUMENT ME!
     */
    public String getDescription()
    {
        return (m_description);
    }

    /**
     * get/set Day of week flags
     *
     * @param aValidFlag DOCUMENT ME!
     */
    public void setIsSunValid(final boolean aValidFlag)
    {
        m_isSunValid = aValidFlag;
    }

    /**
     * DOCUMENT ME!
     *
     * @param aValidFlag DOCUMENT ME!
     */
    public void setIsMonValid(final boolean aValidFlag)
    {
        m_isMonValid = aValidFlag;
    }

    /**
     * DOCUMENT ME!
     *
     * @param aValidFlag DOCUMENT ME!
     */
    public void setIsTueValid(final boolean aValidFlag)
    {
        m_isTueValid = aValidFlag;
    }

    /**
     * DOCUMENT ME!
     *
     * @param aValidFlag DOCUMENT ME!
     */
    public void setIsWedValid(final boolean aValidFlag)
    {
        m_isWedValid = aValidFlag;
    }

    /**
     * DOCUMENT ME!
     *
     * @param aValidFlag DOCUMENT ME!
     */
    public void setIsThuValid(final boolean aValidFlag)
    {
        m_isThuValid = aValidFlag;
    }

    /**
     * DOCUMENT ME!
     *
     * @param aValidFlag DOCUMENT ME!
     */
    public void setIsFriValid(final boolean aValidFlag)
    {
        m_isFriValid = aValidFlag;
    }

    /**
     * DOCUMENT ME!
     *
     * @param aValidFlag DOCUMENT ME!
     */
    public void setIsSatValid(final boolean aValidFlag)
    {
        m_isSatValid = aValidFlag;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isSunValid()
    {
        return (m_isSunValid);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isMonValid()
    {
        return (m_isMonValid);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isTueValid()
    {
        return (m_isTueValid);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isWedValid()
    {
        return (m_isWedValid);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isThuValid()
    {
        return (m_isThuValid);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isFriValid()
    {
        return (m_isFriValid);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isSatValid()
    {
        return (m_isSatValid);
    }

    /**
     * return true if the given date is on a valid day of week
     *
     * @param aDate DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isValidDayOfWeek(final Date aDate)
    {
        // get the day of week for the given date
        final Calendar cal = new GregorianCalendar();
        cal.setTime(aDate);
        final int iDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        // return the corresponding day of week flag
        switch (iDayOfWeek)
        {
            case Calendar.SUNDAY:
                return (m_isSunValid);
            case Calendar.MONDAY:
                return (m_isMonValid);
            case Calendar.TUESDAY:
                return (m_isTueValid);
            case Calendar.WEDNESDAY:
                return (m_isWedValid);
            case Calendar.THURSDAY:
                return (m_isThuValid);
            case Calendar.FRIDAY:
                return (m_isFriValid);
            case Calendar.SATURDAY:
                return (m_isSatValid);
            default:
                throw new IllegalArgumentException("Invalid day of the week");
        }
    }

    /**
     * return true if there are any overlapping days between  the two calendars
     *
     * @param aCalendar DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isOverlap(final DayOfWeekCalendar aCalendar)
    {
        if (aCalendar instanceof DayOfWeekCalendar)
        {
            if (this.m_isSunValid && aCalendar.m_isSunValid)
            {
                return (true);
            }
            if (this.m_isMonValid && aCalendar.m_isMonValid)
            {
                return (true);
            }
            if (this.m_isTueValid && aCalendar.m_isTueValid)
            {
                return (true);
            }
            if (this.m_isWedValid && aCalendar.m_isWedValid)
            {
                return (true);
            }
            if (this.m_isThuValid && aCalendar.m_isThuValid)
            {
                return (true);
            }
            if (this.m_isFriValid && aCalendar.m_isFriValid)
            {
                return (true);
            }
            if (this.m_isSatValid && aCalendar.m_isSatValid)
            {
                return (true);
            }
        }

        return (false);
    }

    /**
     * return true if all the days of the week are valid
     *
     * @return DOCUMENT ME!
     */
    public boolean allDaysValid()
    {
        if (m_isSunValid && m_isMonValid && m_isTueValid && m_isWedValid && 
            m_isThuValid && m_isFriValid && m_isSatValid)
        {
            return (true);
        }
        else
        {
            return (false);
        }
    }

    /**
     * returns true if the given day of week calendar is the same
     * @param aObject object to compare
     * @return true if the object is equal
     */
    public boolean equals(final Object aObject)
    {
        if (!(aObject instanceof DayOfWeekCalendar))
        {
            return (false);
        }
        
        final DayOfWeekCalendar aDayOfWeekCalendar = (DayOfWeekCalendar ) aObject;

        // check code
        if (CompUtils.strEquals(this.m_code, aDayOfWeekCalendar.m_code) == false)
        {
            if (!(this.m_code instanceof String)
                    && CompUtils.strEquals(this.getDayOfWeekPattern(), aDayOfWeekCalendar.m_code))
            {
                aObject.hashCode();
            }
            else if (!(aDayOfWeekCalendar.m_code instanceof String)
                    && CompUtils.strEquals(aDayOfWeekCalendar.getDayOfWeekPattern(), this.m_code))
            {
                aObject.hashCode();
            }
            else
            {
                return (false);
            }
        }

        // check dowDescription
        if (CompUtils.strEquals(this.m_description, aDayOfWeekCalendar.m_description) == false)
        {
            return (false);
        }

        // check blackout days of week
        if (this.getDayOfWeekBitmap() != aDayOfWeekCalendar.getDayOfWeekBitmap())
        {
            return (false);
        }

        return (true);
    }

    
    
    /**
     * Hashcode method
     * @return a unique hashcode value
     */
    public int hashCode()
    {
        return super.hashCode();
    }
    
    
    /**
     * This function is used when reading calendar information from the database.  The database stores the valid days
     * of the week as a numeric value.  This function converts that numeric value and sets the appropriate day of week
     * flags.
     *
     * @param aDayOfWeekPattern DOCUMENT ME!
     */
    public void setDayOfWeekBitmap(final int aDayOfWeekPattern)
    {
        m_isSunValid = (aDayOfWeekPattern & SUNDAY_VALID) == SUNDAY_VALID;
        m_isMonValid = (aDayOfWeekPattern & MONDAY_VALID) == MONDAY_VALID;
        m_isTueValid = (aDayOfWeekPattern & TUESDAY_VALID) == TUESDAY_VALID;
        m_isWedValid = (aDayOfWeekPattern & WEDNESDAY_VALID) == WEDNESDAY_VALID;
        m_isThuValid = (aDayOfWeekPattern & THURSDAY_VALID) == THURSDAY_VALID;
        m_isFriValid = (aDayOfWeekPattern & FRIDAY_VALID) == FRIDAY_VALID;
        m_isSatValid = (aDayOfWeekPattern & SATURDAY_VALID) == SATURDAY_VALID;
    }

    /**
     * This function is used when reading calendar information from the database.  The database stores the valid days
     * of the week as a numeric value.  This function converts that numeric value and sets the appropriate day of week
     * flags.
     *
     * @param aDayOfWeekPattern DOCUMENT ME!
     *
     */
    public void setDayOfWeekPattern(final String aDayOfWeekPattern)
    {
        if (!(aDayOfWeekPattern instanceof String))
        {
            throw new IllegalArgumentException("Cannot set day of week with null string");
        }

        final int iDaysPerWeek = 7;
        if (aDayOfWeekPattern.length() != iDaysPerWeek)
        {
            throw new IllegalArgumentException("Cannot set day of week, string must be 7 characters long");
        }

        final int iSUNDAYPOS = 0;
        final int iMONDAYPOS = 1;
        final int iTUESDAYPOS = 2;
        final int iWEDNESDAYPOS = 3;
        final int iTHURSDAYPOS = 4;
        final int iFRIDAYPOS = 5;
        final int iSATURDAYPOS = 6;
        
        m_isSunValid = (aDayOfWeekPattern.charAt(iSUNDAYPOS) == '1');
        m_isMonValid = (aDayOfWeekPattern.charAt(iMONDAYPOS) == '1');
        m_isTueValid = (aDayOfWeekPattern.charAt(iTUESDAYPOS) == '1');
        m_isWedValid = (aDayOfWeekPattern.charAt(iWEDNESDAYPOS) == '1');
        m_isThuValid = (aDayOfWeekPattern.charAt(iTHURSDAYPOS) == '1');
        m_isFriValid = (aDayOfWeekPattern.charAt(iFRIDAYPOS) == '1');
        m_isSatValid = (aDayOfWeekPattern.charAt(iSATURDAYPOS) == '1');
    }

    /**
     * This function is used when writing calendar information to the database.  The database stores the valid days of
     * the week as a numeric value.  This function converts the day of week flags into a numeric value that the
     * database can store.
     *
     * @return DOCUMENT ME!
     */
    public int getDayOfWeekBitmap()
    {
        int aDayOfWeekPattern = 0;

        aDayOfWeekPattern |= (m_isSunValid ? SUNDAY_VALID : 0);
        aDayOfWeekPattern |= (m_isMonValid ? MONDAY_VALID : 0);
        aDayOfWeekPattern |= (m_isTueValid ? TUESDAY_VALID : 0);
        aDayOfWeekPattern |= (m_isWedValid ? WEDNESDAY_VALID : 0);
        aDayOfWeekPattern |= (m_isThuValid ? THURSDAY_VALID : 0);
        aDayOfWeekPattern |= (m_isFriValid ? FRIDAY_VALID : 0);
        aDayOfWeekPattern |= (m_isSatValid ? SATURDAY_VALID : 0);

        return (aDayOfWeekPattern);
    }

    /**
     * returns a string that describes the day of week pattern. Each day is represented with a "Y" or an "N" depending
     * on whether that day is valid or not.  Starts with Sunday. For example, for weekdays only, this function would
     * return: "NYYYYYN"
     *
     * @return DOCUMENT ME!
     */
    public String getDayOfWeekPattern()
    {
        return (getDayOfWeekPattern('1', '0'));
    }

    /**
     * returns a string that describes the day of week pattern. Each day is represented with a "Y" or an "N" depending
     * on whether that day is valid or not.  Starts with Sunday. For example, for weekdays only, this function would
     * return: "NYYYYYN"
     *
     * @param aTrueChar DOCUMENT ME!
     * @param aFalseChar DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getDayOfWeekPattern(final char aTrueChar, final char aFalseChar)
    {
        final StringBuffer sDays = new StringBuffer();

        sDays.append(m_isSunValid ? aTrueChar : aFalseChar);
        sDays.append(m_isMonValid ? aTrueChar : aFalseChar);
        sDays.append(m_isTueValid ? aTrueChar : aFalseChar);
        sDays.append(m_isWedValid ? aTrueChar : aFalseChar);
        sDays.append(m_isThuValid ? aTrueChar : aFalseChar);
        sDays.append(m_isFriValid ? aTrueChar : aFalseChar);
        sDays.append(m_isSatValid ? aTrueChar : aFalseChar);

        return (sDays.toString());
    }	public Object clone() 	{	    try 	    {	        Object result = super.clone();	    	        return result;	    } 	    catch (CloneNotSupportedException e) 	    {	        return null;	    }	}
}
