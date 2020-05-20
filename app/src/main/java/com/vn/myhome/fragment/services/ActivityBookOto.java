package com.vn.myhome.fragment.services;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.vn.myhome.R;
import com.vn.myhome.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 29-March-2020
 * Time: 23:45
 * Version: 1.0
 */
public class ActivityBookOto extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ll_get_route)
    ConstraintLayout ll_get_route;
    @BindView(R.id.ll_get_car)
    ConstraintLayout ll_get_car;

    @Override
    public int setContentViewId() {
        return R.layout.activity_thuexe_oto;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEvent();
    }

    private void initEvent() {
        ll_get_route.setOnClickListener(this);
        ll_get_car.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_get_route:
                Intent intent = new Intent(ActivityBookOto.this, ActivityListHanhtrinhxe.class);
                startActivity(intent);
                break;
            case R.id.ll_get_car:
                Intent intent_getcar = new Intent(ActivityBookOto.this, ActivityListCar.class);
                startActivity(intent_getcar);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }
}
