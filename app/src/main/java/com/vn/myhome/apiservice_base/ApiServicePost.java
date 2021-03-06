package com.vn.myhome.apiservice_base;

import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.untils.SharedPrefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @description
 * @authour: $User
 * @createdate $Date
 */
public class ApiServicePost {
    InterfaceApiPost apiService;
    InterfaceApiPostAll apiRestFul_All;

    public void getApiService(final CallbackData<String> callbackData, String sService, Map<String, String> mData) {
        apiService = InterfaceApiPost.retrofit_restful.create(InterfaceApiPost.class);
        Call<ResponseBody> getApiservice = apiService.getApiServiceRest(sService, mData);
        getApiservice.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String jsonString = null;
                JSONObject jobj;
                JSONArray jArray;
                try {
                    if (response.body() != null) {
                        jsonString = response.body().string();
                        jobj = new JSONObject(jsonString);
                        String c = jobj.toString();
                        /*jsonString = jsonString.replaceAll("\\\\", "");
                        jsonString = jsonString.substring(11, jsonString.length() - 2);*/
                        callbackData.onGetDataSuccess(c);
                    }
                } catch (IOException e) {
                    callbackData.onGetDataErrorFault(e);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataErrorFault(new Exception(t));
            }
        });
    }

    public void getApi_Token_Enable(final CallbackData<String> callbackData, String sService,
                                      Map<String, String> mData) {
        String sToken = "";
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        if (objLogin != null && objLogin.getTOKEN() != null) {
            sToken = objLogin.getTOKEN();
        }
        apiRestFul_All = InterfaceApiPostAll.retrofit_restful_all.create(InterfaceApiPostAll.class);
        Call<ResponseBody> getApiservice = apiRestFul_All.getApiServiceRest("Bearer " + sToken, sService, mData);
        getApiservice.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String jsonString = null;
                JSONObject jobj;
                JSONArray jArray;
                try {
                    if (response.body() != null) {
                        jsonString = response.body().string();
                      /*  jobj = new JSONObject(jsonString);
                        String c = jobj.getString("return");*/
                        callbackData.onGetDataSuccess(jsonString);
                    }
                } catch (IOException e) {
                    callbackData.onGetDataErrorFault(e);
                } catch (Exception e) {
                    callbackData.onGetDataErrorFault(e);
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
