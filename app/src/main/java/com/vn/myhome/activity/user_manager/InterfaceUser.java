package com.vn.myhome.activity.user_manager;

import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ResponseApi.ResponInfo;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 05-August-2019
 * Time: 15:13
 * Version: 1.0
 */
public interface InterfaceUser {
    interface Presenter {
        void api_get_user_info(String USERNAME, String USERID);

        void api_update_user_info(String USERNAME, String USERID, String PASSWORD, String MOBILE, String EMAIL,
                                  String FULL_NAME, String DOB, String USER_TYPE, String AVATAR, String STATE,
                                  String ADDRESS, String ID_PROVINCE, String STK, String TENTK, String TENNH,
                                  String TENCN);

        void api_get_listuser(String USERNAME, String USERID, String MOBILE, String EMAIL, String FULL_NAME,
                              String USER_TYPE, String STATE, String ADDRESS, String PAGE, String NUMOFPAGE);

        void api_change_pass(String USERNAME, String OLD_PASS, String NEW_PASS);
    }

    interface View {
        void show_error_api(String sService);

        void show_get_user_info(ResponInfo objLogin);

        void show_update_user_info(ObjErrorApi objError);

        void show_get_listuser(ResponInfo objRes);

        void show_change_pass(ObjErrorApi objError);
    }
}
