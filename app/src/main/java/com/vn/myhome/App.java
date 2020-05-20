package com.vn.myhome;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.gson.Gson;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ObjRoute;
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
    public static ObjRoute mRoute;
    public static ObjRoute mCar;
    public static TowerObj mTower;
    public static List<ObjCity> mListCity;
    public static List<TowerObj> mListLocation;
    public static List<ObjRoute> mListRoute;
    public static List<ObjRoute> mListCar;
    public static List<ObjHomeStay> mListHomeStay;
    public static ObjHomeStay mHomestay;
    public static String sTotalNotify="";
    public static boolean isShowPopup = false;
    public static String versionName;
    public static int versionCode;
    @Override
    public void onCreate() {
        super.onCreate();
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionName = pInfo.versionName;//Version Name
        versionCode = pInfo.versionCode;//Version Code
        mListCity = new ArrayList<>();
        mListHomeStay = new ArrayList<>();
        mListRoute = new ArrayList<>();
        mListCar = new ArrayList<>();
        mListLocation = new ArrayList<>();
        sInstance = this;
        mGSon = new Gson();
    }

    public Gson getGSon() {
        return mGSon;
    }
}
