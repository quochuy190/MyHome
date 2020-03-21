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
    @SerializedName("LOCATION_ID")
    String LOCATION_ID;
    @SerializedName("LOCATION_NAME")
    String LOCATION_NAME;
    @SerializedName("ID_PROVINCE")
    String ID_PROVINCE;
    @SerializedName("PROVINCE_NAME")
    String PROVINCE_NAME;
    @SerializedName("USER_ID")
    String USER_ID;
    @SerializedName("NAME_HOST")
    String NAME_HOST;
    @SerializedName("MAX_GUEST_EXIST")
    String MAX_GUEST_EXIST;
    @SerializedName("DISCOUNT")
    String DISCOUNT;
    @SerializedName("PERCENT")
    String PERCENT;
    @SerializedName("PROMO_ST_TIME")
    String PROMO_ST_TIME;
    @SerializedName("PROMO_ED_TIME")
    String PROMO_ED_TIME;
    @SerializedName("CITY_NAME")
    String CITY_NAME;
    @SerializedName("URL_IMAGE")
    String URL_IMAGE;
    @SerializedName("TONG")
    String TONG;

    public String getCITY_NAME() {
        return CITY_NAME;
    }

    public void setCITY_NAME(String CITY_NAME) {
        this.CITY_NAME = CITY_NAME;
    }

    public String getURL_IMAGE() {
        return URL_IMAGE;
    }

    public void setURL_IMAGE(String URL_IMAGE) {
        this.URL_IMAGE = URL_IMAGE;
    }

    public String getTONG() {
        return TONG;
    }

    public void setTONG(String TONG) {
        this.TONG = TONG;
    }

    public String getPROMO_ST_TIME() {
        return PROMO_ST_TIME;
    }

    public void setPROMO_ST_TIME(String PROMO_ST_TIME) {
        this.PROMO_ST_TIME = PROMO_ST_TIME;
    }

    public String getPROMO_ED_TIME() {
        return PROMO_ED_TIME;
    }

    public void setPROMO_ED_TIME(String PROMO_ED_TIME) {
        this.PROMO_ED_TIME = PROMO_ED_TIME;
    }

    public String getNAME_HOST() {
        return NAME_HOST;
    }

    public void setNAME_HOST(String NAME_HOST) {
        this.NAME_HOST = NAME_HOST;
    }

    public ObjHomeStay(String NAME, String GENLINK) {
        this.NAME = NAME;
        this.GENLINK = GENLINK;
    }

    public String getDISCOUNT() {
        return DISCOUNT;
    }

    public void setDISCOUNT(String DISCOUNT) {
        this.DISCOUNT = DISCOUNT;
    }

    public String getPERCENT() {
        return PERCENT;
    }

    public void setPERCENT(String PERCENT) {
        this.PERCENT = PERCENT;
    }

    public String getMAX_GUEST_EXIST() {
        return MAX_GUEST_EXIST;
    }

    public void setMAX_GUEST_EXIST(String MAX_GUEST_EXIST) {
        this.MAX_GUEST_EXIST = MAX_GUEST_EXIST;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getLOCATION_ID() {
        return LOCATION_ID;
    }

    public void setLOCATION_ID(String LOCATION_ID) {
        this.LOCATION_ID = LOCATION_ID;
    }

    public String getLOCATION_NAME() {
        return LOCATION_NAME;
    }

    public void setLOCATION_NAME(String LOCATION_NAME) {
        this.LOCATION_NAME = LOCATION_NAME;
    }

    public String getID_PROVINCE() {
        return ID_PROVINCE;
    }

    public void setID_PROVINCE(String ID_PROVINCE) {
        this.ID_PROVINCE = ID_PROVINCE;
    }

    public String getPROVINCE_NAME() {
        return PROVINCE_NAME;
    }

    public void setPROVINCE_NAME(String PROVINCE_NAME) {
        this.PROVINCE_NAME = PROVINCE_NAME;
    }

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
