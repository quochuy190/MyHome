package com.vn.myhome.activity.myhome;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.adapter.AdapterMyRoom;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.untils.KeyboardUtil;
import com.vn.myhome.untils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 10/16/2017.
 */

public class ActivityListMyhome extends BaseActivity
        implements InterfaceMyHome.View {
    private List<ObjHomeStay> mLisHomeStay;
    private AdapterMyRoom adapter;
    @BindView(R.id.recycle_list_service)
    RecyclerView recycle_service;
    RecyclerView.LayoutManager mLayoutManager;
    MyHomePresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.fragment_service);
        ButterKnife.bind(this);
        KeyboardUtil.hideSoftKeyboard(this);
        //  initData();
        mPresenter = new MyHomePresenter(this);
        initAppbar();
        init();
        initData();
        initEvent();
    }

    private void initAppbar() {
        ImageView img_back = findViewById(R.id.btn_back);
        TextView txt_title = findViewById(R.id.txt_title);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_title.setText("DANH SÁCH NHÀ");
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_list_myroom;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initEvent() {

    }

    private void init() {
        mLisHomeStay = new ArrayList<>();
        adapter = new AdapterMyRoom(mLisHomeStay, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycle_service.setNestedScrollingEnabled(false);
        recycle_service.setHasFixedSize(true);
        recycle_service.setLayoutManager(mLayoutManager);
        recycle_service.setItemAnimator(new DefaultItemAnimator());
        recycle_service.setAdapter(adapter);
        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
              /*  Intent intent = new Intent(ActivityListMyhome.this, ActivityRoomDetail.class);
                ObjHomeStay obj = (ObjHomeStay) item;
                intent.putExtra(Constants.KEY_SEND_ROOM_DETAIL, obj);
                startActivity(intent);*/
            }
        });

    }

    private void initData() {
        String sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        if (sUser != null) {
            showDialogLoading();
            mPresenter.api_get_mylist_room(sUser, "");
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
    public void show_get_mylist_room(GetRoomResponse objRes) {
        hideDialogLoading();
        mLisHomeStay.clear();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            if (objRes.getINFO() != null) {
                mLisHomeStay.addAll(objRes.getINFO());
            }
        } else
            showAlertDialog("Thông báo", objRes.getRESULT());
        adapter.notifyDataSetChanged();
    }

}
