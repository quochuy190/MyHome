package com.vn.myhome.upload_media;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.apiservice_base.ApiServicePostImage;
import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjImageHome;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PresenterUploadImage implements InterfaceUploadImage.Presenter {
    private static final String TAG = "PresenterUploadImage";
    ApiServicePostImage mApiService;
    InterfaceUploadImage.View mView;
    ApiServicePost mApiServiceRoom;

    public PresenterUploadImage(InterfaceUploadImage.View mView) {
        mApiService = new ApiServicePostImage();
        mApiServiceRoom = new ApiServicePost();
        this.mView = mView;
    }

    @Override
    public void api_upload_image(String part, String name) {
        mApiService.apiUploadImage(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api_uploadimage();
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ObjErrorApi objEror = new Gson().fromJson(objT, ObjErrorApi.class);
                    mView.show_upload_image(objT);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api_uploadimage();
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, part);
    }

    @Override
    public void api_upload_image_only(String part) {
        mApiService.apiUploadImage(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api_uploadimage();
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    mView.show_upload_image(objT);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api_uploadimage();
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, part);
    }

    @Override
    public void api_upload_image_multil(String USERNAME, String GENLINK, List<ObjImageHome> mList) {
        mApiService.apiUploadImageMultil(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api_uploadimage();
            }

            @Override
            public void onGetDataSuccess(String objT) {
                String sService = "room/update_image";
                Map<String, String> mMap = new LinkedHashMap<>();
                mMap.put("USERNAME", USERNAME);
                mMap.put("GENLINK", GENLINK);
                mMap.put("PATH", objT);
                try {
                    mApiServiceRoom.getApi_Token_Enable(new CallbackData<String>() {
                        @Override
                        public void onGetDataSuccess(String objT) {
                            Log.i(TAG, "onGetDataSuccess: " + objT);
                        }

                        @Override
                        public void onGetDataErrorFault(Exception e) {

                        }
                    }, sService, mMap);
                   // mView.show_upload_image_multil(objT);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api_uploadimage();
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mList);
    }
}
