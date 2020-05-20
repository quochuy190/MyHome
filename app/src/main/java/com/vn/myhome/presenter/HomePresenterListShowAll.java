package com.vn.myhome.presenter;

import android.util.Log;

import com.vn.myhome.activity.home.ActivityListHomestayShowAll;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.network.RequestApiInterface;
import com.vn.myhome.network.RetrofitClient;

import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 04-March-2020
 * Time: 17:15
 * Version: 1.0
 */
public class HomePresenterListShowAll {
    private static final String TAG = "HomePresenterNew";
    ActivityListHomestayShowAll mView;
    Retrofit retrofit;
    RequestApiInterface mApi;

    public HomePresenterListShowAll(ActivityListHomestayShowAll mView) {
        this.mView = mView;
        retrofit = RetrofitClient.getInstance();
        mApi = retrofit.create(RequestApiInterface.class);
    }


    public void getListHomeStay(String sUserName, String Page) {
        Map<String, String> mMap_get_room = new LinkedHashMap<>();
        mMap_get_room.put("USERNAME", sUserName);
        mMap_get_room.put("PAGE", Page);
        mMap_get_room.put("NUMOFPAGE", "20");

        Observable<GetRoomResponse> userObservable = mApi.get_listroom_idx(mMap_get_room)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());
        userObservable.subscribe(new Observer<GetRoomResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: " );
            }

            @Override
            public void onNext(GetRoomResponse getRoomResponse) {
                if (getRoomResponse.getERROR().equals("0000")){
                    if (getRoomResponse.getINFO()!=null){
                        mView.update_data(getRoomResponse.getINFO());
                    }

                }

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " );
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: " );
                mView.hideDialogLoading();
            }
        });
    }

}
