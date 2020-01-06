package com.vn.myhome.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 12-October-2019
 * Time: 08:48
 * Version: 1.0
 */
public class ObjDayCustom {
    String sDay;
    boolean isHide;
    boolean isBooking;
    int iDayofMonth;
    boolean isWeekend;
    String sPrice;
    String sPriceWeekend;
    String sStartClick;
    String sEndClick;
    boolean isBooked;
    String sStartBooking;
    String sEndBooking;
    List<ObjBooking> mListBooking;
    String sWeekDay;
    String BILLING_STATUS;
    String BOOK_STATUS;
    String BOOK_NAME;
    String CONTENT;
    String ID_BOOKING;
    String USERID;
    String GENLINK;
    String BOOK_STATUS_NAME;
    String BILLING_STATUS_NAME;
    String CONTENT_SERVICES;
    String MONEY_SERVICES;
    String BILLING_STATUS_SERVICE;
    String STATUS_SERVICE;
    String ID_BOOK_SERVICES;
    String IS_BOOK_SERVICES;
    String USER_TYPE;

    public ObjDayCustom(String sDay, int iDayofMonth, boolean isWeekend, String sPrice, String sPriceWeekend) {
        this.sDay = sDay;
        this.iDayofMonth = iDayofMonth;
        this.isWeekend = isWeekend;
        this.sPrice = sPrice;
        this.sPriceWeekend = sPriceWeekend;
        mListBooking = new ArrayList<>();
    }

    public ObjDayCustom(String sDay, int iDayofMonth, boolean isWeekend, String sPrice, String sPriceWeekend, String sWeekDay) {
        this.sDay = sDay;
        this.iDayofMonth = iDayofMonth;
        this.isWeekend = isWeekend;
        this.sPrice = sPrice;
        this.sPriceWeekend = sPriceWeekend;
        this.sWeekDay = sWeekDay;
        mListBooking = new ArrayList<>();
    }

    public String getBOOK_STATUS_NAME() {
        return BOOK_STATUS_NAME;
    }

    public void setBOOK_STATUS_NAME(String BOOK_STATUS_NAME) {
        this.BOOK_STATUS_NAME = BOOK_STATUS_NAME;
    }

    public String getBILLING_STATUS_NAME() {
        return BILLING_STATUS_NAME;
    }

    public void setBILLING_STATUS_NAME(String BILLING_STATUS_NAME) {
        this.BILLING_STATUS_NAME = BILLING_STATUS_NAME;
    }

    public String getCONTENT_SERVICES() {
        return CONTENT_SERVICES;
    }

    public void setCONTENT_SERVICES(String CONTENT_SERVICES) {
        this.CONTENT_SERVICES = CONTENT_SERVICES;
    }

    public String getMONEY_SERVICES() {
        return MONEY_SERVICES;
    }

    public void setMONEY_SERVICES(String MONEY_SERVICES) {
        this.MONEY_SERVICES = MONEY_SERVICES;
    }

    public String getBILLING_STATUS_SERVICE() {
        return BILLING_STATUS_SERVICE;
    }

    public void setBILLING_STATUS_SERVICE(String BILLING_STATUS_SERVICE) {
        this.BILLING_STATUS_SERVICE = BILLING_STATUS_SERVICE;
    }

    public String getSTATUS_SERVICE() {
        return STATUS_SERVICE;
    }

    public void setSTATUS_SERVICE(String STATUS_SERVICE) {
        this.STATUS_SERVICE = STATUS_SERVICE;
    }

    public String getID_BOOK_SERVICES() {
        return ID_BOOK_SERVICES;
    }

    public void setID_BOOK_SERVICES(String ID_BOOK_SERVICES) {
        this.ID_BOOK_SERVICES = ID_BOOK_SERVICES;
    }

    public String getIS_BOOK_SERVICES() {
        return IS_BOOK_SERVICES;
    }

    public void setIS_BOOK_SERVICES(String IS_BOOK_SERVICES) {
        this.IS_BOOK_SERVICES = IS_BOOK_SERVICES;
    }

    public String getUSER_TYPE() {
        return USER_TYPE;
    }

    public void setUSER_TYPE(String USER_TYPE) {
        this.USER_TYPE = USER_TYPE;
    }

    public String getGENLINK() {
        return GENLINK;
    }

    public void setGENLINK(String GENLINK) {
        this.GENLINK = GENLINK;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getID_BOOKING() {
        return ID_BOOKING;
    }

    public void setID_BOOKING(String ID_BOOKING) {
        this.ID_BOOKING = ID_BOOKING;
    }

    public String getBOOK_NAME() {
        return BOOK_NAME;
    }

    public void setBOOK_NAME(String BOOK_NAME) {
        this.BOOK_NAME = BOOK_NAME;
    }

    public String getBILLING_STATUS() {
        return BILLING_STATUS;
    }

    public void setBILLING_STATUS(String BILLING_STATUS) {
        this.BILLING_STATUS = BILLING_STATUS;
    }

    public String getBOOK_STATUS() {
        return BOOK_STATUS;
    }

    public void setBOOK_STATUS(String BOOK_STATUS) {
        this.BOOK_STATUS = BOOK_STATUS;
    }

    public String getsWeekDay() {
        return sWeekDay;
    }

    public void setsWeekDay(String sWeekDay) {
        this.sWeekDay = sWeekDay;
    }

    public String getsStartBooking() {
        return sStartBooking;
    }

    public void setsStartBooking(String sStartBooking) {
        this.sStartBooking = sStartBooking;
    }

    public String getsEndBooking() {
        return sEndBooking;
    }

    public void setsEndBooking(String sEndBooking) {
        this.sEndBooking = sEndBooking;
    }

    public void setmListBooking(List<ObjBooking> mListBooking) {
        this.mListBooking = mListBooking;
    }

    public String getsStartClick() {
        return sStartClick;
    }

    public List<ObjBooking> getmListBooking() {
        return mListBooking;
    }

    public void setsStartClick(String sStartClick) {
        this.sStartClick = sStartClick;
    }

    public String getsEndClick() {
        return sEndClick;
    }

    public void setsEndClick(String sEndClick) {
        this.sEndClick = sEndClick;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public String getsDay() {
        return sDay;
    }

    public void setsDay(String sDay) {
        this.sDay = sDay;
    }

    public int getiDayofMonth() {
        return iDayofMonth;
    }

    public void setiDayofMonth(int iDayofMonth) {
        this.iDayofMonth = iDayofMonth;
    }

    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }

    public boolean isBooking() {
        return isBooking;
    }

    public void setBooking(boolean booking) {
        isBooking = booking;
    }

    public boolean isWeekend() {
        return isWeekend;
    }

    public void setWeekend(boolean weekend) {
        isWeekend = weekend;
    }

    public String getsPrice() {
        return sPrice;
    }

    public void setsPrice(String sPrice) {
        this.sPrice = sPrice;
    }

    public String getsPriceWeekend() {
        return sPriceWeekend;
    }

    public void setsPriceWeekend(String sPriceWeekend) {
        this.sPriceWeekend = sPriceWeekend;
    }

    public void setData(List<ObjBooking> data) {
        mListBooking = new ArrayList<>();
        if (mListBooking != data) {
            mListBooking = data;
        }
    }
}
