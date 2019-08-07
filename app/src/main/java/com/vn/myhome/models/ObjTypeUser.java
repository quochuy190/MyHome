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
public class ObjTypeUser implements Serializable {
    @SerializedName("USER_TYPE")
    String USER_TYPE;
    @SerializedName("TYPE_NAME")
    String TYPE_NAME;

    public String getUSER_TYPE() {
        return USER_TYPE;
    }

    public void setUSER_TYPE(String USER_TYPE) {
        this.USER_TYPE = USER_TYPE;
    }

    public String getTYPE_NAME() {
        return TYPE_NAME;
    }

    public void setTYPE_NAME(String TYPE_NAME) {
        this.TYPE_NAME = TYPE_NAME;
    }
}
