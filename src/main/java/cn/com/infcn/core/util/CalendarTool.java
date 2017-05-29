package cn.com.infcn.core.util;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarTool {
    public final static int[] DAYSINMONTH = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    public String[] monthNames = null;
    protected DateFormatSymbols symbols = null;
    protected Calendar cal = Calendar.getInstance();
    protected int today = cal.get(Calendar.DAY_OF_MONTH);
    protected int month = cal.get(Calendar.MONTH);
    protected int year = cal.get(Calendar.YEAR);

    public CalendarTool() {
        symbols = new DateFormatSymbols();
        monthNames = symbols.getMonths();
    }

    public void setMonth(int monthArg) {
        this.month = monthArg;
    }

    public void setYear(int yearArg) {
        this.year = yearArg;
    }

    public void setMonth(String monthArg) {
        try {
            int month = Integer.parseInt(monthArg);
            setMonth(month);
        } catch (Exception exc) {
        }
    }

    public void setYear(String yearArg) {
        try {
            int year = Integer.parseInt(yearArg);
            setYear(year);
        } catch (Exception exc) {
        }
    }

    public String getMonthName() {
        return monthNames[month];
    }

    public int getToday() {
        return today;
    }

    public int getStartCell() {
        Calendar beginOfMonth = Calendar.getInstance();
        beginOfMonth.set(year, month, 0);
        return beginOfMonth.get(Calendar.DAY_OF_WEEK);
    }

    public int getEndCell() {
        cal.set(year, month, 1);
        int endCell = DAYSINMONTH[month] + getStartCell() - 1;
        if (month == Calendar.FEBRUARY && ((GregorianCalendar) cal).isLeapYear(year)) {
            endCell++;
        }
        return endCell;
    }

    public void update() {
        cal.set(this.year, this.month, 1);
    }

    public String getDayName(int day, boolean longFormat) {
        if (longFormat)
            return symbols.getWeekdays()[day];
        return symbols.getShortWeekdays()[day];
    }

}
