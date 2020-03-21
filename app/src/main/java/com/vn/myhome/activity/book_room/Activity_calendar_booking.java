package com.vn.myhome.activity.book_room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.adapter.AdapterListCustomCalender;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjBooking;
import com.vn.myhome.models.ObjCalendar;
import com.vn.myhome.models.ObjDayCustom;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ResponseApi.ListBookingResponse;
import com.vn.myhome.models.ResponseApi.ResponseListBookingService;
import com.vn.myhome.presenter.BookingPresenter;
import com.vn.myhome.presenter.InterfaceBooking;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 07-October-2019
 * Time: 10:16
 * Version: 1.0
 */
public class Activity_calendar_booking extends BaseActivity implements InterfaceBooking.View {
    private static final String TAG = "activity_calendar_booki";
    int iMonth, iYear;
    List<ObjCalendar> mLisCalendar;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterListCustomCalender adapter;
    @BindView(R.id.rcv_list_calendar_custom)
    RecyclerView rcv_list_calendar_custom;
    @BindView(R.id.txt_start_booking)
    TextView txt_start_booking;
    @BindView(R.id.txt_end_booking)
    TextView txt_end_booking;
    @BindView(R.id.img_home)
    ImageView img_home;
    @BindView(R.id.btn_booking)
    Button btn_booking;
    String sLanguage;
    ObjHomeStay objHomeStay;
    BookingPresenter mPresenterBooking;
    String sUserName;
    List<ObjBooking> mListBooking;
    int MAX_MONTH = 5;

    @Override
    public int setContentViewId() {
        return R.layout.activity_list_calendar_custom;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAppbar();
        mLisCalendar = new ArrayList<>();
        mListBooking = new ArrayList<>();
        mPresenterBooking = new BookingPresenter(this);
        initData();
        sLanguage = Locale.getDefault().getDisplayLanguage();
        Log.e(TAG, "onCreate: " + sLanguage);
        initCalender();
        init_get_list_calendar();
        initEvent();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initEvent() {
        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sStartBookingDay.length() == 0) {
                    showDialogNotify("Thông báo", "Bạn chưa chọn ngày đến");
                    return;
                }
                if (sEndBookingDay.length() == 0) {
                    showDialogNotify("Thông báo", "Bạn chưa chọn ngày đi");
                    return;
                }
                TimeUtils.get_list_date(sStartBookingDay, sEndBookingDay);
                Intent intent = new Intent(Activity_calendar_booking.this, ActivityBooking.class);
                intent.putExtra(Constants.KEY_SEND_ROOM_BOOKING, objHomeStay);
                intent.putExtra(Constants.KEY_SEND_STARTDAY_BOOKING, sStartBookingDay);
                intent.putExtra(Constants.KEY_SEND_ENDDAY_BOOKING, sEndBookingDay);
                startActivityForResult(intent, Constants.RequestCode.GET_BOOKING);
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < MAX_MONTH; i++) {
                    for (int j = 0; j < mLisCalendar.get(i).getmLisday().size(); j++) {
                        ObjDayCustom objDay = mLisCalendar.get(i).getmLisday().get(j);
                        if (objDay != null) {
                            objDay.setsStartBooking("");
                            objDay.setsEndBooking("");
                        }
                    }
                }
                sStartBookingDay = "";
                sEndBookingDay = "";
                set_text_title();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initAppbar() {
        ImageView img_back = findViewById(R.id.btn_back);
        TextView txt_title = findViewById(R.id.txt_title);
        img_home.setImageResource(R.drawable.ic_delete);
        img_home.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_title.setText("DANH SÁCH NHÀ");
    }

    private void initData() {
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        objHomeStay = (ObjHomeStay) getIntent().getSerializableExtra(Constants.KEY_SEND_ROOM_BOOKING);

    }

    private void init_get_list_calendar() {
        mLisCalendar.clear();
        Calendar c = Calendar.getInstance();
        iYear = c.get(Calendar.YEAR);
        iMonth = c.get(Calendar.MONTH) + 1;
        for (int i = 0; i < MAX_MONTH; i++) {
            mLisCalendar.add(new ObjCalendar("" + iMonth, "" + iYear,
                    getWeeksOfMonth(iMonth, iYear)));
            iMonth++;
            if (iMonth > 12) {
                iMonth = 1;
                iYear++;
            }
        }
        Log.e(TAG, "onCreate: " + mLisCalendar);
        for (int j = 0; j < mLisCalendar.size(); j++) {
            ObjCalendar objCal = mLisCalendar.get(j);
            String sWeekDay = objCal.getmLisday().get(0).getsWeekDay();
            switch (sWeekDay) {
                case "monday":
                    mLisCalendar.get(j).getmLisday().add(0, null);
                    break;
                case "tuesday":
                    for (int i = 0; i < 2; i++) {
                        mLisCalendar.get(j).getmLisday().add(0, null);
                    }
                    break;
                case "wednesday":
                    for (int i = 0; i < 3; i++) {
                        mLisCalendar.get(j).getmLisday().add(0, null);
                    }
                    break;
                case "thursday":
                    for (int i = 0; i < 4; i++) {
                        mLisCalendar.get(j).getmLisday().add(0, null);
                    }
                    break;
                case "friday":
                    for (int i = 0; i < 5; i++) {
                        mLisCalendar.get(j).getmLisday().add(0, null);
                    }
                    break;
                case "saturday":
                    for (int i = 0; i < 6; i++) {
                        mLisCalendar.get(j).getmLisday().add(0, null);
                    }
                    break;
            }
        }
        adapter.notifyDataSetChanged();
        get_api();
    }

    private void get_api() {
        showDialogLoading();
        mPresenterBooking.api_get_list_day(sUserName, objHomeStay.getGENLINK());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_BOOKING:
                if (resultCode == RESULT_OK) {
                    init_get_list_calendar();
                }
                break;
        }
    }

    String sStartBookingDay = "";
    String sEndBookingDay = "";

    private void initCalender() {
        adapter = new AdapterListCustomCalender(this, mLisCalendar,
                new ItemClickListener() {
                    @Override
                    public void onClickItem(int position, Object item) {
                        if (item != null) {
                            ObjDayCustom obj = (ObjDayCustom) item;
                            if (!obj.isBooked()) {
                                if (sStartBookingDay.length() > 0) {
                                    if (sEndBookingDay.length() > 0) {
                                        sStartBookingDay = obj.getsDay();
                                        sEndBookingDay = "";
                                        for (int i = 0; i < MAX_MONTH; i++) {
                                            for (int j = 0; j < mLisCalendar.get(i).getmLisday().size(); j++) {
                                                ObjDayCustom objDay = mLisCalendar.get(i).getmLisday().get(j);
                                                if (objDay != null) {
                                                    objDay.setsStartBooking(obj.getsDay());
                                                    objDay.setsEndBooking("");
                                                }
                                            }
                                        }
                                    } else {
                                        if (TimeUtils.CompareDates(sStartBookingDay, obj.getsDay()).equals("3")) {
                                            sEndBookingDay = obj.getsDay();
                                            for (int i = 0; i < MAX_MONTH; i++) {
                                                for (int j = 0; j < mLisCalendar.get(i).getmLisday().size(); j++) {
                                                    ObjDayCustom objDay = mLisCalendar.get(i).getmLisday().get(j);
                                                    if (objDay != null)
                                                        objDay.setsEndBooking(obj.getsDay());
                                                }
                                            }
                                        } else if (TimeUtils.CompareDates(sStartBookingDay, obj.getsDay()).equals("1")) {
                                            sEndBookingDay = "";
                                            sStartBookingDay = "";
                                            for (int i = 0; i < MAX_MONTH; i++) {
                                                for (int j = 0; j < mLisCalendar.get(i).getmLisday().size(); j++) {
                                                    ObjDayCustom objDay = mLisCalendar.get(i).getmLisday().get(j);
                                                    if (objDay != null) {
                                                        objDay.setsStartBooking("");
                                                        objDay.setsEndBooking("");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    sStartBookingDay = obj.getsDay();
                                    for (int i = 0; i < MAX_MONTH; i++) {
                                        for (int j = 0; j < mLisCalendar.get(i).getmLisday().size(); j++) {
                                            ObjDayCustom objDay = mLisCalendar.get(i).getmLisday().get(j);
                                            if (objDay != null)
                                                objDay.setsStartBooking(obj.getsDay());
                                        }
                                    }
                                }
                            }
                            set_text_title();
                            adapter.notifyDataSetChanged();
                        }
                    }
                }, false);
        mLayoutManager = new GridLayoutManager(this, 1);
        // rcv_list_calendar_custom.setHasFixedSize(true);
        rcv_list_calendar_custom.setLayoutManager(mLayoutManager);
        rcv_list_calendar_custom.setItemAnimator(new DefaultItemAnimator());

        rcv_list_calendar_custom.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {

            }
        });
    }

    private void set_text_title() {
        if (sEndBookingDay.length() > 0)
            txt_end_booking.setText(sEndBookingDay);
        else
            txt_end_booking.setText("Ngày đi");
        if (sStartBookingDay.length() > 0)
            txt_start_booking.setText(sStartBookingDay);
        else
            txt_start_booking.setText("Ngày đến");
    }

    public List<ObjDayCustom> getWeeksOfMonth(int month, int year) {
        List mListDay = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MMM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int ndays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(ndays + "<<<ff");
        for (int i = 1; i <= ndays; i++) {
            String day = sdf.format(cal.getTime());
            int iday = cal.get(Calendar.DAY_OF_MONTH);
            String sWeekDay = TimeUtils.get_string_week_day(day);
            if (sWeekDay.equals("friday")) {  //or sunday
                // System.out.println("WEEKEND PRICE");
                mListDay.add(new ObjDayCustom(day, iday, true, objHomeStay.getPRICE(),
                        objHomeStay.getPRICE_SPECIAL(), sWeekDay, objHomeStay.getDISCOUNT(),objHomeStay.getPERCENT(),
                        TimeUtils.convent_date(objHomeStay.getPROMO_ST_TIME(),
                                "yyyy-MM-dd'T'HH:mm:ss.'000Z'",
                                "EEEE dd-MMM-yyyy")
                        ,  TimeUtils.convent_date(objHomeStay.getPROMO_ED_TIME(),
                        "yyyy-MM-dd'T'HH:mm:ss.'000Z'",
                        "EEEE dd-MMM-yyyy")));
            } else if (sWeekDay.equals("saturday")) {     //or sunday
                // System.out.println("WEEKEND PRICE");
                mListDay.add(new ObjDayCustom(day, iday, true, objHomeStay.getPRICE(),
                        objHomeStay.getPRICE_SPECIAL(), sWeekDay, objHomeStay.getDISCOUNT(),objHomeStay.getPERCENT(),
                        TimeUtils.convent_date(objHomeStay.getPROMO_ST_TIME(),
                                "yyyy-MM-dd'T'HH:mm:ss.'000Z'",
                                "EEEE dd-MMM-yyyy")
                        ,  TimeUtils.convent_date(objHomeStay.getPROMO_ED_TIME(),
                        "yyyy-MM-dd'T'HH:mm:ss.'000Z'",
                        "EEEE dd-MMM-yyyy")));
            } else {
                // System.out.println("WEEKDAY");
                mListDay.add(new ObjDayCustom(day, iday, false, objHomeStay.getPRICE(),
                        objHomeStay.getPRICE_SPECIAL(), sWeekDay,objHomeStay.getDISCOUNT(),objHomeStay.getPERCENT(),
                        TimeUtils.convent_date(objHomeStay.getPROMO_ST_TIME(),
                                "yyyy-MM-dd'T'HH:mm:ss.'000Z'",
                                "EEEE dd-MMM-yyyy")
                        ,  TimeUtils.convent_date(objHomeStay.getPROMO_ED_TIME(),
                        "yyyy-MM-dd'T'HH:mm:ss.'000Z'",
                        "EEEE dd-MMM-yyyy")));
            }

            //mListDay.add(new ObjDayCustom(day, iday));
            cal.add(Calendar.DATE, 1);
        }
        return mListDay;
    }

    @Override
    public void show_error_api(String sService) {
        hideDialogLoading();
    }

    @Override
    public void show_lock_room(ObjErrorApi objError) {

    }

    @Override
    public void show_list_bookroom(ListBookingResponse objRes) {
        hideDialogLoading();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            if (objRes.getINFO() != null && objRes.getINFO().size() > 0) {
                mListBooking.addAll(objRes.getINFO());
                for (int i = 0; i < mLisCalendar.size(); i++) {
                    ObjCalendar objCal = mLisCalendar.get(i);
                    for (int j = 0; j < objCal.getmLisday().size(); j++) {
                        ObjDayCustom objDay = objCal.getmLisday().get(j);
                        if (objDay != null) {
                            objDay.setData(mListBooking);
                        }
                    }
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void show_api_get_list_day(ListBookingResponse objRes) {

    }

    @Override
    public void show_booking(ObjErrorApi objError) {

    }

    @Override
    public void show_change_booking(ObjErrorApi objError) {

    }

    @Override
    public void show_booking_services(ObjErrorApi objError) {

    }

    @Override
    public void show_get_booking_services(ResponseListBookingService objRes) {

    }

    @Override
    public void show_change_billing(ObjErrorApi objError) {

    }

    @Override
    public void show_change_booking_services(ObjErrorApi objError) {

    }

}
