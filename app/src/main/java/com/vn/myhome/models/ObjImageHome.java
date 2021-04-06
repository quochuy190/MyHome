package com.vn.myhome.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 04-December-2019
 * Time: 17:05
 * Version: 1.0
 */
public class ObjImageHome implements Serializable {
    @SerializedName("ID")
    String ID;
    @SerializedName("IMG")
    String IMG;
    @SerializedName("ID_ROOM")
    String ID_ROOM;
    @SerializedName("GENLINK")
    String GENLINK;
    @SerializedName("IMAGE_TYPE")
    String IMAGE_TYPE;
    @SerializedName("UPDATE_TIME")
    String UPDATE_TIME;
    String sPath;

    public ObjImageHome() {
    }

    public ObjImageHome(String sPath) {
        this.sPath = sPath;
    }

    public String getsPath() {
        return sPath;
    }

    public void setsPath(String sPath) {
        this.sPath = sPath;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public String getID_ROOM() {
        return ID_ROOM;
    }

    public void setID_ROOM(String ID_ROOM) {
        this.ID_ROOM = ID_ROOM;
    }

    public String getGENLINK() {
        return GENLINK;
    }

    public void setGENLINK(String GENLINK) {
        this.GENLINK = GENLINK;
    }

    public String getIMAGE_TYPE() {
        return IMAGE_TYPE;
    }

    public void setIMAGE_TYPE(String IMAGE_TYPE) {
        this.IMAGE_TYPE = IMAGE_TYPE;
    }

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }
}
