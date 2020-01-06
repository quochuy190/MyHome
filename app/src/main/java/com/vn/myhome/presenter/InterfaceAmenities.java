package com.vn.myhome.presenter;

import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ResponseApi.AmentiniesResponse;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 11-August-2019
 * Time: 15:29
 * Version: 1.0
 */
public interface InterfaceAmenities {
    interface Presenter {
        // Lây danh sách tiện nghi
        void api_get_amenities(String USERNAME);

        void api_get_amenities_room(String USERNAME, String GENLINK);

        void api_update_amenities(String USERNAME, String GENLINK, String AMENITIES);

    }

    interface View {
        void show_error_api(String sService);

        void show_get_amenities();

        void show_amenities_room(AmentiniesResponse objRes);

        void show_update_amenities(ObjErrorApi objError);

    }
}
