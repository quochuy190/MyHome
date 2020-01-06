package com.vn.myhome.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.models.ResponseApi.VersionResponse;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 11-August-2019
 * Time: 15:37
 * Version: 1.0
 */
public class UpdateVersionPresenter implements InterfaceUpdateVersion.Presenter {
    private static final String TAG = "MyHomePresenter";
    ApiServicePost mApiService;
    InterfaceUpdateVersion.View mView;

    public UpdateVersionPresenter(InterfaceUpdateVersion.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }



    @Override
    public void api_get_version() {
        String sService = "get_version";
        Map<String, String> mMap = new LinkedHashMap<>();

        mApiService.getApiService(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api("");
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    VersionResponse obj = new Gson().fromJson(objT, VersionResponse.class);
                    mView.show_get_version(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }
}
