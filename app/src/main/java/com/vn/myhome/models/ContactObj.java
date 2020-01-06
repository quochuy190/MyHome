package com.vn.myhome.models;

import java.io.Serializable;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 19-November-2019
 * Time: 01:31
 * Version: 1.0
 */
public class  ContactObj  implements Serializable {
    String ID;
    String NAME;
    String MOBILE;
    String DOB;
    String EMAIL;
    String ADDRESS;
    String UPDATE_TIME;
    String CMT;
    String ID_PROVINCE;
    String PROVINCE_NAME;

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

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }

    public String getCMT() {
        return CMT;
    }

    public void setCMT(String CMT) {
        this.CMT = CMT;
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
}
