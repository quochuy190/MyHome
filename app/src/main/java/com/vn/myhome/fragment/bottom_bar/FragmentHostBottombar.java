package com.vn.myhome.fragment.bottom_bar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.activity.myhome.ActivityNewRoom;
import com.vn.myhome.activity.notifycation.ActivityListNotify;
import com.vn.myhome.adapter.AdapterSetupMain;
import com.vn.myhome.adapter.AdapterViewpager;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.config.Constants;
import com.vn.myhome.fragment.FragmentMyHome;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ObjSetupMain;
import com.vn.myhome.untils.SharedPrefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentHostBottombar extends BaseFragment {
    private static final String TAG = "FragmentHostBottombar";
    public static FragmentHostBottombar fragment;

    public static FragmentHostBottombar getInstance() {
        if (fragment == null) {
            synchronized (FragmentHostBottombar.class) {
                if (fragment == null)
                    fragment = new FragmentHostBottombar();
            }
        }
        return fragment;
    }

    private List<ObjSetupMain> mList;
    private AdapterSetupMain adapterSetupMain;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.txt_thongke)
    TextView txt_thongke;
    @BindView(R.id.txt_list_myhome)
    TextView txt_list_myhome;
    String sUserName;
    @BindView(R.id.img_home)
    ImageView img_home;
    @BindView(R.id.btn_back)
    ImageView img_back;
    @BindView(R.id.txt_title)
    TextView txt_title;
    @BindView(R.id.txt_badger_notify)
    TextView txt_badger_notify;
    PresenterHostBottombar mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host_bottombar, container, false);
        ButterKnife.bind(this, view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mPresenter = new PresenterHostBottombar(this);
        select_thongke();
        initViewpager();
        initAppbar();
        initEvent();
        initData();
        check_title_notify();
        return view;
    }

    private void check_add_myhome(boolean isShow) {
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        if (objLogin != null && objLogin.getUSER_TYPE().equals(Constants.UserType.CHUNHA)) {
            txt_title.setText("DANH SÁCH NHÀ CỦA TÔI");
            if (isShow) {
                img_home.setVisibility(View.VISIBLE);
                img_home.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
            } else {
                img_home.setVisibility(View.INVISIBLE);
            }
        } else {
            txt_title.setText("DANH SÁCH NHÀ");
            img_home.setVisibility(View.INVISIBLE);
        }
    }

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
        txt_title.setText("LỊCH NHÀ");
    }

    private void select_thongke() {
        txt_thongke.setSelected(true);
        txt_list_myhome.setSelected(false);

    }

    private void select_list_myhome() {
        txt_thongke.setSelected(false);
        txt_list_myhome.setSelected(true);
    }

    private void initEvent() {
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityNewRoom.class);
                intent.putExtra(Constants.KEY_IS_UPDATE_MYHOME, false);
                //startActivity(intent);
                startActivityForResult(intent, Constants.RequestCode.GET_MY_HOME);
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityListNotify.class);
                startActivity(intent);
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

    }

    private void initData() {
        String sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenter.getListMyHome(sUser);

    }

    private void init() {
    }

    public void update_viewpage() {
        EventBus.getDefault().post(new MessageEvent(Constants.EventBus.KEY_UPDATE_API_LIST_MYHOME, 1, 0));
    }

    @BindView(R.id.viewpager_host_bottombar)
    ViewPager viewPager;

    public AdapterViewpager adapterViewpager;

    public void initViewpager() {
        adapterViewpager = new AdapterViewpager(getChildFragmentManager());
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        if (objLogin.getUSER_TYPE().equals(Constants.UserType.CHUNHA)) {
            adapterViewpager.addFragment(new FragmentReportHost(), "");
        }
        adapterViewpager.addFragment(new FragmentMyHome(), "");
   //     viewPager.setOffscreenPageLimit(3);
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
                        check_add_myhome(false);
                        break;
                    case 1:
                        select_list_myhome();
                        check_add_myhome(true);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
