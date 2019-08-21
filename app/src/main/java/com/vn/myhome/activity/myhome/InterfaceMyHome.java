package com.vn.myhome.activity.myhome;

import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ResponseApi.GetAlbumImageHomeResponse;
import com.vn.myhome.models.ResponseApi.GetImageCoverResponse;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;

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
    }

    interface View {
        void show_error_api(String sService);

        void show_get_mylist_room(GetRoomResponse objRes);

    }
}
