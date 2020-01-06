package com.vn.myhome.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 18-November-2019
 * Time: 17:19
 * Version: 1.0
 */
public class ReportObj {
    @SerializedName("ERROR")
    String ERROR;
    @SerializedName("MESSAGE")
    String MESSAGE;
    @SerializedName("RESULT")
    String RESULT;
    @SerializedName("TOTAL_REVENUE")
    String TOTAL_REVENUE;
    @SerializedName("TOTAL_SELLING_COSTS")
    String TOTAL_SELLING_COSTS;
    @SerializedName("TOTAL_SERVICE_COSTS")
    String TOTAL_SERVICE_COSTS;
    @SerializedName("TOTAL_OTHER_COSTS")
    String TOTAL_OTHER_COSTS;
    @SerializedName("REVENUE")
    String REVENUE;

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

    public String getTOTAL_REVENUE() {
        return TOTAL_REVENUE;
    }

    public void setTOTAL_REVENUE(String TOTAL_REVENUE) {
        this.TOTAL_REVENUE = TOTAL_REVENUE;
    }

    public String getTOTAL_SELLING_COSTS() {
        return TOTAL_SELLING_COSTS;
    }

    public void setTOTAL_SELLING_COSTS(String TOTAL_SELLING_COSTS) {
        this.TOTAL_SELLING_COSTS = TOTAL_SELLING_COSTS;
    }

    public String getTOTAL_SERVICE_COSTS() {
        return TOTAL_SERVICE_COSTS;
    }

    public void setTOTAL_SERVICE_COSTS(String TOTAL_SERVICE_COSTS) {
        this.TOTAL_SERVICE_COSTS = TOTAL_SERVICE_COSTS;
    }

    public String getTOTAL_OTHER_COSTS() {
        return TOTAL_OTHER_COSTS;
    }

    public void setTOTAL_OTHER_COSTS(String TOTAL_OTHER_COSTS) {
        this.TOTAL_OTHER_COSTS = TOTAL_OTHER_COSTS;
    }

    public String getREVENUE() {
        return REVENUE;
    }

    public void setREVENUE(String REVENUE) {
        this.REVENUE = REVENUE;
    }
}
