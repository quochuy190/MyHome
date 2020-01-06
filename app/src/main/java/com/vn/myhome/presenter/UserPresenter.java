package com.vn.myhome.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.myhome.activity.user_manager.InterfaceUser;
import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ResponseApi.ResponInfo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 11-August-2019
 * Time: 15:37
 * Version: 1.0
 */
public class UserPresenter implements InterfaceUser.Presenter {
    private static final String TAG = "MyHomePresenter";
    ApiServicePost mApiService;
    InterfaceUser.View mView;

    public UserPresenter(InterfaceUser.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    @Override
    public void api_get_user_info(String USERNAME, String USERID) {
        String sService = "user/get_info";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("USERID", USERID);
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
                    ResponInfo obj = new Gson().fromJson(objT, ResponInfo.class);
                    mView.show_get_user_info(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_update_user_info(String USERNAME, String USERID, String PASSWORD, String MOBILE, String EMAIL,
                                     String FULL_NAME, String DOB, String USER_TYPE, String AVATAR, String STATE,
                                     String ADDRESS, String ID_PROVINCE, String STK, String TENTK, String TENNH,
                                     String TENCN) {
        String sService = "user/update_info";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("USERID", USERID);
        mMap.put("PASSWORD", PASSWORD);
        mMap.put("MOBILE", MOBILE);
        mMap.put("EMAIL", EMAIL);
        mMap.put("FULL_NAME", FULL_NAME);
        mMap.put("DOB", DOB);
        mMap.put("USER_TYPE", USER_TYPE);
        mMap.put("AVATAR", AVATAR);
        mMap.put("STATE", STATE);
        mMap.put("ADDRESS", ADDRESS);
        mMap.put("ID_PROVINCE", ID_PROVINCE);
        mMap.put("STK", STK);
        mMap.put("TENTK", TENTK);
        mMap.put("TENNH", TENNH);
        mMap.put("TENCN", TENCN);

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
                    mView.show_update_user_info(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_listuser(String USERNAME, String USERID, String MOBILE, String EMAIL,
                                 String FULL_NAME, String USER_TYPE, String STATE, String ADDRESS,
                                 String PAGE, String NUMOFPAGE) {
        String sService = "user/get_listuser";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("USERID", USERID);
        mMap.put("MOBILE", MOBILE);
        mMap.put("EMAIL", EMAIL);
        mMap.put("FULL_NAME", FULL_NAME);
        mMap.put("USER_TYPE", USER_TYPE);
        mMap.put("STATE", STATE);
        mMap.put("ADDRESS", ADDRESS);
        mMap.put("PAGE", PAGE);
        mMap.put("NUMOFPAGE", NUMOFPAGE);
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
                    ResponInfo obj = new Gson().fromJson(objT, ResponInfo.class);
                    mView.show_get_listuser(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);

    }

    @Override
    public void api_change_pass(String USERNAME, String OLD_PASS, String NEW_PASS) {
        String sService = "user/change_pass";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("OLD_PASS", OLD_PASS);
        mMap.put("NEW_PASS", NEW_PASS);
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
                    mView.show_change_pass(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }
}
