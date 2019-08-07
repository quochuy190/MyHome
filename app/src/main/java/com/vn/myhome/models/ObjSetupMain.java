package com.vn.myhome.models;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 06-August-2019
 * Time: 22:54
 * Version: 1.0
 */
public class ObjSetupMain {
    int id;
    String sName;
    int iIcon;

    public ObjSetupMain(int id, String sName, int iIcon) {
        this.id = id;
        this.sName = sName;
        this.iIcon = iIcon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public int getiIcon() {
        return iIcon;
    }

    public void setiIcon(int iIcon) {
        this.iIcon = iIcon;
    }
}
