package com.vn.myhome.presenter;

import com.vn.myhome.models.ResponseApi.VersionResponse;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 19-November-2019
 * Time: 01:20
 * Version: 1.0
 */
public interface InterfaceUpdateVersion {
    interface Presenter {
        void api_get_version();
    }

    interface View {
        void show_error_api(String sService);

        void show_get_version(VersionResponse objError);
    }
}
