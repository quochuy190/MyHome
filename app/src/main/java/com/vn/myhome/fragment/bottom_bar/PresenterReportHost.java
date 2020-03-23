package com.vn.myhome.fragment.bottom_bar;

import android.util.Log;

import com.vn.myhome.models.ObjReport;
import com.vn.myhome.network.RequestApiInterface;
import com.vn.myhome.network.RetrofitClient;
import com.vn.myhome.network.response.ResponReport;

import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PresenterReportHost {
    private static final String TAG = "PresenterReportHost";
    FragmentReportHost fragment;
    Retrofit retrofit;
    RequestApiInterface mApi;

    public PresenterReportHost(FragmentReportHost fragment) {
        this.fragment = fragment;
        retrofit = RetrofitClient.getInstance();
        mApi = retrofit.create(RequestApiInterface.class);
    }

    public void getApi(String sUserName) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USERNAME", sUserName);
        Observable<ResponReport> observable_revene = mApi.rp_revenue(mMap)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());
        Observable<ResponReport> observable_cost = mApi.rp_cost(mMap)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());
   /*     Observable<ResponReport> observable_profit = mApi.rp_profit(mMap)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());*/

   Observable.zip(observable_cost, observable_revene, new BiFunction<ResponReport, ResponReport, Object>() {
       @Override
       public Object apply(ResponReport responReport, ResponReport responReport2) throws Exception {
           fragment.set_info_revene(responReport);
           return responReport;
       }
   }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
           .subscribe(new Observer<Object>() {
               @Override
               public void onSubscribe(Disposable d) {

               }

               @Override
               public void onNext(Object o) {

               }

               @Override
               public void onError(Throwable e) {

               }

               @Override
               public void onComplete() {

               }
           });

       /* Observable.zip( observable_cost, observable_revene, observable_profit,
                new Function3< ResponReport, ResponReport, ResponReport, Object>() {
            @Override
            public Object apply( ResponReport responReport2,
                                ResponReport responReport3, ResponReport responReport4) throws Exception {
                List<ResponReport> mlist = new ArrayList<>();
                mlist.add(responReport2);
                mlist.add(responReport3);
                mlist.add(responReport4);
                return responReport2;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Object o) {
                        List<ResponReport> obj = ( List<ResponReport>) o;
                        Log.e(TAG, "onNext: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });*/
    }

    public void getRpBook(String sUserName, String getlink) {
        Map<String, String> mMap_get_room = new LinkedHashMap<>();
        mMap_get_room.put("USERNAME", sUserName);
        mMap_get_room.put("GENLINK", getlink);

        Observable<ResponReport> userObservable = mApi.rp_book_month(mMap_get_room)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());
        userObservable.subscribe(new Observer<ResponReport>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: " );
            }

            @Override
            public void onNext(ResponReport getRoomResponse) {
                if (getRoomResponse.getERROR().equals("0000")){
                    if (getRoomResponse.getINFO()!=null){
                        fragment.set_info_book(getRoomResponse);
                    }

                }

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: " );
            }
        });
    }

    private void set_info_book(ResponReport responReport_book) {
        try {
            if (responReport_book.getINFO() != null) {
                ObjReport current_month = responReport_book.getINFO().get(1);
                ObjReport last_month = responReport_book.getINFO().get(0);

                 fragment.txt_title_book.setText("Số ngày đặt trước trong tháng ");
                fragment.txt_content_book.setText(current_month + "/" + current_month.getNUMBER_OF_DAYS() + " ngày");
                double day_current =  Double.parseDouble(current_month.getTOTALDAY());
                double day_last = Double.parseDouble(last_month.getTOTALDAY());
                if (day_current == day_last) {
                    fragment.txt_note_book.setText("Số ngày đặt đang bằng tháng trước đó");
                } else if (day_current > day_last) {
                    fragment.txt_note_book.setText(day_current + "tăng so với tháng trước");
                } else {
                    fragment.txt_note_book.setText(day_current + "giảm so với tháng trước");
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
