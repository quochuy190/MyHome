package com.vn.myhome.models.ResponseApi;

import com.google.gson.annotations.SerializedName;
import com.vn.myhome.models.ObjImageHome;

import java.io.Serializable;
import java.util.List;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 05-August-2019
 * Time: 15:28
 * Version: 1.0
 */
public class ResponListImageHome implements Serializable {
    @SerializedName("ERROR")
    String ERROR;
    @SerializedName("MESSAGE")
    String MESSAGE;
    @SerializedName("RESULT")
    String RESULT;
    @SerializedName("INFO")
    List<ObjImageHome> INFO;

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

    public List<ObjImageHome> getINFO() {
        return INFO;
    }

    public void setINFO(List<ObjImageHome> INFO) {
        this.INFO = INFO;
    }
}
