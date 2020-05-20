package com.vn.myhome.fragment.services;

import android.util.Log;

import com.vn.myhome.models.ObjFragmentSearchHome;
import com.vn.myhome.models.ObjListBookCar;
import com.vn.myhome.network.RequestApiInterface;
import com.vn.myhome.network.RetrofitClient;
import com.vn.myhome.network.response.ResponGetBannerCity;
import com.vn.myhome.network.response.ResponGetListBookCar;

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
public class PresenterGetListBookCar {
    private static final String TAG = "PresenterGetListBookCar";
    FragmentDanhsachDatxe fragmentDanhsachDatxe;
    Retrofit retrofit;
    RequestApiInterface mApi;

    public PresenterGetListBookCar(FragmentDanhsachDatxe fragmentHome) {
        this.fragmentDanhsachDatxe = fragmentHome;
        retrofit = RetrofitClient.getInstance();
        mApi = retrofit.create(RequestApiInterface.class);
    }


    public void getApiListBookCar(String USERNAME, String BOOKER_TEL) {
        fragmentDanhsachDatxe.showDialogLoading();
        Map<String, String> mMap_get_list_bookcar_pre = new LinkedHashMap<>();
        mMap_get_list_bookcar_pre.put("USERNAME", USERNAME);
        Map<String, String> mMap_get_list_bookcar = new LinkedHashMap<>();
        mMap_get_list_bookcar.put("USERNAME", USERNAME);
        mMap_get_list_bookcar.put("BOOKER_TEL", "BOOKER_TEL");

        Observable<ResponGetListBookCar> userObservable1 = mApi.get_list_bookcar(mMap_get_list_bookcar)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());
        Observable<ResponGetListBookCar> userObservable2 = mApi.get_list_bookcar_pre(mMap_get_list_bookcar_pre)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());
        Observable.zip(userObservable1, userObservable2, new BiFunction<ResponGetListBookCar, ResponGetListBookCar, Object>() {
            @Override
            public Object apply(ResponGetListBookCar responGetListBookCar, ResponGetListBookCar responGetListBookCar2) throws Exception {
                List<ObjListBookCar> mList = new ArrayList<>();
                mList.addAll(responGetListBookCar.getINFO());
                mList.addAll(responGetListBookCar2.getINFO());
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
                        Log.e(TAG, "onNext: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleError(e);
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        fragmentDanhsachDatxe.hideDialogLoading();
                        Log.e(TAG, "onComplete: ");
                    }
                });
    }


    private void handleResults(ResponGetBannerCity marketList) {
        Log.e(TAG, "handleResults: ");
    }

    private void handleError(Throwable t) {
        Log.e(TAG, "handleError: ");
        fragmentDanhsachDatxe.showAlertErrorNetwork();
    }
}
