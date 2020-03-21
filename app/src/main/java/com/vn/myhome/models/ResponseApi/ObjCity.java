package com.vn.myhome.models.ResponseApi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 05-August-2019
 * Time: 15:28
 * Version: 1.0
 */
public class ObjCity implements Serializable {
    @SerializedName("MATP")
    String MATP;
    @SerializedName("NAME")
    String NAME;
    @SerializedName("TYPE")
    String TYPE;
    @SerializedName("OD")
    String OD;
    @SerializedName("ID_PROVINCE")
    String ID_PROVINCE;
    @SerializedName("CITY_NAME")
    String CITY_NAME;
    @SerializedName("URL_IMAGE")
    String URL_IMAGE;
    @SerializedName("TONG")
    String TONG;

    public ObjCity(String MATP, String NAME) {
        this.MATP = MATP;
        this.NAME = NAME;
    }

    public ObjCity(String ID_PROVINCE, String CITY_NAME, String URL_IMAGE, String TONG) {
        this.ID_PROVINCE = ID_PROVINCE;
        this.CITY_NAME = CITY_NAME;
        this.URL_IMAGE = URL_IMAGE;
        this.TONG = TONG;
    }

    public ObjCity() {
    }

    public String getMATP() {
        return MATP;
    }

    public void setMATP(String MATP) {
        this.MATP = MATP;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getOD() {
        return OD;
    }

    public void setOD(String OD) {
        this.OD = OD;
    }
}
