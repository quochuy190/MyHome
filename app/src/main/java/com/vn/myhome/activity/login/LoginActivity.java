package com.vn.myhome.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.vn.myhome.App;
import com.vn.myhome.MainActivity;
import com.vn.myhome.R;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ResponseApi.CityResponse;
import com.vn.myhome.models.ResponseApi.GetTypeResponse;
import com.vn.myhome.untils.KeyboardUtil;
import com.vn.myhome.untils.SharedPrefs;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 05-August-2019
 * Time: 10:41
 * Version: 1.0
 */
public class LoginActivity extends BaseActivity implements
        View.OnClickListener, InterfaceLogin.View {
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.txt_register)
    TextView txt_register;
    @BindView(R.id.txt_remember_pass)
    TextView txt_remember_pass;
    @BindView(R.id.edt_username)
    EditText edt_username;
    @BindView(R.id.edt_pass)
    EditText edt_pass;
    @BindView(R.id.icon_show_pass)
    ImageView icon_show_pass;
    String sUserName, sPass;
    PresenterLogin mPresenter;

    @Override
    public int setContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterLogin(this);
        initData();
        initEvent();

    }

    private void initData() {
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        sPass = SharedPrefs.getInstance().get(Constants.KEY_SAVE_PASSWORD, String.class);
        if (sUserName!=null&&sUserName.length()>0)
            edt_username.setText(sUserName);
        if (sPass!=null&&sPass.length()>0)
            edt_pass.setText(sPass);
    }

    private void initEvent() {
        txt_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        icon_show_pass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_register:
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class),
                        Constants.RequestCode.START_REGISTER);
                //    finish();
                break;
            case R.id.txt_remember_pass:

                break;
            case R.id.btn_login:
                if (edt_username.getText().toString().length() == 0) {
                    showDialogNotify("Thông báo", "Mời bạn nhập vào tên đăng nhập.");
                    KeyboardUtil.requestKeyboard(edt_username);
                    return;
                } else {
                    sUserName = edt_username.getText().toString().trim();
                }
                if (edt_pass.getText().toString().length() == 0) {
                    showDialogNotify("Thông báo", "Mời bạn nhập vào mật khẩu.");
                    KeyboardUtil.requestKeyboard(edt_pass);
                    return;
                } else {
                    sPass = edt_pass.getText().toString().trim();
                }
                init_login();
                break;
            case R.id.icon_show_pass:
                show_pass();
                break;
        }
    }

    boolean isShowpass = true;

    private void show_pass() {
        if (!isShowpass) {
            icon_show_pass.setImageDrawable(getResources().getDrawable(R.drawable.icon_show_unpass));
            //Glide.with(ActivityLogin.this).load(R.drawable.ic_eye_hide).into(img_showpass);
            edt_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isShowpass = !isShowpass;
        } else {
            icon_show_pass.setImageDrawable(getResources().getDrawable(R.drawable.icon_show_pass));
            edt_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isShowpass = !isShowpass;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestCode.START_REGISTER) {
            if (resultCode == RESULT_OK) {
                sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                sPass = SharedPrefs.getInstance().get(Constants.KEY_SAVE_PASSWORD, String.class);
                init_login();
            }
        }
    }

    private void init_login() {
        if (sUserName != null && sUserName.length() > 0 && sPass != null && sPass.length() > 0) {
            showDialogLoading();
            mPresenter.api_login("user/login", sUserName, sPass);
        }
    }

    @Override
    public void show_error_api(String sService) {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_login(ObjLogin objLogin) {
        hideDialogLoading();
        if (objLogin != null && objLogin.getERROR().equals("0000")) {
            String sTokenKey = SharedPrefs.getInstance().get(Constants.KEY_TOKEN, String.class);
            if (sTokenKey == null)
                sTokenKey = "";
            String UUID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            mPresenter.api_update_device(sUserName, App.versionName,
                    android.os.Build.BRAND + " " + android.os.Build.MODEL, sTokenKey,
                    android.os.Build.VERSION.RELEASE, "2", UUID);
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_OBJECT_LOGIN, objLogin);
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_IS_LOGIN, true);
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_USERNAME, sUserName);
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_PASSWORD, sPass);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra(Constants.KEY_SEND_LOGIN_TO_MAIN, true);
            startActivity(intent);
            finish();
        } else showDialogNotify("Thông báo", objLogin.getRESULT());
    }

    @Override
    public void show_reg_user(ObjErrorApi objError) {

    }

    @Override
    public void show_get_type(GetTypeResponse objRes) {

    }

    @Override
    public void show_get_city(CityResponse objResCity) {

    }

    @Override
    public void show_update_device(ObjErrorApi objError) {

    }
}
