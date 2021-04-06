package com.vn.myhome.ui.updateImageCheckin;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.apiservice_base.ApiServicePostImage;
import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ResponseApi.GetImageServiceResponse;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 26-July-2020
 * Time: 08:38
 * Version: 1.0
 */
public class UpdateImageCheckinPresenter {
    private static final String TAG = "UpdateImageCheckinPrese";
    ApiServicePostImage mApiService;
    UpdateImageCheckinActivity mView;
    ApiServicePost mApiServiceGetImage;

    public UpdateImageCheckinPresenter(UpdateImageCheckinActivity mView) {
        mApiService = new ApiServicePostImage();
        mApiServiceGetImage = new ApiServicePost();
        this.mView = mView;
    }
    public void getAllImageCheckinService(String USERNAME, String ID_BOOK_SERVICES){
        String sService = "book/get_img_svs";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("ID_BOOK_SERVICES", ID_BOOK_SERVICES);
        try {
            mApiServiceGetImage.getApi_Token_Enable(new CallbackData<String>() {
                @Override
                public void onGetDataSuccess(String objT) {
                    Log.d(TAG, "onGetDataSuccess: "+objT);
                    GetImageServiceResponse objError = new Gson().fromJson(objT, GetImageServiceResponse.class);
                    if (objError != null && objError.getERROR().equals("0000")) {
                        if (objError.getINFO() != null) {
                            Log.d(TAG, "onGetDataSuccess: "+objError.getINFO().size());
                            mView.showListImageService(objError.getINFO());
                        }
                    }
                }

                @Override
                public void onGetDataErrorFault(Exception e) {

                }
            }, sService, mMap);
            // mView.show_upload_image_multil(objT);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "getAllImageCheckinService error: " + e);
        }
    }
    public void deleteImageCheckinService(String USERNAME, String ID_BOOK_SERVICES, String ID_IMG){
        mView.showDialogLoading();
        String sService = "book/del_imageci";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("ID_BOOK_SERVICES", ID_BOOK_SERVICES);
        mMap.put("ID_IMG", ID_IMG);
        try {
            mApiServiceGetImage.getApi_Token_Enable(new CallbackData<String>() {
                @Override
                public void onGetDataSuccess(String objT) {
                    mView.hideDialogLoading();
                    Log.d(TAG, "onGetDataSuccess: "+objT);
                    ObjErrorApi objError = new Gson().fromJson(objT, ObjErrorApi.class);
                    if (objError != null && objError.getERROR().equals("0000")) {
                        mView.showDeleteImage(true, objError.getRESULT());
                    }else {
                        mView.showDeleteImage(false, objError.getRESULT());
                    }
                }

                @Override
                public void onGetDataErrorFault(Exception e) {
                    mView.hideDialogLoading();
                    mView.showDeleteImage(false, "Kết nối thất bại, vui lòng thử lại sau");
                }
            }, sService, mMap);
            // mView.show_upload_image_multil(objT);
        } catch (Exception e) {
            e.printStackTrace();
            mView.hideDialogLoading();
            mView.showDeleteImage(false, "Kết nối thất bại, vui lòng thử lại sau");
        }
    }
}
