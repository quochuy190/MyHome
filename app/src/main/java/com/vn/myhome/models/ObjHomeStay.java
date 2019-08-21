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
    @SerializedName("PROMOTION")
    String PROMOTION;
    @SerializedName("ADDRESS")
    String ADDRESS;
    @SerializedName("ID_HOST")
    String ID_HOST;
    @SerializedName("ID_SERVICE")
    String ID_SERVICE;
    @SerializedName("RATING")
    String RATING;
    @SerializedName("STATE")
    String STATE;
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
    @SerializedName("CHECKLOCK")
    String CHECKLOCK;
    @SerializedName("MOBILE")
    String MOBILE;
    @SerializedName("EMAIL")
    String EMAIL;
    @SerializedName("HOST_NAME")
    String HOST_NAME;

    public String getHOST_NAME() {
        return HOST_NAME;
    }

    public void setHOST_NAME(String HOST_NAME) {
        this.HOST_NAME = HOST_NAME;
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

    public String getPROMOTION() {
        return PROMOTION;
    }

    public void setPROMOTION(String PROMOTION) {
        this.PROMOTION = PROMOTION;
    }

    public String getID_SERVICE() {
        return ID_SERVICE;
    }

    public void setID_SERVICE(String ID_SERVICE) {
        this.ID_SERVICE = ID_SERVICE;
    }

    public String getRATING() {
        return RATING;
    }

    public void setRATING(String RATING) {
        this.RATING = RATING;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getCHECKLOCK() {
        return CHECKLOCK;
    }

    public void setCHECKLOCK(String CHECKLOCK) {
        this.CHECKLOCK = CHECKLOCK;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

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
