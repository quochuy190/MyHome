package com.vn.myhome.presenter;

import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ResponseApi.ResponseGetContact;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 19-November-2019
 * Time: 01:20
 * Version: 1.0
 */
public interface InterfaceContact {
    interface Presenter {
        void api_get_contact(String USERNAME, String NAME, String CMT,
                             String MOBILE, String EMAIL, String ID_PROVINCE);

        void api_add_contact(String USERNAME, String NAME,
                             String MOBILE, String EMAIL, String CMT, String DOB,
                             String ADDRESS, String ID_PROVINCE);

        void api_edit_contact(String USERNAME, String ID, String NAME,
                              String MOBILE, String EMAIL, String CMT, String DOB,
                              String ADDRESS, String ID_PROVINCE);

        void api_del_contact(String USERNAME, String ID);

    }

    interface View {
        void show_error_api(String sService);

        void show_get_contact(ResponseGetContact objContact);

        void show_add_contact(ObjErrorApi objError);

        void show_edit_contact(ObjErrorApi objError);

        void show_del_contact(ObjErrorApi objError);
    }
}
