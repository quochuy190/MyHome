package com.vn.myhome;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vn.myhome.activity.login.InterfaceLogin;
import com.vn.myhome.activity.login.PresenterLogin;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.config.Constants;
import com.vn.myhome.fragment.FragmentDatphong;
import com.vn.myhome.fragment.FragmentHome;
import com.vn.myhome.fragment.FragmentLichnhaAdmin;
import com.vn.myhome.fragment.FragmentMyHome;
import com.vn.myhome.fragment.FragmentSetup;
import com.vn.myhome.fragment.FragmentThongke;
import com.vn.myhome.fragment.bottom_bar.FragmentHostBottombar;
import com.vn.myhome.fragment.qldondep.FragmentHomeQldondep;
import com.vn.myhome.fragment.services.ActivityBookCar;
import com.vn.myhome.fragment.services.FragmentServicesAll;
import com.vn.myhome.fragment.tabCheckinCheckout.FragmentTabCheckinCheckout;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ObjVersion;
import com.vn.myhome.models.ResponseApi.AmentiniesResponse;
import com.vn.myhome.models.ResponseApi.CityResponse;
import com.vn.myhome.models.ResponseApi.GetTypeResponse;
import com.vn.myhome.models.ResponseApi.VersionResponse;
import com.vn.myhome.presenter.AmentiniesPresenter;
import com.vn.myhome.presenter.InterfaceAmenities;
import com.vn.myhome.presenter.InterfaceUpdateVersion;
import com.vn.myhome.presenter.UpdateVersionPresenter;
import com.vn.myhome.untils.SharedPrefs;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements InterfaceLogin.View, InterfaceAmenities.View, InterfaceUpdateVersion.View {
    private static final String TAG = "MainActivity";
    @BindView(R.id.nav_bottom_bar)
    BottomNavigationView bottom_bar;
    @BindView(R.id.txt_xemthem)
    TextView txt_xemthem;
    @BindView(R.id.txt_dong)
    TextView txt_dong;
    @BindView(R.id.ll_promotion)
    ConstraintLayout ll_promotion;
    @BindView(R.id.img_sale)
    ImageView img_sale;
    PresenterLogin mPresenterLogin;
    AmentiniesPresenter mPresenterAmentinies;
    UpdateVersionPresenter mPresenterUpdateVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenterLogin = new PresenterLogin(this);
        mPresenterAmentinies = new AmentiniesPresenter(this);
        mPresenterUpdateVersion = new UpdateVersionPresenter(this);
        check_show_popup();
        initBottomBar();
        initData();
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        if (objLogin.getUSER_TYPE().equals(Constants.UserType.CHUNHA)
                || objLogin.getUSER_TYPE().equals(Constants.UserType.ADMIN)) {
            TextView textView = (TextView) bottom_bar.findViewById(R.id.tab_myhome).findViewById(R.id.largeLabel);
            textView.setText("MyHome");
        } else if (objLogin.getUSER_TYPE().equals(Constants.UserType.DICHVU)) {
            TextView textView = (TextView) bottom_bar.findViewById(R.id.tab_myhome).findViewById(R.id.largeLabel);
            textView.setText("Ql dọn dẹp");
        }else if (objLogin.getUSER_TYPE().equals(Constants.UserType.DICHVU)) {
            TextView textView = (TextView) bottom_bar.findViewById(R.id.tab_myhome).findViewById(R.id.largeLabel);
            textView.setText("Lịch dọn dẹp");
        }

    }

    Dialog dialog;

    private void check_show_popup() {

        img_sale.setImageResource(R.drawable.img_sale_home);
        if (!App.isShowPopup) {
            ll_promotion.setVisibility(View.VISIBLE);
            App.isShowPopup = true;
        }
        txt_xemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityBookCar.class));
                ll_promotion.setVisibility(View.GONE);
            }
        });
        txt_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_promotion.setVisibility(View.GONE);
            }
        });
    }

    String sUserName = "", sPass = "";

    private void initData() {
        mPresenterUpdateVersion.api_get_version();
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        sPass = SharedPrefs.getInstance().get(Constants.KEY_SAVE_PASSWORD, String.class);
        boolean isLoginstart = getIntent().getBooleanExtra(Constants.KEY_SEND_LOGIN_TO_MAIN, false);
        sTab = getIntent().getStringExtra(Constants.KEY_SEND_NOTIFYCATION);
        if (!isLoginstart) {
            if (sUserName != null && sPass != null & sUserName.length() > 0 && sPass.length() > 0) {
                showDialogLoading();
                mPresenterLogin.api_login("user/login", sUserName, sPass);
                mPresenterAmentinies.api_get_amenities(sUserName);
            }
        } else {
            if (sTab != null && sTab.equals("1")) {
                bottom_bar.setSelectedItemId(R.id.tab_datphong);
            } else if (sTab != null && sTab.equals("2")) {
                bottom_bar.setSelectedItemId(R.id.tab_myhome);
            } else if (sTab != null && sTab.equals("3")) {
                bottom_bar.setSelectedItemId(R.id.tab_thongke);
            } else if (sTab != null && sTab.equals("4")) {
                bottom_bar.setSelectedItemId(R.id.tab_setup);
            } else {
                ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
            /*    if (objLogin.getUSER_TYPE().equals(Constants.UserType.CHUNHA)) {
                    bottom_bar.setSelectedItemId(R.id.tab_myhome);
                } else
                    loadFragmentHome();*/
                if (objLogin.getUSER_TYPE().equals(Constants.UserType.CHUNHA)) {
                    bottom_bar.setSelectedItemId(R.id.tab_myhome);
                } else if (objLogin.getUSER_TYPE().equals(Constants.UserType.ADMIN)) {
                    bottom_bar.setSelectedItemId(R.id.tab_datphong);
                } else if (objLogin.getUSER_TYPE().equals(Constants.UserType.DICHVU) ||
                        objLogin.getUSER_TYPE().equals(Constants.UserType.CHECK_IN)) {
                    bottom_bar.setSelectedItemId(R.id.tab_myhome);
                } else
                    loadFragmentHome();
            }
            // loadFragmentHome();
            if (sUserName != null && sPass != null & sUserName.length() > 0 && sPass.length() > 0) {
                mPresenterAmentinies.api_get_amenities(sUserName);
                String UUID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                String sTokenKey = SharedPrefs.getInstance().get(Constants.KEY_TOKEN, String.class);
                mPresenterLogin.api_update_device(sUserName, App.versionName,
                        android.os.Build.BRAND + " " + android.os.Build.MODEL, sTokenKey,
                        android.os.Build.VERSION.RELEASE, "2", UUID);
            }
        }


    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_main;
    }

    String sTab;

    private void initBottomBar() {
        bottom_bar.setItemIconTintList(null);
        bottom_bar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        if (objLogin.getUSER_TYPE().equals(Constants.UserType.DICHVU)) {
            MenuItem selectedItem = bottom_bar.getMenu().findItem(R.id.tab_myhome);
            selectedItem.setTitle("Ql dọn dẹp");
        } else if (objLogin.getUSER_TYPE().equals(Constants.UserType.CHECK_IN)) {
            MenuItem selectedItem = bottom_bar.getMenu().findItem(R.id.tab_myhome);
            selectedItem.setTitle("Lịch dọn dẹp");
        }

        // init_badger();
        //  bottom_bar.getTabAtPosition(x).setVisibility(View.GONE);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
            switch (item.getItemId()) {
                case R.id.tab_home:
                    loadFragmentHome();
                    return true;
                case R.id.tab_datphong:
                    if (objLogin.getUSER_TYPE().equals(Constants.UserType.ADMIN)) {
                        loadFragmentLichnha_Admin();
                    } else
                        loadFragmentDatphong();
                    return true;
                case R.id.tab_thongke:
                    loadFragmentServiceAll();
                    return true;
                case R.id.tab_setup:
                    loadFragmentSetup();
                    return true;
                case R.id.tab_myhome:
                    if (objLogin.getUSER_TYPE().equals(Constants.UserType.DICHVU)) {
                        loadFragment_Lichdonnha_DV();
                    } else if (objLogin.getUSER_TYPE().equals(Constants.UserType.CHECK_IN)) {
                        loadFragmentUserCheckin();
                    } else if (objLogin.getUSER_TYPE().equals(Constants.UserType.CHUNHA)) {
                        loadFragmentHost();
                    } else
                        loadFragmentHost();
                    return true;
            }
            return false;
        }
    };
    FragmentHome fragmentHome;
    FragmentDatphong fragmentDatphong;
    FragmentThongke fragmentThongke;
    FragmentSetup fragmentSetup;
    FragmentMyHome fragmentMyhome;
    FragmentHostBottombar fragmentHost;
    FragmentLichnhaAdmin fragmentLichnhaAdmin;
    FragmentHomeQldondep fragmentLichdonnha_home;
    FragmentServicesAll fragmentServicesAll;
    FragmentTabCheckinCheckout fragmentTabCheckinCheckout;

    private void loadFragmentHome() {
        check_user_type();
        //   objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        fragmentHome = (FragmentHome) getSupportFragmentManager().findFragmentByTag(FragmentHome.class.getName());
        if (fragmentHome == null) {
            fragmentHome = FragmentHome.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentHome.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentHome, FragmentHome.class.getName());
        } else {
            //  transaction.hide(fragmentCurrent);
            if (fragmentDatphong != null && fragmentDatphong.isAdded()) {
                transaction.hide(fragmentDatphong);
            }
            if (fragmentThongke != null && fragmentThongke.isAdded()) {
                transaction.hide(fragmentThongke);
            }
            if (fragmentSetup != null && fragmentSetup.isAdded()) {
                transaction.hide(fragmentSetup);
            }
            if (fragmentMyhome != null && fragmentMyhome.isAdded()) {
                transaction.hide(fragmentMyhome);
            }
            if (fragmentLichnhaAdmin != null && fragmentLichnhaAdmin.isAdded()) {
                transaction.hide(fragmentLichnhaAdmin);
            }
            if (fragmentLichdonnha_home != null && fragmentLichdonnha_home.isAdded()) {
                transaction.hide(fragmentLichdonnha_home);
            }
            if (fragmentHost != null && fragmentHost.isAdded()) {
                transaction.hide(fragmentHost);
            }
            if (fragmentServicesAll != null && fragmentServicesAll.isAdded()) {
                transaction.hide(fragmentServicesAll);
            }
            transaction.show(fragmentHome);
        }
        //   fragmentCurrent = fragmentHome;
        transaction.commit();
    }

    private void check_user_type() {
        try {
            ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
            if (objLogin != null) {
                switch (objLogin.getUSER_TYPE()) {
                    case Constants.UserType.ADMIN:
                        //     bottom_bar.getMenu().removeItem(R.id.tab_myhome);
                        break;
                    case Constants.UserType.CHUNHA:
                        break;
                    case Constants.UserType.CTV:
                        bottom_bar.getMenu().removeItem(R.id.tab_myhome);
                        break;
                    case Constants.UserType.DICHVU:
                        //   bottom_bar.getMenu().removeItem(R.id.tab_myhome);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
/*
    private void show_all_fragment() {
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        fragmentMyhome = (FragmentMyHome) getSupportFragmentManager().findFragmentByTag(FragmentMyHome.class.getName());
        if (fragmentMyhome == null) {
            fragmentMyhome = FragmentMyHome.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (objLogin.getUSER_TYPE().equals(Constants.UserType.ADMIN)) {
            if (!fragmentDatphong.isAdded()) {
                transaction.add(R.id.frame_home_fragment, fragmentDatphong, FragmentDatphong.class.getName());
            }
        } else {

        }

        if (!fragmentThongke.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentThongke, FragmentThongke.class.getName());
        }
        if (!fragmentSetup.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentSetup, FragmentSetup.class.getName());
        }
        if (!objLogin.getUSER_TYPE().equals(Constants.UserType.CTV)) {
            if (!fragmentMyhome.isAdded()) {
                transaction.add(R.id.frame_home_fragment, fragmentMyhome, FragmentMyHome.class.getName());
            }
        }
        if (!fragmentHome.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentHome, FragmentHome.class.getName());
        }
        if (!fragmentLichdonnha_home.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentLichdonnha_home, Fragment_Tab_Lichdonnha_Admin.class.getName());
        }
        transaction.commit();
    }*/

    private void loadFragmentMyHome() {
        //   objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        fragmentMyhome = (FragmentMyHome) getSupportFragmentManager().findFragmentByTag(FragmentMyHome.class.getName());
        if (fragmentMyhome == null) {
            fragmentMyhome = FragmentMyHome.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentMyhome.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentMyhome, FragmentMyHome.class.getName());
        } else {
            //  transaction.hide(fragmentCurrent);
            if (fragmentDatphong != null && fragmentDatphong.isAdded()) {
                transaction.hide(fragmentDatphong);
            }
            if (fragmentThongke != null && fragmentThongke.isAdded()) {
                transaction.hide(fragmentThongke);
            }
            if (fragmentSetup != null && fragmentSetup.isAdded()) {
                transaction.hide(fragmentSetup);
            }
            if (fragmentHome != null && fragmentHome.isAdded()) {
                transaction.hide(fragmentHome);
            }
            if (fragmentLichnhaAdmin != null && fragmentLichnhaAdmin.isAdded()) {
                transaction.hide(fragmentLichnhaAdmin);
            }
            if (fragmentLichdonnha_home != null && fragmentLichdonnha_home.isAdded()) {
                transaction.hide(fragmentLichdonnha_home);
            }
            transaction.show(fragmentMyhome);
        }
        //   fragmentCurrent = fragmentHome;
        transaction.commit();
    }

    private void loadFragmentHost() {
        //   objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        fragmentHost = (FragmentHostBottombar) getSupportFragmentManager().findFragmentByTag(FragmentHostBottombar.class.getName());
        if (fragmentHost == null) {
            fragmentHost = FragmentHostBottombar.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentHost.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentHost, FragmentHostBottombar.class.getName());
        } else {
            //  transaction.hide(fragmentCurrent);
            if (fragmentDatphong != null && fragmentDatphong.isAdded()) {
                transaction.hide(fragmentDatphong);
            }
            if (fragmentThongke != null && fragmentThongke.isAdded()) {
                transaction.hide(fragmentThongke);
            }
            if (fragmentSetup != null && fragmentSetup.isAdded()) {
                transaction.hide(fragmentSetup);
            }
            if (fragmentHome != null && fragmentHome.isAdded()) {
                transaction.hide(fragmentHome);
            }
            if (fragmentLichnhaAdmin != null && fragmentLichnhaAdmin.isAdded()) {
                transaction.hide(fragmentLichnhaAdmin);
            }
            if (fragmentLichdonnha_home != null && fragmentLichdonnha_home.isAdded()) {
                transaction.hide(fragmentLichdonnha_home);
            }
            if (fragmentServicesAll != null && fragmentServicesAll.isAdded()) {
                transaction.hide(fragmentServicesAll);
            }
            transaction.show(fragmentHost);
        }
        //   fragmentCurrent = fragmentHome;
        transaction.commit();
    }

    private void loadFragmentThongke() {
        //   objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        fragmentThongke = (FragmentThongke) getSupportFragmentManager().findFragmentByTag(FragmentThongke.class.getName());
        if (fragmentThongke == null) {
            fragmentThongke = FragmentThongke.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentThongke.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentThongke, FragmentThongke.class.getName());
        } else {
            //  transaction.hide(fragmentCurrent);
            if (fragmentHome != null && fragmentHome.isAdded()) {
                transaction.hide(fragmentHome);
            }
            if (fragmentDatphong != null && fragmentThongke.isAdded()) {
                transaction.hide(fragmentThongke);
            }
            if (fragmentSetup != null && fragmentSetup.isAdded()) {
                transaction.hide(fragmentSetup);
            }
            if (fragmentMyhome != null && fragmentMyhome.isAdded()) {
                transaction.hide(fragmentMyhome);
            }
            if (fragmentLichnhaAdmin != null && fragmentLichnhaAdmin.isAdded()) {
                transaction.hide(fragmentLichnhaAdmin);
            }
            if (fragmentLichdonnha_home != null && fragmentLichdonnha_home.isAdded()) {
                transaction.hide(fragmentLichdonnha_home);
            }
            if (fragmentHost != null && fragmentHost.isAdded()) {
                transaction.hide(fragmentHost);
            }
            transaction.show(fragmentThongke);
        }
        //   fragmentCurrent = fragmentHome;
        transaction.commit();
    }

    private void loadFragmentServiceAll() {
        //   objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        fragmentServicesAll = (FragmentServicesAll) getSupportFragmentManager().findFragmentByTag(FragmentServicesAll.class.getName());
        if (fragmentServicesAll == null) {
            fragmentServicesAll = FragmentServicesAll.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentServicesAll.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentServicesAll, FragmentServicesAll.class.getName());
        } else {
            //  transaction.hide(fragmentCurrent);
            if (fragmentHome != null && fragmentHome.isAdded()) {
                transaction.hide(fragmentHome);
            }
            if (fragmentDatphong != null && fragmentDatphong.isAdded()) {
                transaction.hide(fragmentDatphong);
            }
            if (fragmentSetup != null && fragmentSetup.isAdded()) {
                transaction.hide(fragmentSetup);
            }
            if (fragmentMyhome != null && fragmentMyhome.isAdded()) {
                transaction.hide(fragmentMyhome);
            }
            if (fragmentLichnhaAdmin != null && fragmentLichnhaAdmin.isAdded()) {
                transaction.hide(fragmentLichnhaAdmin);
            }
            if (fragmentLichdonnha_home != null && fragmentLichdonnha_home.isAdded()) {
                transaction.hide(fragmentLichdonnha_home);
            }
            if (fragmentHost != null && fragmentHost.isAdded()) {
                transaction.hide(fragmentHost);
            }
            if (fragmentHost != null && fragmentHost.isAdded()) {
                transaction.hide(fragmentHost);
            }
            transaction.show(fragmentServicesAll);
        }
        //   fragmentCurrent = fragmentHome;
        transaction.commit();
    }

    private void loadFragmentDatphong() {
        //   objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        fragmentDatphong = (FragmentDatphong) getSupportFragmentManager().findFragmentByTag(FragmentDatphong.class.getName());
        if (fragmentDatphong == null) {
            fragmentDatphong = FragmentDatphong.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentDatphong.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentDatphong, FragmentDatphong.class.getName());
        } else {
            //  transaction.hide(fragmentCurrent);
            if (fragmentHome != null && fragmentHome.isAdded()) {
                transaction.hide(fragmentHome);
            }
            if (fragmentThongke != null && fragmentThongke.isAdded()) {
                transaction.hide(fragmentThongke);
            }
            if (fragmentSetup != null && fragmentSetup.isAdded()) {
                transaction.hide(fragmentSetup);
            }
            if (fragmentMyhome != null && fragmentMyhome.isAdded()) {
                transaction.hide(fragmentMyhome);
            }
            if (fragmentLichdonnha_home != null && fragmentLichdonnha_home.isAdded()) {
                transaction.hide(fragmentLichdonnha_home);
            }
            if (fragmentLichnhaAdmin != null && fragmentLichnhaAdmin.isAdded()) {
                transaction.hide(fragmentLichnhaAdmin);
            }
            if (fragmentLichdonnha_home != null && fragmentLichdonnha_home.isAdded()) {
                transaction.hide(fragmentLichdonnha_home);
            }
            if (fragmentHost != null && fragmentHost.isAdded()) {
                transaction.hide(fragmentHost);
            }
            if (fragmentServicesAll != null && fragmentServicesAll.isAdded()) {
                transaction.hide(fragmentServicesAll);
            }
            transaction.show(fragmentDatphong);
        }
        //   fragmentCurrent = fragmentHome;
        transaction.commit();
    }

    private void loadFragmentLichnha_Admin() {
        //   objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        fragmentLichnhaAdmin = (FragmentLichnhaAdmin) getSupportFragmentManager().
                findFragmentByTag(FragmentLichnhaAdmin.class.getName());
        if (fragmentLichnhaAdmin == null) {
            fragmentLichnhaAdmin = FragmentLichnhaAdmin.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentLichnhaAdmin.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentLichnhaAdmin, FragmentLichnhaAdmin.class.getName());
        } else {
            //  transaction.hide(fragmentCurrent);
            if (fragmentHome != null && fragmentHome.isAdded()) {
                transaction.hide(fragmentHome);
            }
            if (fragmentThongke != null && fragmentThongke.isAdded()) {
                transaction.hide(fragmentThongke);
            }
            if (fragmentSetup != null && fragmentSetup.isAdded()) {
                transaction.hide(fragmentSetup);
            }
            if (fragmentMyhome != null && fragmentMyhome.isAdded()) {
                transaction.hide(fragmentMyhome);
            }
            if (fragmentLichdonnha_home != null && fragmentLichdonnha_home.isAdded()) {
                transaction.hide(fragmentLichdonnha_home);
            }
            if (fragmentHost != null && fragmentHost.isAdded()) {
                transaction.hide(fragmentHost);
            }
            if (fragmentServicesAll != null && fragmentServicesAll.isAdded()) {
                transaction.hide(fragmentServicesAll);
            }
            transaction.show(fragmentLichnhaAdmin);
        }
        //   fragmentCurrent = fragmentHome;
        transaction.commit();
    }

    private void loadFragmentUserCheckin() {
        //   objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        fragmentTabCheckinCheckout = (FragmentTabCheckinCheckout) getSupportFragmentManager().
                findFragmentByTag(FragmentTabCheckinCheckout.class.getName());
        if (fragmentTabCheckinCheckout == null) {
            fragmentTabCheckinCheckout = FragmentTabCheckinCheckout.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentTabCheckinCheckout.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentTabCheckinCheckout,
                    FragmentTabCheckinCheckout.class.getName());
        } else {
            //  transaction.hide(fragmentCurrent);
            if (fragmentHome != null && fragmentHome.isAdded()) {
                transaction.hide(fragmentHome);
            }
            if (fragmentThongke != null && fragmentThongke.isAdded()) {
                transaction.hide(fragmentThongke);
            }
            if (fragmentSetup != null && fragmentSetup.isAdded()) {
                transaction.hide(fragmentSetup);
            }
            if (fragmentMyhome != null && fragmentMyhome.isAdded()) {
                transaction.hide(fragmentMyhome);
            }
            if (fragmentDatphong != null && fragmentDatphong.isAdded()) {
                transaction.hide(fragmentDatphong);
            }
            if (fragmentLichnhaAdmin != null && fragmentLichnhaAdmin.isAdded()) {
                transaction.hide(fragmentLichnhaAdmin);
            }
            if (fragmentHost != null && fragmentHost.isAdded()) {
                transaction.hide(fragmentHost);
            }
            if (fragmentServicesAll != null && fragmentServicesAll.isAdded()) {
                transaction.hide(fragmentServicesAll);
            }
            transaction.show(fragmentTabCheckinCheckout);
        }
        //   fragmentCurrent = fragmentHome;
        transaction.commit();
    }

    private void loadFragment_Lichdonnha_DV() {
        //   objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        fragmentLichdonnha_home = (FragmentHomeQldondep) getSupportFragmentManager().
                findFragmentByTag(FragmentHomeQldondep.class.getName());
        if (fragmentLichdonnha_home == null) {
            fragmentLichdonnha_home = FragmentHomeQldondep.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentLichdonnha_home.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentLichdonnha_home,
                    FragmentHomeQldondep.class.getName());
        } else {
            //  transaction.hide(fragmentCurrent);
            if (fragmentHome != null && fragmentHome.isAdded()) {
                transaction.hide(fragmentHome);
            }
            if (fragmentThongke != null && fragmentThongke.isAdded()) {
                transaction.hide(fragmentThongke);
            }
            if (fragmentSetup != null && fragmentSetup.isAdded()) {
                transaction.hide(fragmentSetup);
            }
            if (fragmentMyhome != null && fragmentMyhome.isAdded()) {
                transaction.hide(fragmentMyhome);
            }
            if (fragmentDatphong != null && fragmentDatphong.isAdded()) {
                transaction.hide(fragmentDatphong);
            }
            if (fragmentLichnhaAdmin != null && fragmentLichnhaAdmin.isAdded()) {
                transaction.hide(fragmentLichnhaAdmin);
            }
            if (fragmentHost != null && fragmentHost.isAdded()) {
                transaction.hide(fragmentHost);
            }
            if (fragmentServicesAll != null && fragmentServicesAll.isAdded()) {
                transaction.hide(fragmentServicesAll);
            }
            transaction.show(fragmentLichdonnha_home);
        }
        //   fragmentCurrent = fragmentHome;
        transaction.commit();
    }

    private void loadFragmentSetup() {
        //   objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        fragmentSetup = (FragmentSetup) getSupportFragmentManager().findFragmentByTag(FragmentSetup.class.getName());
        if (fragmentSetup == null) {
            fragmentSetup = FragmentSetup.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentSetup.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentSetup, FragmentSetup.class.getName());
        } else {
            //  transaction.hide(fragmentCurrent);
            if (fragmentHome != null && fragmentHome.isAdded()) {
                transaction.hide(fragmentHome);
            }
            if (fragmentThongke != null && fragmentThongke.isAdded()) {
                transaction.hide(fragmentThongke);
            }
            if (fragmentDatphong != null && fragmentDatphong.isAdded()) {
                transaction.hide(fragmentDatphong);
            }
            if (fragmentLichnhaAdmin != null && fragmentLichnhaAdmin.isAdded()) {
                transaction.hide(fragmentLichnhaAdmin);
            }
            if (fragmentMyhome != null && fragmentMyhome.isAdded()) {
                transaction.hide(fragmentMyhome);
            }
            if (fragmentLichdonnha_home != null && fragmentLichdonnha_home.isAdded()) {
                transaction.hide(fragmentLichdonnha_home);
            }
            if (fragmentHost != null && fragmentHost.isAdded()) {
                transaction.hide(fragmentHost);
            }
            if (fragmentServicesAll != null && fragmentServicesAll.isAdded()) {
                transaction.hide(fragmentServicesAll);
            }
            transaction.show(fragmentSetup);
        }
        //   fragmentCurrent = fragmentHome;
        transaction.commit();
    }

    @Override
    public void show_error_api(String sService) {
        hideDialogLoading();
    }

    ObjVersion mVersion;

    @Override
    public void show_get_version(VersionResponse objError) {
        if (objError != null && objError.getERROR().equals("0000")) {
            if (objError.getINFO() != null) {
                for (int i = 0; i < objError.getINFO().size(); i++) {
                    if (objError.getINFO().get(i).getNAME().equals("ANDROID")) {
                        mVersion = objError.getINFO().get(i);
                    }
                }
            }
            int version = Integer.parseInt(mVersion.getVERSION());
            int version_code = App.versionCode;
            if (version_code < version) {
                //  showDialogNotify("Thông báo","Hiện đang có phiên bản mới ");
                showDialogComfirm("Thông báo",
                        "Đang có phiên bản cập nhật mới bạn có muốn cập nhật không?",
                        true, new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                }
                            }

                            @Override
                            public void onClickNoDialog() {

                            }
                        });
            }
        }
    }


    @Override
    public void show_get_amenities() {
        hideDialogLoading();

    }

    @Override
    public void show_amenities_room(AmentiniesResponse objRes) {

    }

    @Override
    public void show_update_amenities(ObjErrorApi objError) {

    }

    @Override
    public void show_login(ObjLogin objLogin) {
        hideDialogLoading();
        if (objLogin != null && objLogin.getERROR().equals("0000")) {
            //   bottom_bar.findViewById(R.id.tab_myhome).setVisibility(View.GONE);
            //if (objLogin)
            if (sTab != null && sTab.equals("1")) {
                bottom_bar.setSelectedItemId(R.id.tab_datphong);
            } else if (sTab != null && sTab.equals("2")) {
                bottom_bar.setSelectedItemId(R.id.tab_myhome);
            } else if (sTab != null && sTab.equals("3")) {
                bottom_bar.setSelectedItemId(R.id.tab_thongke);
            } else if (sTab != null && sTab.equals("4")) {
                bottom_bar.setSelectedItemId(R.id.tab_setup);
            } else {
                if (objLogin.getUSER_TYPE().equals(Constants.UserType.CHUNHA)) {
                    bottom_bar.setSelectedItemId(R.id.tab_myhome);
                } else if (objLogin.getUSER_TYPE().equals(Constants.UserType.ADMIN)) {
                    bottom_bar.setSelectedItemId(R.id.tab_datphong);
                } else if (objLogin.getUSER_TYPE().equals(Constants.UserType.DICHVU) ||
                        objLogin.getUSER_TYPE().equals(Constants.UserType.CHECK_IN)) {
                    bottom_bar.setSelectedItemId(R.id.tab_myhome);
                } else
                    loadFragmentHome();
            }
            String sTokenKey = SharedPrefs.getInstance().get(Constants.KEY_TOKEN, String.class);
            if (sTokenKey == null)
                sTokenKey = "";
            String UUID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            mPresenterLogin.api_update_device(sUserName, App.versionName,
                    android.os.Build.BRAND + " " + android.os.Build.MODEL, sTokenKey,
                    android.os.Build.VERSION.RELEASE, "2", UUID);
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_OBJECT_LOGIN, objLogin);
        } else showDialogNotify("Thông báo", objLogin.getRESULT());
    }

    @Override
    public void show_reg_user(ObjErrorApi objError) {
        hideDialogLoading();
    }

    @Override
    public void show_get_type(GetTypeResponse objRes) {
        hideDialogLoading();
    }

    @Override
    public void show_get_city(CityResponse objResCity) {
        hideDialogLoading();

    }

    @Override
    public void show_update_device(ObjErrorApi objError) {

    }
}
