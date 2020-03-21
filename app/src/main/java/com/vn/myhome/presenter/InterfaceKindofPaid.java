package com.vn.myhome.presenter;

import com.vn.myhome.models.ObjErrorApi;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 12-February-2020
 * Time: 10:08
 * Version: 1.0
 */
public interface InterfaceKindofPaid {
    interface Presenter {
        void api_change_kind_of_paid(String USERNAME, String ID_BOOK_SERVICE, String KD_PAID);
    }

    interface View {
        void show_error_api(ObjErrorApi sService);

        void show_change_kind_of_paid(ObjErrorApi sService);
    }
}
