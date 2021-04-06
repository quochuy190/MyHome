package com.vn.myhome.fragment.services;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.activity.user_manager.Activity_Info_User;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ObjRoute;
import com.vn.myhome.models.ResponseApi.ResponseBookCarDetail;
import com.vn.myhome.models.ResponseApi.ResponsePriceEstimates;
import com.vn.myhome.models.ResponseApi.RouteResponse;
import com.vn.myhome.network.response.ResponGetListBookCar;
import com.vn.myhome.untils.PhoneUtil;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 19-April-2020
 * Time: 10:09
 * Version: 1.0
 */
public class FragmentDatXe extends BaseFragment implements View.OnClickListener, InterfaceServices.View {
    public static FragmentDatXe fragment;

    public static FragmentDatXe getInstance() {
        if (fragment == null) {
            synchronized (FragmentDatXe.class) {
                if (fragment == null)
                    fragment = new FragmentDatXe();
            }
        }
        return fragment;
    }

    @BindView(R.id.ll_get_route)
    ConstraintLayout ll_get_route;
    @BindView(R.id.ll_get_car)
    ConstraintLayout ll_get_car;
    @BindView(R.id.ll_date)
    ConstraintLayout ll_date;
    @BindView(R.id.ll_gio)
    ConstraintLayout ll_gio;
    @BindView(R.id.txt_get_route)
    TextView txt_get_route;
    @BindView(R.id.txt_get_car)
    TextView txt_get_car;
    @BindView(R.id.edt_cuoctamtinh)
    EditText edt_cuoctamtinh;
    @BindView(R.id.edt_ngaydon)
    EditText edt_ngaydon;
    @BindView(R.id.edt_gio)
    EditText edt_gio;
    @BindView(R.id.edt_cuoctinhthem)
    EditText edt_cuoctinhthem;
    @BindView(R.id.txt_name_customer)
    EditText edt_name_customer;
    @BindView(R.id.edt_phone_customer)
    EditText edt_phone_customer;
    @BindView(R.id.edt_diachi_don)
    EditText edt_diachi_don;
    @BindView(R.id.edt_diadiemden)
    EditText edt_diadiemden;
    @BindView(R.id.edt_note_customer)
    EditText edt_note_customer;
    @BindView(R.id.radio_thuchenh)
    RadioButton radio_thuchenh;
    @BindView(R.id.radio_thanhtoanchuyenkhoan)
    RadioButton radio_thanhtoanchuyenkhoan;
    @BindView(R.id.btn_book_car)
    Button btn_book_car;
    private ObjRoute mRoute;
    private ObjRoute mCar;
    private PresenterServices mPresenter;
    String USERNAME = "", BOOKER_TEL = "", BOOKER_NAME = "", SO_TK = "", TEN_TK = "", TEN_NH = "", TEN_CN = "", CAR_TYPE = "", ROUTE_TYPE = "",
            RADA_PRICE = "", TOTAL_PRICE = "", EXTRA_PRICE = "", CUSTOMER_TEL = "", CUSTOMER_NAME = "", PAYMENT_TYPE = "", ADDRESS_SOURCE = "",
            ADDRESS_DES = "", SCHEDULE = "", NOTES = "";
    public Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener start_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            update_date();
        }

    };

    private void update_date() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edt_ngaydon.setText(sdf.format(myCalendar.getTime()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_thuexe_oto, container, false);
        ButterKnife.bind(this, view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mPresenter = new PresenterServices(this);
        radio_thuchenh.setChecked(true);
        initEvent();
        return view;
    }

    private void initEvent() {
        ll_get_route.setOnClickListener(this);
        ll_get_car.setOnClickListener(this);
        ll_date.setOnClickListener(this);
        ll_gio.setOnClickListener(this);
        btn_book_car.setOnClickListener(this);
    }

    private void get_api_book_car() {
        try {
            ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
            USERNAME = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
            if (USERNAME.equals("chunha") || USERNAME.equals("toannn")
                    || USERNAME.equals("dichvu") || USERNAME.equals("qldondep")) {
                BOOKER_TEL = objLogin.getMOBILE();
            } else {
                BOOKER_TEL = USERNAME;
            }
            if (mRoute != null) {
                ROUTE_TYPE = mRoute.getId();
            } else {
                showAlertDialog("Thông báo", "Bạn chưa chọn tuyến xe muốn đi");
                return;
            }
            if (mCar != null) {
                CAR_TYPE = mCar.getId();
            } else {
                showAlertDialog("Thông báo", "Bạn chưa chọn loại xe muốn đi");
                return;
            }
            BOOKER_NAME = objLogin.getFULL_NAME();
            if (objLogin.getSO_TK() != null && objLogin.getTEN_TK() != null &&
                    objLogin.getTEN_NH() != null && objLogin.getTEN_CN() != null) {
                SO_TK = objLogin.getSO_TK();
                TEN_TK = objLogin.getTEN_TK();
                TEN_NH = objLogin.getTEN_NH();
                TEN_CN = objLogin.getTEN_CN();
            } else {
                showDialogComfirm("Thông báo",
                        "Mời bạn cập nhật thông tin tài khoản ngân hàng để nhận được tiền thành toán đặt xe", false,
                        new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                Intent intent = new Intent(getContext(), Activity_Info_User.class);
                                intent.putExtra(Constants.KEY_SEND_INFO_USERID, USERNAME);
                                intent.putExtra(Constants.KEY_SEND_INFO_USERID_TITLE, "HỒ SƠ");
                                //startActivity(intent);
                                startActivity(intent);
                                // startActivity(new Intent(getContext(), Activity_Info_User.class));
                            }

                            @Override
                            public void onClickNoDialog() {

                            }
                        });
                return;
            }
            RADA_PRICE = edt_cuoctamtinh.getText().toString();
            EXTRA_PRICE = edt_cuoctinhthem.getText().toString();
            if (RADA_PRICE.length() == 0)
                RADA_PRICE = "0";
            else
                RADA_PRICE = RADA_PRICE.replaceAll("VND", "").replaceAll("\\.", "").replaceAll(",", "");
            if (EXTRA_PRICE.length() == 0)
                EXTRA_PRICE = "0";
            else
                EXTRA_PRICE = EXTRA_PRICE.replaceAll("VND", "").replaceAll("\\.", "").replaceAll(",", "");
            int total_price = Integer.parseInt(RADA_PRICE.trim()) + Integer.parseInt(EXTRA_PRICE.trim());
            TOTAL_PRICE = total_price + "";
            if (edt_name_customer.getText().toString().length() > 0)
                CUSTOMER_NAME = edt_name_customer.getText().toString();
            else {
                showAlertDialog("Thông báo", "Bạn chưa nhập vào tên khách hàng");
                return;
            }
            if (edt_phone_customer.getText().toString().length() > 0)
                if (PhoneUtil.isPhoneNumberNew(edt_phone_customer.getText().toString())) {
                    CUSTOMER_TEL = edt_phone_customer.getText().toString();
                } else {
                    showAlertDialog("Thông báo", "Số điện thoại không đúng định dạng");
                    return;
                }
            else {
                showAlertDialog("Thông báo", "Bạn chưa nhập vào số điện thoại khách");
                return;
            }
            if (edt_diachi_don.getText().toString().length() > 0)
                ADDRESS_SOURCE = edt_diachi_don.getText().toString();
            else {
                showAlertDialog("Thông báo", "Bạn chưa nhập vào địa chỉ đón khách");
                return;
            }
            if (edt_diadiemden.getText().toString().length() > 0)
                ADDRESS_DES = edt_diadiemden.getText().toString();
            else {
                showAlertDialog("Thông báo", "Bạn chưa nhập vào địa chỉ trả khách");
                return;
            }
            if (edt_note_customer.getText().toString().length() > 0)
                NOTES = edt_note_customer.getText().toString();
            if (radio_thuchenh.isChecked()) {
                PAYMENT_TYPE = "0";
            } else
                PAYMENT_TYPE = "2";
            if (edt_ngaydon.getText().toString().length() > 0
                    && edt_gio.getText().toString().length() > 0) {
                SCHEDULE = edt_ngaydon.getText().toString() + " " + edt_gio.getText().toString();
            } else {
                showAlertDialog("Thông báo", "Bạn chưa ngày giờ đón khách");
                return;
            }
            showDialogLoading();
            mPresenter.api_bookcar(USERNAME, BOOKER_TEL, BOOKER_NAME, SO_TK, TEN_TK, TEN_NH, TEN_CN, CAR_TYPE, ROUTE_TYPE,
                    RADA_PRICE, TOTAL_PRICE, EXTRA_PRICE, CUSTOMER_TEL, CUSTOMER_NAME, PAYMENT_TYPE, ADDRESS_SOURCE, ADDRESS_DES,
                    SCHEDULE, NOTES);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_book_car:
                get_api_book_car();
                break;
            case R.id.ll_get_route:
                startIntentforResult(Constants.RequestCode.GET_LIST_ROUTE);
                break;
            case R.id.ll_get_car:
                if (mRoute != null)
                    startIntentforResult(Constants.RequestCode.GET_LIST_CAR);
                else
                    showAlertDialog("Thông báo", "Bạn chưa chọn hành trình đi");
                break;
            case R.id.ll_date:
                new DatePickerDialog(getContext(), R.style.MyDatePickerStyle, start_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.ll_gio:
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(),TimePickerDialog.THEME_HOLO_LIGHT,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String hour = "";
                        String minute = "";
                        if (selectedHour < 10) {
                            hour = "0" + selectedHour;
                        } else
                            hour = "" + selectedHour;
                        if (selectedMinute < 10) {
                            minute = "0" + selectedMinute;
                        } else
                            minute = "" + selectedMinute;
                        edt_gio.setText(hour + ":" + minute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
        }
    }

    private void startIntentforResult(int REQUEST_CODE) {
        Intent intent;
        switch (REQUEST_CODE) {
            case Constants.RequestCode.GET_LIST_ROUTE:
                intent = new Intent(getContext(), ActivityListHanhtrinhxe.class);
                break;
            case Constants.RequestCode.GET_LIST_CAR:
                intent = new Intent(getContext(), ActivityListCar.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + REQUEST_CODE);
        }
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_LIST_ROUTE:
                if (resultCode == RESULT_OK) {
                    txt_get_route.setText(App.mRoute.getName());
                    mRoute = App.mRoute;
                }
                break;
            case Constants.RequestCode.GET_LIST_CAR:
                if (resultCode == RESULT_OK) {
                    txt_get_car.setText(App.mCar.getName());
                    mCar = App.mCar;
                    api_get_giauoctinh();
                }
                break;
        }
    }

    private void api_get_giauoctinh() {
        showDialogLoading();
        mPresenter.api_get_price_estimates(
                SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class),
                mCar.getId(), mRoute.getId());
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
    public void show_get_price_estimates(ResponsePriceEstimates objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            if (objError.getINFO() != null) {
                edt_cuoctamtinh.setText(StringUtil.conventMonney_Long(objError.getINFO().getPrice()));
            }
        } else showAlertDialog("Thông báo", objError.getRESULT());
    }

    @Override
    public void show_bookcar(ObjErrorApi objResCity) {
        hideDialogLoading();
        if (objResCity.getERROR().equals("0000")) {
            if (PAYMENT_TYPE.equals("2")) {
                Toast.makeText(getContext(), "Đặt xe thành công", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            } else{
                if (objResCity.getINFO() != null) {
                    Toast.makeText(getContext(), "Đặt xe thành công", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                } else
                    Toast.makeText(getContext(), "Đặt xe thành công không thành công", Toast.LENGTH_SHORT).show();
            }
        } else {
            showAlertDialog("Thông báo", objResCity.getRESULT());
        }
    }

    @Override
    public void show_get_bookcar_detail(ResponseBookCarDetail objError) {

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
