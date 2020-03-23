package com.vn.myhome.fragment.bottom_bar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.activity.login.ActivityListHomestay;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.network.response.ResponReport;
import com.vn.myhome.untils.SharedPrefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class FragmentReportHost extends BaseFragment {
    private static final String TAG = "FragmentReportHost";
    public static FragmentReportHost fragment;

    public static FragmentReportHost getInstance() {
        if (fragment == null) {
            synchronized (FragmentReportHost.class) {
                if (fragment == null)
                    fragment = new FragmentReportHost();
            }
        }
        return fragment;
    }

    List<String> data_Home;
    @BindView(R.id.spinner_home)
    TextView txt_myhome;
    List<ObjHomeStay> mListHome;
    PresenterReportHost mPresenter;
    TextView txt_title_book;
    TextView txt_content_book;
    TextView txt_note_book;
    @BindView(R.id.txt_title_revenue)
    TextView txt_title_revenue;
    @BindView(R.id.txt_revenue_of_month)
    TextView txt_revenue_of_month;
    @BindView(R.id.txt_revenue_last_month)
    TextView txt_revenue_last_month;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_host, container, false);
        ButterKnife.bind(this, view);
        txt_note_book = view.findViewById(R.id.txt_note_book);
        txt_content_book = view.findViewById(R.id.txt_content_book);
        txt_title_book = view.findViewById(R.id.txt_title_book);
        mListHome = new ArrayList<>();
        mPresenter = new PresenterReportHost(this);
        String sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenter.getApi(sUsername);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initEvent();
        return view;
    }

    private void initEvent() {
        txt_myhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent  intent_city = new Intent(getContext(), ActivityListHomestay.class);
                startActivityForResult(intent_city, Constants.RequestCode.GET_LIST_MYHOME);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.KEY_UPDATE_API_LIST_MYHOME)) {
            if (App.mListHomeStay != null && App.mListHomeStay.size() > 0) {
                mListHome.clear();
                mListHome.addAll(App.mListHomeStay);
              //  set_data_spinner();
                String sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                mPresenter.getRpBook(sUsername,mListHome.get(0).getGENLINK());
            }
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


    public void set_info_book(ResponReport objRest_book) {
      txt_note_book.setText("cdj");
      txt_title_book.setText("abc");
    }
    public void set_info_revene(ResponReport objRest_book){
        txt_title_revenue.setText("cdj");
        txt_revenue_of_month.setText("abc");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_LIST_MYHOME:
                if (resultCode == RESULT_OK) {
                   txt_myhome.setText(App.mHomestay.getNAME());
                }
                break;
        }
    }
}
