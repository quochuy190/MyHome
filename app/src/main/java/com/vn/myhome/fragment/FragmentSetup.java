package com.vn.myhome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.activity.login.LoginActivity;
import com.vn.myhome.activity.myhome.ActivityListMyhome;
import com.vn.myhome.adapter.AdapterSetupMain;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjSetupMain;
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
public class FragmentSetup extends BaseFragment {
    private static final String TAG = "FragmentSetup";
    public static FragmentSetup fragment;
    private List<ObjSetupMain> mList;
    private AdapterSetupMain adapterSetupMain;
    @BindView(R.id.rcv_setup)
    RecyclerView rcv_setup;
    @BindView(R.id.btn_back)
    ImageView img_back;
    @BindView(R.id.txt_title)
    TextView txt_title;
    RecyclerView.LayoutManager mLayoutManager;

    public static FragmentSetup getInstance() {
        if (fragment == null) {
            synchronized (FragmentSetup.class) {
                if (fragment == null)
                    fragment = new FragmentSetup();
            }
        }
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup, container, false);
        ButterKnife.bind(this, view);
        Log.e(TAG, "onCreateView: Setup");
        init();
        initData();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private void initData() {
        img_back.setVisibility(View.INVISIBLE);
        txt_title.setText("CÀI ĐẶT");
    }

    private void init() {
        mList = new ArrayList<>();
        mList.add(new ObjSetupMain(0, "Hồ sơ", R.drawable.ic_setup_1));
        mList.add(new ObjSetupMain(1, "Quản lý nhà", R.drawable.ic_setup_2));
        mList.add(new ObjSetupMain(2, "Danh sách khách hàng", R.drawable.ic_setup_3));
        mList.add(new ObjSetupMain(3, "Gửi tin nhắn, Email chăm sóc khách hàng", R.drawable.ic_setup_4));
        mList.add(new ObjSetupMain(4, "Hỗ trợ", R.drawable.ic_setup_5));
        mList.add(new ObjSetupMain(5, "Đăng xuất", R.drawable.ic_setup_5));
        adapterSetupMain = new AdapterSetupMain(mList, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        rcv_setup.setNestedScrollingEnabled(true);
        rcv_setup.setHasFixedSize(true);
        rcv_setup.setLayoutManager(mLayoutManager);
        rcv_setup.setItemAnimator(new DefaultItemAnimator());
        rcv_setup.setAdapter(adapterSetupMain);
      /*  adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                setResult(RESULT_OK, new Intent());
                App.mCity = (City) item;
            }
        });*/
        adapterSetupMain.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                ObjSetupMain obj = (ObjSetupMain) item;
                switch (obj.getId()) {
                    case 0:

                        break;
                    case 1:
                        startActivity(new Intent(getContext(), ActivityListMyhome.class));
                        break;
                    case 5:
                        SharedPrefs.getInstance().put(Constants.KEY_SAVE_IS_LOGIN, false);
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        getActivity().finish();
                        break;

                }
            }
        });

    }
}
