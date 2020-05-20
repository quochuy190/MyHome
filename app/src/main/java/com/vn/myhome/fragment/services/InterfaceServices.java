package com.vn.myhome.fragment.services;

import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ResponseApi.ResponseBookCarDetail;
import com.vn.myhome.models.ResponseApi.ResponsePriceEstimates;
import com.vn.myhome.models.ResponseApi.RouteResponse;
import com.vn.myhome.network.response.ResponGetListBookCar;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 30-March-2020
 * Time: 00:29
 * Version: 1.0
 */
public interface InterfaceServices {
    interface Presenter {
        void api_get_type_car(String USERNAME);

        void api_get_route_car(String USERNAME);

        void api_get_price_estimates(String USERNAME, String CAR_TYPE, String ROUTE_TYPE);

        void api_bookcar(String USERNAME, String BOOKER_TEL, String BOOKER_NAME, String SO_TK, String TEN_TK,
                         String TEN_NH, String TEN_CN, String CAR_TYPE, String ROUTE_TYPE, String RADA_PRICE,
                         String TOTAL_PRICE, String EXTRA_PRICE, String CUSTOMER_TEL, String CUSTOMER_NAME,
                         String PAYMENT_TYPE, String ADDRESS_SOURCE, String ADDRESS_DES, String SCHEDULE, String NOTES);

        void api_get_bookcar_detail(String USERNAME, String BOOK_ID);

        void api_get_list_bookcar(String USERNAME, String BOOKER_TEL);

        void api_get_list_bookcar_pre(String USERNAME);

        void api_update_billing(String USERNAME, String ID);
    }

    interface View {
        void show_error_api(String sService);

        void show_get_type_car(RouteResponse objError);

        void show_get_route_car(RouteResponse objError);

        void show_get_price_estimates(ResponsePriceEstimates objRes);

        void show_bookcar(ObjErrorApi objResCity);

        void show_get_bookcar_detail(ResponseBookCarDetail objError);

        void show_list_book_car(ResponGetListBookCar objError);

        void show_list_book_car_pre(ResponGetListBookCar objError);

        void show_update_billing(ObjErrorApi objError);
    }
}
