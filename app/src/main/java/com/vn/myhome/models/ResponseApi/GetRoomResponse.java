package com.vn.myhome.models.ResponseApi;

import com.google.gson.annotations.SerializedName;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ObjTypeUser;

import java.util.List;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 05-August-2019
 * Time: 15:30
 * Version: 1.0
 */
public class GetRoomResponse {
    @SerializedName("ERROR")
    String ERROR;
    @SerializedName("MESSAGE")
    String MESSAGE;
    @SerializedName("RESULT")
    String RESULT;
    @SerializedName("INFO")
    List<ObjHomeStay> INFO;

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

    public List<ObjHomeStay> getINFO() {
        return INFO;
    }

    public void setINFO(List<ObjHomeStay> INFO) {
        this.INFO = INFO;
    }
}
