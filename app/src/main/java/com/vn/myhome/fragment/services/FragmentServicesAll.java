package com.vn.myhome.fragment.services;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.activity.notifycation.ActivityListNotify;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 29-March-2020
 * Time: 23:05
 * Version: 1.0
 */
public class FragmentServicesAll extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "FragmentServicesAll";
    public static FragmentServicesAll fragment;

    public static FragmentServicesAll getInstance() {
        if (fragment == null) {
            synchronized (FragmentServicesAll.class) {
                if (fragment == null)
                    fragment = new FragmentServicesAll();
            }
        }
        return fragment;
    }

    @BindView(R.id.txt_thuexemay)
    TextView txt_thuexemay;
    @BindView(R.id.txt_thue_oto)
    TextView txt_thue_oto;
    @BindView(R.id.txt_duthuyen_rieng)
    TextView txt_duthuyen_rieng;
    @BindView(R.id.txt_thuyenthamvinh)
    TextView txt_thuyenthamvinh;
    @BindView(R.id.txt_thuyphico)
    TextView txt_thuyphico;
    @BindView(R.id.txt_tructhang)
    TextView txt_tructhang;
    @BindView(R.id.btn_back)
    ImageView img_back;
    @BindView(R.id.txt_title)
    TextView txt_title;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_services, container, false);
        ButterKnife.bind(this, view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initAppbar();
        initEvent();
        check_title_notify();
        return view;

    }
    @BindView(R.id.txt_badger_notify)
    TextView txt_badger_notify;
    private void check_title_notify() {
        if (App.sTotalNotify.length() > 0) {
            if (!App.sTotalNotify.equals("0")) {
                txt_badger_notify.setText(App.sTotalNotify);
                txt_badger_notify.setVisibility(View.VISIBLE);
            } else
                txt_badger_notify.setVisibility(View.GONE);
        } else {
            txt_badger_notify.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.RELOAD_NOTIFY)) {
            Log.e(TAG, "onMessageEvent: " + App.sTotalNotify);
            check_title_notify();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initAppbar() {
        img_back.setVisibility(View.VISIBLE);
        img_back.setImageResource(R.drawable.ic_notify);
        txt_title.setText("DỊCH VỤ");

    }
    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityListNotify.class);
                startActivity(intent);
            }
        });
        txt_thuexemay.setOnClickListener(this);
        txt_thue_oto.setOnClickListener(this);
        txt_thuyenthamvinh.setOnClickListener(this);
        txt_duthuyen_rieng.setOnClickListener(this);
        txt_thuyphico.setOnClickListener(this);
        txt_tructhang.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_thuexemay:
                showAlertDialog("Thông báo", "Chức năng đang xây dựng");
                break;
            case R.id.txt_thue_oto:
                startActivity(new Intent(getContext(), ActivityBookCar.class));
                break;
            case R.id.txt_thuyenthamvinh:
                showAlertDialog("Thông báo", "Chức năng đang xây dựng");
                break;
            case R.id.txt_duthuyen_rieng:
                showAlertDialog("Thông báo", "Chức năng đang xây dựng");
                break;
            case R.id.txt_thuyphico:
                showAlertDialog("Thông báo", "Chức năng đang xây dựng");
                break;
            case R.id.txt_tructhang:
                showAlertDialog("Thông báo", "Chức năng đang xây dựng");
                break;
        }
    }
}
