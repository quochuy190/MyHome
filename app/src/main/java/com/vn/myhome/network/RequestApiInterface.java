package com.vn.myhome.network;


import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.network.response.ResponGetBannerCity;
import com.vn.myhome.network.response.ResponGetListBookCar;
import com.vn.myhome.network.response.ResponReport;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by nguyenhuyquyet on 7/27/17.
 */

public interface RequestApiInterface {
    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("/room/get_banner_city")
    Observable<ResponGetBannerCity> get_banner_city(
            @FieldMap Map<String, String> data);

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("/room/get_listroom_idx")
    Observable<GetRoomResponse> get_listroom_idx(
            @FieldMap Map<String, String> data);

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("/room/search_home2")
    Observable<GetRoomResponse> get_search_home2(@FieldMap Map<String, String> data);


    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("/room/get_mylist_room")
    Observable<GetRoomResponse> get_list_myhome(@FieldMap Map<String, String> data);

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("/report/rp_book_month")
    Observable<ResponReport> rp_book_month(@FieldMap Map<String, String> data);

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("/report/rp_revenue")
    Observable<ResponReport> rp_revenue(@FieldMap Map<String, String> data);

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("/report/rp_cost")
    Observable<ResponReport> rp_cost(@FieldMap Map<String, String> data);

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("/report/rp_profit")
    Observable<ResponReport> rp_profit(@FieldMap Map<String, String> data);

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("/bookcar/get_list_bookcar")
    Observable<ResponGetListBookCar> get_list_bookcar(@FieldMap Map<String, String> data);

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("/bookcar/get_list_bookcar_pre")
    Observable<ResponGetListBookCar> get_list_bookcar_pre(@FieldMap Map<String, String> data);
}
