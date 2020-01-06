package com.vn.myhome.upload_media;

import com.vn.myhome.models.ObjImageHome;

import java.util.List;

public interface InterfaceUploadImage {
    interface Presenter {
        void api_upload_image(String part, String name);

        void api_upload_image_only(String part);

        void api_upload_image_multil(String USERNAME, String GENLINK, List<ObjImageHome> mList);
    }

    interface View {
        void show_error_api_uploadimage();

        void show_upload_image(String objError);

        void show_upload_image_multil(String sListImage);
    }
}
