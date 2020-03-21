package com.vn.myhome.activity.book_room;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.vn.myhome.R;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.presenter.InterfaceUpdateDiscountMyhome;
import com.vn.myhome.presenter.UpdateDiscountPresenter;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;


/**
 * Created by QQ on 10/16/2017.
 */

public class ActivityUpdateDiscountMyhome extends BaseActivity implements InterfaceUpdateDiscountMyhome.View {
    @BindView(R.id.seekbar_percent)
    SeekBar seekbar_percent;
    @BindView(R.id.txt_percent)
    TextView txt_percent;
    int percent=10;
    @BindView(R.id.edt_date_start)
    EditText edt_date_start;
    @BindView(R.id.edt_date_end)
    EditText edt_date_end;
    Calendar myCalendar_start = Calendar.getInstance();
    Calendar myCalendar_end = Calendar.getInstance();
    @BindView(R.id.ll_date_start)
    ConstraintLayout ll_date_start;
    @BindView(R.id.ll_date_end)
    ConstraintLayout ll_date_end;
    @BindView(R.id.btn_update_discount)
    Button btn_update;
    UpdateDiscountPresenter mPresenter;
    ObjHomeStay objMyhome;
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
    DatePickerDialog.OnDateSetListener end_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar_end.set(Calendar.YEAR, year);
            myCalendar_end.set(Calendar.MONTH, monthOfYear);
            myCalendar_end.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            update_end_date();
        }

    };

    private void update_start_date() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edt_date_start.setText(sdf.format(myCalendar_start.getTime()));
    }

    private void update_end_date() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edt_date_end.setText(sdf.format(myCalendar_end.getTime()));
    }

    String sDateStart = "", sDateEnd = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.fragment_service);
        mPresenter = new UpdateDiscountPresenter(this);
        initData();
        initAppbar();
        initSeekbar();
        initEvent();
    }

    private void initData() {
        objMyhome = (ObjHomeStay) getIntent().getSerializableExtra(Constants.KEY_SEND_MYHOME);
    }

    private void initEvent() {
        ll_date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ActivityUpdateDiscountMyhome.this, R.style.MyDatePickerStyle, start_date, myCalendar_start
                        .get(Calendar.YEAR), myCalendar_start.get(Calendar.MONTH),
                        myCalendar_start.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        ll_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ActivityUpdateDiscountMyhome.this, R.style.MyDatePickerStyle, end_date, myCalendar_end
                        .get(Calendar.YEAR), myCalendar_end.get(Calendar.MONTH),
                        myCalendar_end.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sDateStart = edt_date_start.getText().toString().trim();
                sDateEnd = edt_date_end.getText().toString().trim();
                if (sDateStart == null || sDateStart.length() == 0) {
                    showAlertDialog("Thông báo", "Bạn chưa nhập vào ngày bắt đầu");
                    return;
                }
                if (sDateEnd == null || sDateEnd.length() == 0) {
                    showAlertDialog("Thông báo", "Bạn chưa nhập vào ngày kết thúc");
                    return;
                }
                if (TimeUtils.compare_two_date(sDateStart, sDateEnd,
                        "dd/MM/yyyy", "dd/MM/yyyy")) {
                    showDialogLoading();
                    String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                    mPresenter.api_update_discount(sUserName, objMyhome.getGENLINK(),
                            percent + "", sDateStart, sDateEnd);
                } else showAlertDialog("Thông báo", "Thời gian nhập vào chưa hợp lệ");
            }
        });
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
        txt_title.setText("Cài đặt giảm giá");
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_discount_myhome;
    }


    private void initSeekbar() {
        seekbar_percent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txt_percent.setText(i + "%");
                percent = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void show_error_api(ObjErrorApi sService) {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_update_discount(ObjErrorApi sService) {
        hideDialogLoading();
        if (sService != null && sService.getERROR().equals("0000")) {
            Toast.makeText(ActivityUpdateDiscountMyhome.this,
                    "Cập nhật giảm giá thành công", Toast.LENGTH_LONG).show();
            setResult(RESULT_OK, new Intent());
            finish();
        }
    }
}
