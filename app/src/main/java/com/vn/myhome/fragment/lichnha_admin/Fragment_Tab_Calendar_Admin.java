package com.vn.myhome.fragment.lichnha_admin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.adapter.AdapterListTabLich;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.fragment.FragmentLichnhaAdmin;
import com.vn.myhome.models.ObjBooking;
import com.vn.myhome.models.ObjCalendar;
import com.vn.myhome.models.ObjDayCustom;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ResponseApi.ListBookingResponse;
import com.vn.myhome.models.ResponseApi.ResponseListBookingService;
import com.vn.myhome.presenter.BookingPresenter;
import com.vn.myhome.presenter.InterfaceBooking;
import com.vn.myhome.presenter.InterfaceKindofPaid;
import com.vn.myhome.presenter.KindofPairPresenter;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
public class Fragment_Tab_Calendar_Admin extends BaseFragment implements InterfaceBooking.View, View.OnClickListener, InterfaceKindofPaid.View {
    private static final String TAG = "FragmentSetup";
    public static Fragment_Tab_Calendar_Admin fragment;

    @BindView(R.id.ll_date_start)
    ConstraintLayout ll_date_start;
    @BindView(R.id.ll_date_end)
    ConstraintLayout ll_date_end;
    @BindView(R.id.edt_date_start)
    EditText edt_date_start;
    @BindView(R.id.edt_date_end)
    EditText edt_date_end;
    @BindView(R.id.img_date_end)
    ImageView img_date_end;
    @BindView(R.id.img_date_start)
    ImageView img_date_start;
    @BindView(R.id.btn_tracuu)
    Button btn_tracuu;
    Calendar myCalendar_start = Calendar.getInstance();
    Calendar myCalendar_end = Calendar.getInstance();
    List<ObjBooking> mListBooking;
    int iMonth_start, iMonth_end, iYear_start, iYear_end, iDuration;
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

    public static Fragment_Tab_Calendar_Admin getInstance() {
        if (fragment == null) {
            synchronized (Fragment_Tab_Calendar_Admin.class) {
                if (fragment == null)
                    fragment = new Fragment_Tab_Calendar_Admin();
            }
        }
        return fragment;
    }

    BookingPresenter mPresenterBooking;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_calendar, container, false);
        ButterKnife.bind(this, view);
        Log.e(TAG, "onCreateView: Setup");
        mListHome = new ArrayList<>();
        data_Home = new ArrayList<>();
        mListBooking = new ArrayList<>();
        mLisCalendar = new ArrayList<>();
        mPresenterBooking = new BookingPresenter(this);
        mPresenterKindokPair = new KindofPairPresenter(this);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        get_time();
        set_data_spinner();
        initCalender();
        init_get_list_calendar();
        initEvent();
        return view;
    }

    private void initEvent() {
        ll_date_start.setOnClickListener(this);
        ll_date_end.setOnClickListener(this);
        btn_tracuu.setOnClickListener(this);
    }

    public void get_time() {
        int dayOfMonth_start = myCalendar_start.get(Calendar.DAY_OF_MONTH);
        myCalendar_start.add(Calendar.DAY_OF_MONTH, -(dayOfMonth_start - 1));
        update_start_date();
        int dayOfMonth = myCalendar_end.get(Calendar.DAY_OF_MONTH);
        myCalendar_end.add(Calendar.MONTH, +4);
        update_end_date();

    }

    boolean isClick = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_date_start:
                new DatePickerDialog(getContext(), R.style.MyDatePickerStyle, start_date, myCalendar_start
                        .get(Calendar.YEAR), myCalendar_start.get(Calendar.MONTH),
                        myCalendar_start.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.ll_date_end:
                new DatePickerDialog(getContext(), R.style.MyDatePickerStyle, end_date, myCalendar_end
                        .get(Calendar.YEAR), myCalendar_end.get(Calendar.MONTH),
                        myCalendar_end.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.btn_tracuu:
                if (!isClick) {
                    isClick = true;
                    showDialogLoading();
                    get_api_list_booking();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isClick = false;
                    }
                }, 1500);
                break;

        }
    }

    List<String> data_Home;
    @BindView(R.id.spinner_home)
    Spinner spinner_home;
    String sGetLink = "";
    List<ObjHomeStay> mListHome;

    private void set_data_spinner() {
        try {
            if (FragmentLichnhaAdmin.mListMyhome.size() > 0) {
                for (ObjHomeStay obj : FragmentLichnhaAdmin.mListMyhome) {
                    if (obj != null && obj.getNAME() != null) {
                        mListHome.add(obj);
                        data_Home.add(obj.getNAME());
                    }
                }
            }
            ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.item_spinner, data_Home);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spinner_home.setAdapter(adapter);
            spinner_home.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        ObjHomeStay objHOme = mListHome.get(position);
                        sGetLink = objHOme.getGENLINK();
                        get_api_list_booking();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void get_api_list_booking() {
        String sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        String startDate = edt_date_start.getText().toString();
        String endDate = edt_date_end.getText().toString();
        mPresenterBooking.api_list_bookroom(sUser, "", "",
                "", startDate, endDate, "", "", sGetLink);
    }


    KindofPairPresenter mPresenterKindokPair;
    @Override
    public void show_booking_services(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            String sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
            mPresenterKindokPair.api_change_kind_of_paid(sUsername, objError.getID_BOOK_SERVICE(), "1");
            get_api_list_booking();
            Toast.makeText(getContext(), "Khóa phòng và đặt dọn phòng thành công", Toast.LENGTH_SHORT).show();
        } else {
            showAlertDialog("Thông báo", objError.getRESULT());
        }
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

    RecyclerView.LayoutManager mLayoutManager;
    AdapterListTabLich adapter;
    @BindView(R.id.rcv_list_tab_lich)
    RecyclerView rcv_list_calendar_custom;
    List<ObjCalendar> mLisCalendar;
    String sStartBookingDay = "";
    String sEndBookingDay = "";

    private void initCalender() {
        adapter = new AdapterListTabLich(getContext(), mLisCalendar, new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
                if (objLogin == null)
                    return;
                if (objLogin.getUSER_TYPE().equals(Constants.UserType.CHUNHA)
                        || objLogin.getUSER_TYPE().equals(Constants.UserType.ADMIN)
                ) {
                    ObjDayCustom obj = (ObjDayCustom) item;
                    if (!obj.isBooked()) {
                        if (sStartBookingDay.length() == 0) {
                            sStartBookingDay = obj.getsDay();
                            for (int i = 0; i < iDuration; i++) {
                                for (int j = 0; j < mLisCalendar.get(i).getmLisday().size(); j++) {
                                    ObjDayCustom objDay = mLisCalendar.get(i).getmLisday().get(j);
                                    if (objDay != null)
                                        objDay.setsStartClick(obj.getsDay());
                                }

                            }
                        } else if (sStartBookingDay.length() > 0) {
                            if (sEndBookingDay.length() == 0) {
                                sEndBookingDay = obj.getsDay();
                                if (sStartBookingDay.equals(sEndBookingDay)) {
                                    reload_all_click();
                                    return;
                                }
                                for (int i = 0; i < iDuration; i++) {
                                    for (int j = 0; j < mLisCalendar.get(i).getmLisday().size(); j++) {
                                        ObjDayCustom objDay = mLisCalendar.get(i).getmLisday().get(j);
                                        if (objDay != null)
                                            objDay.setsEndClick(obj.getsDay());
                                    }
                                }
                                List<Date> lisDate = TimeUtils.get_list_date(sStartBookingDay, sEndBookingDay);
                                int day = lisDate.size() - 1;
                                String sMessage = "Bạn có chắc chắn khóa phòng <font color='#FF6633'>"
                                        + lisDate.size() + " ngày " + day + " đêm</font>"
                                        + " từ <font color='#FF6633'> "
                                        + TimeUtils.convent_date(sStartBookingDay, "EEEE dd-MMM-yyyy",
                                        "dd/MM/yyyy")
                                        + " </font> đến ngày <font color='#FF6633'>"
                                        + TimeUtils.convent_date(sEndBookingDay, "EEEE dd-MMM-yyyy",
                                        "dd/MM/yyyy")
                                        + "</font> không?";
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        showDialogComfirm("Thông báo",
                                                sMessage,
                                                true, new ClickDialog() {
                                                    @Override
                                                    public void onClickYesDialog() {
                                                        get_api_lock_room(sGetLink,
                                                                        sStartBookingDay, sEndBookingDay);
                                                        reload_all_click();
                                                    }

                                                    @Override
                                                    public void onClickNoDialog() {
                                                        reload_all_click();
                                                    }
                                                });
                                    }
                                }, 1000);

                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, false);
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        // rcv_list_calendar_custom.setHasFixedSize(true);
        rcv_list_calendar_custom.setLayoutManager(mLayoutManager);
        rcv_list_calendar_custom.setItemAnimator(new DefaultItemAnimator());
        rcv_list_calendar_custom.setAdapter(adapter);
        rcv_list_calendar_custom.setNestedScrollingEnabled(true);
        adapter.notifyDataSetChanged();
        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {

            }
        });
    }

    private void reload_all_click() {
        sEndBookingDay = "";
        sStartBookingDay = "";
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < mLisCalendar.get(i).getmLisday().size(); j++) {
                ObjDayCustom objDay = mLisCalendar.get(i).getmLisday().get(j);
                if (objDay != null) {
                    objDay.setBooked(false);
                    objDay.setsEndClick("");
                    objDay.setsStartClick("");
                }
            }
        }
        init_get_list_calendar();
    }

    int iMonth, iYear;

    private void init_get_list_calendar() {
        mLisCalendar.clear();
        Calendar c = Calendar.getInstance();
        iYear = myCalendar_start.get(Calendar.YEAR);
        iMonth = myCalendar_start.get(Calendar.MONTH) + 1;

        iYear_end = myCalendar_end.get(Calendar.YEAR);
        iMonth_end = myCalendar_end.get(Calendar.MONTH) + 1;
        iDuration = get_duration_month(iYear, iYear_end, iMonth, iMonth_end);
        for (int i = 0; i < iDuration; i++) {
            mLisCalendar.add(new ObjCalendar("" + iMonth, "" + iYear,
                    getWeeksOfMonth(iMonth, iYear)));
            iMonth++;
            if (iMonth > 12) {
                iMonth = 1;
                iYear++;
            }
        }
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
    }

    private int get_duration_month(int year_start, int year_end, int month_start, int month_end) {
        if (year_end == year_start) {
            if (month_end > month_start)
                return month_end - month_start + 1;
            else return 1;
        } else {
            if (month_end > month_start)
                return month_end - month_start + 1;
            else {
                return month_end + 12 - month_start + 1;
            }
        }
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
            ObjDayCustom objDayCustom = new ObjDayCustom(day, iday, false, "",
                    "", sWeekDay);
            if (mListBooking.size() > 0) {
                for (int j = 0; j < mListBooking.size(); j++) {
                    ObjBooking objBooking = mListBooking.get(j);
                    if (TimeUtils.CompareDates_Two(day, objBooking.getSTART_TIME()).equals("1")) {
                        objDayCustom.setGENLINK(objBooking.getGENLINK());
                        objDayCustom.setsStartBooking(objBooking.getSTART_TIME());
                        objDayCustom.setBILLING_STATUS(objBooking.getBILLING_STATUS());
                        objDayCustom.setBOOK_STATUS(objBooking.getBOOK_STATUS());
                        objDayCustom.setBooked(true);
                    }
                    if (TimeUtils.CompareDates_Two(day, objBooking.getEND_TIME()).equals("1")) {
                        objDayCustom.setGENLINK(objBooking.getGENLINK());
                        objDayCustom.setsEndBooking(objBooking.getEND_TIME());
                        objDayCustom.setBILLING_STATUS(objBooking.getBILLING_STATUS());
                        objDayCustom.setBOOK_STATUS(objBooking.getBOOK_STATUS());
                    }
                    if (TimeUtils.CompareDates_Three(objBooking.getSTART_TIME(), objBooking.getEND_TIME(), day)) {
                        objDayCustom.setBooked(true);
                        objDayCustom.setBILLING_STATUS(objBooking.getBILLING_STATUS());
                        objDayCustom.setBOOK_STATUS(objBooking.getBOOK_STATUS());
                        objDayCustom.setGENLINK(objBooking.getGENLINK());
                    }
                }
                objDayCustom.setData(mListBooking);
            }
            /*mListDay.add(new ObjDayCustom(day, iday, false, "",
                    "", sWeekDay));*/

            mListDay.add(objDayCustom);
            //mListDay.add(new ObjDayCustom(day, iday));
            cal.add(Calendar.DATE, 1);
        }
        return mListDay;
    }

    public static String mStartDay = "", mEndDay = "", mGetLink = "";
    public  void get_api_lock_room(String sGetlink, String sStartDay, String sEndDay) {
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenterBooking.api_lock_room(sUserName, sGetlink,
                TimeUtils.convent_date(sStartDay, "EEEE dd-MMM-yyyy", "dd/MM/yyyy"),
                TimeUtils.convent_date(sEndDay, "EEEE dd-MMM-yyyy", "dd/MM/yyyy")
        );
        mStartDay = TimeUtils.convent_date(sStartDay, "EEEE dd-MMM-yyyy", "dd/MM/yyyy");
        mEndDay = TimeUtils.convent_date(sEndDay, "EEEE dd-MMM-yyyy", "dd/MM/yyyy");
        mGetLink = sGetlink;
        // get_api_book_service(sGetlink, sStartDay, sEndDay, "");
    }
    public  void get_api_book_service(String sGetLink, String CHECKIN, String CHECKOUT, String ID_BOOKROOM) {
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenterBooking.api_booking_services2(sUserName, sGetLink, CHECKIN, CHECKOUT, ID_BOOKROOM);
    }

    @Override
    public void show_error_api(ObjErrorApi sService) {

    }

    @Override
    public void show_change_kind_of_paid(ObjErrorApi sService) {

    }
    @Override
    public void show_error_api(String sService) {

    }

    @Override
    public void show_lock_room(ObjErrorApi objError) {
        if (objError != null && objError.getERROR().equals("0000")) {
            get_api_book_service(mGetLink, mStartDay, mEndDay, objError.getID_BOOKROOM());
        } else {
            showAlertDialog("Thông báo", objError.getRESULT());
        }
    }

    @Override
    public void show_list_bookroom(ListBookingResponse objRes) {
        hideDialogLoading();
        mListBooking.clear();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            if (objRes.getINFO() != null)
                mListBooking.addAll(objRes.getINFO());
        }
        init_get_list_calendar();
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
}
