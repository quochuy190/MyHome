package com.vn.myhome.activity.book_room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vn.myhome.R;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.presenter.InterfaceKindofPaid;
import com.vn.myhome.presenter.KindofPairPresenter;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.StringUtil;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 18-November-2019
 * Time: 22:31
 * Version: 1.0
 */
public class ActivityThongtinChuyenkhoan extends BaseActivity implements InterfaceKindofPaid.View {
    @BindView(R.id.btn_hoanthanh)
    Button btn_hoanthanh;
    @BindView(R.id.txt_price_thanhtoan)
    TextView txt_price_thanhtoan;
    @BindView(R.id.txt_content)
    TextView txt_content;
    @BindView(R.id.btn_change_pay)
    TextView btn_change_pay;
    @BindView(R.id.txt_title_bnt_change)
    TextView txt_title_bnt_change;
    KindofPairPresenter mPresenterKindokPair;
    ImageView img_home;
    String sId_BookService;

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
        mPresenterKindokPair = new KindofPairPresenter(this);
        initAppbar();
        initEvent();
        initData();
    }

    private void initData() {
        try {
            String price = getIntent().getStringExtra(Constants.KEY_SEND_PRICE_THANHTOAN);
            long lPrice = Long.parseLong(price);
            String sContent = getIntent().getStringExtra(Constants.KEY_SEND_CONTENT_THANHTOAN);
            sId_BookService = getIntent().getStringExtra(Constants.KEY_SEND_ID_BOOKSERVICE_THANHTOAN);
            long price_chietkhau = (lPrice*85)/100;
            txt_price_thanhtoan.setText(StringUtil.conventMonney_Long(price_chietkhau+""));
            txt_content.setText(sContent);
            if (sId_BookService != null && sId_BookService.length() > 0) {
                txt_title_bnt_change.setVisibility(View.VISIBLE);
                btn_change_pay.setVisibility(View.VISIBLE);
            } else {
                txt_title_bnt_change.setVisibility(View.INVISIBLE);
                btn_change_pay.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initEvent() {
        btn_change_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogLoading();
                String sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                mPresenterKindokPair.api_change_kind_of_paid(sUsername, sId_BookService,
                        "1");
            }
        });
        btn_hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent());
                finish();
            }
        });
    }

    @Override
    public void show_error_api(ObjErrorApi sService) {
        hideDialogLoading();
    }

    @Override
    public void show_change_kind_of_paid(ObjErrorApi sService) {
        hideDialogLoading();
        if (sService.getERROR().equals("0000")) {
            Toast.makeText(this, "Thay đổi trạng thái thanh toán thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else showAlertDialog("Thông báo", sService.getRESULT());
    }
}
