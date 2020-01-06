package com.vn.myhome.activity.myhome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.vn.myhome.R;
import com.vn.myhome.adapter.AdapterViewpager;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.config.Constants;
import com.vn.myhome.fragment.myhome.FragmentAmenitiesNewRoom;
import com.vn.myhome.fragment.myhome.FragmentInfoNewRoom;
import com.vn.myhome.fragment.myhome.FragmentPriceNewRoom;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.models.ResponseApi.ResponListImageHome;
import com.vn.myhome.presenter.InterfaceMyHome;
import com.vn.myhome.presenter.MyHomePresenter;
import com.vn.myhome.untils.SharedPrefs;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 15-September-2019
 * Time: 23:51
 * Version: 1.0
 */
public class ActivityNewRoom extends BaseActivity implements InterfaceMyHome.View {
    @BindView(R.id.img_home)
    ImageView img_home;
    MyHomePresenter mPresenterMyhome;
    String sUserName;
    public static String sGetlink = "";
    boolean isShowdelete = false;

    @Override
    public int setContentViewId() {
        return R.layout.activity_new_room;
    }

    private void initAppbar() {
        ImageView img_back = findViewById(R.id.btn_back);
        TextView txt_title = findViewById(R.id.txt_title);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_room();
            }
        });
        txt_title.setText("DANH SÁCH NHÀ");
        img_home.setVisibility(View.VISIBLE);
        img_home.setImageResource(R.drawable.ic_delete);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenterMyhome = new MyHomePresenter(this);
        initAppbar();
        initData();
        initEvent();
        select_lichdatnha();
    }

    @BindView(R.id.frame_new_room)
    ViewPager viewPager;
    AdapterViewpager adapterViewpager;
    private static final String TAG = "ActivityNewRoom";

    private void initViewpager() {
        Log.e(TAG, "initViewpager: ");
        adapterViewpager = new AdapterViewpager(getSupportFragmentManager());
        adapterViewpager.addFragment(new FragmentInfoNewRoom(), "");
        adapterViewpager.addFragment(new FragmentPriceNewRoom(), "");
        adapterViewpager.addFragment(new FragmentAmenitiesNewRoom(), "");
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
                        select_lichdatnha();
                        break;
                    case 1:
                        select_lichdonnha();
                        break;
                    case 2:
                        select_calendar();
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isUpdate) {
            if (sGetlink.length() > 0)
                mPresenterMyhome.api_del_room(sUserName, sGetlink);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        delete_room();
    }

    private void delete_room() {
        if (!isUpdate) {
            if (sGetlink.length() > 0) {
                mPresenterMyhome.api_del_room(sUserName, sGetlink);
            }

        }
        finish();
    }

    private void initEvent() {
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sGetlink.length() > 0) {
                    showDialogComfirm("Thông báo", "Bạn có chắc chắn muốn xóa nhà hiện tại",
                            true, new ClickDialog() {
                                @Override
                                public void onClickYesDialog() {
                                    showDialogLoading();
                                    isShowdelete = true;
                                    mPresenterMyhome.api_del_room(sUserName, sGetlink);
                                }

                                @Override
                                public void onClickNoDialog() {

                                }
                            });
                }

            }
        });
        txt_lichdatnha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        txt_lichdonnha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        txt_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });
   /*     txt_tab_info_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_fragment_price_room();
            }
        });
        txt_tab_info_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_fragment_info_room();
            }
        });
        txt_title_tab_until.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_fragment_until();
            }
        });*/
    }

    @BindView(R.id.txt_lichdatnha)
    TextView txt_lichdatnha;
    @BindView(R.id.txt_lichdonnha)
    TextView txt_lichdonnha;
    @BindView(R.id.txt_calendar)
    TextView txt_calendar;

    private void select_lichdatnha() {
        txt_lichdatnha.setSelected(true);
        txt_lichdonnha.setSelected(false);
        txt_calendar.setSelected(false);
        txt_lichdatnha.setTextColor(getResources().getColor(R.color.White));
        txt_lichdonnha.setTextColor(getResources().getColor(R.color.app_main));
        txt_calendar.setTextColor(getResources().getColor(R.color.app_main));
    }

    private void select_lichdonnha() {
        txt_lichdatnha.setSelected(false);
        txt_lichdonnha.setSelected(true);
        txt_calendar.setSelected(false);
        txt_lichdatnha.setTextColor(getResources().getColor(R.color.app_main));
        txt_lichdonnha.setTextColor(getResources().getColor(R.color.White));
        txt_calendar.setTextColor(getResources().getColor(R.color.app_main));
    }

    private void select_calendar() {
        txt_lichdatnha.setSelected(false);
        txt_lichdonnha.setSelected(false);
        txt_calendar.setSelected(true);
        txt_lichdatnha.setTextColor(getResources().getColor(R.color.app_main));
        txt_lichdonnha.setTextColor(getResources().getColor(R.color.app_main));
        txt_calendar.setTextColor(getResources().getColor(R.color.White));

    }

    public static ObjHomeStay objMyhome;
    public static boolean isUpdate = false;

    private void initData() {
        try {
            sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
            objMyhome = (ObjHomeStay) getIntent().getSerializableExtra(Constants.KEY_SEND_MYHOME);
            isUpdate = getIntent().getBooleanExtra(Constants.KEY_IS_UPDATE_MYHOME, false);
            if (isUpdate) {
                if (objMyhome.getGENLINK() != null)
                    sGetlink = objMyhome.getGENLINK();
                showDialogLoading();
                mPresenterMyhome.api_get_room_detail(sUserName, sGetlink);
                //load_fragment_info_room();
            } else {
                showDialogLoading();
                mPresenterMyhome.api_new_room(sUserName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    Fragment fragmentCurrent;
    FragmentInfoNewRoom fragmentInfoNewRoom;
    FragmentPriceNewRoom fragmentPriceNewRoom;
    FragmentAmenitiesNewRoom fragmentAmenitiesNewRoom;
/*

    private void load_fragment_price_room() {
        txt_tab_info_room.setTextColor(getResources().getColor(R.color.app_main));
        txt_tab_info_room.setBackgroundColor(getResources().getColor(R.color.White));
        txt_tab_info_price.setTextColor(getResources().getColor(R.color.White));
        txt_tab_info_price.setBackgroundColor(getResources().getColor(R.color.app_main));
        txt_title_tab_until.setTextColor(getResources().getColor(R.color.app_main));
        txt_title_tab_until.setBackgroundColor(getResources().getColor(R.color.White));
        fragmentPriceNewRoom = (FragmentPriceNewRoom) getSupportFragmentManager()
                .findFragmentByTag(FragmentPriceNewRoom.class.getName());

        if (fragmentPriceNewRoom == null) {
            fragmentPriceNewRoom = FragmentPriceNewRoom.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentPriceNewRoom.isAdded()) {
            transaction.add(R.id.frame_new_room, fragmentPriceNewRoom, FragmentPriceNewRoom.class.getName());
        } else {
            if (fragmentInfoNewRoom != null && fragmentInfoNewRoom.isAdded()) {
                transaction.hide(fragmentInfoNewRoom);
            }
            if (fragmentAmenitiesNewRoom != null && fragmentAmenitiesNewRoom.isAdded()) {
                transaction.hide(fragmentAmenitiesNewRoom);
            }

            transaction.hide(fragmentCurrent);
            transaction.show(fragmentPriceNewRoom);
        }
        fragmentCurrent = fragmentPriceNewRoom;
        transaction.commit();
    }

    private void load_fragment_until() {
        txt_tab_info_room.setTextColor(getResources().getColor(R.color.app_main));
        txt_tab_info_room.setBackgroundColor(getResources().getColor(R.color.White));
        txt_tab_info_price.setTextColor(getResources().getColor(R.color.app_main));
        txt_tab_info_price.setBackgroundColor(getResources().getColor(R.color.White));
        txt_title_tab_until.setTextColor(getResources().getColor(R.color.White));
        txt_title_tab_until.setBackgroundColor(getResources().getColor(R.color.app_main));
        fragmentAmenitiesNewRoom = (FragmentAmenitiesNewRoom) getSupportFragmentManager()
                .findFragmentByTag(FragmentAmenitiesNewRoom.class.getName());

        if (fragmentAmenitiesNewRoom == null) {
            fragmentAmenitiesNewRoom = FragmentAmenitiesNewRoom.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentAmenitiesNewRoom.isAdded()) {
            transaction.add(R.id.frame_new_room, fragmentAmenitiesNewRoom, FragmentAmenitiesNewRoom.class.getName());
        } else {
            if (fragmentInfoNewRoom != null && fragmentInfoNewRoom.isAdded()) {
                transaction.hide(fragmentInfoNewRoom);
            }
            if (fragmentPriceNewRoom != null && fragmentPriceNewRoom.isAdded()) {
                transaction.hide(fragmentPriceNewRoom);
            }
            transaction.hide(fragmentCurrent);
            transaction.show(fragmentAmenitiesNewRoom);
        }
        fragmentCurrent = fragmentAmenitiesNewRoom;
        transaction.commit();
    }

    private void load_fragment_info_room() {
        txt_tab_info_price.setTextColor(getResources().getColor(R.color.app_main));
        txt_tab_info_price.setBackgroundColor(getResources().getColor(R.color.White));
        txt_tab_info_room.setTextColor(getResources().getColor(R.color.White));
        txt_tab_info_room.setBackgroundColor(getResources().getColor(R.color.app_main));
        txt_title_tab_until.setTextColor(getResources().getColor(R.color.app_main));
        txt_title_tab_until.setBackgroundColor(getResources().getColor(R.color.White));
        fragmentInfoNewRoom = (FragmentInfoNewRoom) getSupportFragmentManager()
                .findFragmentByTag(FragmentInfoNewRoom.class.getName());

        if (fragmentInfoNewRoom == null) {
            fragmentInfoNewRoom = FragmentInfoNewRoom.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentInfoNewRoom.isAdded()) {
            transaction.add(R.id.frame_new_room, fragmentInfoNewRoom, FragmentInfoNewRoom.class.getName());
        } else {
            if (fragmentAmenitiesNewRoom != null && fragmentAmenitiesNewRoom.isAdded()) {
                transaction.hide(fragmentAmenitiesNewRoom);
            }
            if (fragmentPriceNewRoom != null && fragmentPriceNewRoom.isAdded()) {
                transaction.hide(fragmentPriceNewRoom);
            }
            transaction.hide(fragmentCurrent);
            transaction.show(fragmentInfoNewRoom);
        }
        fragmentCurrent = fragmentInfoNewRoom;
        transaction.commit();
    }
*/

    @Override
    public void show_error_api(String sService) {
        hideDialogLoading();
    }

    @Override
    public void show_get_mylist_room(GetRoomResponse objRes) {
        hideDialogLoading();
    }

    @Override
    public void show_room_detail(ObjHomeStay objRes) {
        hideDialogLoading();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            //load_fragment_info_room();
            objMyhome = objRes;
            initViewpager();
        }
    }

    @Override
    public void show_new_room(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            sGetlink = objError.getGENLINK();

            //load_fragment_info_room();
            initViewpager();
        }
    }

    @Override
    public void show_edit_room(ObjErrorApi objError) {
        hideDialogLoading();
    }

    @Override
    public void show_delete_room(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            if (isShowdelete) {
                Toast.makeText(this, "Xóa nhà thành công", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, new Intent());
            }
            finish();
        } else {
            showAlertDialog("Thông báo", objError.getRESULT());
        }
    }

    @Override
    public void show_update_state_room(ObjErrorApi objError) {

    }

    @Override
    public void show_get_album_image(ResponListImageHome objRes) {

    }
}
