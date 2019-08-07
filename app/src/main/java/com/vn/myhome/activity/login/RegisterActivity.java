package com.vn.myhome.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ObjTypeUser;
import com.vn.myhome.models.ResponseApi.CityResponse;
import com.vn.myhome.models.ResponseApi.GetTypeResponse;
import com.vn.myhome.untils.KeyboardUtil;
import com.vn.myhome.untils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 05-August-2019
 * Time: 10:42
 * Version: 1.0
 */
public class RegisterActivity extends BaseActivity implements InterfaceLogin.View, View.OnClickListener {
    @BindView(R.id.ll_city)
    ConstraintLayout ll_city;
    @BindView(R.id.edt_city)
    EditText edt_city;
    @BindView(R.id.spinner_)
    Spinner spinner_;
    @BindView(R.id.icon_get_city)
    ImageView icon_get_city;
    @BindView(R.id.edt_fullname)
    EditText edt_fullname;
    @BindView(R.id.edt_username)
    EditText edt_username;
    @BindView(R.id.edt_phone)
    EditText edt_phone;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.edt_address)
    EditText edt_address;
    @BindView(R.id.edt_pass)
    EditText edt_pass;
    @BindView(R.id.edt_pass_comfirm)
    EditText edt_pass_comfirm;
    @BindView(R.id.btn_reg_user)
    Button btn_reg_user;
    List<String> dataset = new ArrayList<>();
    PresenterLogin mPresenter;
    private String sUserType = "", sUsername = "", sPassWord = "", sEmail = "",
            sFulName = "", sMobile = "", sAddress = "", sDes = "", sProvince = "", sSTK = "",
            sTenTK = "", sTenNN = "", sTenCN = "";

    @Override
    public int setContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initEvent();
    }

    private void initEvent() {
        ll_city.setOnClickListener(this);
        icon_get_city.setOnClickListener(this);
        edt_city.setOnClickListener(this);
        btn_reg_user.setOnClickListener(this);
    }

    private void initData() {
        mPresenter = new PresenterLogin(this);
        showDialogLoading();
        initGetType();
    }

    private void initGetType() {
        mPresenter.api_get_type("");
    }

    private void set_data_spinner() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_spinner, dataset);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_.setAdapter(adapter);
        spinner_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sUserType = mLisType.get(position).getUSER_TYPE();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void get_api_reg() {
        if (sUserType.length() == 0) {
            showDialogNotify("Thông báo", "Bạn chưa loại tài khoản để đăng ký.");
            return;
        }
        if (edt_username.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào tên đăng nhập.");
            KeyboardUtil.requestKeyboard(edt_username);
            return;
        } else {
            sUsername = edt_username.getText().toString().trim();
        }
        if (edt_fullname.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào họ và tên.");
            KeyboardUtil.requestKeyboard(edt_fullname);
            return;
        } else {
            sFulName = edt_fullname.getText().toString().trim();
        }
        if (edt_phone.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào số điện thoại.");
            KeyboardUtil.requestKeyboard(edt_phone);
            return;
        } else {
            sMobile = edt_phone.getText().toString().trim();
        }
        if (edt_email.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào email của bạn.");
            KeyboardUtil.requestKeyboard(edt_email);
            return;
        } else {
            sEmail = edt_email.getText().toString().trim();
        }
        if (sProvince.length() == 0) {
            showDialogNotify("Thông báo", "Bạn chưa chọn tỉnh thành phố.");
            return;
        }

        if (edt_address.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào địa chỉ của bạn.");
            KeyboardUtil.requestKeyboard(edt_address);
            return;
        } else {
            sAddress = edt_address.getText().toString().trim();
        }
        if (edt_pass.getText().toString().length() < 6) {
            showDialogNotify("Thông báo", "Mật khẩu phải dài hơn 6 ký tự.");
            KeyboardUtil.requestKeyboard(edt_pass);
            return;
        } else {
            sPassWord = edt_pass.getText().toString().trim();
        }
        if (edt_pass_comfirm.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mật khẩu phải dài hơn 6 ký tự.");
            KeyboardUtil.requestKeyboard(edt_pass_comfirm);
            return;
        }
        if (!edt_pass.getText().toString().equals(edt_pass_comfirm.getText().toString())) {
            showDialogNotify("Thông báo", "Xác nhận mật khẩu không chính xác.");
            return;
        }
        showDialogLoading();
        mPresenter.api_reg_user("user/reg_user", sUsername, sPassWord, sEmail, sFulName,
                sUserType, sMobile, sAddress, "", sProvince, "", "", "", "");
    }

    @Override
    public void show_error_api(String sService) {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_login(ObjLogin objLogin) {
        hideDialogLoading();
    }

    @Override
    public void show_reg_user(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_USERNAME, sUsername);
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_PASSWORD, sPassWord);
            setResult(RESULT_OK, new Intent());
            finish();
        } else {
            if (objError != null && objError.getRESULT() != null) {
                showDialogNotify("Thông báo", objError.getRESULT());
            } else
                showAlertErrorNetwork();
        }
    }

    List<ObjTypeUser> mLisType = new ArrayList<>();

    @Override
    public void show_get_type(GetTypeResponse objRes) {
        hideDialogLoading();
        mLisType.clear();
        if (objRes != null && objRes.getERROR() != null && objRes.getERROR().equals("0000")) {
            mLisType.addAll(objRes.getINFO());
            for (ObjTypeUser obj : mLisType) {
                if (obj.getTYPE_NAME() != null) {
                    dataset.add(obj.getTYPE_NAME());
                } else {
                    dataset.add("");
                }
            }

        }
        set_data_spinner();
    }

    @Override
    public void show_get_city(CityResponse objResCity) {

    }

    Intent intent_city = null;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_city:
                intent_city = new Intent(RegisterActivity.this, ActivityListCity.class);
                startActivityForResult(intent_city, Constants.RequestCode.GET_CITY);
                break;
            case R.id.icon_get_city:
                intent_city = new Intent(RegisterActivity.this, ActivityListCity.class);
                startActivityForResult(intent_city, Constants.RequestCode.GET_CITY);
                break;
            case R.id.edt_city:
                intent_city = new Intent(RegisterActivity.this, ActivityListCity.class);
                startActivityForResult(intent_city, Constants.RequestCode.GET_CITY);
                break;
            case R.id.btn_reg_user:
                get_api_reg();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_CITY:
                if (resultCode == RESULT_OK) {
                    edt_city.setText(App.mCity.getNAME());
                    sProvince = App.mCity.getMATP();
                }
                break;
        }
    }
}
