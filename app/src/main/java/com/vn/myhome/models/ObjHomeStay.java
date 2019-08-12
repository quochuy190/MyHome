package com.vn.myhome.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 11-August-2019
 * Time: 15:12
 * Version: 1.0
 */
public class ObjHomeStay implements Serializable {
    @SerializedName("ERROR")
    String ERROR;
    @SerializedName("MESSAGE")
    String MESSAGE;
    @SerializedName("RESULT")
    String RESULT;
    @SerializedName("ID")
    String ID;
    @SerializedName("NAME")
    String NAME;
    @SerializedName("DESCRIPTION")
    String DESCRIPTION;
    @SerializedName("PRICE")
    String PRICE;
    @SerializedName("ADDRESS")
    String ADDRESS;
    @SerializedName("ID_HOST")
    String ID_HOST;
    @SerializedName("INFOMATION")
    String INFOMATION;
    @SerializedName("UPDATE_TIME")
    String UPDATE_TIME;
    @SerializedName("CREATE_DATE")
    String CREATE_DATE;
    @SerializedName("USER_EDIT")
    String USER_EDIT;
    @SerializedName("MAX_GUEST")
    String MAX_GUEST;
    @SerializedName("MAX_ROOM")
    String MAX_ROOM;
    @SerializedName("MAX_BED")
    String MAX_BED;
    @SerializedName("PRICE_SPECIAL")
    String PRICE_SPECIAL;
    @SerializedName("PRICE_EXTRA")
    String PRICE_EXTRA;
    @SerializedName("CLEAN_ROOM")
    String CLEAN_ROOM;
    @SerializedName("GENLINK")
    String GENLINK;
    @SerializedName("POLICY_CANCLE")
    String POLICY_CANCLE;
    @SerializedName("COVER")
    String COVER;
    @SerializedName("IMG")
    String IMG;
    @SerializedName("AVATAR")
    String AVATAR;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getPRICE() {
        return PRICE;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getID_HOST() {
        return ID_HOST;
    }

    public void setID_HOST(String ID_HOST) {
        this.ID_HOST = ID_HOST;
    }

    public String getINFOMATION() {
        return INFOMATION;
    }

    public void setINFOMATION(String INFOMATION) {
        this.INFOMATION = INFOMATION;
    }

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getUSER_EDIT() {
        return USER_EDIT;
    }

    public void setUSER_EDIT(String USER_EDIT) {
        this.USER_EDIT = USER_EDIT;
    }

    public String getMAX_GUEST() {
        return MAX_GUEST;
    }

    public void setMAX_GUEST(String MAX_GUEST) {
        this.MAX_GUEST = MAX_GUEST;
    }

    public String getMAX_ROOM() {
        return MAX_ROOM;
    }

    public void setMAX_ROOM(String MAX_ROOM) {
        this.MAX_ROOM = MAX_ROOM;
    }

    public String getMAX_BED() {
        return MAX_BED;
    }

    public void setMAX_BED(String MAX_BED) {
        this.MAX_BED = MAX_BED;
    }

    public String getPRICE_SPECIAL() {
        return PRICE_SPECIAL;
    }

    public void setPRICE_SPECIAL(String PRICE_SPECIAL) {
        this.PRICE_SPECIAL = PRICE_SPECIAL;
    }

    public String getPRICE_EXTRA() {
        return PRICE_EXTRA;
    }

    public void setPRICE_EXTRA(String PRICE_EXTRA) {
        this.PRICE_EXTRA = PRICE_EXTRA;
    }

    public String getCLEAN_ROOM() {
        return CLEAN_ROOM;
    }

    public void setCLEAN_ROOM(String CLEAN_ROOM) {
        this.CLEAN_ROOM = CLEAN_ROOM;
    }

    public String getGENLINK() {
        return GENLINK;
    }

    public void setGENLINK(String GENLINK) {
        this.GENLINK = GENLINK;
    }

    public String getPOLICY_CANCLE() {
        return POLICY_CANCLE;
    }

    public void setPOLICY_CANCLE(String POLICY_CANCLE) {
        this.POLICY_CANCLE = POLICY_CANCLE;
    }

    public String getCOVER() {
        return COVER;
    }

    public void setCOVER(String COVER) {
        this.COVER = COVER;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public String getAVATAR() {
        return AVATAR;
    }

    public void setAVATAR(String AVATAR) {
        this.AVATAR = AVATAR;
    }
}
