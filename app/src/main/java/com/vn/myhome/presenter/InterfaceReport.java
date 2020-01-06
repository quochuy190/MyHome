package com.vn.myhome.presenter;

import com.vn.myhome.models.ReportObj;
import com.vn.myhome.models.ResponseApi.ResponseGetListReportDetail;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 18-November-2019
 * Time: 17:11
 * Version: 1.0
 */
public interface InterfaceReport {
    interface Presenter {
        void api_get_report(String USERNAME, String MONTH);

        void api_get_report_detail(String USERNAME, String ROOM_NAME, String BOOKING_NAME,
                                   String START_TIME, String END_TIME);
    }

    interface View {
        void show_error_api(String sService);

        void show_get_report(ReportObj objReport);

        void show_get_report_detail(ResponseGetListReportDetail objReport);


    }
}
