package com.vn.myhome.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ResponseApi.ResponseGetContact;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 11-August-2019
 * Time: 15:37
 * Version: 1.0
 */
public class ContactPresenter implements InterfaceContact.Presenter {
    private static final String TAG = "MyHomePresenter";
    ApiServicePost mApiService;
    InterfaceContact.View mView;

    public ContactPresenter(InterfaceContact.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    @Override
    public void api_get_contact(String USERNAME, String NAME, String CMT, String MOBILE,
                                String EMAIL, String ID_PROVINCE) {
        String sService = "contact/get_contact";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("NAME", NAME);
        mMap.put("CMT", CMT);
        mMap.put("MOBILE", MOBILE);
        mMap.put("EMAIL", EMAIL);
        mMap.put("ID_PROVINCE", ID_PROVINCE);

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
                    ResponseGetContact obj = new Gson().fromJson(objT, ResponseGetContact.class);
                    mView.show_get_contact(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_add_contact(String USERNAME, String NAME, String MOBILE, String EMAIL,
                                String CMT, String DOB, String ADDRESS, String ID_PROVINCE) {
        String sService = "contact/add_contact";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("NAME", NAME);
        mMap.put("CMT", CMT);
        mMap.put("MOBILE", MOBILE);
        mMap.put("EMAIL", EMAIL);
        mMap.put("DOB", DOB);
        mMap.put("ADDRESS", ADDRESS);
        mMap.put("ID_PROVINCE", ID_PROVINCE);

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
                    mView.show_add_contact(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_edit_contact(String USERNAME, String ID, String NAME, String MOBILE,
                                 String EMAIL, String CMT, String DOB, String ADDRESS, String ID_PROVINCE) {
        String sService = "contact/edit_contact";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("ID", ID);
        mMap.put("NAME", NAME);
        mMap.put("CMT", CMT);
        mMap.put("MOBILE", MOBILE);
        mMap.put("EMAIL", EMAIL);
        mMap.put("DOB", DOB);
        mMap.put("ADDRESS", ADDRESS);
        mMap.put("ID_PROVINCE", ID_PROVINCE);

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
                    mView.show_edit_contact(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_del_contact(String USERNAME, String ID) {
        String sService = "contact/del_contact";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("ID", ID);


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
                    mView.show_del_contact(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }
}
