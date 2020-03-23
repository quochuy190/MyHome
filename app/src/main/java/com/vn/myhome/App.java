package com.vn.myhome;

import android.app.Application;

import com.google.gson.Gson;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ResponseApi.ObjCity;
import com.vn.myhome.models.TowerObj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 05-August-2019
 * Time: 14:23
 * Version: 1.0
 */
public class App extends Application {
    private static App sInstance;
    private Gson mGSon;

    public static App self() {
        return sInstance;
    }

    public static ObjCity mCity;
    public static TowerObj mTower;
    public static List<ObjCity> mListCity;
    public static List<ObjHomeStay> mListHomeStay;
    public static ObjHomeStay mHomestay;
    public static String sTotalNotify="";
    @Override
    public void onCreate() {
        super.onCreate();
        mListCity = new ArrayList<>();
        mListHomeStay = new ArrayList<>();
        sInstance = this;
        mGSon = new Gson();
    }

    public Gson getGSon() {
        return mGSon;
    }
}
