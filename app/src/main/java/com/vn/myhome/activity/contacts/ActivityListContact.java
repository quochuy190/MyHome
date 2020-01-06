package com.vn.myhome.activity.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.adapter.AdapterListContact;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ContactObj;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ResponseApi.ResponseGetContact;
import com.vn.myhome.presenter.ContactPresenter;
import com.vn.myhome.presenter.InterfaceContact;
import com.vn.myhome.untils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 19-November-2019
 * Time: 01:38
 * Version: 1.0
 */
public class ActivityListContact extends BaseActivity implements InterfaceContact.View {
    ContactPresenter mPresenter;
    private List<ContactObj> mList;
    private AdapterListContact adapterService;
    @BindView(R.id.rcv_list_contacts)
    RecyclerView recycle_service;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    public int setContentViewId() {
        return R.layout.activity_list_contact;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ContactPresenter(this);
        initAppbar();
        init();
        initData();
    }
    private void initAppbar() {
        ImageView img_back = findViewById(R.id.btn_back);
        ImageView img_home = findViewById(R.id.img_home);
        TextView txt_title = findViewById(R.id.txt_title);
        img_home.setImageResource(R.drawable.ic_add);
        img_home.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_title.setText("DANH SÁCH KHÁCH HÀNG");
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityListContact.this, ActivityContactDetail.class);
                startActivityForResult(intent, Constants.RequestCode.GET_CONTACT);
            }
        });
    }

    private void initData() {
        showDialogLoading();
        String sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenter.api_get_contact(sUser, "", "", "", "", "");
    }

    private void init() {
        mList = new ArrayList<>();
        adapterService = new AdapterListContact(mList, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycle_service.setNestedScrollingEnabled(false);
        recycle_service.setHasFixedSize(true);
        recycle_service.setLayoutManager(mLayoutManager);
        recycle_service.setItemAnimator(new DefaultItemAnimator());
        recycle_service.setAdapter(adapterService);

        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                ContactObj obj = (ContactObj) item;
                Intent intent = new Intent(ActivityListContact.this, ActivityContactDetail.class);
                intent.putExtra(Constants.KEY_SEND_CONTACT_DETAIL, obj);
                startActivityForResult(intent, Constants.RequestCode.GET_CONTACT);
            }
        });
    }

    @Override
    public void show_error_api(String sService) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_CONTACT:
                if (resultCode == RESULT_OK) {
                    initData();
                }
                break;
        }}
    @Override
    public void show_get_contact(ResponseGetContact objContact) {
        hideDialogLoading();
        mList.clear();
        if (objContact != null && objContact.getERROR().equals("0000")) {
            if (objContact.getINFO() != null)
                mList.addAll(objContact.getINFO());
        } else showAlertDialog("Thông báo", objContact.getRESULT());
        adapterService.notifyDataSetChanged();
    }

    @Override
    public void show_add_contact(ObjErrorApi objError) {

    }

    @Override
    public void show_edit_contact(ObjErrorApi objError) {

    }

    @Override
    public void show_del_contact(ObjErrorApi objError) {

    }
}
