package com.vn.myhome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.activity.home.ActivityListHomeStaySearch;
import com.vn.myhome.adapter.AdapterFragmentSearchHome;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjFragmentSearchHome;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ResponseApi.ResponGetListNotify;
import com.vn.myhome.presenter.HomePresenterNew;
import com.vn.myhome.presenter.InterfaceNotify;
import com.vn.myhome.presenter.NotifyPresenter;
import com.vn.myhome.untils.KeyboardUtil;
import com.vn.myhome.untils.SharedPrefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 22-April-2019
 * Time: 10:30
 * Version: 1.0
 */
public class FragmentHome extends BaseFragment
        implements View.OnClickListener, InterfaceNotify.View {
    private static final String TAG = "FragmentSetup";
    public static FragmentHome fragment;
    @BindView(R.id.rcv_search_fragment_home)
    RecyclerView rcv_home;
    @BindView(R.id.appbar_search)
    ConstraintLayout appbar_search;
    @BindView(R.id.edt_search_home)
    TextView edt_search_home;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterFragmentSearchHome mAdapter;
    List<ObjHomeStay> mList;
    int PAGE = 1;
    int NUMBER = 200;
    NotifyPresenter mPresenterNotify;
    HomePresenterNew mPresenterHomeNew;
    String sUserName;

    public static FragmentHome getInstance() {
        if (fragment == null) {
            synchronized (FragmentHome.class) {
                if (fragment == null)
                    fragment = new FragmentHome();
            }
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_search_ctv, container, false);
        ButterKnife.bind(this, view);
        KeyboardUtil.hideSoftKeyboard(getActivity());
        Log.e(TAG, "onCreateView: Setup");
        mPresenterNotify = new NotifyPresenter(this);
        mPresenterHomeNew = new HomePresenterNew(this);
        mListImage = new ArrayList<>();
        initData();
        initEvent();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }

    private void initEvent() {
        appbar_search.setOnClickListener(this);
        edt_search_home.setOnClickListener(this);
    }


    private void initData() {
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenterHomeNew.getBannerCity(sUserName);
        if (sUserName != null) {
            mPresenterNotify.api_get_list_notifi(sUserName, "1", "500");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.KEY_SEND_NOTIFY)) {
            sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
            mPresenterNotify.api_get_list_notifi(sUserName, "1", "500");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void init(List<ObjFragmentSearchHome> objFragmentSearchHome) {
        mList = new ArrayList<>();
        mAdapter = new AdapterFragmentSearchHome(objFragmentSearchHome, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        rcv_home.setNestedScrollingEnabled(true);
        rcv_home.setHasFixedSize(true);
        rcv_home.setLayoutManager(mLayoutManager);
        rcv_home.setItemAnimator(new DefaultItemAnimator());
        rcv_home.setAdapter(mAdapter);
    }

    List<String> mListImage;

    @Override
    public void show_error_api(String sService) {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_list_notifi(ResponGetListNotify objError) {
        hideDialogLoading();

    }

    @Override
    public void show_update_list_notifi(ObjErrorApi objError) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appbar_search:
                start_activity_search();
                break;
            case R.id.edt_search_home:
                start_activity_search();
                break;
        }
    }

    private void start_activity_search() {
        Intent intent2 = new Intent(getContext(), ActivityListHomeStaySearch.class);
        intent2.putExtra(Constants.KEY_SEND_ID_PROVINCE, "home");
        getContext().startActivity(intent2);
    }
}
