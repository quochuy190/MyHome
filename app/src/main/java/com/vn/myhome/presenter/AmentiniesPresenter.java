package com.vn.myhome.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ResponseApi.AmentiniesResponse;
import com.vn.myhome.untils.SharedPrefs;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 11-August-2019
 * Time: 15:37
 * Version: 1.0
 */
public class AmentiniesPresenter implements InterfaceAmenities.Presenter {
    private static final String TAG = "MyHomePresenter";
    ApiServicePost mApiService;
    InterfaceAmenities.View mView;

    public AmentiniesPresenter(InterfaceAmenities.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    @Override
    public void api_get_amenities(String USERNAME) {
        String sService = "get_amenities";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);

        mApiService.getApiService(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(sService);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    AmentiniesResponse obj = new Gson().fromJson(objT, AmentiniesResponse.class);
                    if (obj != null)
                        SharedPrefs.getInstance().put(Constants.KEY_SAVE_AMENITIES, obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_amenities_room(String USERNAME, String GENLINK) {
        String sService = "room/get_amenities_room";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("GENLINK", GENLINK);


        mApiService.getApi_Token_Enable(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api("");
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    AmentiniesResponse obj = new Gson().fromJson(objT, AmentiniesResponse.class);
                    mView.show_amenities_room(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_update_amenities(String USERNAME, String GENLINK, String AMENITIES) {
        String sService = "room/update_amenities";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("GENLINK", GENLINK);
        mMap.put("AMENITIES", AMENITIES);


        mApiService.getApi_Token_Enable(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api("");
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ObjErrorApi obj = new Gson().fromJson(objT, ObjErrorApi.class);
                    mView.show_update_amenities(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }
}
