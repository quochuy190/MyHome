package com.vn.myhome.activity.report;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.adapter.AdapterReportDetail;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjReportDetail;
import com.vn.myhome.models.ReportObj;
import com.vn.myhome.models.ResponseApi.ResponseGetListReportDetail;
import com.vn.myhome.models.TitleReportDetail;
import com.vn.myhome.presenter.InterfaceReport;
import com.vn.myhome.presenter.ReportPresenter;
import com.vn.myhome.untils.SharedPrefs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 19-November-2019
 * Time: 15:30
 * Version: 1.0
 */
public class ActivityReportDetail extends BaseActivity implements InterfaceReport.View {
    ReportPresenter mPresenter;
    private List<TitleReportDetail> mListtitle;
    private AdapterReportDetail adapterService;
    @BindView(R.id.rcv_report_detail)
    RecyclerView recycle_service;
    @BindView(R.id.img_date_start)
    ImageView img_date_start;
    @BindView(R.id.img_date_end)
    ImageView img_date_end;
    @BindView(R.id.txt_date_start)
    TextView txt_date_start;
    @BindView(R.id.txt_date_end)
    TextView txt_date_end;
    RecyclerView.LayoutManager mLayoutManager;
    Calendar myCalendar_to = Calendar.getInstance();
    Calendar myCalendar_from = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener to_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar_to.set(Calendar.YEAR, year);
            myCalendar_to.set(Calendar.MONTH, monthOfYear);
            myCalendar_to.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTodate();
        }

    };

    DatePickerDialog.OnDateSetListener from_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar_from.set(Calendar.YEAR, year);
            myCalendar_from.set(Calendar.MONTH, monthOfYear);
            myCalendar_from.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateFromdate();
        }

    };
    String sDateStart = "", sDateEnd = "";

    private void updateFromdate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        String myFormat_update = "MMddyyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf_update = new SimpleDateFormat(myFormat_update, Locale.US);
        txt_date_start.setText(sdf.format(myCalendar_from.getTime()));
        sDateStart = sdf.format(myCalendar_from.getTime());

    }

    private void updateTodate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        String myFormat_update = "MMddyyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf_update = new SimpleDateFormat(myFormat_update, Locale.US);
        txt_date_end.setText(sdf.format(myCalendar_to.getTime()));
        sDateEnd = sdf.format(myCalendar_to.getTime());
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_report_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ReportPresenter(this);
        init();
        get_all_history();
        initData();
        initEvent();
    }

    public void get_all_history() {
        int ndays = myCalendar_to.getActualMaximum(Calendar.DAY_OF_MONTH);
        int day_current_from = myCalendar_to.get(Calendar.DAY_OF_MONTH);
        myCalendar_to.add(Calendar.DAY_OF_MONTH, +(ndays - day_current_from));
        updateTodate();
        int dayOfMonth = myCalendar_from.get(Calendar.DAY_OF_MONTH);

        myCalendar_from.add(Calendar.DAY_OF_MONTH, -(dayOfMonth - 1));
        updateFromdate();
    }

    private void initEvent() {
        img_date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ActivityReportDetail.this,
                        R.style.MyDatePickerStyle, to_date,
                        myCalendar_from.get(Calendar.YEAR),
                        myCalendar_from.get(Calendar.MONTH),
                        myCalendar_from.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
        img_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ActivityReportDetail.this,
                        R.style.MyDatePickerStyle, from_date,
                        myCalendar_to.get(Calendar.YEAR),
                        myCalendar_to.get(Calendar.MONTH),
                        myCalendar_to.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
    }

    private void init() {
        mListtitle = new ArrayList<>();
        adapterService = new AdapterReportDetail(this, mListtitle, new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {

            }
        });
        mLayoutManager = new GridLayoutManager(this, 1);
        recycle_service.setNestedScrollingEnabled(false);
        recycle_service.setHasFixedSize(true);
        recycle_service.setLayoutManager(mLayoutManager);
        recycle_service.setItemAnimator(new DefaultItemAnimator());
        recycle_service.setAdapter(adapterService);

    }

    private void initData() {
        showDialogLoading();
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenter.api_get_report_detail(sUserName,
                "", "", sDateStart, sDateEnd);
    }

    @Override
    public void show_error_api(String sService) {

    }

    @Override
    public void show_get_report(ReportObj objReport) {

    }

    @Override
    public void show_get_report_detail(ResponseGetListReportDetail objReport) {
        hideDialogLoading();
        List<TitleReportDetail> mList = new ArrayList<>();
        String sGetlink = "";
        if (objReport != null && objReport.getERROR().equals("0000")) {
            if (objReport.getINFO() != null) {
                for (ObjReportDetail obj : objReport.getINFO()) {
                    if (!obj.getGENLINK().equals(sGetlink)) {
                        sGetlink = obj.getGENLINK();
                        mList.add(new TitleReportDetail(obj.getROOM_NAME(), obj.getGENLINK()));
                    }
                }
                if (mList.size() > 0) {
                    for (TitleReportDetail objTitle : mList) {
                        List<ObjReportDetail> mLisRe = new ArrayList<>();
                        for (ObjReportDetail objRes : objReport.getINFO()) {
                            if (objRes.getGENLINK().equals(objTitle.getsGetlink())) {
                                mLisRe.add(objRes);
                            }
                        }
                        objTitle.setmList(mLisRe);
                    }
                }
            }
        }
        mListtitle.addAll(mList);
        adapterService.notifyDataSetChanged();
    }
}
