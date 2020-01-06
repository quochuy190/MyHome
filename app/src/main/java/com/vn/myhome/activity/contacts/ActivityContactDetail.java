package com.vn.myhome.activity.contacts;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.activity.login.ActivityListCity;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ContactObj;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ResponseApi.ResponseGetContact;
import com.vn.myhome.presenter.ContactPresenter;
import com.vn.myhome.presenter.InterfaceContact;
import com.vn.myhome.untils.KeyboardUtil;
import com.vn.myhome.untils.SharedPrefs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 23-November-2019
 * Time: 11:06
 * Version: 1.0
 */
public class ActivityContactDetail extends BaseActivity implements InterfaceContact.View {
    @BindView(R.id.edt_fullname)
    EditText edt_fullname;
    @BindView(R.id.edt_phone)
    EditText edt_phone;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.edt_birthday)
    EditText edt_birthday;    @BindView(R.id.ll_birthday)
    ConstraintLayout ll_birthday;
    @BindView(R.id.edt_ctm)
    EditText edt_ctm;
    @BindView(R.id.edt_city)
    EditText edt_city;
    @BindView(R.id.img_get_city)
    ImageView img_get_city;
    @BindView(R.id.ll_city)
    ConstraintLayout ll_city;
    @BindView(R.id.edt_address)
    EditText edt_address;
    @BindView(R.id.btn_capnhat)
    Button btn_capnhat;
    @BindView(R.id.btn_delete)
    Button btn_delete;
    ContactObj mContact;
    ContactPresenter mPresenter;
    boolean isUpdate = false;
    String USERNAME = "";
    String NAME = "";
    String MOBILE = "";
    String EMAIL = "";
    String CMT = "";
    String DOB = "";
    String ADDRESS = "";
    String ID_PROVINCE = "";
    Calendar myCalendar_start = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener start_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar_start.set(Calendar.YEAR, year);
            myCalendar_start.set(Calendar.MONTH, monthOfYear);
            myCalendar_start.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            update_start_date();
        }

    };

    private void update_start_date() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edt_birthday.setText(sdf.format(myCalendar_start.getTime()));
    }


    @Override
    public int setContentViewId() {
        return R.layout.activity_info_contact_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ContactPresenter(this);
        initAppbar();
        initData();
        initEvent();
    }

    private void initEvent() {
        btn_capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_api_update(isUpdate);
            }
        });
        ll_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ActivityContactDetail.this, R.style.MyDatePickerStyle, start_date,
                        myCalendar_start.get(Calendar.YEAR), myCalendar_start.get(Calendar.MONTH),
                        myCalendar_start.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        edt_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_city = new Intent(ActivityContactDetail.this, ActivityListCity.class);
                startActivityForResult(intent_city, Constants.RequestCode.GET_CITY);
            }
        });
        ll_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_city = new Intent(ActivityContactDetail.this, ActivityListCity.class);
                startActivityForResult(intent_city, Constants.RequestCode.GET_CITY);
            }
        });
        img_get_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_city = new Intent(ActivityContactDetail.this, ActivityListCity.class);
                startActivityForResult(intent_city, Constants.RequestCode.GET_CITY);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogComfirm("Thông báo",
                        "Bạn có chắc chắn muốn xóa thông tin khách hàng này không?",
                        true,
                        new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                showDialogLoading();
                                mPresenter.api_del_contact(USERNAME, mContact.getID());
                            }

                            @Override
                            public void onClickNoDialog() {

                            }
                        });

            }
        });
    }

    private void get_api_update(boolean isUpdate) {
        if (edt_fullname.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào tên khách hàng");
            KeyboardUtil.requestKeyboard(edt_fullname);
            return;
        } else {
            NAME = edt_fullname.getText().toString().trim();
        }
        if (edt_phone.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào số điện thoại khách hàng");
            KeyboardUtil.requestKeyboard(edt_phone);
            return;
        } else {
            MOBILE = edt_phone.getText().toString().trim();
        }
        EMAIL = edt_email.getText().toString().trim();
        DOB = edt_birthday.getText().toString();
        CMT = edt_ctm.getText().toString().trim();
        ADDRESS = edt_address.getText().toString();
        showDialogLoading();
        if (isUpdate) {
            mPresenter.api_edit_contact(USERNAME, mContact.getID(), NAME, MOBILE, EMAIL,
                    CMT, DOB, ADDRESS, ID_PROVINCE);
        } else {
            mPresenter.api_add_contact(USERNAME, NAME, MOBILE, EMAIL, CMT, DOB,
                    ADDRESS, ID_PROVINCE);
        }

    }

    private void initData() {
        USERNAME = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mContact = (ContactObj) getIntent().getSerializableExtra(Constants.KEY_SEND_CONTACT_DETAIL);
        if (mContact != null) {
            isUpdate = true;
            set_info_contact();
            btn_delete.setVisibility(View.VISIBLE);
        } else {
            btn_capnhat.setText("Tạo mới");
        }
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
        txt_title.setText("Thông tin khách hàng");
    }

    private void set_info_contact() {
        try {
            edt_fullname.setText(mContact.getNAME());
            edt_phone.setText(mContact.getMOBILE());
            edt_email.setText(mContact.getEMAIL());
            edt_birthday.setText(mContact.getDOB());
            edt_ctm.setText(mContact.getCMT());
            edt_city.setText(mContact.getPROVINCE_NAME());
            edt_address.setText(mContact.getADDRESS());
            ID_PROVINCE = mContact.getID_PROVINCE();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show_error_api(String sService) {
        hideDialogLoading();
    }

    @Override
    public void show_get_contact(ResponseGetContact objContact) {

    }

    @Override
    public void show_add_contact(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            Toast.makeText(this, "Thêm thông tin khách hàng thành công", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, new Intent());
            finish();
        } else {
            showAlertDialog("Thông báo", objError.getRESULT());
        }
    }

    @Override
    public void show_edit_contact(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            Toast.makeText(this, "Cập nhật thông tin khách hàng thành công", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, new Intent());
            finish();
        } else {
            showAlertDialog("Thông báo", objError.getRESULT());
        }
    }

    @Override
    public void show_del_contact(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            Toast.makeText(this, "Xóa thông tin khách hàng thành công", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, new Intent());
            finish();
        } else {
            showAlertDialog("Thông báo", objError.getRESULT());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_CITY:
                if (resultCode == RESULT_OK) {
                    edt_city.setText(App.mCity.getNAME());
                    ID_PROVINCE = App.mCity.getMATP();
                }
                break;
        }
    }
}
