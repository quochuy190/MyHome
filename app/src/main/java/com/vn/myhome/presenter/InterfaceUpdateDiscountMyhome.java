package com.vn.myhome.presenter;

import com.vn.myhome.models.ObjErrorApi;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 12-February-2020
 * Time: 10:08
 * Version: 1.0
 */
public interface InterfaceUpdateDiscountMyhome {
    interface Presenter {
        void api_update_discount(String USERNAME, String GENLINK, String PERCENT, String START_TIME, String END_TIME);
    }

    interface View {
        void show_error_api(ObjErrorApi sService);

        void show_update_discount(ObjErrorApi sService);
    }
}
