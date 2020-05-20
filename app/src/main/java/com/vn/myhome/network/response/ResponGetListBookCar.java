package com.vn.myhome.network.response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.vn.myhome.models.ObjListBookCar;

import java.util.List;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 05-March-2020
 * Time: 11:19
 * Version: 1.0
 */
public class ResponGetListBookCar {
    @SerializedName("ERROR")
    String ERROR;
    @SerializedName("MESSAGE")
    String MESSAGE;
    @SerializedName("RESULT")
    String RESULT;
    @SerializedName("INFO")
    List<ObjListBookCar> INFO;

    public ResponGetListBookCar(JsonObject jsonObject, JsonArray jsonElements) {
    }

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

    public List<ObjListBookCar> getINFO() {
        return INFO;
    }

    public void setINFO(List<ObjListBookCar> INFO) {
        this.INFO = INFO;
    }
}
