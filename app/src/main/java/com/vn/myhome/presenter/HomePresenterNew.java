package com.vn.myhome.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

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

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 04-March-2020
 * Time: 17:15
 * Version: 1.0
 */
public class HomePresenterNew {
    private static final String TAG = "HomePresenterNew";
    FragmentHome fragmentHome;
    Retrofit retrofit;
    RequestApiInterface mApi;

    public HomePresenterNew(FragmentHome fragmentHome) {
        this.fragmentHome = fragmentHome;
        retrofit = RetrofitClient.getInstance();
        mApi = retrofit.create(RequestApiInterface.class);
    }

    @SuppressLint("CheckResult")
    public void getBannerCity(String sUserName) {
        fragmentHome.showDialogLoading();
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", sUserName);
        Map<String, String> mMap_get_room = new LinkedHashMap<>();
        mMap_get_room.put("USERNAME", sUserName);
        mMap_get_room.put("PAGE", "1");
        mMap_get_room.put("NUMOFPAGE", "20");

        Observable<ResponGetBannerCity> userObservable1 = mApi.get_banner_city(mMap)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());
        Observable<GetRoomResponse> userObservable2 = mApi.get_listroom_idx(mMap_get_room)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());
                Observable.zip(userObservable1, userObservable2,
                        new BiFunction<ResponGetBannerCity, GetRoomResponse, Object>() {
                            @Override
                            public Object apply(ResponGetBannerCity responGetBannerCity,
                                                GetRoomResponse getRoomResponse) throws Exception {
                                List<ObjFragmentSearchHome> mList = new ArrayList<>();
                                mList.add(new ObjFragmentSearchHome(Constants.DIEM_DEN_YEU_THICH, responGetBannerCity.getINFO()));
                                mList.add(new ObjFragmentSearchHome(Constants.TOP_NHA_NOI_BAT, getRoomResponse.getINFO()));
                       /* ObjFragmentSearchHome objFragmentSearchHome = new ObjFragmentSearchHome();
                        objFragmentSearchHome.setmListHomeStay(getRoomResponse.getINFO());*/
                                return mList;
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<Object>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.e(TAG, "onSubscribe: ");
                            }

                            @Override
                            public void onNext(Object o) {
                                List<ObjFragmentSearchHome> obj = (List<ObjFragmentSearchHome>) o;
                                fragmentHome.init(obj);
                                Log.e(TAG, "onNext: ");
                            }

                            @Override
                            public void onError(Throwable e) {
                                handleError(e);
                                Log.e(TAG, "onError: " );
                            }

                            @Override
                            public void onComplete() {
                                fragmentHome.hideDialogLoading();
                                Log.e(TAG, "onComplete: ");
                            }
                        });
    }


    private void handleResults(ResponGetBannerCity marketList) {
        Log.e(TAG, "handleResults: ");
    }

    private void handleError(Throwable t) {
        Log.e(TAG, "handleError: ");
        fragmentHome.show_error_api(t.getMessage());
    }
}
