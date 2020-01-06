package com.vn.myhome.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.models.ResponseApi.ResponListImageHome;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 11-August-2019
 * Time: 15:37
 * Version: 1.0
 */
public class MyHomePresenter implements InterfaceMyHome.Presenter {
    private static final String TAG = "MyHomePresenter";
    ApiServicePost mApiService;
    InterfaceMyHome.View mView;

    public MyHomePresenter(InterfaceMyHome.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    @Override
    public void api_get_mylist_room(String USERNAME, String OPT) {
        String sService = "room/get_mylist_room";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("OPT", OPT);

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
                    GetRoomResponse obj = new Gson().fromJson(objT, GetRoomResponse.class);
                    mView.show_get_mylist_room(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_room_detail(String USERNAME, String GENLINK) {
        String sService = "room/get_room_detail";
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
                    ObjHomeStay obj = new Gson().fromJson(objT, ObjHomeStay.class);
                    mView.show_room_detail(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_new_room(String USERNAME) {
        String sService = "room/new_room";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);

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
                    mView.show_new_room(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_del_room(String USERNAME, String GENLINK) {
        String sService = "room/del_room";
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
                    ObjErrorApi obj = new Gson().fromJson(objT, ObjErrorApi.class);
                    mView.show_delete_room(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_album_image(String USERNAME, String GENLINK) {
        String sService = "room/get_album_image";
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
                    ResponListImageHome obj = new Gson().fromJson(objT, ResponListImageHome.class);
                    mView.show_get_album_image(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_edit_room(String USERNAME, String NAME, String ADDRESS, String PRICE, String PRICE_SPECIAL,
                              String PRICE_EXTRA, String MAX_GUEST, String MAX_ROOM, String MAX_BED, String CLEAN_ROOM,
                              String DESCRIPTION, String INFOMATION, String POLICY_CANCLE, String GENLINK,
                              String PROVINCE_ID, String LOCATION_ID, String COVER, String MAX_GUEST_EXIST) {
        String sService = "room/edit_room";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("NAME", NAME);
        mMap.put("ADDRESS", ADDRESS);
        mMap.put("PRICE", PRICE);
        mMap.put("PRICE_SPECIAL", PRICE_SPECIAL);
        mMap.put("PRICE_EXTRA", PRICE_EXTRA);
        mMap.put("MAX_GUEST", MAX_GUEST);
        mMap.put("MAX_ROOM", MAX_ROOM);
        mMap.put("MAX_BED", MAX_BED);
        mMap.put("CLEAN_ROOM", CLEAN_ROOM);
        mMap.put("DESCRIPTION", DESCRIPTION);
        mMap.put("INFOMATION", INFOMATION);
        mMap.put("POLICY_CANCLE", POLICY_CANCLE);
        mMap.put("GENLINK", GENLINK);
        mMap.put("PROVINCE_ID", PROVINCE_ID);
        mMap.put("LOCATION_ID", LOCATION_ID);
        mMap.put("COVER", COVER);
        mMap.put("MAX_GUEST_EXIST", MAX_GUEST_EXIST);

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
                    mView.show_edit_room(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_update_state_room(String USERNAME, String GENLINK, String STATE) {
        String sService = "room/update_state_room";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("GENLINK", GENLINK);
        mMap.put("STATE", STATE);
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
                    mView.show_update_state_room(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }
}
