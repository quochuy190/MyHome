package com.vn.myhome.fragment.services;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.adapter.AdapterRoute;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjRoute;
import com.vn.myhome.models.ResponseApi.ResponseBookCarDetail;
import com.vn.myhome.models.ResponseApi.ResponsePriceEstimates;
import com.vn.myhome.models.ResponseApi.RouteResponse;
import com.vn.myhome.network.response.ResponGetListBookCar;
import com.vn.myhome.untils.KeyboardUtil;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 10/16/2017.
 */

public class ActivityListHanhtrinhxe extends BaseActivity
        implements InterfaceServices.View {

    private List<ObjRoute> mList;
    private AdapterRoute adapterService;
    @BindView(R.id.recycle_list_service)
    RecyclerView recycle_service;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.edt_search_appbar)
    EditText edt_search_service;
    @BindView(R.id.img_back)
    ImageView img_back;
    private List<ObjRoute> temp;
    String sUserId;
    PresenterServices mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.fragment_service);
        ButterKnife.bind(this);
        KeyboardUtil.hideSoftKeyboard(this);
        //  initData();
        mPresenter = new PresenterServices(this);
        //initAppbar();
        init();
        initData();
        initEvent();
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_recycleview;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edt_search_service.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });


    }

    void filter(String text) {
        temp.clear();
        for (ObjRoute d : mList) {
            String sName = StringUtil.removeAccent(d.getName().toLowerCase());
            String sInput = StringUtil.removeAccent(text.toLowerCase());
            if (sName.contains(sInput)) {
                //adding the element to filtered list
                temp.add(d);
            }
        }
        adapterService.updateList(temp);
        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                setResult(RESULT_OK, new Intent());
                App.mRoute = (ObjRoute) item;
                finish();
            }
        });
    }

    private void init() {
        mList = new ArrayList<>();
        temp = new ArrayList<>();
        adapterService = new AdapterRoute(temp, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycle_service.setNestedScrollingEnabled(false);
        recycle_service.setHasFixedSize(true);
        recycle_service.setLayoutManager(mLayoutManager);
        recycle_service.setItemAnimator(new DefaultItemAnimator());
        recycle_service.setAdapter(adapterService);
        adapterService.updateList(temp);
        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                setResult(RESULT_OK, new Intent());
                App.mRoute = (ObjRoute) item;
                finish();
            }
        });

    }

    private void initData() {
        if (App.mListRoute != null && App.mListRoute.size() > 0) {
            mList.addAll(App.mListRoute);
            temp.addAll(mList);
            adapterService.notifyDataSetChanged();
        } else {
            showDialogLoading();
            mPresenter.api_get_route_car(SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void show_error_api(String sService) {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_get_type_car(RouteResponse objLogin) {

    }

    @Override
    public void show_get_route_car(RouteResponse objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            if (objError.getINFO() != null) {
                mList.clear();
                temp.clear();
                mList.addAll(objError.getINFO());
                temp.addAll(objError.getINFO());
                App.mListRoute.clear();
                App.mListRoute.addAll(mList);
                adapterService.notifyDataSetChanged();
            }
        } else showDialogNotify("Thông báo", objError.getRESULT());
    }

    @Override
    public void show_get_price_estimates(ResponsePriceEstimates objRes) {

    }

    @Override
    public void show_bookcar(ObjErrorApi objResCity) {

    }

    @Override
    public void show_get_bookcar_detail(ResponseBookCarDetail objError) {

    }


    @Override
    public void show_list_book_car(ResponGetListBookCar objError) {

    }

    @Override
    public void show_list_book_car_pre(ResponGetListBookCar objError) {

    }

    @Override
    public void show_update_billing(ObjErrorApi objError) {

    }

}
