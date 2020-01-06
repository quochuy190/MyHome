package com.vn.myhome.models;

import java.io.Serializable;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 20-November-2019
 * Time: 18:16
 * Version: 1.0
 */
public class ObjNotify implements Serializable {
    String ID;
    String USERID;
    String STATE;
    String TYPES;
    String UPDATE_TIME;
    String CONTENT;
    String SENT_TIME;
    String IS_READ;
    String TAB;
    String SUB_TAB;
    String TONG;
    String TITLE;
    String LINE_NUMBER;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getTYPES() {
        return TYPES;
    }

    public void setTYPES(String TYPES) {
        this.TYPES = TYPES;
    }

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getSENT_TIME() {
        return SENT_TIME;
    }

    public void setSENT_TIME(String SENT_TIME) {
        this.SENT_TIME = SENT_TIME;
    }

    public String getIS_READ() {
        return IS_READ;
    }

    public void setIS_READ(String IS_READ) {
        this.IS_READ = IS_READ;
    }

    public String getTAB() {
        return TAB;
    }

    public void setTAB(String TAB) {
        this.TAB = TAB;
    }

    public String getSUB_TAB() {
        return SUB_TAB;
    }

    public void setSUB_TAB(String SUB_TAB) {
        this.SUB_TAB = SUB_TAB;
    }

    public String getTONG() {
        return TONG;
    }

    public void setTONG(String TONG) {
        this.TONG = TONG;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getLINE_NUMBER() {
        return LINE_NUMBER;
    }

    public void setLINE_NUMBER(String LINE_NUMBER) {
        this.LINE_NUMBER = LINE_NUMBER;
    }
}
