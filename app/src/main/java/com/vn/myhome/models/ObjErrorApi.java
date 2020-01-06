package com.vn.myhome.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 05-August-2019
 * Time: 15:34
 * Version: 1.0
 */
public class ObjErrorApi {
    @SerializedName("ERROR")
    String ERROR;
    @SerializedName("MESSAGE")
    String MESSAGE;
    @SerializedName("RESULT")
    String RESULT;
    @SerializedName("GENLINK")
    String GENLINK;
    @SerializedName("CODE_ACTIVE")
    String CODE_ACTIVE;
    @SerializedName("IDROOM")
    String IDROOM;
    @SerializedName("CONTENT")
    String CONTENT;
    @SerializedName("STATUS")
    String STATUS;
    @SerializedName("PRICE")
    String PRICE;

    public String getPRICE() {
        return PRICE;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
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

    public String getIDROOM() {
        return IDROOM;
    }

    public void setIDROOM(String IDROOM) {
        this.IDROOM = IDROOM;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public String getCODE_ACTIVE() {
        return CODE_ACTIVE;
    }

    public void setCODE_ACTIVE(String CODE_ACTIVE) {
        this.CODE_ACTIVE = CODE_ACTIVE;
    }
}
