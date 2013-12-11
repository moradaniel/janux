package biz.janux.calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Used to represent a period in time bounded by two date/times.  Either of the bounds may be null, and the application
 * is left to interpret what this would mean (a null 'from' date may indicate that no expiration date for this
 * DateRange has been set, for example)
 *
 * @author Philippe Paravicini, David Fairchild
 * @version $Revision: 1.12 $ - $Date: 2008-04-24 00:49:09 $
 */

public class DateRange implements Serializable, Comparable, Cloneable
{
	private static final long serialVersionUID = 5077991174775436270L;

	protected static Log log = LogFactory.getLog(DateRange.class);
	/** environment variable for setting minimum year value */
	public static final String MIN_YEAR_ENV_VAR = "janux.biz.calendar.minyear";
	/** environment variable for setting maximum year value */
	public static final String MAX_YEAR_ENV_VAR = "janux.biz.calendar.maxyear";
	
	/** start date */
	private final Date m_from;
	/** stop date */
	private final Date m_to;
	/** the number of milliseconds in one day */
	public static final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
	@SuppressWarnings("deprecation")
	public static final Date EARLIEST_DATE = new Date(System.getProperty(MIN_YEAR_ENV_VAR, "1/1/1000"));
	@SuppressWarnings("deprecation")
	public static final Date LATEST_DATE = new Date(System.getProperty(MAX_YEAR_ENV_VAR, "12/31/9999"));

	
	/**
	 * constructor: instantiate a DateRange with from/to dates; either date may be null, and the application is left to
	 * interpret what this would mean; the 'from' date must precede the 'to' date.
	 *
	 * @param aDateFrom start date
	 * @param aDateTo stop date
	 *
	 */
	public DateRange(final Date aDateFrom, final Date aDateTo)
	{
		if ((aDateFrom instanceof Date) && (aDateTo instanceof Date))
		{
			if (!aDateFrom.equals(aDateTo) && aDateFrom.after(aDateTo))
			{
				throw new IllegalArgumentException("The 'From' date: '" + aDateFrom.toString()
					+ "' occurs after the 'To' date: '" + aDateTo.toString() + "'");
			}
		}

		m_from = aDateFrom;
		m_to = aDateTo;
	}



	/**
	 * constructor: instantiate a DateRange with a date and the Range duration in milliseconds; if the duration is
	 * positive, the date is considered to be the 'from' date, if the duration is negative, the date is considered to
	 * be the 'to' date.
	 *
	 * @param startDate startdate
	 * @param duration duration in milliseconds
	 */
	public DateRange(final Date startDate, final long duration)
	{
		if (duration >= 0)
		{
			m_from = startDate;
			m_to = new Date(startDate.getTime() + duration);
		}
		else
		{
			m_from = new Date(startDate.getTime() + duration);
			m_to = startDate;
		}
	}


	/**
	 * The beginning of this DateRange, inclusive; shorthand for getFrom()
	 * @return start date
	 */
	public Date getFrom()
	{
		return m_from;
	}


	/**
	 * The end date of this DateRange, inclusive
	 * @return stop date
	 */
	public Date getTo()
	{
		return m_to;
	}


 
	/**
	 * Returns true if the given date falls within the date range
	 * @param aDate the date to check
	 * @return true if the given date falls within the date range
	 */
	public boolean contains(final Date aDate)
	{
		if (aDate instanceof Date)
		{
			if (aDate.equals(m_from) || aDate.equals(m_to))
			{
				return (true);
			}
			else if (aDate.after(m_from) && aDate.before(m_to))
			{
				return (true);
			}
			else
			{
				return (false);
			}
		}
		else
		{
			return (false);
		}
	}


	/**
	 * Returns true if the given date range is contained within this instance
	 * @param aDateRange daterange to compare
	 * @return true if the given daterange is contained within this instance
	 */
	public boolean contains(final DateRange aDateRange)
	{
		if ((aDateRange instanceof DateRange) && (this.m_from instanceof Date) && (this.m_to instanceof Date))
		{
			if ((aDateRange.m_from instanceof Date) && (aDateRange.m_to instanceof Date))
			{
				if (aDateRange.getFrom().before(this.m_from))
				{
					return (false);
				}
				else if (aDateRange.getTo().after(this.m_to))
				{
					return (false);
				}
				else
				{
					return (true);
				}
			}
		}

		return (false);
	}

	


	/**
	 * returns the datetime N milliseconds into this DateRange, or: dateRange.from() + N Milliseconds,	returns null if
	 * the from() date is null;
	 * @param duration the number of milliseconds into this date range you want to offset
	 * @return the new date with the given offset
	 */
	public Date getDateTimeAfter(final long duration)
	{
		if (this.getFrom() == null)
		{
			return null;
		}
		else
		{
			return (new Date(this.getFrom().getTime() + duration));
		}
	}



	/**
	 * Returns the Nth day of this DateRange, counting from the from() date, or null if the from() date is null
	 * @param aNumDays the number of days offset
	 * @return DOCUMENT ME!
	 */
	public Date getDay(final long aNumDays)
	{
		return DateUtils.incDate(this.getFrom(), Calendar.DATE, (int )aNumDays);
	}

	/** 
	 * returns the whole number of days in this range, or -1 if either the From or To dates are null
	 */
	public int numDaysInRange() 
	{
		if (this.getFrom() == null || this.getTo() == null)
		{
			return -1;
		}
		
		final int iDays = DateUtils.numDaysBetween(this.getFrom(), this.getTo());
		
		/*
		final long start = this.getFrom().getTime();
		final long stop = this.getTo().getTime();
		final long diff = stop - start;
		final long diffDays = diff/MILLIS_PER_DAY;
        */
		
		return iDays;
	}

	public boolean isOpenFrom()
	{
		boolean isOpenFrom = (this.m_from != null && (EARLIEST_DATE.getYear() == this.m_to.getYear()));
//		boolean isOpenFrom = (this.m_from != null && (EARLIEST_DATE.equals(this.m_to)));
		return isOpenFrom;
	}

	public boolean isOpenTo()
	{
		boolean isOpenTo = (this.m_to != null && (LATEST_DATE.getYear() == this.m_to.getYear()));
//		boolean isOpenTo = (this.m_to != null && (LATEST_DATE.equals(this.m_to)));
// NOTE: can't seem to reliably set a To date equal to LATEST_DATE all the way down to the second
// Work around this by considering To date to be "open" if just the years match. (Likewise for isOpenFrom).
		return isOpenTo;
	}
	
	/**
	 * Returns true if the given date range overlaps at any time with this instance
	 * @param aDateRange daterange to compare
	 * @return true if any portion of the date ranges overlap
	 */
	public boolean isOverlapping(final DateRange aDateRange)
	{
		if ((aDateRange instanceof DateRange) && (this.m_from instanceof Date) && (this.m_to instanceof Date))
		{
			if ((aDateRange.m_from instanceof Date) && (aDateRange.m_to instanceof Date))
			{
				if (aDateRange.m_to.after(this.m_from) && aDateRange.m_from.before(this.m_to))
				{
					return (true);
				}

				if (this.m_to.after(aDateRange.m_from) && this.m_from.before(aDateRange.m_to))
				{
					return (true);
				}
			}
		}

		return (false);
	}



	/**
	 * Compares two DateRange objects
	 * @param aObject daterange object to compare
	 * @return DOCUMENT ME!
	 */
	public int compareTo(final Object aObject)
	{
		if (aObject instanceof DateRange)
		{
			final DateRange dt = (DateRange) aObject;

			if (this.m_from instanceof Date)
			{
				final int iFrom = this.m_from.compareTo(dt.m_from);
				if (iFrom != 0)
				{
					return (iFrom);
				}
			}

			if (this.m_to instanceof Date)
			{
				final int iTo = this.m_to.compareTo(dt.m_to);
				return (iTo);
			}

			return (0);
		}
		else
		{
			throw new ClassCastException();
		}
	}

	public String toString() 
	{
		return new ToStringBuilder(this)
			.append("from", getFrom())
			.append("to",	getTo())
			.toString();
	}


	/**
	 * Compares the given object for equality
	 * @param aObject the object to compare
	 * @return true if the date ranges are equal
	 */
	/*
	public boolean equals(final Object aObject)
	{
		if (!(aObject instanceof DateRange))
		{
			if ((aObject == null) && (this.m_from == null) && (this.m_to == null))
			{
				return (true);
			}
			else
			{
				return (false);
			}
		}


		final DateRange aDateRange = (DateRange ) aObject;
		
		// check the start dates
		if (this.m_from instanceof Date)
		{
			if (this.m_from.getTime() != aDateRange.m_from.getTime())
			{
				return (false);
			}
		}
		else if (aDateRange.m_from instanceof Date)
		{
			return (false);
		}


		// check the stop dates
		if (this.m_to instanceof Date)
		{
			if (this.m_to.getTime() != aDateRange.m_to.getTime())
			{
				return (false);
			}
		}
		else if (aDateRange.m_to instanceof Date)
		{
			return (false);
		}


		return (true);
	}
	*/

	/**
	 * Hashcode method 
	 * @return a unique hashcode for this instance
	 */
	/*
	public int hashCode()
	{
		return super.hashCode();
	}
	*/
	
	
	public Iterator<Date> iterator()
	{
		return iterator(Calendar.DATE, false);
	}
	
	public Iterator<Date> iterator(final boolean aIncludeEndDate)
	{
		return iterator(Calendar.DATE, aIncludeEndDate);
	}
	
	
	public Iterator<Date> iterator(final int aIteraterField, final boolean aIncludeEndDate)
	{
		final Calendar itCal = new GregorianCalendar();
		itCal.setTime(this.getFrom());
		
		
		final Iterator<Date> it = new Iterator<Date>()
		{
			final private Calendar cal = itCal;
			// final private boolean includeEndDate = aIncludeEndDate;

			public boolean hasNext()
			{
				// advance to the next date and see if it's inbounds
				// cal.add(aField, 1);
			//	final Date currentDate = cal.getTime();
			//	cal.add(aField, 1);
				
			//	return currentDate;
				return withinRange();
			}

			public Date next()
			{
				if (withinRange() == false)
				{
					throw new NoSuchElementException();
				}
				
				final Date currentDate = cal.getTime();
				cal.add(aIteraterField, 1);
				return currentDate;
			}
			
			private boolean withinRange()
			{
				final Date checkDate = cal.getTime();
				final Date endDate = getTo();
			
				if (checkDate.before(endDate))
				{
					return true;
				}
				
				
				if (aIncludeEndDate && checkDate.equals(endDate))
				{
					return true;
				}
				
				
				return false;
			}

			public void remove()
			{
				throw new UnsupportedOperationException(); 
			}
		};		
		
		
		return it;
	}
	

	/**  Two DateRanges are equal if they have the same From and To dates */
	public boolean equals(Object other)
	{
		if ( (this == other ) ) return true;
		if ( !(other instanceof DateRange) ) return false;
		DateRange castOther = (DateRange)other; 

		return new EqualsBuilder()
			.append(this.getFrom(), castOther.getFrom())
			.append(this.getTo(), castOther.getTo())
			.isEquals();
	}


	public int hashCode() 
	{
		return new HashCodeBuilder()
		.append(this.getFrom())
		.append(this.getTo())
		.toHashCode();
	}   


	public Object clone() 
	{
	    try 
	    {
	        Object result = super.clone();
	    
	        return result;
	    } 
	    catch (CloneNotSupportedException e) 
	    {
	        return null;
	    }
	}

}

