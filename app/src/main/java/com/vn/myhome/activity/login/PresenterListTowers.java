package com.vn.myhome.activity.login;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.models.ResponseApi.LocationResponse;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-May-2019
 * Time: 10:44
 * Version: 1.0
 */
public class PresenterListTowers  {
    private static final String TAG = "PresenterLogin";
    ApiServicePost mApiService;
    ActivityListTowers mView;

    public PresenterListTowers(ActivityListTowers mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    public void api_get_list_tower( String USERNAME, String PROVINCE_ID) {
        String sService = "room/get_location";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("PROVINCE_ID", PROVINCE_ID);
        mApiService.getApi_Token_Enable(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api();
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    LocationResponse obj = new Gson().fromJson(objT, LocationResponse.class);
                    mView.show_list_tower(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

}
