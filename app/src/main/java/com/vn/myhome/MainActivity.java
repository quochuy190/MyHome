package com.vn.myhome;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.fragment.FragmentDatphong;
import com.vn.myhome.fragment.FragmentHome;
import com.vn.myhome.fragment.FragmentSetup;
import com.vn.myhome.fragment.FragmentThongke;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.nav_bottom_bar)
    BottomNavigationView bottom_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBottomBar();
        loadFragmentHome();
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_main;
    }

    private void initBottomBar() {
        bottom_bar.setItemIconTintList(null);
        bottom_bar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // init_badger();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.tab_home:
                    loadFragmentHome();
                    return true;
                case R.id.tab_datphong:
                    loadFragmentDatphong();
                    return true;
                case R.id.tab_thongke:
                    loadFragmentThongke();
                    return true;
                case R.id.tab_setup:
                    loadFragmentSetup();
                    return true;
            }
            return false;
        }
    };
    FragmentHome fragmentHome;
    FragmentDatphong fragmentDatphong;
    FragmentThongke fragmentThongke;
    FragmentSetup fragmentSetup;

    private void loadFragmentHome() {
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
            transaction.show(fragmentHome);
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
            transaction.show(fragmentThongke);
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
            transaction.show(fragmentDatphong);
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
            transaction.show(fragmentSetup);
        }
        //   fragmentCurrent = fragmentHome;
        transaction.commit();
    }
}
