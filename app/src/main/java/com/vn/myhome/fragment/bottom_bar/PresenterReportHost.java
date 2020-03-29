package com.vn.myhome.fragment.bottom_bar;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.network.RequestApiInterface;
import com.vn.myhome.network.response.ResponReport;

import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Retrofit;

public class PresenterReportHost {
    private static final String TAG = "PresenterReportHost";
    FragmentReportHost fragment;
    Retrofit retrofit;
    RequestApiInterface mApi;
    ApiServicePost mApiService;
    public PresenterReportHost(FragmentReportHost fragment) {
        this.fragment = fragment;
        mApiService = new ApiServicePost();
    }

    public void get_rp_revenue(String sUserName) {
        String sService="report/rp_revenue";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", sUserName);
        mApiService.getApi_Token_Enable(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ResponReport objReport = new Gson().fromJson(objT, ResponReport.class);
                    fragment.show_rp_venuue(objReport);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }, sService, mMap);
    }

    public void getRpBook(String sUserName, String getlink) {
        fragment.showDialogLoading();
        String sService="report/rp_book_month";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", sUserName);
        mMap.put("GENLINK", getlink);
        mApiService.getApi_Token_Enable(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                fragment.hideDialogLoading();
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                fragment.hideDialogLoading();
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ResponReport objReport = new Gson().fromJson(objT, ResponReport.class);
                    fragment.show_rp_book(objReport);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }, sService, mMap);
    }

    public void get_rp_cost(String sUserName) {
        String sService="report/rp_cost";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", sUserName);
        mApiService.getApi_Token_Enable(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ResponReport objReport = new Gson().fromJson(objT, ResponReport.class);
                    fragment.show_rp_cost(objReport);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }, sService, mMap);
    }
    public void get_rp_profit(String sUserName) {
        String sService="report/rp_profit";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", sUserName);
        mApiService.getApi_Token_Enable(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ResponReport objReport = new Gson().fromJson(objT, ResponReport.class);
                    fragment.show_rp_profit(objReport);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }, sService, mMap);
    }
}
