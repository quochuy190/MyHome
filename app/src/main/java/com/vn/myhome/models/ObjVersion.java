package com.vn.myhome.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 05-August-2019
 * Time: 15:34
 * Version: 1.0
 */
public class ObjVersion {
    @SerializedName("NAME")
    String NAME;
    @SerializedName("VERSION")
    String VERSION;
    @SerializedName("MUST_UPDATE")
    String MUST_UPDATE;

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public String getMUST_UPDATE() {
        return MUST_UPDATE;
    }

    public void setMUST_UPDATE(String MUST_UPDATE) {
        this.MUST_UPDATE = MUST_UPDATE;
    }
}
