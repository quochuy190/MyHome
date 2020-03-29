package com.vn.myhome.fragment.services;

import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ResponseApi.CityResponse;
import com.vn.myhome.models.ResponseApi.GetTypeResponse;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 30-March-2020
 * Time: 00:29
 * Version: 1.0
 */
public class InterfaceServices {
    interface Presenter {
        void api_get_type_car(String USERNAME);

        void api_get_route_car(String USERNAME);

        void api_get_price_estimates(String USERNAME, String CAR_TYPE, String ROUTE_TYPE);

        void api_bookcar(String USERNAME, String BOOKER_TEL, String BOOKER_NAME, String SO_TK, String TEN_TK,
                         String TEN_NH, String TEN_CN, String CAR_TYPE, String ROUTE_TYPE, String RADA_PRICE,
                         String TOTAL_PRICE, String EXTRA_PRICE, String CUSTOMER_TEL, String CUSTOMER_NAME, String PAYMENT_TYPE);

        void api_get_bookcar_detail(String USERNAME, String BOOK_ID);


    }

    interface View {
        void show_error_api(String sService);

        void show_get_type_car(ObjLogin objLogin);

        void show_get_route_car(ObjErrorApi objError);

        void show_get_price_estimates(GetTypeResponse objRes);

        void show_bookcar(CityResponse objResCity);

        void show_get_bookcar_detail(ObjErrorApi objError);
    }
}
