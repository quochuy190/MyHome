package com.vn.myhome.activity.home;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ResponseApi.GetAlbumImageHomeResponse;
import com.vn.myhome.models.ResponseApi.GetImageCoverResponse;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 11-August-2019
 * Time: 15:37
 * Version: 1.0
 */
public class RoomPresenter implements InterfaceRoom.Presenter {
    private static final String TAG = "PresenterLogin";
    ApiServicePost mApiService;
    InterfaceRoom.View mView;

    public RoomPresenter(InterfaceRoom.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    @Override
    public void api_get_listroom_idx(String USERNAME, String PAGE, String NUMOFPAGE) {
        String sService = "room/get_listroom_idx";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
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
                    GetRoomResponse obj = new Gson().fromJson(objT, GetRoomResponse.class);
                    mView.show_get_listroom_idx(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_cover_idx(String USERNAME) {
        String sService = "get_cover_idx";
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
                    GetImageCoverResponse obj = new Gson().fromJson(objT, GetImageCoverResponse.class);
                    mView.show_get_cover_idx(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_search_home(String USERNAME, String LOCATION, String CHECKIN, String CHECKOUT,
                                String PEOPLE, String PRICE_FROM, String PRICE_TO, String AMENITIES) {
        String sService = "room/search_home";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("LOCATION", LOCATION);
        mMap.put("CHECKIN", CHECKIN);
        mMap.put("CHECKOUT", CHECKOUT);
        mMap.put("PEOPLE", PEOPLE);
        mMap.put("PRICE_FROM", PRICE_FROM);
        mMap.put("PRICE_TO", PRICE_TO);
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
                    GetRoomResponse obj = new Gson().fromJson(objT, GetRoomResponse.class);
                    mView.show_search_home(obj);
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
                    mView.show_get_room_detail(obj);
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
                    GetAlbumImageHomeResponse obj = new Gson().fromJson(objT, GetAlbumImageHomeResponse.class);
                    mView.show_get_album_image(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_check_lock(String USERNAME, String GENLINK) {
        String sService = "book/check_lock";
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
                    mView.show_check_lock(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }
}
