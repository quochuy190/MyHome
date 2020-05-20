package com.vn.myhome.fragment.services;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vn.myhome.R;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjListBookCar;
import com.vn.myhome.models.ResponseApi.ResponseBookCarDetail;
import com.vn.myhome.models.ResponseApi.ResponsePriceEstimates;
import com.vn.myhome.models.ResponseApi.RouteResponse;
import com.vn.myhome.network.response.ResponGetListBookCar;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.TimeUtils;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 19-April-2020
 * Time: 23:26
 * Version: 1.0
 */
public class ActivityBookCarDetail extends BaseActivity implements InterfaceServices.View {
    PresenterServices mPresenter;
    @BindView(R.id.txt_madh)
    TextView txt_madh;
    @BindView(R.id.txt_state)
    TextView txt_state;
    @BindView(R.id.txt_name_customer)
    TextView txt_name_customer;
    @BindView(R.id.txt_phone_customer)
    TextView txt_phone_customer;
    @BindView(R.id.txt_address_customer)
    TextView txt_address_customer;
    @BindView(R.id.edt_ngaydon)
    EditText edt_ngaydon;
    @BindView(R.id.edt_gio)
    EditText edt_gio;
    @BindView(R.id.txt_detail)
    TextView txt_detail;

    @Override
    public int setContentViewId() {
        return R.layout.activity_bookcar_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterServices(this);
        initAppbar();
        initData();
    }

    private void initData() {
        String sBookCarId = getIntent().getStringExtra(Constants.KEY_SEND_ID_BOOKCAR);
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        showDialogLoading();
        mPresenter.api_get_bookcar_detail(sUserName, sBookCarId);
    }

    private void initAppbar() {
        ImageView img_back = findViewById(R.id.btn_back);
        TextView txt_title = findViewById(R.id.txt_title);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_title.setText("CHI TIẾT ĐẶT XE");
    }

    @Override
    public void show_error_api(String sService) {

    }

    @Override
    public void show_get_type_car(RouteResponse objError) {

    }

    @Override
    public void show_get_route_car(RouteResponse objError) {

    }

    @Override
    public void show_get_price_estimates(ResponsePriceEstimates objRes) {

    }

    @Override
    public void show_bookcar(ObjErrorApi objResCity) {

    }

    @Override
    public void show_get_bookcar_detail(ResponseBookCarDetail objError) {
        hideDialogLoading();
        if (objError.getERROR().equals("0000")) {
            ObjListBookCar obj = objError.getINFO();
            try {
                edt_ngaydon.setText(TimeUtils.convent_date(obj.getAppoint_date(),
                        "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm"));
                txt_madh.setText("Mã ĐH: "+obj.getId());
                txt_address_customer.setText(obj.getLocation());
                txt_detail.setText(obj.getNote());
                txt_name_customer.setText(obj.getCustomer_name());
                txt_phone_customer.setText(obj.getCustomer_phone());
                switch (obj.getState()){
                    case "1":
                        txt_state.setBackground(getResources().getDrawable(R.drawable.spr_btn_login));
                        txt_state.setText("Đã có lái xe tiếp nhận");
                        break;
                    case "6":
                        txt_state.setBackground(getResources().getDrawable(R.drawable.spr_btn_orange));
                        txt_state.setText("Đã hoàn thành chuyến đi");
                        break;
                    case "-1":
                        txt_state.setText("Khách hàng hủy đơn");
                        break;
                    case "5":
                        txt_state.setText("Không tìm được lái xe phù hợp");
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void show_list_book_car(ResponGetListBookCar objError) {

    }

    @Override
    public void show_list_book_car_pre(ResponGetListBookCar objError) {

    }

    @Override
    public void show_update_billing(ObjErrorApi objError) {

    }
}
