package com.vn.myhome.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 03-October-2019
 * Time: 10:14
 * Version: 1.0
 */
public class TowerObj {
    @SerializedName("LOCATION_NAME")
    String sName;
    @SerializedName("LOCATION_ID")
    String sId;

    public TowerObj(String sName, String sId) {
        this.sName = sName;
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }
}
