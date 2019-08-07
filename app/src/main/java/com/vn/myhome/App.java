package com.vn.myhome;

import android.app.Application;

import com.google.gson.Gson;
import com.vn.myhome.models.ResponseApi.ObjCity;

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
    public static List<ObjCity> mListCity;

    @Override
    public void onCreate() {
        super.onCreate();
        mListCity = new ArrayList<>();
        sInstance = this;
        mGSon = new Gson();
    }

    public Gson getGSon() {
        return mGSon;
    }
}
