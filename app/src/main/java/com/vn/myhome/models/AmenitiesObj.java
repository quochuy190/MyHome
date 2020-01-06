package com.vn.myhome.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-October-2019
 * Time: 09:19
 * Version: 1.0
 */
public class AmenitiesObj {
    @SerializedName("ERROR")
    String ERROR;
    @SerializedName("MESSAGE")
    String MESSAGE;
    @SerializedName("RESULT")
    String RESULT;
    @SerializedName("ID")
    String ID;
    @SerializedName("TYPE_AMENITIES")
    String TYPE_AMENITIES;
    @SerializedName("CLASS_ICON")
    String CLASS_ICON;
    @SerializedName("NAME_AMENITIES")
    String NAME_AMENITIES;
    @SerializedName("DES")
    String DES;
    boolean isHeader;
    boolean isChecked;

    public AmenitiesObj(String DES, boolean isHeader) {
        this.DES = DES;
        this.isHeader = isHeader;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTYPE_AMENITIES() {
        return TYPE_AMENITIES;
    }

    public void setTYPE_AMENITIES(String TYPE_AMENITIES) {
        this.TYPE_AMENITIES = TYPE_AMENITIES;
    }

    public String getCLASS_ICON() {
        return CLASS_ICON;
    }

    public void setCLASS_ICON(String CLASS_ICON) {
        this.CLASS_ICON = CLASS_ICON;
    }

    public String getDES() {
        return DES;
    }

    public void setDES(String DES) {
        this.DES = DES;
    }

    public String getNAME_AMENITIES() {
        return NAME_AMENITIES;
    }

    public void setNAME_AMENITIES(String NAME_AMENITIES) {
        this.NAME_AMENITIES = NAME_AMENITIES;
    }
}
