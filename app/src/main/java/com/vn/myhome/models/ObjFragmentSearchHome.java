package com.vn.myhome.models;

import java.util.List;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 05-March-2020
 * Time: 22:55
 * Version: 1.0
 */
public class ObjFragmentSearchHome {

    String TITLE;
    List<ObjHomeStay> mListHomeStay;

    public ObjFragmentSearchHome(String TITLE, List<ObjHomeStay> mListHomeStay) {
        this.TITLE = TITLE;
        this.mListHomeStay = mListHomeStay;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public List<ObjHomeStay> getmListHomeStay() {
        return mListHomeStay;
    }

    public void setmListHomeStay(List<ObjHomeStay> mListHomeStay) {
        this.mListHomeStay = mListHomeStay;
    }
}
