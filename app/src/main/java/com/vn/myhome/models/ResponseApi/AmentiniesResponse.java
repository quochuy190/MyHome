package com.vn.myhome.models.ResponseApi;

import com.google.gson.annotations.SerializedName;
import com.vn.myhome.models.AmenitiesObj;

import java.util.List;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 05-August-2019
 * Time: 15:30
 * Version: 1.0
 */
public class AmentiniesResponse {
    @SerializedName("ERROR")
    String ERROR;
    @SerializedName("MESSAGE")
    String MESSAGE;
    @SerializedName("RESULT")
    String RESULT;
    @SerializedName("INFO")
    List<AmenitiesObj> INFO;

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

    public List<AmenitiesObj> getINFO() {
        return INFO;
    }

    public void setINFO(List<AmenitiesObj> INFO) {
        this.INFO = INFO;
    }
}
