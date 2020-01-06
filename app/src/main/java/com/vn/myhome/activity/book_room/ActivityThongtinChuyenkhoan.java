package com.vn.myhome.activity.book_room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vn.myhome.R;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.config.Constants;
import com.vn.myhome.untils.StringUtil;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 18-November-2019
 * Time: 22:31
 * Version: 1.0
 */
public class ActivityThongtinChuyenkhoan extends BaseActivity {
    @BindView(R.id.btn_hoanthanh)
    Button btn_hoanthanh;
    @BindView(R.id.txt_price_thanhtoan)
    TextView txt_price_thanhtoan;
    @BindView(R.id.txt_content)
    TextView txt_content;

    ImageView img_home;

    @Override
    public int setContentViewId() {
        return R.layout.activity_thongtin_thanhtoan;
    }

    private void initAppbar() {
        ImageView img_back = findViewById(R.id.btn_back);
        TextView txt_title = findViewById(R.id.txt_title);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent());
                finish();
            }
        });
        txt_title.setText("THÔNG TIN THANH TOÁN");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAppbar();
        initEvent();
        initData();
    }

    private void initData() {
        try {
            String price = getIntent().getStringExtra(Constants.KEY_SEND_PRICE_THANHTOAN);
            String sContent = getIntent().getStringExtra(Constants.KEY_SEND_CONTENT_THANHTOAN);
            txt_price_thanhtoan.setText(StringUtil.conventMonney_Long(price));
            txt_content.setText(sContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initEvent() {
        btn_hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent());
                finish();
            }
        });
    }
}
