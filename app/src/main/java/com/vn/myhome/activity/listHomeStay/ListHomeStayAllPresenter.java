package com.vn.myhome.activity.listHomeStay;

import android.util.Log;

import com.vn.myhome.fragment.FragmentHome;
import com.vn.myhome.network.RequestApiInterface;
import com.vn.myhome.network.RetrofitClient;
import com.vn.myhome.network.response.ResponGetBannerCity;

import retrofit2.Retrofit;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 04-March-2020
 * Time: 17:15
 * Version: 1.0
 */
public class ListHomeStayAllPresenter {
    private static final String TAG = "HomePresenterNew";
    FragmentHome fragmentHome;
    Retrofit retrofit;
    RequestApiInterface mApi;

    public ListHomeStayAllPresenter(FragmentHome fragmentHome) {
        this.fragmentHome = fragmentHome;
        retrofit = RetrofitClient.getInstance();
        mApi = retrofit.create(RequestApiInterface.class);
    }



    private void handleResults(ResponGetBannerCity marketList) {
        Log.e(TAG, "handleResults: ");
    }

    private void handleError(Throwable t) {
        Log.e(TAG, "handleError: ");
        fragmentHome.show_error_api(t.getMessage());
    }
}
