package com.vn.myhome.activity.login;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ResponseApi.CityResponse;
import com.vn.myhome.models.ResponseApi.GetTypeResponse;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-May-2019
 * Time: 10:44
 * Version: 1.0
 */
public class PresenterLogin implements InterfaceLogin.Presenter {
    private static final String TAG = "PresenterLogin";
    ApiServicePost mApiService;
    InterfaceLogin.View mView;

    public PresenterLogin(InterfaceLogin.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    @Override
    public void api_login(String sService, String USERNAME, String PASSWORD) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("PASSWORD", PASSWORD);

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
                    ObjLogin obj = new Gson().fromJson(objT, ObjLogin.class);
                    mView.show_login(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_reg_user(String sService, String USERNAME, String PASSWORD, String EMAIL,
                             String FULL_NAME, String USER_TYPE, String MOBILE, String ADDRESS, String DES,
                             String ID_PROVINCE, String STK, String TENTK, String TENNH, String TENCN) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("PASSWORD", PASSWORD);
        mMap.put("EMAIL", EMAIL);
        mMap.put("FULL_NAME", FULL_NAME);
        mMap.put("USER_TYPE", USER_TYPE);
        mMap.put("MOBILE", MOBILE);
        mMap.put("ADDRESS", ADDRESS);
        mMap.put("DES", DES);
        mMap.put("ID_PROVINCE", ID_PROVINCE);
        mMap.put("STK", STK);
        mMap.put("TENTK", TENTK);
        mMap.put("TENNH", TENNH);
        mMap.put("TENCN", TENCN);

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
                    ObjErrorApi obj = new Gson().fromJson(objT, ObjErrorApi.class);
                    mView.show_reg_user(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_type(String USERNAME) {
        String sService = "get_type";
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
                    GetTypeResponse obj = new Gson().fromJson(objT, GetTypeResponse.class);
                    mView.show_get_type(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_city(String USERNAME) {
        String sService = "get_city";
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
                    CityResponse obj = new Gson().fromJson(objT, CityResponse.class);
                    mView.show_get_city(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("get_city");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_update_device(String USERNAME, String VERSION, String DEVICE_MODEL, String TOKEN_KEY,
                                  String OS_VERSION, String DEVICE_TYPE, String UUID) {
        String sService = "user/update_device";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("VERSION", VERSION);
        mMap.put("DEVICE_MODEL", DEVICE_MODEL);
        mMap.put("TOKEN_KEY", TOKEN_KEY);
        mMap.put("OS_VERSION", OS_VERSION);
        mMap.put("DEVICE_TYPE", DEVICE_TYPE);
        mMap.put("UUID", UUID);

        mApiService.getApi_Token_Enable(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(sService);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ObjErrorApi obj = new Gson().fromJson(objT, ObjErrorApi.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("get_city");
                }
            }
        }, sService, mMap);
    }


}
