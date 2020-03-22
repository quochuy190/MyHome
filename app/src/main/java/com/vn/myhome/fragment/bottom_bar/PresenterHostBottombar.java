package com.vn.myhome.fragment.bottom_bar;

import android.util.Log;
import android.widget.Toast;

import com.vn.myhome.config.Constants;
import com.vn.myhome.fragment.FragmentHome;
import com.vn.myhome.models.ObjFragmentSearchHome;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.network.RequestApiInterface;
import com.vn.myhome.network.RetrofitClient;
import com.vn.myhome.network.response.ResponGetBannerCity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
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
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", sUserName);
        Map<String, String> mMap_get_room = new LinkedHashMap<>();
        mMap_get_room.put("USERNAME", sUserName);
        mMap_get_room.put("OPT", "");

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

                Log.e(TAG, "onNext: " );
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                fragment.hideDialogLoading();
                Log.e(TAG, "onComplete: " );
            }
        });
    }
}
