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

    public ObjCity(String MATP, String NAME) {
        this.MATP = MATP;
        this.NAME = NAME;
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
