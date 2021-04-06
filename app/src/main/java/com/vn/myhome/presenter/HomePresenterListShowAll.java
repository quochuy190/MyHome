package com.vn.myhome.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.myhome.activity.home.ActivityListHomestayShowAll;
import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;

import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Retrofit;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 04-March-2020
 * Time: 17:15
 * Version: 1.0
 */
public class HomePresenterListShowAll {
    private static final String TAG = "HomePresenterNew";
    ActivityListHomestayShowAll mView;
    Retrofit retrofit;
    ApiServicePost mApiService;

    public HomePresenterListShowAll(ActivityListHomestayShowAll mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }


    public void getListHomeStay(String sUserName, String Page) {
        String sService = "room/get_listroom_idx";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", sUserName);
        mMap.put("PAGE", Page);
        mMap.put("NUMOFPAGE", "20");

        mApiService.getApi_Token_Enable(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.showAlertErrorNetwork();
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                mView.hideDialogLoading();
                try {
                    GetRoomResponse obj = new Gson().fromJson(objT, GetRoomResponse.class);
                    if (obj.getERROR().equals("0000")){
                        mView.update_data(obj.getINFO());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                  // mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

}
