package com.vn.myhome.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 22-March-2020
 * Time: 15:57
 * Version: 1.0
 */
public class ObjReport {
    @SerializedName("MONTH")
    String MONTH;
    @SerializedName("COST")
    String COST;
    @SerializedName("PROFIT")
    String PROFIT;
    @SerializedName("BALANCE")
    String BALANCE;
    @SerializedName("REVENUE")
    String REVENUE;
    @SerializedName("TOTALDAY")
    String TOTALDAY;
    @SerializedName("NUMBER_OF_DAYS")
    String NUMBER_OF_DAYS;

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

    public String getCOST() {
        return COST;
    }

    public void setCOST(String COST) {
        this.COST = COST;
    }

    public String getPROFIT() {
        return PROFIT;
    }

    public void setPROFIT(String PROFIT) {
        this.PROFIT = PROFIT;
    }

    public String getBALANCE() {
        return BALANCE;
    }

    public void setBALANCE(String BALANCE) {
        this.BALANCE = BALANCE;
    }

    public String getREVENUE() {
        return REVENUE;
    }

    public void setREVENUE(String REVENUE) {
        this.REVENUE = REVENUE;
    }

    public String getTOTALDAY() {
        return TOTALDAY;
    }

    public void setTOTALDAY(String TOTALDAY) {
        this.TOTALDAY = TOTALDAY;
    }

    public String getNUMBER_OF_DAYS() {
        return NUMBER_OF_DAYS;
    }

    public void setNUMBER_OF_DAYS(String NUMBER_OF_DAYS) {
        this.NUMBER_OF_DAYS = NUMBER_OF_DAYS;
    }
}
