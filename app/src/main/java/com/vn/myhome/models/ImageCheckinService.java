package com.vn.myhome.models;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 26-July-2020
 * Time: 09:18
 * Version: 1.0
 */
public class ImageCheckinService {
    String ID;
    String IMG;
    String ID_BOOK_SERVICES;
    String UPDATE_TIME;
    String sPath;

    public ImageCheckinService(String ID, String ID_BOOK_SERVICES, String sPath) {
        this.ID = ID;
        this.ID_BOOK_SERVICES = ID_BOOK_SERVICES;
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

    public String getID_BOOK_SERVICES() {
        return ID_BOOK_SERVICES;
    }

    public void setID_BOOK_SERVICES(String ID_BOOK_SERVICES) {
        this.ID_BOOK_SERVICES = ID_BOOK_SERVICES;
    }

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }
}
