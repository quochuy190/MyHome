package com.vn.myhome.activity.login;

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
import com.vn.myhome.adapter.AdapterListTower;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ResponseApi.CityResponse;
import com.vn.myhome.models.ResponseApi.GetTypeResponse;
import com.vn.myhome.models.TowerObj;
import com.vn.myhome.untils.KeyboardUtil;
import com.vn.myhome.untils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 10/16/2017.
 */

public class ActivityListTowers extends BaseActivity
        implements InterfaceLogin.View {

    private List<TowerObj> mLisCity;
    private AdapterListTower adapterService;
    @BindView(R.id.recycle_list_service)
    RecyclerView recycle_service;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.edt_search_appbar)
    EditText edt_search_service;
    @BindView(R.id.img_back)
    ImageView img_back;
    private List<TowerObj> temp;
    String sUserId;
    PresenterLogin mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.fragment_service);
        ButterKnife.bind(this);
        KeyboardUtil.hideSoftKeyboard(this);
        //  initData();
        mPresenter = new PresenterLogin(this);
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
        for (TowerObj d : mLisCity) {
            String sName = StringUtil.removeAccent(d.getsName().toLowerCase());
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
                App.mTower = (TowerObj) item;
                finish();
            }
        });
    }

    private void init() {
        mLisCity = new ArrayList<>();
        temp = new ArrayList<>();
        adapterService = new AdapterListTower(temp, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycle_service.setNestedScrollingEnabled(false);
        recycle_service.setHasFixedSize(true);
        recycle_service.setLayoutManager(mLayoutManager);
        recycle_service.setItemAnimator(new DefaultItemAnimator());
        recycle_service.setAdapter(adapterService);
        adapterService.updateList(temp);
      /*  adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                setResult(RESULT_OK, new Intent());
                App.mCity = (City) item;
            }
        });*/
        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                setResult(RESULT_OK, new Intent());
                App.mTower = (TowerObj) item;
                finish();
            }
        });

    }

    private void initData() {
        mLisCity.clear();
        temp.clear();
        mLisCity.add(new TowerObj("Sunrise", "1"));
        mLisCity.add(new TowerObj("Greenbay Premium", "2"));
        mLisCity.add(new TowerObj("Greenbay Garden", "3"));
        temp.addAll(mLisCity);
        adapterService.notifyDataSetChanged();
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
    public void show_login(ObjLogin objLogin) {

    }

    @Override
    public void show_reg_user(ObjErrorApi objError) {

    }

    @Override
    public void show_get_type(GetTypeResponse objRes) {

    }

    @Override
    public void show_get_city(CityResponse objResCity) {
        hideDialogLoading();
    }

    @Override
    public void show_update_device(ObjErrorApi objError) {

    }
}
