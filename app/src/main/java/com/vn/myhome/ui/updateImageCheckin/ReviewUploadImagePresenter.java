package com.vn.myhome.ui.updateImageCheckin;

import android.util.Log;

import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.apiservice_base.ApiServicePostImage;
import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.models.ObjImageHome;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 26-July-2020
 * Time: 08:38
 * Version: 1.0
 */
public class ReviewUploadImagePresenter {
    private static final String TAG = "UpdateImageCheckinPrese";
    ApiServicePostImage mApiServicePost;
    ReviewUploadImageActivity mView;
    ApiServicePost mApiService;

    public ReviewUploadImagePresenter(ReviewUploadImageActivity mView) {
        mApiServicePost = new ApiServicePostImage();
        mApiService = new ApiServicePost();
        this.mView = mView;
    }
    public void api_upload_image_multil(String USERNAME, String ID_BOOK_SERVICES, List<ObjImageHome> mList, String stype) {
       // mView.showDialogLoading();
        mApiServicePost.apiUploadImageMultil(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.showErrorApiUploadimage();
            }

            @Override
            public void onGetDataSuccess(String objT) {
                String sService = "book/update_img_cin";
                Map<String, String> mMap = new LinkedHashMap<>();
                mMap.put("USERNAME", USERNAME);
                mMap.put("ID_BOOK_SERVICES", ID_BOOK_SERVICES);
                mMap.put("IMG_PATH", objT);
                mMap.put("IMG_TYPE", stype);
                try {
                    mApiService.getApi_Token_Enable(new CallbackData<String>() {
                        @Override
                        public void onGetDataSuccess(String objT) {
                            Log.i(TAG, "onGetDataSuccess: " + objT);
                            mView.showUploadImageSuccess();
                        }

                        @Override
                        public void onGetDataErrorFault(Exception e) {
                            mView.showErrorApiUploadimage();
                        }
                    }, sService, mMap);
                    // mView.show_upload_image_multil(objT);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.showErrorApiUploadimage();
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mList);
    }
    public void uploadImageCheckinService(String USERNAME, String ID_BOOK_SERVICES, String IMG_PATH, String IMG_TYPE){
        String sService = "book/update_img_cin";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("ID_BOOK_SERVICES", ID_BOOK_SERVICES);
        mMap.put("IMG_PATH", IMG_PATH);
        mMap.put("IMG_TYPE", IMG_TYPE);
        try {
            mApiService.getApi_Token_Enable(new CallbackData<String>() {
                @Override
                public void onGetDataSuccess(String objT) {
                    Log.d(TAG, "onGetDataSuccess: "+objT);

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

}
