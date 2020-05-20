package com.vn.myhome.fragment.services;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ResponseApi.ResponseBookCarDetail;
import com.vn.myhome.models.ResponseApi.ResponsePriceEstimates;
import com.vn.myhome.models.ResponseApi.RouteResponse;
import com.vn.myhome.network.response.ResponGetListBookCar;

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
    private static final String TAG = "PresenterServices";
    ApiServicePost mApiService;
    InterfaceServices.View mView;

    public PresenterServices(InterfaceServices.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }


    @Override
    public void api_get_type_car(String USERNAME) {
        String sService = "bookcar/get_car";
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
                    RouteResponse obj = new Gson().fromJson(objT, RouteResponse.class);
                    mView.show_get_type_car(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_route_car(String USERNAME) {
        String sService = "bookcar/get_route";
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
                    RouteResponse obj = new Gson().fromJson(objT, RouteResponse.class);
                    mView.show_get_route_car(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_price_estimates(String USERNAME, String CAR_TYPE, String ROUTE_TYPE) {
        String sService = "bookcar/get_price_estimates";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("CAR_TYPE", CAR_TYPE);
        mMap.put("ROUTE_TYPE", ROUTE_TYPE);

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
                    ResponsePriceEstimates obj = new Gson().fromJson(objT, ResponsePriceEstimates.class);
                    mView.show_get_price_estimates(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_bookcar(String USERNAME, String BOOKER_TEL, String BOOKER_NAME, String SO_TK,
                            String TEN_TK, String TEN_NH, String TEN_CN, String CAR_TYPE, String ROUTE_TYPE,
                            String RADA_PRICE, String TOTAL_PRICE, String EXTRA_PRICE, String CUSTOMER_TEL,
                            String CUSTOMER_NAME, String PAYMENT_TYPE, String ADDRESS_SOURCE, String ADDRESS_DES,
                            String SCHEDULE, String NOTES) {
        String sService = "bookcar/bookcar";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("BOOKER_TEL", BOOKER_TEL);
        mMap.put("BOOKER_NAME", BOOKER_NAME);
        mMap.put("SO_TK", SO_TK);
        mMap.put("TEN_TK", TEN_TK);
        mMap.put("TEN_NH", TEN_NH);
        mMap.put("TEN_CN", TEN_CN);
        mMap.put("CAR_TYPE", CAR_TYPE);
        mMap.put("ROUTE_TYPE", ROUTE_TYPE);
        mMap.put("RADA_PRICE", RADA_PRICE);
        mMap.put("TOTAL_PRICE", TOTAL_PRICE);
        mMap.put("EXTRA_PRICE", EXTRA_PRICE);
        mMap.put("CUSTOMER_TEL", CUSTOMER_TEL);
        mMap.put("CUSTOMER_NAME", CUSTOMER_NAME);
        mMap.put("PAYMENT_TYPE", PAYMENT_TYPE);
        mMap.put("ADDRESS_SOURCE", ADDRESS_SOURCE);
        mMap.put("ADDRESS_DES", ADDRESS_DES);
        mMap.put("SCHEDULE", SCHEDULE);
        mMap.put("NOTE", NOTES);
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
                    mView.show_bookcar(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_bookcar_detail(String USERNAME, String BOOK_ID) {
        String sService = "bookcar/get_bookcar_detail";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("BOOK_ID", BOOK_ID);

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
                    ResponseBookCarDetail obj = new Gson().fromJson(objT, ResponseBookCarDetail.class);
                    mView.show_get_bookcar_detail(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_list_bookcar(String USERNAME, String BOOKER_TEL) {
        String sService = "bookcar/get_list_bookcar";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("BOOKER_TEL", BOOKER_TEL);

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
                    ResponGetListBookCar obj = new Gson().fromJson(objT, ResponGetListBookCar.class);
                    mView.show_list_book_car(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_list_bookcar_pre(String USERNAME) {
        String sService = "bookcar/get_list_bookcar_pre";
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
                    ResponGetListBookCar obj = new Gson().fromJson(objT, ResponGetListBookCar.class);
                    mView.show_list_book_car_pre(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_update_billing(String USERNAME, String ID) {
        String sService = "bookcar/update_billing";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("ID", ID);

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
                    mView.show_update_billing(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }
}
