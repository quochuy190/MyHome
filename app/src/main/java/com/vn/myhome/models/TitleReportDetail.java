package com.vn.myhome.models;

import java.util.List;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 19-November-2019
 * Time: 16:02
 * Version: 1.0
 */
public class TitleReportDetail {
    String sNameHome;
    String sGetlink;
    List<ObjReportDetail> mList;

    public TitleReportDetail(String sNameHome, List<ObjReportDetail> mList) {
        this.sNameHome = sNameHome;
        this.mList = mList;
    }

    public TitleReportDetail(String sNameHome, String sGetlink) {
        this.sNameHome = sNameHome;
        this.sGetlink = sGetlink;
    }

    public String getsGetlink() {
        return sGetlink;
    }

    public void setsGetlink(String sGetlink) {
        this.sGetlink = sGetlink;
    }

    public String getsNameHome() {
        return sNameHome;
    }

    public void setsNameHome(String sNameHome) {
        this.sNameHome = sNameHome;
    }

    public List<ObjReportDetail> getmList() {
        return mList;
    }

    public void setmList(List<ObjReportDetail> mList) {
        this.mList = mList;
    }
}
