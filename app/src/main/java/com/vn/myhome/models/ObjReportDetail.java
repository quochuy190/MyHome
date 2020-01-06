package com.vn.myhome.models;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 19-November-2019
 * Time: 15:48
 * Version: 1.0
 */
public class ObjReportDetail {
    String ID;
    String ROOM_NAME;
    String ID_SERVICE;
    String BOOK_NAME;
    String START_TIME;
    String END_TIME;
    String BILLING_STATUS;
    String BILLING_STATUS_NAME;
    String BOOK_STATUS;
    String BOOK_PRICE;
    String BOOKING_TIME;
    String CONTENT;
    String GENLINK;
    String COVER;
    String USER_TYPE;
    String REVENUE;
    String SELL_COSTS;
    String SERVICE_COSTS;
    String OTHER_COSTS;

    public ObjReportDetail(String ROOM_NAME, String GENLINK) {
        this.ROOM_NAME = ROOM_NAME;
        this.GENLINK = GENLINK;
    }

    public String getBOOK_PRICE() {
        return BOOK_PRICE;
    }

    public void setBOOK_PRICE(String BOOK_PRICE) {
        this.BOOK_PRICE = BOOK_PRICE;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getROOM_NAME() {
        return ROOM_NAME;
    }

    public void setROOM_NAME(String ROOM_NAME) {
        this.ROOM_NAME = ROOM_NAME;
    }

    public String getID_SERVICE() {
        return ID_SERVICE;
    }

    public void setID_SERVICE(String ID_SERVICE) {
        this.ID_SERVICE = ID_SERVICE;
    }

    public String getBOOK_NAME() {
        return BOOK_NAME;
    }

    public void setBOOK_NAME(String BOOK_NAME) {
        this.BOOK_NAME = BOOK_NAME;
    }

    public String getSTART_TIME() {
        return START_TIME;
    }

    public void setSTART_TIME(String START_TIME) {
        this.START_TIME = START_TIME;
    }

    public String getEND_TIME() {
        return END_TIME;
    }

    public void setEND_TIME(String END_TIME) {
        this.END_TIME = END_TIME;
    }

    public String getBILLING_STATUS() {
        return BILLING_STATUS;
    }

    public void setBILLING_STATUS(String BILLING_STATUS) {
        this.BILLING_STATUS = BILLING_STATUS;
    }

    public String getBILLING_STATUS_NAME() {
        return BILLING_STATUS_NAME;
    }

    public void setBILLING_STATUS_NAME(String BILLING_STATUS_NAME) {
        this.BILLING_STATUS_NAME = BILLING_STATUS_NAME;
    }

    public String getBOOK_STATUS() {
        return BOOK_STATUS;
    }

    public void setBOOK_STATUS(String BOOK_STATUS) {
        this.BOOK_STATUS = BOOK_STATUS;
    }

    public String getBOOKING_TIME() {
        return BOOKING_TIME;
    }

    public void setBOOKING_TIME(String BOOKING_TIME) {
        this.BOOKING_TIME = BOOKING_TIME;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getGENLINK() {
        return GENLINK;
    }

    public void setGENLINK(String GENLINK) {
        this.GENLINK = GENLINK;
    }

    public String getCOVER() {
        return COVER;
    }

    public void setCOVER(String COVER) {
        this.COVER = COVER;
    }

    public String getUSER_TYPE() {
        return USER_TYPE;
    }

    public void setUSER_TYPE(String USER_TYPE) {
        this.USER_TYPE = USER_TYPE;
    }

    public String getREVENUE() {
        return REVENUE;
    }

    public void setREVENUE(String REVENUE) {
        this.REVENUE = REVENUE;
    }

    public String getSELL_COSTS() {
        return SELL_COSTS;
    }

    public void setSELL_COSTS(String SELL_COSTS) {
        this.SELL_COSTS = SELL_COSTS;
    }

    public String getSERVICE_COSTS() {
        return SERVICE_COSTS;
    }

    public void setSERVICE_COSTS(String SERVICE_COSTS) {
        this.SERVICE_COSTS = SERVICE_COSTS;
    }

    public String getOTHER_COSTS() {
        return OTHER_COSTS;
    }

    public void setOTHER_COSTS(String OTHER_COSTS) {
        this.OTHER_COSTS = OTHER_COSTS;
    }
}
