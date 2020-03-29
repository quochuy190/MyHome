package com.vn.myhome.fragment.qldondep;

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
import com.vn.myhome.activity.notifycation.ActivityListNotify;
import com.vn.myhome.adapter.AdapterSetupMain;
import com.vn.myhome.adapter.AdapterViewpager;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ObjSetupMain;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.models.ResponseApi.ResponListImageHome;
import com.vn.myhome.presenter.InterfaceMyHome;
import com.vn.myhome.untils.SharedPrefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
public class FragmentHomeQldondep extends BaseFragment implements InterfaceMyHome.View {
    private static final String TAG = "FragmentLichnhaAdmin";
    public static FragmentHomeQldondep fragment;
    private List<ObjSetupMain> mList;
    private AdapterSetupMain adapterSetupMain;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.txt_lichdondep_tabhome)
    TextView txt_lichdondep_tabhome;
    @BindView(R.id.txt_bokkdondep_tabhome)
    TextView txt_bokkdondep_tabhome;
    String sUserName;
    @BindView(R.id.img_home)
    ImageView img_home;
    @BindView(R.id.btn_back)
    ImageView img_back;
    @BindView(R.id.txt_title)
    TextView txt_title;
    @BindView(R.id.txt_badger_notify)
    TextView txt_badger_notify;

    public static FragmentHomeQldondep getInstance() {
        if (fragment == null) {
            synchronized (FragmentHomeQldondep.class) {
                if (fragment == null)
                    fragment = new FragmentHomeQldondep();
            }
        }
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fagment_tabhome_qldondep, container, false);
        ButterKnife.bind(this, view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        select_lichdondep();
        initViewpager();
        initAppbar();
        initEvent();
        initData();
        check_title_notify();
        return view;
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

    private void select_lichdondep() {
        txt_lichdondep_tabhome.setSelected(true);
        txt_bokkdondep_tabhome.setSelected(false);

    }

    private void select_bookdondep() {
        txt_lichdondep_tabhome.setSelected(false);
        txt_bokkdondep_tabhome.setSelected(true);
    }

    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityListNotify.class);
                startActivity(intent);
            }
        });
        txt_lichdondep_tabhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        txt_bokkdondep_tabhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
    }

    private void initData() {
       /* showDialogLoading();
        String sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);*/
    }

    private void init() {

    }

    @BindView(R.id.viewpager_tabhome_qldondep)
    ViewPager viewPager;

    public AdapterViewpager adapterViewpager;

    private void initViewpager() {
        adapterViewpager = new AdapterViewpager(getChildFragmentManager());
        adapterViewpager.addFragment(new Fragment_Tab_Lichdonnha_Dichvu(), "");
        adapterViewpager.addFragment(new Fragment_Tab_Qldondep_Bookdondep(), "");
        viewPager.setOffscreenPageLimit(2);
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
                        select_lichdondep();
                        break;
                    case 1:
                        select_bookdondep();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void show_error_api(String sService) {
        hideDialogLoading();
    }

    @Override
    public void show_get_mylist_room(GetRoomResponse objRes) {
    }

    @Override
    public void show_room_detail(ObjHomeStay objRes) {

    }

    @Override
    public void show_new_room(ObjErrorApi objError) {

    }

    @Override
    public void show_edit_room(ObjErrorApi objError) {

    }

    @Override
    public void show_delete_room(ObjErrorApi objError) {

    }

    @Override
    public void show_update_state_room(ObjErrorApi objError) {

    }

    @Override
    public void show_get_album_image(ResponListImageHome objRes) {

    }
}
