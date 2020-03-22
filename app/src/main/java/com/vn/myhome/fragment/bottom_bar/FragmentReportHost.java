package com.vn.myhome.fragment.bottom_bar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vn.myhome.R;
import com.vn.myhome.base.BaseFragment;

import butterknife.ButterKnife;

public class FragmentReportHost extends BaseFragment {
    public static FragmentReportHost fragment;
    public static FragmentReportHost getInstance() {
        if (fragment == null) {
            synchronized (FragmentReportHost.class) {
                if (fragment == null)
                    fragment = new FragmentReportHost();
            }
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_host, container, false);
        ButterKnife.bind(this, view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

}
