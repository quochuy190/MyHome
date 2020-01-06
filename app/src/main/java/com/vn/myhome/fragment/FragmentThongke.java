package com.vn.myhome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.activity.notifycation.ActivityListNotify;
import com.vn.myhome.activity.report.ActivityReportDetail;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ReportObj;
import com.vn.myhome.models.ResponseApi.ResponseGetListReportDetail;
import com.vn.myhome.presenter.InterfaceReport;
import com.vn.myhome.presenter.ReportPresenter;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.StringUtil;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 22-April-2019
 * Time: 10:30
 * Version: 1.0
 */
public class FragmentThongke extends BaseFragment implements InterfaceReport.View {
    private static final String TAG = "FragmentSetup";
    public static FragmentThongke fragment;
    ReportPresenter mPresenter;
    @BindView(R.id.img_date_start)
    ImageView img_date_start;
    @BindView(R.id.edt_date_start)
    EditText edt_date_start;
    @BindView(R.id.txt_doanhthutong)
    TextView txt_doanhthutong;
    @BindView(R.id.txt_tong_doanhthu)
    TextView txt_tong_doanhthu;
    @BindView(R.id.txt_tong_loinhuan)
    TextView txt_tong_loinhuan;
    @BindView(R.id.ll_thongke_tong)
    ConstraintLayout ll_thongke_tong;
    String sMonthYear;
    @BindView(R.id.btn_back)
    ImageView img_back;
    @BindView(R.id.txt_title)
    TextView txt_title;
    @BindView(R.id.txt_badger_notify)
    TextView txt_badger_notify;

    public static FragmentThongke getInstance() {
        if (fragment == null) {
            synchronized (FragmentThongke.class) {
                if (fragment == null)
                    fragment = new FragmentThongke();
            }
        }
        return fragment;
    }

    Calendar myCalendar = Calendar.getInstance();

    private void updateTodate() {
        txt_title.setText("THỐNG KÊ");
        String myFormat = "MM/yyyy"; //In which you need put here
        String myFormat_update = "MMddyyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf_update = new SimpleDateFormat(myFormat_update, Locale.US);
        String sDate = sdf.format(myCalendar.getTime());
        Log.d(TAG, "updateTodate: " + sDate);
        edt_date_start.setText(sdf.format(myCalendar.getTime()));
        sMonthYear = sdf_update.format(myCalendar.getTime());
     /*   sThang = "" + (myCalendar.get(Calendar.MONTH) + 1);
        sNam = "" + myCalendar.get(Calendar.YEAR);*/
        initData();
    }
    private void check_title_notify() {
        if (App.sTotalNotify.length() > 0) {
            if (!App.sTotalNotify.equals("0")) {
                txt_badger_notify.setText(App.sTotalNotify);
                txt_badger_notify.setVisibility(View.VISIBLE);
            } else
                txt_badger_notify.setVisibility(View.GONE);
        } else {
            txt_badger_notify.setVisibility(View.GONE);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongke, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new ReportPresenter(this);
        Log.e(TAG, "onCreateView: Setup");
        img_back.setVisibility(View.VISIBLE);
        img_back.setImageResource(R.drawable.ic_notify);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityListNotify.class);
                startActivity(intent);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        updateTodate();

        initEvent();
        check_title_notify();
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.RELOAD_NOTIFY)) {
            Log.e(TAG, "onMessageEvent: ");
            check_title_notify();
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    private void initEvent() {
        ll_thongke_tong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                mPresenter.api_get_report_detail(sUserName, "",
                        "", "01/09/2019", "30/11/2019");*/
                Intent intent = new Intent(getContext(), ActivityReportDetail.class);
                startActivity(intent);
            }
        });
        img_date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   new DatePickerDialog(ActivityHoadondientu.this,
                        R.style.MyDatePickerStyle, to_date,
                        myCalendar_to.get(Calendar.YEAR),
                        myCalendar_to.get(Calendar.MONTH),
                        myCalendar_to.get(Calendar.DAY_OF_MONTH))
                        .show();*/
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getContext(),
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                myCalendar.set(Calendar.YEAR, selectedYear);
                                myCalendar.set(Calendar.MONTH, selectedMonth);
                                Log.d(TAG, "selectedMonth : " + selectedMonth + " selectedYear : " + selectedYear);
                                updateTodate();

                            }
                        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH));

                builder.setActivatedMonth(myCalendar.get(Calendar.MONTH))
                        .setMinYear(1990)
                        .setActivatedYear(myCalendar.get(Calendar.YEAR))
                        .setMaxYear(2130)
                        .setMinMonth(Calendar.FEBRUARY)
                        .setTitle("Chọn tháng năm")
                        .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                        // .setMaxMonth(Calendar.OCTOBER)
                        // .setYearRange(1890, 1890)
                        // .setMonthAndYearRange(Calendar.FEBRUARY, Calendar.OCTOBER, 1890, 1890)
                        //.showMonthOnly()
                        // .showYearOnly()
                        .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                            @Override
                            public void onMonthChanged(int selectedMonth) {
                                Log.d(TAG, "Selected month : " + selectedMonth);
                                // Toast.makeText(MainActivity.this, " Selected month : " + selectedMonth, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                            @Override
                            public void onYearChanged(int selectedYear) {
                                Log.d(TAG, "Selected year : " + selectedYear);
                                // Toast.makeText(MainActivity.this, " Selected year : " + selectedYear, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build()
                        .show();
            }
        });
    }

    private void initData() {
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        if (edt_date_start.getText().toString().length() > 0) {
            showDialogLoading();
            mPresenter.api_get_report(sUserName, edt_date_start.getText().toString());
        } else {
            showAlertDialog("Thông báo", "Bạn chưa chọn ngày tháng");
        }

    }

    @Override
    public void show_error_api(String sService) {

    }

    @Override
    public void show_get_report(ReportObj objReport) {
        hideDialogLoading();
        if (objReport != null && objReport.getERROR().equals("0000")) {
            set_info_report(objReport);
        }
    }

    @Override
    public void show_get_report_detail(ResponseGetListReportDetail objReport) {

    }

    private void set_info_report(ReportObj objReport) {
        try {
            txt_doanhthutong.setText(StringUtil.formatNumber(objReport.getTOTAL_REVENUE()));
            txt_tong_doanhthu.setText(StringUtil.conventMonney_Long(objReport.getTOTAL_REVENUE()));
            txt_tong_loinhuan.setText(StringUtil.conventMonney_Long(objReport.getREVENUE()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
