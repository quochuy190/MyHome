package com.vn.myhome.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 05-August-2019
 * Time: 15:28
 * Version: 1.0
 */
public class ObjLocation implements Serializable {
    @SerializedName("LOCATION_ID")
    String LOCATION_ID;
    @SerializedName("LOCATION_NAME")
    String LOCATION_NAME;

    public ObjLocation(String LOCATION_ID, String LOCATION_NAME) {
        this.LOCATION_ID = LOCATION_ID;
        this.LOCATION_NAME = LOCATION_NAME;
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
}
