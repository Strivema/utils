package com.ray.utils;

import org.apache.commons.lang3.time.FastDateFormat;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.util.*;

/**
 * @author Ray.Ma
 * @date 2019/7/9 10:43
 */
public class DateUtils {

    public static Date PLATFORM_MAX_TIME = parseDateFromString("2200-01-01", "yyyy-MM-dd");
    public static Date PLATFORM_ONBOARD_TIME = parseDateFromString("2014-01-25", "yyyy-MM-dd");


    static FastDateFormat sdfShort = FastDateFormat.getInstance("yyyyMMdd");
    static FastDateFormat sdfShortShort = FastDateFormat.getInstance("MMdd");
    static FastDateFormat sdfLong = FastDateFormat.getInstance("yyyy-MM-dd");
    static FastDateFormat sdfLongCn = FastDateFormat.getInstance("yyyy年MM月dd日");
    static FastDateFormat sdfShortU = FastDateFormat.getInstance("MMM dd", Locale.ENGLISH);
    static FastDateFormat sdfLongU = FastDateFormat.getInstance("MMM dd,yyyy", Locale.ENGLISH);
    static FastDateFormat sdfLongTime = FastDateFormat.getInstance("yyyyMMddHHmmss");
    static FastDateFormat sdfLongTimePlus = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    static FastDateFormat sdfShortLongTimePlusCn = FastDateFormat.getInstance("yyyy年MM月dd日 HH:mm");
    static FastDateFormat sdfLongTimePlusMill = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");
    static FastDateFormat sdfMd = FastDateFormat.getInstance("MM月dd日");
    static FastDateFormat sdfHm = FastDateFormat.getInstance("HH:mm");
    static FastDateFormat sdfHms = FastDateFormat.getInstance("HH:mm:ss");

    static FastDateFormat sdfutc8 = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00");

    static FastDateFormat sdfLongMonthCn = FastDateFormat.getInstance("yyyy-MM");

    static FastDateFormat sdfyyyyMM = FastDateFormat.getInstance("yyyyMM");

    static FastDateFormat sdfXlsTimePlus = FastDateFormat.getInstance("MM/dd/yyyy HH:mm:ss");

    /**
     * 得到分钟和秒  x分x秒
     */
    public static String getMinuteAndSecond(Long seconds) {
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormat.fullDateTime();

        if (seconds / 60 == 0) {
            return seconds % 60 + "秒";
        }
        return seconds / 60 + "分" + seconds % 60 + "秒";
    }

    /**
     * 得到平台开始时间
     */
    public static Date getPlatformStartDate() {
        return PLATFORM_ONBOARD_TIME;
    }

    /**
     * 得到平台开始时间
     */
    public static Date getPlatformMaxDate() {
        return PLATFORM_MAX_TIME;
    }


    /**
     * get Date format Example：2008-05-15
     */
    public static String getDateLong(Date date) {
        String nowDate = "";
        try {
            if (date != null)
                nowDate = sdfLong.format(date);
            return nowDate;
        } catch (Exception e) {
            return "";
        }
    }

    public static Date getDateLongToDate(String date) {
        String nowDate = "";
        try {
            if (date != null)
                return sdfLong.parse(date);
            else
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * get Date format Example：20080515
     */
    public static String getDateShort(Date date) {
        String nowDate = "";
        try {
            if (date != null)
                nowDate = sdfShort.format(date);
            return nowDate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * get Date format Example：2008年-05月-15日
     */
    public static String getDateLongCn(Date date) {
        String nowDate = "";
        try {
            if (date != null)
                nowDate = sdfLongCn.format(date);
            return nowDate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * get Date format Example：2008年-05月-15日 11:05
     */
    public static String getDateShortLongTimeCn(Date date) {
        String nowDate = "";
        try {
            if (date != null)
                nowDate = sdfShortLongTimePlusCn.format(date);
            return nowDate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * get current date,fomart:YYYYMMDDHHMISS
     *
     * @return String
     */
    public static String getNowLongTime() {
        String nowTime = "";
        try {
            java.sql.Date date = null;
            date = new java.sql.Date(DateUtils.getNowDate().getTime());
            nowTime = sdfLongTime.format(date);
            return nowTime;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * get current date,fomart:YYYYMMDD
     *
     * @return String
     */
    public static String getNowShortDate() {
        String nowDate = "";
        try {
            java.sql.Date date = null;
            date = new java.sql.Date(DateUtils.getNowDate().getTime());
            nowDate = sdfShort.format(date);
            return nowDate;
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * get current date,fomart:MMDD
     *
     * @return String
     */
    public static String getNowShortShortDate() {
        String nowDate = "";
        try {
            java.sql.Date date = null;
            date = new java.sql.Date(DateUtils.getNowDate().getTime());
            nowDate = sdfShortShort.format(date);
            return nowDate;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * get current date,fomart:YYYY-MM-DD
     *
     * @return String
     */
    public static String getNowFormateDate() {
        String nowDate = "";
        try {
            java.sql.Date date = null;
            date = new java.sql.Date(DateUtils.getNowDate().getTime());
            nowDate = sdfLong.format(date);
            return nowDate;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * get current date,fomart:YYYY-MM-DD
     *
     * @return String
     */
    public static String getYesterdayFormateDate() {
        String nowDate = "";
        try {
            java.sql.Date date = null;
            date = new java.sql.Date(DateUtils.getNowDate().getTime());
            Date yesterday = addDays(date, -1);
            nowDate = sdfLong.format(yesterday);
            return nowDate;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * getYearAndMonth toString
     *
     * @param date
     * @return
     */
    public static String getYearAndMonth(Date date) {
        return sdfLongMonthCn.format(date);
    }

    public static String getYearAndMonthNoformat(Date date) {
        return sdfyyyyMM.format(date);
    }

    /**
     * getYearAndMonth toDate
     *
     * @param YearAndMonth
     * @return
     */
    public static Date getYearAndMonth(String YearAndMonth) {
        try {
            return sdfLongMonthCn.parse(YearAndMonth);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * get current date,fomart:YYYY-MM-DD
     *
     * @return String
     */
    public static String getNowFormateYMDhmsDate() {
        String nowDate = "";
        try {
            java.sql.Date date = null;
            date = new java.sql.Date(DateUtils.getNowDate().getTime());
            nowDate = sdfLongTimePlus.format(date);
            return nowDate;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * @return 当前时间
     */
    public static Date getNowDate() {

        return new Date();
//        if (!WebConfig.isDebugMode()) {
//            return new Date();
//        }
//        int offsetDays = WebConfig.getServerDateOffsetDays();
//        if (offsetDays == 0) {
//            return new Date();
//        } else {
//            Calendar calo = Calendar.getInstance();
//            calo.setTime(new Date());
//            calo.set(Calendar.DAY_OF_YEAR, calo.get(Calendar.DAY_OF_YEAR) + offsetDays);
//            return calo.getTime();
//        }

    }


    /**
     * @return 年月日
     */
    public static Date getNowDay() {
        Calendar calo = Calendar.getInstance();
        calo.setTime(DateUtils.getNowDate());
        calo.set(Calendar.HOUR_OF_DAY, 0);
        calo.set(Calendar.MINUTE, 0);
        calo.set(Calendar.SECOND, 0);
        calo.set(Calendar.MILLISECOND, 0);
        return calo.getTime();
    }


    public static String getUTCString(Date date) {
        String formatDate = sdfutc8.format(date);
        return formatDate;
    }

    public static Date getLongTimePlusDateSecond(String sDate) throws ParseException {
//        return sdfLongTimePlus.parse(sDate);
        return sdfutc8.parse(sDate);

    }

    public static String getShortCurrentTimeStamp() {
        FastDateFormat dateFormat = FastDateFormat.getInstance("dd日HH:mm");
        String formatDate = dateFormat.format(DateUtils.getNowDate());
        return formatDate;
    }

    /**
     * get current date,fomart:yyyy-MM-dd HH:mm:ss
     *
     * @return String
     * @throws Exception
     */
    public static String getNowPlusTime() {
        String nowDate = "";
        try {
            java.sql.Date date = null;
            date = new java.sql.Date(DateUtils.getNowDate().getTime());
            nowDate = sdfLongTimePlus.format(date);
            return nowDate;
        } catch (Exception e) {
            throw e;
        }
    }

    public static String getNowPlusMSTime() {

        String nowDate = "";
        try {
            java.sql.Date date = null;
            date = new java.sql.Date(DateUtils.getNowDate().getTime());
            nowDate = sdfLongTimePlusMill.format(date);
            return nowDate;
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * get specified date,fomart:yyyy-MM-dd HH:mm:ss
     *
     * @return String
     * @throws Exception
     */
    public static String getPlusTime(Date date) {
        if (date == null) return null;
        try {
            String nowDate = sdfLongTimePlus.format(date);
            return nowDate;
        } catch (Exception e) {
            // do nothing for this;
            return "";
        }
    }

    /**
     * get specified date,fomart:yyyy-MM-dd HH:mm:ss
     *
     * @return String
     * @throws Exception
     */
    public static String getPlusTimeEmpty(Date date) {
        if (date == null) return "";
        try {
            String nowDate = sdfLongTimePlus.format(date);
            return nowDate;
        } catch (Exception e) {
            // do nothing for this;
            return "";
        }
    }

    /**
     * difference between months
     *
     * @param dealMonth
     * @param alterMonth
     * @return alterMonth
     */
    public static int calBetweenTwoMonth(String dealMonth, String alterMonth) {
        int length = 0;
        if ((dealMonth.length() != 6) || (alterMonth.length() != 6)) {
            length = -1;
        } else {
            int dealInt = Integer.parseInt(dealMonth);
            int alterInt = Integer.parseInt(alterMonth);
            if (dealInt < alterInt) {
                length = -2;
            } else {
                int dealYearInt = Integer.parseInt(dealMonth.substring(0, 4));
                int dealMonthInt = Integer.parseInt(dealMonth.substring(4, 6));
                int alterYearInt = Integer.parseInt(alterMonth.substring(0, 4));
                int alterMonthInt = Integer.parseInt(alterMonth.substring(4, 6));
                length = (dealYearInt - alterYearInt) * 12 + (dealMonthInt - alterMonthInt);
            }
        }
        return length;
    }


    /**
     * 2个日期的月份差
     *
     * @param newDate
     * @param oldDate
     * @return
     */
    public static int monthBetweenDates(Date newDate, Date oldDate) {

        Calendar c = Calendar.getInstance();
        c.setTime(newDate);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        c.setTime(oldDate);
        int yearOld = c.get(Calendar.YEAR);
        int monthOld = c.get(Calendar.MONTH);

        int result;
        if (year == yearOld) {

            result = month - monthOld;//两个日期相差几个月，即月份差
        } else {
            result = 12 * (year - yearOld) + month - monthOld;//两个日期相差几个月，即月份差
        }

        return result;

    }

    public static int hoursBetweenDates(Date newDate, Date oldDate) {

        int days = 0;
        Calendar calo = Calendar.getInstance();
        Calendar caln = Calendar.getInstance();
        calo.setTime(oldDate);
        caln.setTime(newDate);
        int oday = calo.get(Calendar.DAY_OF_YEAR);
        int nyear = caln.get(Calendar.YEAR);
        int oyear = calo.get(Calendar.YEAR);
        while (nyear > oyear) {
            calo.set(Calendar.MONTH, 11);
            calo.set(Calendar.DATE, 31);
            days = days + calo.get(Calendar.DAY_OF_YEAR);
            oyear = oyear + 1;
            calo.set(Calendar.YEAR, oyear);
        }
        int nday = caln.get(Calendar.DAY_OF_YEAR);
        days = days + nday - oday;

        int hours = caln.get(Calendar.HOUR_OF_DAY) - calo.get(Calendar.HOUR_OF_DAY);

        int gap = days * 24 + hours;
        return gap;

    }

    /**
     * difference between days
     *
     * @param newDate
     * @param oldDate
     * @return newDate-oldDate
     */
    public static int daysBetweenDates(Date newDate, Date oldDate) {
        int days = 0;
        Calendar calo = Calendar.getInstance();
        Calendar caln = Calendar.getInstance();
        calo.setTime(oldDate);
        caln.setTime(newDate);
        int oday = calo.get(Calendar.DAY_OF_YEAR);
        int nyear = caln.get(Calendar.YEAR);
        int oyear = calo.get(Calendar.YEAR);
        while (nyear > oyear) {
            calo.set(Calendar.MONTH, 11);
            calo.set(Calendar.DATE, 31);
            days = days + calo.get(Calendar.DAY_OF_YEAR);
            oyear = oyear + 1;
            calo.set(Calendar.YEAR, oyear);
        }
        int nday = caln.get(Calendar.DAY_OF_YEAR);
        days = days + nday - oday;

        return days;
    }

    /**
     * difference between days
     *
     * @param newDate
     * @param oldDate
     * @return newDate-oldDate
     */
    public static int weeksBetweenDates(Date newDate, Date oldDate) {
        return daysBetweenDates(newDate, oldDate) / 7;
    }

    public static int getLastDayOfMonth(Date date) {
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(date);
        int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
        return lastDay;
    }

    public static int getDayOfMonth(Date date) {
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(date);
        int day = cDay1.get(Calendar.DAY_OF_MONTH);
        return day;
    }


    public static int getWeekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, getYear(date));
        c.set(Calendar.WEEK_OF_YEAR, getWeekOfYear(date));
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//设置周一
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static long secondsBetweenDates(Date newDate, Date oldDate) {
        return (newDate.getTime() - oldDate.getTime()) / 1000;
    }

    public static String getFormattedDateUtil(Date dtDate, String strFormatTo) {
        if (dtDate == null) {
            return "";
        }
        strFormatTo = strFormatTo.replace('/', '-');
        try {
            FastDateFormat formatter = FastDateFormat.getInstance(strFormatTo);
            return formatter.format(dtDate);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 将一个字符串转化成日期对象，如果转化失败，返回null
     *
     * @param sourceString
     * @param pattern
     * @return
     */
    public static Date parseDateFromString(String sourceString, String pattern) {
        Date re = null;

        if (sourceString != null && !sourceString.equals("")) {
            FastDateFormat dateFormat = FastDateFormat.getInstance(pattern);
            try {
                re = dateFormat.parse(sourceString);
            } catch (ParseException e) {
                return null;
            }
        }

        return re;
    }


    /**
     * 将某指定的字符串转换为型如：yyyy-MM-dd HH:mm:ss的时间
     *
     * @param str 将被转换为Date的字符串
     * @return 转换后的Date
     */
    public static Date getStr2LDate(String str) {
        return parseDateFromString(str, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将某指定的字符串转换为型如：yyyy-MM-dd HH:mm的时间
     *
     * @param str 将被转换为Date的字符串
     * @return 转换后的Date
     */
    public static Date getStr2LDateWithoutSecond(String str) {
        return parseDateFromString(str, "yyyy-MM-dd HH:mm");
    }

    public static Date getStr2LDateByyyyyMMddHHmmss(String str) {
        return parseDateFromString(str, "yyyyMMddHHmmss");
    }

    /**
     * 将某指定的字符串转换为型如：yyyy-MM-dd
     *
     * @param str 将被转换为Date的字符串
     * @return 转换后的Date
     */
    public static Date getStr2SDate(String str) {
        return parseDateFromString(str, "yyyy-MM-dd");
    }

    /**
     * 将某指定的字符串转换为型如：yyyy-M
     *
     * @param str 将被转换为Date的字符串
     * @return 转换后的Date
     */
    public static Date getStr2YearMonth(String str) {
        return parseDateFromString(str, "yyyy-M");
    }

    /**
     * 返回日期加X天后的日期
     *
     * @param date
     * @param i
     * @return
     * @author CPH
     * @date Mar 11, 2012
     */
    public static String addDays(String date, int i) {
        try {
            GregorianCalendar gCal = new GregorianCalendar(
                    Integer.parseInt(date.substring(0, 4)),
                    Integer.parseInt(date.substring(5, 7)) - 1,
                    Integer.parseInt(date.substring(8, 10)));
            gCal.add(GregorianCalendar.DATE, i);
            return sdfLong.format(gCal.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 返回日期加X月后的日期
     *
     * @param date
     * @param i
     * @return
     * @author CPH
     * @date Mar 11, 2012
     */
    public static String addMonths(String date, int i) {
        try {
            GregorianCalendar gCal = new GregorianCalendar(
                    Integer.parseInt(date.substring(0, 4)),
                    Integer.parseInt(date.substring(5, 7)) - 1,
                    Integer.parseInt(date.substring(8, 10)));
            gCal.add(GregorianCalendar.MONTH, i);
            return sdfLong.format(gCal.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 返回月加X月后的日期
     *
     * @param date yyyy年-mm月
     * @param i
     * @return
     * @author CPH
     * @date Mar 11, 2012
     */
    public static String addMonthsYearAndMonths(String date, int i) {
        try {
            if (date != null) {
                String[] dates = date.split("-");
                GregorianCalendar gCal = new GregorianCalendar(
                        Integer.parseInt(dates[0].substring(0, 4)),
                        Integer.parseInt(dates[1].substring(0, 2)) - 1,
                        0);
                gCal.add(GregorianCalendar.MONTH, i);
                return sdfLongMonthCn.format(gCal.getTime());
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回日期加X年后的日期
     *
     * @param date
     * @param i
     * @return
     * @author CPH
     * @date Mar 11, 2012
     */
    public static String addYears(String date, int i) {
        try {
            GregorianCalendar gCal = new GregorianCalendar(
                    Integer.parseInt(date.substring(0, 4)),
                    Integer.parseInt(date.substring(5, 7)) - 1,
                    Integer.parseInt(date.substring(8, 10)));
            gCal.add(GregorianCalendar.YEAR, i);
            return sdfLong.format(gCal.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取基准日的前后偏移日
     *
     * @param count 偏移数量
     * @param date  基准日期
     * @return Date
     */
    public static Date addDays(Date date, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, count);
        return calendar.getTime();
    }

    /**
     * 获取基准日的前后偏移毫秒
     *
     * @param count 偏移数量
     * @param date  基准日期
     * @return Date
     */
    public static Date addMilliseconds(Date date, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MILLISECOND, count);
        return calendar.getTime();
    }


    /**
     * 获取基准日的前后偏移秒
     *
     * @param count 偏移数量
     * @param date  基准日期
     * @return Date
     */
    public static Date addSeconds(Date date, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, count);
        return calendar.getTime();
    }

    public static Date addMinutes(Date date, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, count);
        return calendar.getTime();
    }

    public static Date addHours(Date date, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, count);
        return calendar.getTime();
    }


    /**
     * 获取基准日的前后偏移周
     *
     * @param count 偏移数量
     * @param date  基准周数
     * @return Date
     */
    public static Date addWeeks(Date date, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR, count);
        return calendar.getTime();
    }


    /**
     * 获取基准日的前后偏移月
     *
     * @param count 偏移数量
     * @param date  基准月数
     * @return Date
     */

    public static Date addYears(Date date, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, count);
        return calendar.getTime();
    }

    public static Date addMonths(Date date, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, count);
        return calendar.getTime();
    }

    public static int plusYears(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, i);
        return calendar.get(Calendar.YEAR);
    }

    public static int plusMonths(Date date, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, count);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    //in calendar.get(Calendar.MONTH): return month is base from 0, so natural_month = month_base_zero + 1
    public static int getNaturalMonthNumber(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month_base_zero = calendar.get(Calendar.MONTH);
        return month_base_zero + 1;
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static Date getNowDateAddMinutes(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static String getHHMMDateFormat(Date date) {
        return sdfHm.format(date);
    }

    public static String getHHMMSSDateFormat(Date date) {
        return sdfHms.format(date);
    }


    public static boolean DateEquals(Date left, Date right) {
        Calendar calendarLeft = Calendar.getInstance();
        calendarLeft.setTime(left);

        Calendar calendarRight = Calendar.getInstance();
        calendarRight.setTime(right);
        if (calendarLeft.get(Calendar.YEAR) != calendarRight.get(Calendar.YEAR)) {
            return false;
        }
        if (calendarLeft.get(Calendar.MONTH) != calendarRight.get(Calendar.MONTH)) {
            return false;
        }
        return calendarLeft.get(Calendar.DAY_OF_MONTH) == calendarRight.get(Calendar.DAY_OF_MONTH);
    }

    public static int DateCompare(Date left, Date right) {
        Calendar calendarLeft = Calendar.getInstance();
        calendarLeft.setTime(left);

        Calendar calendarRight = Calendar.getInstance();
        calendarRight.setTime(right);

        return calendarLeft.compareTo(calendarRight);
    }

    public static boolean twoDateEquals(Date left, Date right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return left.equals(right);
    }


    public static int getDaysBetweenDates(Date left, Date right) {
        return getDay(left) - getDay(right);
    }


    public static Date getNowWithYMD() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.getNowDate());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    //in calendar.set(xxx): month is base from 0, so must use :natural_month - 1
    public static Date getDateWithYearMonthDate(int year, int natural_month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, natural_month - 1, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date setDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static Date getStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


    public static String dateDiffDate(Date startTime, Date endTime) {
        //按照传入的格式生成一个simpledateformate对象

        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long nm = 1000 * 60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数
        long diff;
        String difftime = "";

        //获得两个时间的毫秒时间差异
        diff = startTime.getTime() - endTime.getTime();
        long day = diff / nd;//计算差多少天
        long hour = diff % nd / nh;//计算差多少小时
        long min = diff % nd % nh / nm;//计算差多少分钟
        long sec = diff % nd % nh % nm / ns;//计算差多少秒
        //输出结果
        difftime = day + "天" + hour + "小时" + min + "分钟" + sec + "秒";


        return difftime;
    }

    public static String dateDiffDateMin(Date startTime, Date endTime) {
        //按照传入的格式生成一个simpledateformate对象

        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long nm = 1000 * 60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数
        long diff;
        String difftime = "";

        //获得两个时间的毫秒时间差异
        diff = startTime.getTime() - endTime.getTime();
        long day = diff / nd;//计算差多少天
        long hour = diff % nd / nh;//计算差多少小时
        long min = diff % nd % nh / nm;//计算差多少分钟
        long sec = diff % nd % nh % nm / ns;//计算差多少秒

        //输出结果
        if (day > 0) {
            difftime += day + " 天 ";
        }
        if (hour > 0) {
            difftime += hour + " 小时 ";
        }
        if (min > 0) {
            difftime += min + " 分 ";
        }

        return difftime;
    }

    /**
     * format the data as 'yyyy年MM月dd日'
     *
     * @param dt
     * @return
     */
    public static String toChineseDateString(Date dt) {
        if (dt == null) return "";
        return sdfLongCn.format(dt);
    }

    /**
     * 是否合法日期
     *
     * @param sDate
     * @return
     */
    public static boolean isValidDate(String sDate) {
        try {
            sdfLongTimePlus.parse(sDate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Date getLongTimePlusDate(String sDate) {
        try {
            return sdfLongTimePlus.parse(sDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getLongTimeDate(String sDate) throws ParseException {
        return sdfLongTime.parse(sDate);
    }

    public static Date getXlsTimePlusDate(String sDate) throws ParseException {
        return sdfXlsTimePlus.parse(sDate);
    }

    /**
     * judge the date whether between start and end with YMD
     *
     * @param judgeTime
     * @param start
     * @param end
     * @return
     */
    public static int isBetweenDuration(Date judgeTime,
                                        Date start, Date end) {
        Date startTime = getDateWithYearMonthDate(getYear(start), getMonth(start), getDay(start));
        Date endTime = getDateWithYearMonthDate(getYear(end), getMonth(end), getDay(end));
        Date formatJudgeTime = getDateWithYearMonthDate(getYear(judgeTime), getMonth(judgeTime), getDay(judgeTime));
        if (formatJudgeTime.compareTo(startTime) >= 0 && formatJudgeTime.compareTo(endTime) <= 0) {
            return 0;
        }
        if (formatJudgeTime.compareTo(startTime) < 0) {
            return -1;
        }
        if (formatJudgeTime.compareTo(endTime) > 0) {
            return 1;
        }
        return 0;
    }

    public static Date getYMDDate(Date date) {
        return getDateWithYearMonthDate(getYear(date), getMonth(date), getDay(date));
    }

    public static LocalDate offset(LocalDate asOfDate, Duration duration) {
        return asOfDate.plusDays(duration.getDays()).plusMonths(duration.getMonths()).plusYears(duration.getYears());
    }


    /**
     * @param dateString
     * @return
     */
    public static Date parseyyyyMMddHHmmssTimeToDate(String dateString) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.valueOf(dateString.substring(0, 4)), Integer.valueOf(dateString.substring(5, 6)), Integer.valueOf(dateString.substring(7, 8)), Integer.valueOf(dateString.substring(9, 10)), Integer.valueOf(dateString.substring(11, 12)), Integer.valueOf(dateString.substring(13, 14)));
        return calendar.getTime();
    }

    public static String getString(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    //得到指定日期的每一个小时
    public static List<String> getHoursTimeOfDate(DateTime date) {
        final ImmutableList.Builder<String> hourTimeList = ImmutableList.builder();

        DateTime firstHourTime = date.withTimeAtStartOfDay();
        final DateTime nextDayFirstHourTime = firstHourTime.plusDays(1);
        while (firstHourTime.isBefore(nextDayFirstHourTime)) {
            hourTimeList.add(firstHourTime.toLocalDateTime().toString("yyyy-MM-dd HH:mm:ss"));
            firstHourTime = firstHourTime.plusHours(1);
        }
        return hourTimeList.build();

    }

    //得到指定月的每一天
    public static List<String> getDaysOfMonth(DateTime date) {
        final ImmutableList.Builder<String> dayList = ImmutableList.builder();

        LocalDate firstDay = date.toLocalDate().withDayOfMonth(1);
        final LocalDate nextMonthFirstDay = firstDay.plusMonths(1);
        while (firstDay.isBefore(nextMonthFirstDay)) {
            dayList.add(firstDay.toString("yyyy-MM-dd"));
            firstDay = firstDay.plusDays(1);
        }
        return dayList.build();
    }


    //得到指定日期范围内的的每一天
    public static List<String> getEachDayBewteen2Date(DateTime start, DateTime end) {
        final ImmutableList.Builder<String> dayList = ImmutableList.builder();
        LocalDate endBoundry = end.toLocalDate().plusDays(1);
        LocalDate startDate = start.toLocalDate();
        while (startDate.isBefore(endBoundry)) {
            dayList.add(startDate.toString("yyyy-MM-dd"));
            startDate = startDate.plusDays(1);
        }
        return dayList.build();
    }


    public static List<String> getEachDayBewteen2DateDate(LocalDate start, LocalDate end) {
        final ImmutableList.Builder<String> dayList = ImmutableList.builder();
        LocalDate startDate = start;
        while (startDate.isBefore(end)) {
            dayList.add(startDate.toString("yyyy-MM-dd"));
            startDate = startDate.plusDays(1);
        }
        return dayList.build();
    }


    //得到指定日期范围内的的每一天
    public static List<String> getEachMonthBewteen2Date(DateTime start, DateTime end) {
        final ImmutableList.Builder<String> dayList = ImmutableList.builder();
        LocalDate endBoundry = end.toLocalDate().plusMonths(1);
        LocalDate startDate = start.toLocalDate();
        while (startDate.isBefore(endBoundry)) {
            dayList.add(startDate.toString("yyyy-MM"));
            startDate = startDate.plusMonths(1);
        }
        return dayList.build();
    }


    //得到指定年的每一月
    public static List<String> getMonthsOfYear(DateTime date) {
        final ImmutableList.Builder<String> monthList = ImmutableList.builder();

        LocalDate firstMonth = date.toLocalDate().withDayOfMonth(1).withMonthOfYear(1);
        final LocalDate nextYearFirstMonth = firstMonth.plusYears(1);
        while (firstMonth.isBefore(nextYearFirstMonth)) {
            monthList.add(firstMonth.toString("yyyy-MM"));
            firstMonth = firstMonth.plusMonths(1);
        }
        return monthList.build();
    }

    public static List<String> getDaysOfYear(DateTime date) {
        int dayOfYear = date.dayOfYear().getMaximumValue();
        List<String> days = Lists.newArrayListWithExpectedSize(dayOfYear);
        for (int i = 1; i <= dayOfYear; i++) {
            days.add(date.withDayOfYear(i).toLocalDate().toString());
        }
        return days;
    }

    public static void main(String[] args) {

        Years years = Years.parseYears("2018");
        System.out.println(years);


//        List<String> hoursTimeOfDate = getHoursTimeOfDate(now);
//        hoursTimeOfDate.forEach(new Consumer<String>() {
//            @Override
//            public void accept(String aLong) {
//                System.out.println(aLong);
//            }
//        });
//        List<String> daysOfMonth = getDaysOfMonth(now);
//        daysOfMonth.forEach(new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s);
//            }
//        });

//        List<String> monthsOfYear = getMonthsOfYear(now);
//        monthsOfYear.forEach(new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s);
//            }
//        });

    }
}
