package com.vn.myhome.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 05-August-2019
 * Time: 15:35
 * Version: 1.0
 */
public class ObjLogin {
    @SerializedName("ERROR")
    String ERROR;
    @SerializedName("MESSAGE")
    String MESSAGE;
    @SerializedName("RESULT")
    String RESULT;
    @SerializedName("ID")
    String ID;
    @SerializedName("USERID")
    String USERID;
    @SerializedName("PASS")
    String PASS;
    @SerializedName("MOBILE")
    String MOBILE;
    @SerializedName("EMAIL")
    String EMAIL;
    @SerializedName("FULL_NAME")
    String FULL_NAME;
    @SerializedName("DOB")
    String DOB;
    @SerializedName("ACTIVE_TIME")
    String ACTIVE_TIME;
    @SerializedName("UPDATE_TIME")
    String UPDATE_TIME;
    @SerializedName("USER_TYPE")
    String USER_TYPE;
    @SerializedName("DES_USERTYPE")
    String DES_USERTYPE;
    @SerializedName("AVATAR")
    String AVATAR;
    @SerializedName("VERSION")
    String VERSION;
    @SerializedName("DEVICE_MODEL")
    String DEVICE_MODEL;
    @SerializedName("TOKEN_KEY")
    String TOKEN_KEY;
    @SerializedName("OS_VERSION")
    String OS_VERSION;
    @SerializedName("STATE")
    String STATE;
    @SerializedName("DEVICE_TYPE")
    String DEVICE_TYPE;
    @SerializedName("UUID")
    String UUID;
    @SerializedName("ADDRESS")
    String ADDRESS;
    @SerializedName("DESCRIPTION")
    String DESCRIPTION;
    @SerializedName("ACTIVE_CODE")
    String ACTIVE_CODE;
    @SerializedName("ID_PROVINCE")
    String ID_PROVINCE;
    @SerializedName("PROVINCE_NAME")
    String PROVINCE_NAME;
    @SerializedName("TOKEN")
    String TOKEN;
    @SerializedName("TEN_CN")
    String TEN_CN;
    @SerializedName("TEN_NH")
    String TEN_NH;
    @SerializedName("TEN_TK")
    String TEN_TK;
    @SerializedName("SO_TK")
    String SO_TK;
    @SerializedName("LOCATION_NAME")
    String LOCATION_NAME;
    @SerializedName("LOCATION_ID")
    String LOCATION_ID;

    public String getPROVINCE_NAME() {
        return PROVINCE_NAME;
    }

    public void setPROVINCE_NAME(String PROVINCE_NAME) {
        this.PROVINCE_NAME = PROVINCE_NAME;
    }

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

    public String getPASS() {
        return PASS;
    }

    public void setPASS(String PASS) {
        this.PASS = PASS;
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

    public String getFULL_NAME() {
        return FULL_NAME;
    }

    public void setFULL_NAME(String FULL_NAME) {
        this.FULL_NAME = FULL_NAME;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getACTIVE_TIME() {
        return ACTIVE_TIME;
    }

    public void setACTIVE_TIME(String ACTIVE_TIME) {
        this.ACTIVE_TIME = ACTIVE_TIME;
    }

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }

    public String getUSER_TYPE() {
        return USER_TYPE;
    }

    public void setUSER_TYPE(String USER_TYPE) {
        this.USER_TYPE = USER_TYPE;
    }

    public String getDES_USERTYPE() {
        return DES_USERTYPE;
    }

    public void setDES_USERTYPE(String DES_USERTYPE) {
        this.DES_USERTYPE = DES_USERTYPE;
    }

    public String getAVATAR() {
        return AVATAR;
    }

    public void setAVATAR(String AVATAR) {
        this.AVATAR = AVATAR;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public String getDEVICE_MODEL() {
        return DEVICE_MODEL;
    }

    public void setDEVICE_MODEL(String DEVICE_MODEL) {
        this.DEVICE_MODEL = DEVICE_MODEL;
    }

    public String getTOKEN_KEY() {
        return TOKEN_KEY;
    }

    public void setTOKEN_KEY(String TOKEN_KEY) {
        this.TOKEN_KEY = TOKEN_KEY;
    }

    public String getOS_VERSION() {
        return OS_VERSION;
    }

    public void setOS_VERSION(String OS_VERSION) {
        this.OS_VERSION = OS_VERSION;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getDEVICE_TYPE() {
        return DEVICE_TYPE;
    }

    public void setDEVICE_TYPE(String DEVICE_TYPE) {
        this.DEVICE_TYPE = DEVICE_TYPE;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getACTIVE_CODE() {
        return ACTIVE_CODE;
    }

    public void setACTIVE_CODE(String ACTIVE_CODE) {
        this.ACTIVE_CODE = ACTIVE_CODE;
    }

    public String getID_PROVINCE() {
        return ID_PROVINCE;
    }

    public void setID_PROVINCE(String ID_PROVINCE) {
        this.ID_PROVINCE = ID_PROVINCE;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public String getTEN_CN() {
        return TEN_CN;
    }

    public void setTEN_CN(String TEN_CN) {
        this.TEN_CN = TEN_CN;
    }

    public String getTEN_NH() {
        return TEN_NH;
    }

    public void setTEN_NH(String TEN_NH) {
        this.TEN_NH = TEN_NH;
    }

    public String getTEN_TK() {
        return TEN_TK;
    }

    public void setTEN_TK(String TEN_TK) {
        this.TEN_TK = TEN_TK;
    }

    public String getSO_TK() {
        return SO_TK;
    }

    public void setSO_TK(String SO_TK) {
        this.SO_TK = SO_TK;
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

    public String getLOCATION_NAME() {
        return LOCATION_NAME;
    }

    public void setLOCATION_NAME(String LOCATION_NAME) {
        this.LOCATION_NAME = LOCATION_NAME;
    }

    public String getLOCATION_ID() {
        return LOCATION_ID;
    }

    public void setLOCATION_ID(String LOCATION_ID) {
        this.LOCATION_ID = LOCATION_ID;
    }
}
