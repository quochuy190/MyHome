package com.vn.myhome.models;

import java.util.List;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 26-July-2020
 * Time: 09:21
 * Version: 1.0
 */
public class ListImageCheckinService {
    String IMG_TYPE;
    String TYPE_NAME;
    List<ImageCheckinService> DATA;

    public String getIMG_TYPE() {
        return IMG_TYPE;
    }

    public void setIMG_TYPE(String IMG_TYPE) {
        this.IMG_TYPE = IMG_TYPE;
    }

    public String getTYPE_NAME() {
        return TYPE_NAME;
    }

    public void setTYPE_NAME(String TYPE_NAME) {
        this.TYPE_NAME = TYPE_NAME;
    }

    public List<ImageCheckinService> getDATA() {
        return DATA;
    }

    public void setDATA(List<ImageCheckinService> DATA) {
        this.DATA = DATA;
    }
}
