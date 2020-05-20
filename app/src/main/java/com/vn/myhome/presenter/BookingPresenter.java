package com.vn.myhome.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.myhome.apiservice_base.ApiServicePost;
import com.vn.myhome.callback.CallbackData;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ResponseApi.ListBookingResponse;
import com.vn.myhome.models.ResponseApi.ResponseListBookingService;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 11-August-2019
 * Time: 15:37
 * Version: 1.0
 */
public class BookingPresenter implements InterfaceBooking.Presenter {
    private static final String TAG = "MyHomePresenter";
    ApiServicePost mApiService;
    InterfaceBooking.View mView;

    public BookingPresenter(InterfaceBooking.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    @Override
    public void api_list_bookroom(String USERNAME, String ID_BOOKROOM, String ROOM_NAME, String BOOKING_NAME,
                                  String START_TIME, String END_TIME, String BILLING_STATUS,
                                  String BOOKING_STATUS, String GENLINK) {
        String sService = "book/list_bookroom";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("ID_BOOKROOM", ID_BOOKROOM);
        mMap.put("ROOM_NAME", ROOM_NAME);
        mMap.put("BOOKING_NAME", BOOKING_NAME);
        mMap.put("START_TIME", START_TIME);
        mMap.put("END_TIME", END_TIME);
        mMap.put("BILLING_STATUS", BILLING_STATUS);
        mMap.put("BOOKING_STATUS", BOOKING_STATUS);
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
                    ListBookingResponse obj = new Gson().fromJson(objT, ListBookingResponse.class);
                    mView.show_list_bookroom(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_booking(String USERNAME, String GENLINK, String CHECKIN, String CHECKOUT,
                            String BOOKING_PRICE, String CONTENT, String NAME, String EMAIL, String MOBILE,
                            String BIRTHDAY, String NUM_OF_GUEST, String PRICE_TOTAL_NIGHT, String CLEAN_FEE,
                            String ADD_GUEST_FEE, String CMT, String ADDRESS) {
        String sService = "book/booking";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("GENLINK", GENLINK);
        mMap.put("CHECKIN", CHECKIN);
        mMap.put("CHECKOUT", CHECKOUT);
        mMap.put("BOOKING_PRICE", BOOKING_PRICE);
        mMap.put("CONTENT", CONTENT);
        mMap.put("NAME", NAME);
        mMap.put("EMAIL", EMAIL);
        mMap.put("MOBILE", MOBILE);
        mMap.put("BIRTHDAY", BIRTHDAY);
        mMap.put("NUM_OF_GUEST", NUM_OF_GUEST);
        mMap.put("PRICE_TOTAL_NIGHT", PRICE_TOTAL_NIGHT);
        mMap.put("CLEAN_FEE", CLEAN_FEE);
        mMap.put("ADD_GUEST_FEE", ADD_GUEST_FEE);
        mMap.put("CMT", CMT);
        mMap.put("ADDRESS", ADDRESS);
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
                    mView.show_booking(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_lock_room(String USERNAME, String GENINK, String CHECKIN, String CHECKOUT) {
        String sService = "book/lockroom";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("GENLINK", GENINK);
        mMap.put("CHECKIN", CHECKIN);
        mMap.put("CHECKOUT", CHECKOUT);

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
                    mView.show_lock_room(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_change_billing(String USERNAME, String ID_BOOKROOM, String BILLING_STATUS) {
        String sService = "book/change_billing";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("ID_BOOKROOM", ID_BOOKROOM);
        mMap.put("BILLING_STATUS", BILLING_STATUS);

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
                    mView.show_change_billing(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_change_booking(String USERNAME, String ID_BOOKROOM, String BOOKING_STATUS) {
        String sService = "book/change_booking";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("ID_BOOKROOM", ID_BOOKROOM);
        mMap.put("BOOKING_STATUS", BOOKING_STATUS);

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
                    mView.show_change_booking(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_booking_services(String USERNAME, String GENLINK, String CHECKIN,
                                         String CHECKOUT, String BOOKING_STATUS, String BILLING_STATUS) {
        String sService = "book/get_booking_services2";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("GENLINK", GENLINK);
        mMap.put("CHECKIN", CHECKIN);
        mMap.put("CHECKOUT", CHECKOUT);
        mMap.put("BOOKING_STATUS", BOOKING_STATUS);
        mMap.put("BILLING_STATUS", BILLING_STATUS);

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
                    ResponseListBookingService obj = new Gson().fromJson(objT, ResponseListBookingService.class);
                    mView.show_get_booking_services(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_booking_services2(String USERNAME, String GENLINK, String CHECKIN,
                                      String CHECKOUT, String ID_BOOKROOM, String NOTES) {
        String sService = "book/booking_services2";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("GENLINK", GENLINK);
        mMap.put("CHECKIN", CHECKIN);
        mMap.put("CHECKOUT", CHECKOUT);
        mMap.put("ID_BOOKROOM", ID_BOOKROOM);
        mMap.put("NOTES", NOTES);

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
                    mView.show_booking_services(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_change_booking_services(String USERNAME, String ID_BOOK, String STATE, String BILLING_STATUS) {
        String sService = "book/change_booking_services";
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("ID_BOOK", ID_BOOK);
        mMap.put("STATE", STATE);
        mMap.put("BILLING_STATUS", BILLING_STATUS);

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
                    mView.show_change_booking_services(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_list_day(String USERNAME, String GENLINK) {
        String sService = "book/get_list_day";
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
                    ListBookingResponse obj = new Gson().fromJson(objT, ListBookingResponse.class);
                    mView.show_list_bookroom(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api("");
                }
            }
        }, sService, mMap);
    }
}
