package com.vn.myhome.untils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/2/16
 * Time: 6:07 PM
 * Desc: TimeUtils
 */
public class TimeUtils {

    /**
     * Parse the time in milliseconds into String with the format: hh:mm:ss or mm:ss
     *
     * @param duration The time needs to be parsed.
     */
    @SuppressLint("DefaultLocale")
    public static String formatDuration(int duration) {
        duration /= 1000; // milliseconds into seconds
        int minute = duration / 60;
        int hour = minute / 60;
        minute %= 60;
        int second = duration % 60;
        if (hour != 0)
            return String.format("%2d giờ:%02d:%02d", hour, minute, second);
        else
            return String.format("%02d:%02d", minute, second);
    }

    public static String formatTime_lambai(int duration) {
        duration /= 1000; // milliseconds into seconds
        int minute = duration / 60;
        int hour = minute / 60;
        minute %= 60;
        int second = duration % 60;
        if (hour != 0)
            return String.format("%2d giờ %02d phút %02d giây", hour, minute, second);
        else
            return String.format("%02d phút %02d giây", minute, second);
    }

    public static String convent_date(String sDateinput, String fomatDateinput, String fomatDateoutput) {
        String strDateTime = "";
        DateFormat inputFormatter = new SimpleDateFormat(fomatDateinput);
        Date da = null;
        if (sDateinput==null||sDateinput.length()==0)
            return strDateTime;
        try {
            inputFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            da = (Date) inputFormatter.parse(sDateinput);
            DateFormat outputFormatter = new SimpleDateFormat(fomatDateoutput);
            strDateTime = outputFormatter.format(da);

            return strDateTime;
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return strDateTime;
    }

    public static String CompareDates(String date1, String date2) {
        String isTrue = "";
        try {
            String pattern = "EEEE dd-MMM-yyyy";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date Date1 = formatter.parse(date1);
            Date Date2 = formatter.parse(date2);
            if (Date1 != null && Date2 != null) {
                if (Date1.equals(Date2)) {
                    isTrue = "1";
                } else if (Date1.before(Date2)) {
                    isTrue = "3";
                } else {
                    isTrue = "2";
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return isTrue;
    }

    public static boolean check_discount(Date date_current, String date_start, String date_end) {
        boolean iCheck = false;
        try {
            String pattern = "EEEE dd-MMM-yyyy";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date Date1_start = formatter.parse(date_start);
            Date Date2_end = formatter.parse(date_end);
            if (date_current != null && Date1_start != null && Date2_end != null) {
                if (date_current.equals(Date1_start)) {
                    return true;
                }
                if (date_current.equals(Date2_end)) {
                    return true;
                }
                if (Date1_start.before(date_current) && date_current.before(Date2_end)) {
                    return true;
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return iCheck;
    }

    public static String CompareDates_Two(String date1, String date2) {
        String isTrue = "";
        try {
            String pattern = "EEEE dd-MMM-yyyy";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            String pattern_date2 = "dd/MM/yyyy";
            SimpleDateFormat formatter_date2 = new SimpleDateFormat(pattern_date2);
            Date Date1 = formatter.parse(date1);
            Date Date2 = formatter_date2.parse(date2);
            if (Date1 != null && Date2 != null) {
                if (Date1.equals(Date2)) {
                    isTrue = "1";
                } else if (Date1.before(Date2)) {
                    isTrue = "3";
                } else {
                    isTrue = "2";
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return isTrue;
    }

    public static boolean CompareDates_Three(String dateStart, String dateEnd, String current) {
        boolean isTrue = false;
        try {
            String pattern = "EEEE dd-MMM-yyyy";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            String pattern_date2 = "dd/MM/yyyy";
            SimpleDateFormat formatter_date2 = new SimpleDateFormat(pattern_date2);
            Date DateStart = formatter_date2.parse(dateStart);
            Date DateEnd = formatter_date2.parse(dateEnd);
            Date DateCurrent = formatter.parse(current);
            if (DateCurrent.before(DateEnd) && DateCurrent.after(DateStart)) {
                isTrue = true;
                return isTrue;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return isTrue;
    }

    public static boolean CompareDates_Three_Tab_Lich(String dateStart, String dateEnd, String current) {
        boolean isTrue = false;
        try {
            String pattern = "EEEE dd-MMM-yyyy";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            String pattern_date2 = "dd/MM/yyyy";
            SimpleDateFormat formatter_date2 = new SimpleDateFormat(pattern_date2);
            Date DateStart = formatter.parse(dateStart);
            Date DateEnd = formatter.parse(dateEnd);
            Date DateCurrent = formatter.parse(current);
            if (DateCurrent.before(DateEnd) && DateCurrent.after(DateStart)) {
                isTrue = true;
                return isTrue;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return isTrue;
    }

    public static String CompareDates_3(String date1, String date2) {
        String isTrue = "";
        try {
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            String pattern_date2 = "dd/MM/yyyy";
            SimpleDateFormat formatter_date2 = new SimpleDateFormat(pattern_date2);
            Date Date1 = formatter.parse(date1);
            Date Date2 = formatter_date2.parse(date2);
            if (Date1 != null && Date2 != null) {
                if (Date1.equals(Date2)) {
                    isTrue = "1";
                } else if (Date1.before(Date2)) {
                    isTrue = "3";
                } else {
                    isTrue = "2";
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return isTrue;
    }

    public static List<Date> get_list_date(String sDateStart, String sDateEnd) {
        List<Date> dates = new ArrayList<Date>();
        DateFormat formatter;
        formatter = new SimpleDateFormat("EEEE dd-MMM-yyyy");
        Date startDate = null;
        try {
            startDate = (Date) formatter.parse(sDateStart);
            Date endDate = (Date) formatter.parse(sDateEnd);
            long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
            long endTime = endDate.getTime(); // create your endtime here, possibly using Calendar or Date
            long curTime = startDate.getTime();
            while (curTime <= endTime) {
                dates.add(new Date(curTime));
                curTime += interval;
            }
            for (int i = 0; i < dates.size(); i++) {
                Date lDate = (Date) dates.get(i);
                String ds = formatter.format(lDate);
                System.out.println(" Date is ..." + ds);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }

    public static String get_string_week_day(String sDay) {
        Date d1 = null;
        SimpleDateFormat format = new SimpleDateFormat("EEEE dd-MMM-yyyy");
        try {
            d1 = format.parse(sDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        String weekDay = "";
        //  int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            weekDay = "monday";
        } else if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            weekDay = "tuesday";
        } else if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            weekDay = "wednesday";
        } else if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            weekDay = "thursday";
        } else if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            weekDay = "friday";
        } else if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            weekDay = "saturday";
        } else if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            weekDay = "sunday";
        }
        return weekDay;
    }

    public static boolean check_weekend(Date sDay) {
        boolean isWeekend = false;
        Calendar c1 = Calendar.getInstance();
        c1.setTime(sDay);
        String weekDay = "";
        //  int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            weekDay = "monday";
        } else if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            weekDay = "tuesday";
        } else if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            weekDay = "wednesday";
        } else if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            weekDay = "thursday";
        } else if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            weekDay = "friday";
            isWeekend = true;
        } else if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            weekDay = "saturday";
            isWeekend = true;
        } else if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            weekDay = "sunday";
        }
        return isWeekend;
    }

    public static String StringTimetoDate(Date sDateinput, String fomatDateoutput) {
        String strDateTime = "";
        Date da = null;
        try {
            DateFormat outputFormatter = new SimpleDateFormat(fomatDateoutput);
            strDateTime = outputFormatter.format(sDateinput);
            return strDateTime;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return strDateTime;
    }

    public static boolean compare_date_time(String sDateinput, String fomatDateinput) {
        long long_date1 = 0;
        long long_date2 = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fomatDateinput);
            Date date_1 = sdf.parse(sDateinput);
            long_date1 = date_1.getTime();
            // convent date 2
            long_date2 = System.currentTimeMillis();
            Date date = new Date(long_date2);
            SimpleDateFormat df2 = new SimpleDateFormat(fomatDateinput);
            String da = df2.format(date);
            //
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = long_date2 - long_date1;
        long hour = TimeUnit.HOURS.convert(time, TimeUnit.MILLISECONDS);
        if (hour > 24) {
            return true;
        } else return false;
    }

    public static boolean compare_two_date(String inputDate, String outputDate,
                                           String inputDateFormat, String outputDateFormat) {
        try {
            Date current = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat(inputDateFormat);
            Date date_1 = sdf.parse(inputDate);
            // convent date 2
            SimpleDateFormat sdf2 = new SimpleDateFormat(outputDateFormat);
            Date date_2 = sdf2.parse(outputDate);
            //long_date2 = System.currentTimeMillis();
            if (current.getTime() >= date_1.getTime())
                return false;
            if (date_2.getTime() >= date_1.getTime()) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean compare_date_current(String inputDate,
                                               String inputDateFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(inputDateFormat);
            Date date_1 = sdf.parse(inputDate);
            // convent date 2
            if (System.currentTimeMillis() < date_1.getTime()) {
                return false;
            } else return true;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}
