package com.vn.myhome.apiservice_base;

import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.models.ObjImageHome;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @description
 * @authour: $User
 * @createdate $Date
 */
public class ApiServicePostImage {
    List<File> lisFile = new ArrayList<>();
    ApiSeviceUploadImage apiService;
    InterfaceApiUpdateImage apiUploadImage;
    InterfaceApiUpdateImageMultilPath apiUpdateImageMultilPath;

    public void apiUploadImage(final CallbackData<String> callbackData, String sUrl) {
        apiUploadImage = InterfaceApiUpdateImage.retrofit_upload_image.create(InterfaceApiUpdateImage.class);
        File file = new File(sUrl);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
// create list of file parts (photo, video, ...)
        List<MultipartBody.Part> parts = new ArrayList<>();

// create upload service client

    /*    if (arrayList != null) {
            // create part for file (photo, video, ...)
            for (int i = 0; i < arrayList.size(); i++) {
                // parts.add(prepareFilePart("image"+i, arrayList.get(i)));
                parts.add(MultipartBody.Part.createFormData("image", file.getName(), requestFile));
            }
        }*/

        Call<ResponseBody> getApiservice = apiUploadImage.uploadImage(body);
        getApiservice.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String jsonString = null;
                JSONObject jobj;
                try {
                    if (response.body() != null) {
                        jsonString = response.body().string();
                        callbackData.onGetDataSuccess(jsonString);
                    }
                } catch (IOException e) {
                    callbackData.onGetDataErrorFault(e);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataErrorFault(new Exception(t));
            }
        });
    }

    public void apiUploadImageMultil(final CallbackData<String> callbackData, List<ObjImageHome> mListImage) {
        apiUpdateImageMultilPath = InterfaceApiUpdateImageMultilPath.
                retrofit_upload_image_multil.create(InterfaceApiUpdateImageMultilPath.class);
/*
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);*/
// create list of file parts (photo, video, ...)
        List<MultipartBody.Part> parts = new ArrayList<>();

// create upload service client

        if (mListImage != null) {
            // create part for file (photo, video, ...)
            for (int i = 0; i < mListImage.size(); i++) {
                // parts.add(prepareFilePart("image"+i, arrayList.get(i)));
                if (mListImage.get(i) != null && mListImage.get(i).getsPath() != null) {
                    File file = new File(mListImage.get(i).getsPath());
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    parts.add(MultipartBody.Part.createFormData("image", file.getName(), requestFile));
                }
            }
            if (parts.size() == 0)
                return;
        }
        Call<ResponseBody> getApiservice = apiUpdateImageMultilPath.uploadImagesMultil(parts);
        getApiservice.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String jsonString = null;
                JSONObject jobj;
                try {
                    if (response.body() != null) {
                        jsonString = response.body().string();
                        callbackData.onGetDataSuccess(jsonString);
                    }
                } catch (IOException e) {
                    callbackData.onGetDataErrorFault(e);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataErrorFault(new Exception(t));
            }
        });
    }

}
