package com.vn.myhome.activity.home;

import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.network.RequestApiInterface;
import com.vn.myhome.network.RetrofitClient;

import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 09-March-2020
 * Time: 15:14
 * Version: 1.0
 */
public class ListHomeSearchPresenter {
    ActivityListHomeStaySearch mView;
    Retrofit mRetrofit;
    RequestApiInterface mApi;
    private CompositeDisposable mCompositeDisposable;

    public ListHomeSearchPresenter(ActivityListHomeStaySearch mView) {
        mRetrofit = RetrofitClient.getInstance();
        mCompositeDisposable = new CompositeDisposable();
        this.mView = mView;
        mApi = mRetrofit.create(RequestApiInterface.class);
    }

    public void get_api_search_home(String USERNAME, String LOCATION, String CHECKIN, String CHECKOUT,
                                    String PEOPLE, String PRICE_FROM, String PRICE_TO, String AMENITIES, String ID_PROVINCE) {
        mView.showDialogLoading();
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", USERNAME);
        mMap.put("LOCATION", LOCATION);
        mMap.put("CHECKIN", CHECKIN);
        mMap.put("CHECKOUT", CHECKOUT);
        mMap.put("PEOPLE", PEOPLE);
        mMap.put("PRICE_FROM", PRICE_FROM);
        mMap.put("PRICE_TO", PRICE_TO);
        mMap.put("AMENITIES", AMENITIES);
        mMap.put("ID_PROVINCE", ID_PROVINCE);

        mCompositeDisposable.add(mApi.get_search_home2(mMap)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );

    }

    private void handleResponse(GetRoomResponse obj) {
        mView.hideDialogLoading();
        mView.updateData(obj.getINFO());
    }

    private void handleError(Throwable error) {
        mView.hideDialogLoading();
    }

    private void handleSuccess() {
        mView.hideDialogLoading();
    }
}
