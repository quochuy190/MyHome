package com.vn.myhome.fragment.services;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.adapter.AdapterViewpager;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.config.Constants;
import com.vn.myhome.fragment.bottom_bar.PresenterHostBottombar;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.untils.SharedPrefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class ActivityBookCar extends BaseActivity {
    private static final String TAG = "FragmentHostBottombar";
    public static ActivityBookCar fragment;

    public static ActivityBookCar getInstance() {
        if (fragment == null) {
            synchronized (ActivityBookCar.class) {
                if (fragment == null)
                    fragment = new ActivityBookCar();
            }
        }
        return fragment;
    }

    @BindView(R.id.txt_thongke)
    TextView txt_thongke;
    @BindView(R.id.txt_list_myhome)
    TextView txt_list_myhome;
    @BindView(R.id.txt_list_bookxe_trasau)
    TextView txt_list_bookxe_trasau;
    String sUserName;
    @BindView(R.id.img_home)
    ImageView img_home;
    @BindView(R.id.btn_back)
    ImageView img_back;
    PresenterHostBottombar mPresenter;
  /*  @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        select_thongke();
        initViewpager();
        initAppbar();
        initEvent();
        initData();
    }*/


    @Override
    public int setContentViewId() {
        return R.layout.fragment_host_bottombar;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.RELOAD_NOTIFY)) {
            Log.e(TAG, "onMessageEvent: " + App.sTotalNotify);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        select_thongke();
        initViewpager();
        initAppbar();
        initEvent();
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initAppbar() {
        img_back.setVisibility(View.VISIBLE);
        img_home.setVisibility(View.INVISIBLE);
        img_home.setImageResource(R.drawable.ic_book_note);
    }

    private void select_thongke() {
        txt_thongke.setSelected(true);
        txt_list_myhome.setSelected(false);
        txt_list_bookxe_trasau.setSelected(false);
    }

    private void select_thongke_bookcar_late() {
        txt_thongke.setSelected(false);
        txt_list_myhome.setSelected(false);
        txt_list_bookxe_trasau.setSelected(true);
    }

    private void select_list_myhome() {
        txt_thongke.setSelected(false);
        txt_list_myhome.setSelected(true);
        txt_list_bookxe_trasau.setSelected(false);
    }

    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txt_thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        txt_list_myhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        txt_list_bookxe_trasau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });
    }

    private void initData() {
    }

    @BindView(R.id.viewpager_host_bottombar)
    ViewPager viewPager;

    public AdapterViewpager adapterViewpager;

    public void initViewpager() {
        txt_list_bookxe_trasau.setVisibility(View.VISIBLE);
        txt_thongke.setText("Đặt xe");
        txt_list_myhome.setText("DS đặt xe thanh toán trả sau");
        txt_list_bookxe_trasau.setText("DS đặt xe thanh toán chuyển khoản");
        adapterViewpager = new AdapterViewpager(getSupportFragmentManager());
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        adapterViewpager.addFragment(new FragmentDatXe(), "");
        adapterViewpager.addFragment(new FragmentDanhsachDatxe(), "");
        adapterViewpager.addFragment(new FragmentListBookCarTTChuyenkhoan(), "");
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapterViewpager);
        // tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        select_thongke();
                        break;
                    case 1:
                        select_list_myhome();
                        break;
                    case 2:
                        select_thongke_bookcar_late();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
