package com.vn.myhome.presenter;

import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.models.ResponseApi.ResponListImageHome;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 11-August-2019
 * Time: 15:29
 * Version: 1.0
 */
public interface InterfaceMyHome {
    interface Presenter {
        // Lây danh sách phòng trang chủ
        void api_get_mylist_room(String USERNAME, String OPT);

        void api_get_room_detail(String USERNAME, String GENLINK);

        void api_new_room(String USERNAME);

        void api_del_room(String USERNAME, String GENLINK);

        void api_get_album_image(String USERNAME, String GENLINK);

        void api_edit_room(String USERNAME, String NAME, String ADDRESS, String PRICE,
                           String PRICE_SPECIAL, String PRICE_EXTRA, String MAX_GUEST, String MAX_ROOM,
                           String MAX_BED, String CLEAN_ROOM, String DESCRIPTION, String INFOMATION, String POLICY_CANCLE,
                           String GENLINK, String PROVINCE_ID, String LOCATION_ID, String COVER, String MAX_GUEST_EXIST
        );

        void api_update_state_room(String USERNAME, String GENLINK, String STATE);
    }

    interface View {
        void show_error_api(String sService);

        void show_get_mylist_room(GetRoomResponse objRes);

        void show_room_detail(ObjHomeStay objRes);

        void show_new_room(ObjErrorApi objError);

        void show_edit_room(ObjErrorApi objError);

        void show_delete_room(ObjErrorApi objError);

        void show_update_state_room(ObjErrorApi objError);

        void show_get_album_image(ResponListImageHome objRes);
    }
}
