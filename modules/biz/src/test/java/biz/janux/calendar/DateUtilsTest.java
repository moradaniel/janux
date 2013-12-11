package biz.janux.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import biz.janux.calendar.DateUtils;

import junit.framework.TestCase;

public class DateUtilsTest extends TestCase {

    public DateUtilsTest() {
    }

    public void testValidateDateRange() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd");

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date today = calendar.getTime();

        int maxDays = 730;
        Date startDate = today;
        Date endDate = today;

        // Same start and end dates

        try {
            DateUtils.validateDateRange(startDate, endDate, maxDays);
        } catch (Exception re) {
            fail("test failed for startDate=" + df.format(startDate)
                    + ",endDate=" + df.format(endDate) + ",maxDays=" + maxDays);
        }

        startDate = today;
        endDate = DateUtils.incDate(today, Calendar.DATE, 1);

        // today as start date and future date as end date

        try {
            DateUtils.validateDateRange(startDate, endDate, maxDays);
        } catch (Exception re) {
            fail("test failed for startDate=" + df.format(startDate)
                    + ",endDate=" + df.format(endDate) + ",maxDays=" + maxDays);
        }

        startDate = DateUtils.incDate(today, Calendar.DATE, 10);
        endDate = DateUtils.incDate(today, Calendar.DATE, 730);

        // valid future dates as start and end dates

        try {
            DateUtils.validateDateRange(startDate, endDate, maxDays);
        } catch (Exception re) {
            fail("test failed for startDate=" + df.format(startDate)
                    + ",endDate=" + df.format(endDate) + ",maxDays=" + maxDays);
        }

        // Past Date as Start date and valid future date as End date

        startDate = DateUtils.incDate(today, Calendar.DATE, -10);
        endDate = DateUtils.incDate(today, Calendar.DATE, 10);

        try {
            DateUtils.validateDateRange(startDate, endDate, maxDays);
            fail("test failed for startDate=" + df.format(startDate)
                    + ",endDate=" + df.format(endDate) + ",maxDays=" + maxDays);
        } catch (Exception re) {
            assertEquals("From Date should be same or after today's date", re
                    .getMessage());
        }

        //    Valid future dates for Start and End dates. But End date is before
        // Start date

        startDate = DateUtils.incDate(today, Calendar.DATE, 10);
        endDate = DateUtils.incDate(today, Calendar.DATE, 2);

        try {
            DateUtils.validateDateRange(startDate, endDate, maxDays);
            fail("test failed for startDate=" + df.format(startDate)
                    + ",endDate=" + df.format(endDate) + ",maxDays=" + maxDays);
        } catch (Exception re) {
            assertEquals("To Date should be same or after From Date", re
                    .getMessage());
        }

        // Today as End Date and future date is Start date. But End date is before
        // Start date

        endDate = today;
        startDate = DateUtils.incDate(today, Calendar.DATE, 10);

        try {
            DateUtils.validateDateRange(startDate, endDate, maxDays);
            fail("test failed for startDate=" + df.format(startDate)
                    + ",endDate=" + df.format(endDate) + ",maxDays=" + maxDays);
        } catch (Exception re) {
            assertEquals("To Date should be same or after From Date", re
                    .getMessage());
        }

        // Test for allowed maximum dates.

        startDate = DateUtils.incDate(today, Calendar.DATE, 10);
        endDate = DateUtils.incDate(today, Calendar.DATE, 750);

        try {
            DateUtils.validateDateRange(startDate, endDate, maxDays);
            fail("test failed for startDate=" + df.format(startDate)
                    + ",endDate=" + df.format(endDate) + ",maxDays=" + maxDays);
        } catch (Exception re) {
            assertEquals("To Date should not be more than " + maxDays
                    + " days from today", re.getMessage());
        }
    }
}