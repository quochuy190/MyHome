package com.vn.myhome.presenter;

import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ResponseApi.ResponGetListNotify;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 14-October-2019
 * Time: 10:14
 * Version: 1.0
 */
public interface InterfaceNotify {
    interface Presenter {

        void api_get_list_notifi(String USERNAME, String PIPAGE, String NUMOFPAGE);

        void api_update_list_notifi(String USERNAME, String LISTID);

    }

    interface View {
        void show_error_api(String sService);

        void show_list_notifi(ResponGetListNotify objError);

        void show_update_list_notifi(ObjErrorApi objError);

    }
}
