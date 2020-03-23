package com.vn.myhome.fragment.bottom_bar;

import android.util.Log;

import com.vn.myhome.App;
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

public class PresenterHostBottombar {
    private static final String TAG = "PresenterHostBottombar";
    FragmentHostBottombar fragment;
    Retrofit retrofit;
    RequestApiInterface mApi;

    public PresenterHostBottombar(FragmentHostBottombar fragment) {
        this.fragment = fragment;
        retrofit = RetrofitClient.getInstance();
        mApi = retrofit.create(RequestApiInterface.class);
    }

    public void getListMyHome(String sUserName) {
        fragment.showDialogLoading();
        Map<String, String> mMap_get_room = new LinkedHashMap<>();
        mMap_get_room.put("USERNAME", sUserName);
        mMap_get_room.put("OPT", "");

        Observable<GetRoomResponse> userObservable = mApi.get_list_myhome(mMap_get_room)
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
                        App.mListHomeStay.addAll(getRoomResponse.getINFO());
                        fragment.update_viewpage();
                    }

                }

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: " );
                fragment.hideDialogLoading();
            }
        });
    }
}
