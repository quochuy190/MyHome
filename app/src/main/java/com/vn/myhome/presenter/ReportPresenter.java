package com.vn.myhome.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.models.ReportObj;
import com.vn.myhome.models.ResponseApi.ResponseGetListReportDetail;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 11-August-2019
 * Time: 15:37
 * Version: 1.0
 */
public class ReportPresenter implements InterfaceReport.Presenter {
    private static final String TAG = "MyHomePresenter";
    ApiServicePost mApiService;
    InterfaceReport.View mView;

    public ReportPresenter(InterfaceReport.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }


    @Override
    public void api_get_report(String USERNAME, String MONTH) {
        String sService = "report/get_report";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("MONTH", MONTH);
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
                    ReportObj obj = new Gson().fromJson(objT, ReportObj.class);
                    mView.show_get_report(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_report_detail(String USERNAME, String ROOM_NAME, String BOOKING_NAME,
                                      String START_TIME, String END_TIME) {
        String sService = "report/get_report_detail";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("ROOM_NAME", ROOM_NAME);
        mMap.put("BOOKING_NAME", BOOKING_NAME);
        mMap.put("START_TIME", START_TIME);
        mMap.put("END_TIME", END_TIME);
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
                    ResponseGetListReportDetail obj = new Gson().fromJson(objT, ResponseGetListReportDetail.class);
                    mView.show_get_report_detail(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }
}
