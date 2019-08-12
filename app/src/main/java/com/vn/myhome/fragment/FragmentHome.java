package com.vn.myhome.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.activity.home.InterfaceRoom;
import com.vn.myhome.activity.home.RoomPresenter;
import com.vn.myhome.adapter.AdapterHomeStay;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ResponseApi.GetAlbumImageHomeResponse;
import com.vn.myhome.models.ResponseApi.GetImageCoverResponse;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.untils.SharedPrefs;

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
public class FragmentHome extends BaseFragment implements InterfaceRoom.View {
    private static final String TAG = "FragmentSetup";
    public static FragmentHome fragment;
    @BindView(R.id.rcv_homestay_home)
    RecyclerView rcv_home;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterHomeStay mAdapter;
    List<ObjHomeStay> mList;
    int PAGE = 1;
    int NUMBER = 20;

    public static FragmentHome getInstance() {
        if (fragment == null) {
            synchronized (FragmentHome.class) {
                if (fragment == null)
                    fragment = new FragmentHome();
            }
        }
        return fragment;
    }

    RoomPresenter mPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        Log.e(TAG, "onCreateView: Setup");
        mPresenter = new RoomPresenter(this);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        init();
        initData();
        initEvent();
        return view;
    }

    private void initEvent() {
    }

    String sUserName;

    private void initData() {
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        if (sUserName != null) {
            showDialogLoading();
            get_api();
            mPresenter.api_get_cover_idx(sUserName);
        }
    }

    private void get_api() {
        mPresenter.api_get_listroom_idx(sUserName, "" + PAGE, "" + NUMBER);
    }

    private void init() {
        mList = new ArrayList<>();
        mAdapter = new AdapterHomeStay(mList, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        rcv_home.setNestedScrollingEnabled(false);
        rcv_home.setHasFixedSize(true);
        rcv_home.setLayoutManager(mLayoutManager);
        rcv_home.setItemAnimator(new DefaultItemAnimator());
        rcv_home.setAdapter(mAdapter);
    }

    @Override
    public void show_error_api(String sService) {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_get_listroom_idx(GetRoomResponse objRes) {
        hideDialogLoading();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            mList.addAll(objRes.getINFO());
            mAdapter.notifyDataSetChanged();
        } else {

        }
    }

    @Override
    public void show_get_cover_idx(GetImageCoverResponse obj) {

    }

    @Override
    public void show_search_home(GetRoomResponse objRes) {

    }

    @Override
    public void show_get_room_detail(ObjHomeStay objRoom) {

    }

    @Override
    public void show_get_album_image(GetAlbumImageHomeResponse objRes) {

    }
}
