package com.vn.myhome.fragment.tabCheckinCheckout;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.myhome.activity.user_manager.ActivityListUserCheckin;
import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.models.ObjErrorApi;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 28-May-2020
 * Time: 21:33
 * Version: 1.0
 */
public class PresenterTabCheckinCheckout {
    private static final String TAG = "PresenterTabCheckinChec";
    ActivityListUserCheckin mView;
    ApiServicePost mApiService;

    public PresenterTabCheckinCheckout(ActivityListUserCheckin mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    public void api_split_book_services(String USERNAME, String CONTENT, String USER_CHECKIN) {
        String sService = "book/split_book_services";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("CONTENT", CONTENT);
        mMap.put("USER_CHECKIN", USER_CHECKIN);

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
                    mView.show_api_chiadon(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

}
