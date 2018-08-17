package lib.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * description: 时间工具类
 * created by kalu on 2017/7/2 21:29
 */
public class DateUtil {
    // 标准日期时间格式
    /**
     * yyyy-MM
     */
    public static final String FORMAT_MONTH = "yyyy-MM";
    /**
     * yyyy
     */
    public static final String FORMAT_DATE_YEAR = "yyyy";
    /**
     * yyyy-MM-dd
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String FORMAT_DATE_MIN = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd E
     */
    public static final String FORMAT_DATE_WEEK = "yyyy年MM月dd日   E";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat();
    private static final long ONE_DAY = 1000 * 3600 * 24;

    /**
     * 将标准时间转成时间格式
     *
     * @param date 标准时间
     * @return 时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static String format(Date date) {
        return format(date, FORMAT_DATE_TIME);
    }

    /**
     * 按指定格式格式化时期时间
     *
     * @param date   date
     * @param format format
     * @return string.
     */
    public static String format(Date date, String format) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(format);
            return df.format(date);
        } else {
            return "";
        }
    }

    /**
     * 将时间的字符串格式转成Date
     *
     * @param str str
     * @return Date
     */
    public static Date parse(String str) {
        return parse(str, FORMAT_DATE_TIME);
    }

    /**
     * 按指定格式解析字符串，将字符串转为日期时间格式
     *
     * @param str    string
     * @param format format
     * @return date
     */
    public static Date parse(String str, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isSameDate(long timestamp1, long timestamp2) {

        final Date date1 = new Date(timestamp1);
        final Date date2 = new Date(timestamp2);

        final boolean equal1 = (date1.getDay() == date2.getDay());
        final boolean equal2 = (date1.getMonth() == date2.getMonth());
        final boolean equal3 = (date1.getYear() == date2.getYear());

        return equal1 && equal2 && equal3;
    }

    public static String formatDate(long currentTimeMillis) {

        final Date date = new Date(currentTimeMillis);
        return toDisplaySimpleDatetime(date);
    }

    /**
     * 转化成容易阅读的日期格式（标准版）：
     * 去年以前 ----> 16-09-10 14:23
     * 今年 ----> 09-10 14:23
     * 昨天 ----> 昨天 14:23
     * 今天 ----> 14:23
     */
    public static String foamatTimestampDisplay(long timestamp) {

        Date date = new Date(timestamp);
        return toDisplaySimpleDatetime(date);
    }

    /**
     * 转化成容易阅读的日期格式（简化版）：
     * 去年以前 ----> 2016-09-10
     * 今年 ----> 09-10
     * 昨天 ----> 昨天
     * 今天 ----> 14:23
     */
    public static String toDisplaySimpleDatetime(Date date) {
        Calendar temp = Calendar.getInstance();
        temp.set(Calendar.HOUR_OF_DAY, 0);
        temp.set(Calendar.MINUTE, 0);
        temp.set(Calendar.SECOND, 0);
        temp.set(Calendar.MILLISECOND, 0);

        temp.add(Calendar.DATE, 1);

        String formatStr;
        if (date.getTime() >= temp.getTimeInMillis()) {
            // 明天或以后...
            formatStr = "yy-MM-dd";
        } else {
            temp.add(Calendar.DATE, -1);
            if (date.getTime() >= temp.getTimeInMillis()) {
                // 今天
                formatStr = "HH:mm";
            } else {
                temp.add(Calendar.DATE, -1);
                if (date.getTime() >= temp.getTimeInMillis()) {
                    // 昨天
                    formatStr = "昨天";
                } else {
                    temp.set(Calendar.DAY_OF_YEAR, 1);
                    if (date.getTime() >= temp.getTimeInMillis()) {
                        // 今年
                        formatStr = "M-d";
                    } else {
                        // 很久以前...
                        formatStr = "yy-M-d";
                    }
                }
            }
        }

        return format(date, formatStr);
    }

    /**
     * 时间戳格式化 21:15:21
     */
    public static String foamatTimestamp(long timestamp) {
        String ms, ss;

        long m, s;
        m = timestamp / 60;
        s = timestamp % 60;

        if (m < 10) {
            ms = "0" + m;
        } else {
            ms = "" + m;
        }

        if (s < 10) {
            ss = "0" + s;
        } else {
            ss = "" + s;
        }

        return ms + ":" + ss;
    }

    /**
     * 返回一定格式的当前时间
     *
     * @param pattern "yyyy-MM-dd HH:mm:ss E"
     * @return
     */
    public static String getCurrentDate(String pattern) {
        SIMPLE_DATE_FORMAT.applyPattern(pattern);
        Date date = new Date(System.currentTimeMillis());
        String dateString = SIMPLE_DATE_FORMAT.format(date);
        return dateString;

    }

    public static long getDateMillis(String dateString, String pattern) {
        long millionSeconds = 0;
        SIMPLE_DATE_FORMAT.applyPattern(pattern);
        try {
            millionSeconds = SIMPLE_DATE_FORMAT.parse(dateString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }// 毫秒

        return millionSeconds;
    }

    /**
     * 格式化输入的millis
     *
     * @param millis
     * @param pattern yyyy-MM-dd HH:mm:ss E
     * @return
     */
    public static String dateFormat(long millis, String pattern) {
        SIMPLE_DATE_FORMAT.applyPattern(pattern);
        Date date = new Date(millis);
        String dateString = SIMPLE_DATE_FORMAT.format(date);
        return dateString;
    }

    /**
     * 将dateString原来old格式转换成new格式
     *
     * @param dateString
     * @param oldPattern yyyy-MM-dd HH:mm:ss E
     * @param newPattern
     * @return oldPattern和dateString形式不一样直接返回dateString
     */
    public static String dateFormat(String dateString, String oldPattern,
                                    String newPattern) {
        long millis = getDateMillis(dateString, oldPattern);
        if (0 == millis) {
            return dateString;
        }
        String date = dateFormat(millis, newPattern);
        return date;
    }

    public static int getDate(String formatStr, Date date) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return Integer.parseInt(format.format(date));
    }

    /**
     * 获取某月的天数
     *
     * @param year  年
     * @param month 月
     * @return 某月的天数
     */
    public static int getMonthDaysCount(int year, int month) {
        int count = 0;
        //判断大月份
        if (month == 1 || month == 3 || month == 5 || month == 7
                || month == 8 || month == 10 || month == 12) {
            count = 31;
        }

        //判断小月
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            count = 30;
        }

        //判断平年与闰年
        if (month == 2) {
            if (isLeapYear(year)) {
                count = 29;
            } else {
                count = 28;
            }
        }
        return count;
    }


    /**
     * 是否是闰年
     *
     * @param year year
     * @return return
     */
    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    /**
     * 获取某年的天数
     *
     * @param year 某一年
     * @return 366 or 365
     */
    public static int getYearCount(int year) {
        return isLeapYear(year) ? 366 : 365;
    }


    /**
     * 获取月视图的确切高度
     *
     * @param year       年
     * @param month      月
     * @param itemHeight 每项的高度
     * @return 不需要多余行的高度
     */
    public static int getMonthViewHeight(int year, int month, int itemHeight) {
        Calendar date = Calendar.getInstance();
        date.set(year, month - 1, 1);
        int firstDayOfWeek = date.get(Calendar.DAY_OF_WEEK) - 1;//月第一天为星期几,星期天 == 0
        int mDaysCount = getMonthDaysCount(year, month);
        date.set(year, month - 1, mDaysCount);
        int mLastCount = date.get(Calendar.DAY_OF_WEEK) - 1;//月最后一天为星期几,星期天 == 0
        int nextMonthDaysOffset = 6 - mLastCount;//下个月的日偏移天数
        return (firstDayOfWeek + mDaysCount + nextMonthDaysOffset) / 7 * itemHeight;
    }


    /**
     * 获取某年第几天是第几个月
     *
     * @param year      年
     * @param dayInYear 某年第几天
     * @return 第几个月
     */
    public static int getMonthFromDayInYear(int year, int dayInYear) {
        int count = 0;
        for (int i = 1; i <= 12; i++) {
            count += getMonthDaysCount(year, i);
            if (dayInYear <= count)
                return i;
        }
        return 0;
    }

    /**
     * 获取某年第几周是在第几个月
     *
     * @param year       年
     * @param weekInYear 某年第几周
     * @return 第几个月
     */
    public static int getMonthFromWeekFirstDayInYear(int year, int weekInYear) {
        Calendar date = Calendar.getInstance();
        date.set(year, 0, 1);
        int diff = date.get(Calendar.DAY_OF_WEEK) - 1;//1月第一天为星期几,星期天 == 0，也就是偏移量
        int count = 0;
        int diy = (weekInYear - 1) * 7 - diff + 1;
        for (int i = 1; i <= 12; i++) {
            count += getMonthDaysCount(year, i);
            if (diy <= count)
                return i;
        }
        return 0;
    }


    /**
     * 获取两个年份之间一共有多少周
     *
     * @param minYear minYear
     * @param maxYear maxYear
     * @return 周数
     */
    public static int getWeekCountBetweenYearAndYear(int minYear, int maxYear) {
        if (minYear > maxYear)
            return 0;
        Calendar date = Calendar.getInstance();
        date.set(minYear, 0, 1);//1月1日
        int preDiff = date.get(Calendar.DAY_OF_WEEK) - 1;//1月第一天为星期几,星期天 == 0，也就是偏移量
        date.set(maxYear, 11, 31);//12月31日
        int nextDiff = 7 - date.get(Calendar.DAY_OF_WEEK);//1月第一天为星期几,星期天 == 0，也就是偏移量
        int count = preDiff + nextDiff;
        for (int i = minYear; i <= maxYear; i++) {
            count += getYearCount(i);
        }
        return count / 7;
    }


    /**
     * 获取两个年份之间一共有多少周
     *
     * @param minYear      minYear 最小年份
     * @param minYearMonth maxYear 最小年份月份
     * @param maxYear      maxYear 最大年份
     * @param maxYearMonth maxYear 最大年份月份
     * @return 周数
     */
    public static int getWeekCountBetweenYearAndYear(int minYear, int minYearMonth, int maxYear, int maxYearMonth) {
        Calendar date = Calendar.getInstance();
        date.set(minYear, minYearMonth - 1, 1);
        long minTime = date.getTimeInMillis();//给定时间戳
        int preDiff = date.get(Calendar.DAY_OF_WEEK) - 1;//1月第一天为星期几,星期天 == 0，也就是偏移量
        date.set(maxYear, maxYearMonth - 1, getMonthDaysCount(maxYear, maxYearMonth));
        long maxTime = date.getTimeInMillis();//给定时间戳
        int nextDiff = 7 - date.get(Calendar.DAY_OF_WEEK);//1月第一天为星期几,星期天 == 0，也就是偏移量
        int count = preDiff + nextDiff;
        int c = (int) ((maxTime - minTime) / ONE_DAY) + 1;
        count += c;
        return count / 7;
    }

    /**
     * 是否在日期范围內
     *
     * @param year         year
     * @param month        month
     * @param minYear      minYear
     * @param minYearMonth minYearMonth
     * @param maxYear      maxYear
     * @param maxYearMonth maxYearMonth
     * @return 是否在日期范围內
     */
    public static boolean isMonthInRange(int year, int month, int minYear, int minYearMonth, int maxYear, int maxYearMonth) {
        return !(year < minYear || year > maxYear) &&
                !(year == minYear && month < minYearMonth) &&
                !(year == maxYear && month > maxYearMonth);
    }


    /**
     * 获取星期偏移了多少周
     *
     * @param minYear      minYear
     * @param minYearMonth minYearMonth
     * @return 获取星期偏移了多少周
     */
    public static int getWeekCountDiff(int minYear, int minYearMonth) {
        if (minYearMonth == 1) {
            return -1;
        }
        Calendar date = Calendar.getInstance();
        date.set(minYear, 0, 1);//1月1日
        long firstTime = date.getTimeInMillis();//获得起始时间戳
        int preDiff = date.get(Calendar.DAY_OF_WEEK) - 1;//1月第一天为星期几,星期天 == 0，也就是偏移量
        date.set(minYear, minYearMonth - 1, 1);
        long minTime = date.getTimeInMillis();//获得时间戳
        int nextDiff = date.get(Calendar.DAY_OF_WEEK) - 1;//minYearMonth月第一天为星期几,星期天 == 0，也就是偏移量
        int c = (int) ((minTime - firstTime) / ONE_DAY) - 1;
        int count = preDiff + c - nextDiff;
        return count / 7;
    }

    public static String getDate(Date d, int day) {

        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(now.getTime());
        return result;
    }
}
