package com.vn.myhome.models;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 14-October-2019
 * Time: 11:05
 * Version: 1.0
 */
public class ObjBooking {
    String ID;
    String ROOM_NAME;
    String USERID;
    String ID_SERVICE;
    String BOOK_NAME;
    String START_TIME;
    String END_TIME;
    String BILLING_STATUS;
    String BILLING_STATUS_NAME;
    String BOOK_STATUS;
    String BOOK_STATUS_NAME;
    String BOOKING_TIME;
    String CONTENT;
    String GENLINK;
    String USER_TYPE;
    String IS_BOOK_SERVICES;
    String ID_BOOK_SERVICES;
    String STATUS_SERVICE;
    String BILLING_STATUS_SERVICE; //0 là chờ thanh toán , 1 là đã tt
    String MONEY_SERVICES;
    String CONTENT_SERVICES;
    String BOOKING_PRICE;
    String KIND_OF_PAID;

    public String getKIND_OF_PAID() {
        return KIND_OF_PAID;
    }

    public void setKIND_OF_PAID(String KIND_OF_PAID) {
        this.KIND_OF_PAID = KIND_OF_PAID;
    }

    public String getBOOKING_PRICE() {
        return BOOKING_PRICE;
    }

    public void setBOOKING_PRICE(String BOOKING_PRICE) {
        this.BOOKING_PRICE = BOOKING_PRICE;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
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

    public String getIS_BOOK_SERVICES() {
        return IS_BOOK_SERVICES;
    }

    public void setIS_BOOK_SERVICES(String IS_BOOK_SERVICES) {
        this.IS_BOOK_SERVICES = IS_BOOK_SERVICES;
    }

    public String getID_BOOK_SERVICES() {
        return ID_BOOK_SERVICES;
    }

    public void setID_BOOK_SERVICES(String ID_BOOK_SERVICES) {
        this.ID_BOOK_SERVICES = ID_BOOK_SERVICES;
    }

    public String getSTATUS_SERVICE() {
        return STATUS_SERVICE;
    }

    public void setSTATUS_SERVICE(String STATUS_SERVICE) {
        this.STATUS_SERVICE = STATUS_SERVICE;
    }

    public String getBILLING_STATUS_SERVICE() {
        return BILLING_STATUS_SERVICE;
    }

    public void setBILLING_STATUS_SERVICE(String BILLING_STATUS_SERVICE) {
        this.BILLING_STATUS_SERVICE = BILLING_STATUS_SERVICE;
    }

    public String getMONEY_SERVICES() {
        return MONEY_SERVICES;
    }

    public void setMONEY_SERVICES(String MONEY_SERVICES) {
        this.MONEY_SERVICES = MONEY_SERVICES;
    }

    public String getCONTENT_SERVICES() {
        return CONTENT_SERVICES;
    }

    public void setCONTENT_SERVICES(String CONTENT_SERVICES) {
        this.CONTENT_SERVICES = CONTENT_SERVICES;
    }

    public void setBOOK_STATUS(String BOOK_STATUS) {
        this.BOOK_STATUS = BOOK_STATUS;
    }

    public String getBOOK_STATUS_NAME() {
        return BOOK_STATUS_NAME;
    }

    public void setBOOK_STATUS_NAME(String BOOK_STATUS_NAME) {
        this.BOOK_STATUS_NAME = BOOK_STATUS_NAME;
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

    public String getUSER_TYPE() {
        return USER_TYPE;
    }

    public void setUSER_TYPE(String USER_TYPE) {
        this.USER_TYPE = USER_TYPE;
    }
}
