package lib.demo.util;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

    private final static Calendar mCalendar = Calendar.getInstance();

    public final static void resetData() {
        mCalendar.clear();
    }

    public final static Calendar getCalendar() {
        mCalendar.setTime(new Date());
        return mCalendar;
    }

    public final static String getYears() {
        mCalendar.setTime(new Date());
        return String.valueOf(mCalendar.get(Calendar.YEAR));
    }

    public final static int getYear() {
        mCalendar.setTime(new Date());
        return mCalendar.get(Calendar.YEAR);
    }

    public final static String getMonths() {
        mCalendar.setTime(new Date());
        int i = mCalendar.get(Calendar.MONTH) + 1;
        if (i < 10) {
            return "0" + i;
        } else {
            return String.valueOf(i);
        }
    }

    public final static int getMonth() {
        mCalendar.setTime(new Date());
        return mCalendar.get(Calendar.MONTH) + 1;
    }

    public final static String getDays() {
        mCalendar.setTime(new Date());
        int i = mCalendar.get(Calendar.DATE);
        if (i < 10) {
            return "0" + i;
        } else {
            return String.valueOf(i);
        }
    }

    public final static int getDay() {
        mCalendar.setTime(new Date());
        return mCalendar.get(Calendar.DATE);
    }

    public final static String getMinutes() {
        mCalendar.setTime(new Date());
        int i = mCalendar.get(Calendar.MINUTE);
        if (i < 10) {
            return "0" + i;
        } else {
            return String.valueOf(i);
        }
    }

    public final static int getMinute() {
        mCalendar.setTime(new Date());
        return mCalendar.get(Calendar.MINUTE);
    }

    public final static String getHours() {
        mCalendar.setTime(new Date());
        int i = mCalendar.get(Calendar.HOUR_OF_DAY);
        if (i < 10) {
            return "0" + i;
        } else {
            return String.valueOf(i);
        }
    }

    public final static int getHour() {
        mCalendar.setTime(new Date());
        return mCalendar.get(Calendar.HOUR_OF_DAY);
    }

    public final static String getSeconds() {
        mCalendar.setTime(new Date());
        int i = mCalendar.get(Calendar.SECOND);
        if (i < 10) {
            return "0" + i;
        } else {
            return String.valueOf(i);
        }
    }

    public final static int getSecond() {
        mCalendar.setTime(new Date());
        return mCalendar.get(Calendar.SECOND);
    }

    public final static String getDates() {
        return getYears() + "-" + getMonths() + "-" + getDays() + " " + getHours() + ":" + getMinutes() + ":" + getSeconds();
    }

    public final static String getTimes() {
        return getHours() + ":" + getMinutes() + ":" + getSeconds();
    }
}
