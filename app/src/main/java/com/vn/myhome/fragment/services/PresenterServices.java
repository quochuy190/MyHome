package com.vn.myhome.fragment.services;

import android.util.Log;

import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.callback.CallbackData;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-May-2019
 * Time: 10:44
 * Version: 1.0
 */
public class PresenterServices implements InterfaceServices.Presenter {
    private static final String TAG = "PresenterLogin";
    ApiServicePost mApiService;
    InterfaceServices.View mView;

    public PresenterServices(InterfaceServices.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }


    @Override
    public void api_get_type_car(String USERNAME) {
        String sService = "book/list_bookroom";
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
                    mView.show_get_type_car(null);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_route_car(String USERNAME) {

    }

    @Override
    public void api_get_price_estimates(String USERNAME, String CAR_TYPE, String ROUTE_TYPE) {

    }

    @Override
    public void api_bookcar(String USERNAME, String BOOKER_TEL, String BOOKER_NAME, String SO_TK, String TEN_TK, String TEN_NH, String TEN_CN, String CAR_TYPE, String ROUTE_TYPE, String RADA_PRICE, String TOTAL_PRICE, String EXTRA_PRICE, String CUSTOMER_TEL, String CUSTOMER_NAME, String PAYMENT_TYPE) {

    }

    @Override
    public void api_get_bookcar_detail(String USERNAME, String BOOK_ID) {

    }
}
