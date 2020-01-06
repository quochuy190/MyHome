package com.vn.myhome.models;

import java.util.List;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 11-October-2019
 * Time: 23:19
 * Version: 1.0
 */
public class ObjCalendar {
    String sMonth;
    String sYear;
    List<ObjDayCustom> mLisday;

    public ObjCalendar(String sMonth, String sYear, List<ObjDayCustom> mLisday) {
        this.sMonth = sMonth;
        this.sYear = sYear;
        this.mLisday = mLisday;
    }

    public String getsMonth() {
        return sMonth;
    }

    public void setsMonth(String sMonth) {
        this.sMonth = sMonth;
    }

    public String getsYear() {
        return sYear;
    }

    public void setsYear(String sYear) {
        this.sYear = sYear;
    }

    public List<ObjDayCustom> getmLisday() {
        return mLisday;
    }

    public void setmLisday(List<ObjDayCustom> mLisday) {
        this.mLisday = mLisday;
    }
}
