package com.vn.myhome.activity.login;

import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ResponseApi.CityResponse;
import com.vn.myhome.models.ResponseApi.GetTypeResponse;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 05-August-2019
 * Time: 15:13
 * Version: 1.0
 */
public interface InterfaceLogin {
    interface Presenter {
        void api_login(String sService, String USERNAME, String PASSWORD);

        void api_reg_user(String sService, String USERNAME, String PASSWORD, String EMAIL, String FULL_NAME,
                          String USER_TYPE, String MOBILE, String ADDRESS, String DES, String ID_PROVINCE,
                          String STK, String TENTK, String TENNH, String TENCN);

        void api_get_type(String USERNAME);

        void api_get_city(String USERNAME);

        void api_update_device(String USERNAME,String VERSION, String DEVICE_MODEL, String TOKEN_KEY,
                               String OS_VERSION, String DEVICE_TYPE, String UUID );


    }

    interface View {
        void show_error_api(String sService);

        void show_login(ObjLogin objLogin);

        void show_reg_user(ObjErrorApi objError);

        void show_get_type(GetTypeResponse objRes);

        void show_get_city(CityResponse objResCity);

        void show_update_device(ObjErrorApi objError);
    }
}
