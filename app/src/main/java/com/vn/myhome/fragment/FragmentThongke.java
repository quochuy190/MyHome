package com.vn.myhome.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vn.myhome.R;
import com.vn.myhome.base.BaseFragment;

import butterknife.ButterKnife;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 22-April-2019
 * Time: 10:30
 * Version: 1.0
 */
public class FragmentThongke extends BaseFragment {
    private static final String TAG = "FragmentSetup";
    public static FragmentThongke fragment;


    public static FragmentThongke getInstance() {
        if (fragment == null) {
            synchronized (FragmentThongke.class) {
                if (fragment == null)
                    fragment = new FragmentThongke();
            }
        }
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup, container, false);
        ButterKnife.bind(this, view);
        Log.e(TAG, "onCreateView: Setup");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

}
