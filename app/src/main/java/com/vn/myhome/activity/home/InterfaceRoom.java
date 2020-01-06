package com.vn.myhome.activity.home;

import com.vn.myhome.models.ObjErrorApi;
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
public interface InterfaceRoom {
    interface Presenter {
        // Lây danh sách phòng trang chủ
        void api_get_listroom_idx(String USERNAME, String PAGE, String NUMOFPAGE);

        void api_get_cover_idx(String USERNAME);

        void api_search_home(String USERNAME, String LOCATION, String CHECKIN, String CHECKOUT,
                             String PEOPLE, String PRICE_FROM, String PRICE_TO, String AMENITIES);

        void api_get_room_detail(String USERNAME, String GENLINK);

        void api_get_album_image(String USERNAME, String GENLINK);
        void api_check_lock(String USERNAME, String GENLINK);
    }

    interface View {
        void show_error_api(String sService);

        void show_get_listroom_idx(GetRoomResponse objRes);

        void show_get_cover_idx(GetImageCoverResponse obj);

        void show_search_home(GetRoomResponse objRes);

        void show_get_room_detail(ObjHomeStay objRoom);

        void show_get_album_image(GetAlbumImageHomeResponse objRes);
        void show_check_lock(ObjErrorApi objRes);
    }
}
