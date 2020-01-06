package com.vn.myhome.presenter;

import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ResponseApi.ListBookingResponse;
import com.vn.myhome.models.ResponseApi.ResponseListBookingService;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 14-October-2019
 * Time: 10:14
 * Version: 1.0
 */
public interface InterfaceBooking {
    interface Presenter {
        // Lây danh sách tiện nghi
        void api_list_bookroom(String USERNAME, String ID_BOOKROOM, String ROOM_NAME, String BOOKING_NAME, String START_TIME,
                               String END_TIME, String BILLING_STATUS, String BOOKING_STATUS, String GENLINK);

        void api_booking(String USERNAME, String GENLINK, String CHECKIN, String CHECKOUT, String BOOKING_PRICE,
                         String CONTENT, String NAME, String EMAIL, String MOBILE, String BIRTHDAY, String NUM_OF_GUEST, String PRICE_TOTAL_NIGHT,
                         String CLEAN_FEE, String ADD_GUEST_FEE, String CMT, String ADDRESS);

        void api_lock_room(String USERNAME, String GENINK, String CHECKIN, String CHECKOUT);

        void api_change_billing(String USERNAME, String ID_BOOKROOM, String BILLING_STATUS);

        void api_change_booking(String USERNAME, String ID_BOOKROOM, String BOOKING_STATUS);

        void api_get_booking_services(String USERNAME, String GENLINK, String CHECKIN, String CHECKOUT);

        void api_booking_services2(String USERNAME, String GENLINK, String CHECKIN, String CHECKOUT, String ID_BOOKROOM);

        void api_change_booking_services(String USERNAME, String ID_BOOK, String STATE, String BILLING_STATUS);

        void api_get_list_day(String USERNAME, String GENLINK);
    }

    interface View {
        void show_error_api(String sService);

        void show_lock_room(ObjErrorApi objError);

        void show_list_bookroom(ListBookingResponse objRes);

        void show_api_get_list_day(ListBookingResponse objRes);

        void show_booking(ObjErrorApi objError);

        void show_change_booking(ObjErrorApi objError);

        void show_booking_services(ObjErrorApi objError);

        void show_get_booking_services(ResponseListBookingService objRes);

        void show_change_billing(ObjErrorApi objError);

        void show_change_booking_services(ObjErrorApi objError);
    }
}
