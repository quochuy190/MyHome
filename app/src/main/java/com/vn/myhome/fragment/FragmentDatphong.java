package com.vn.myhome.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.activity.book_room.ActivityThongtinChuyenkhoan;
import com.vn.myhome.activity.notifycation.ActivityListNotify;
import com.vn.myhome.adapter.AdapterViewpager;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.config.Constants;
import com.vn.myhome.fragment.lich_datphong.FragmentDateDatphong;
import com.vn.myhome.fragment.lich_datphong.FragmentDatphongChitiet;
import com.vn.myhome.fragment.lich_datphong.FragmentLichdatphong;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjBooking;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.models.ResponseApi.ListBookingResponse;
import com.vn.myhome.models.ResponseApi.ResponListImageHome;
import com.vn.myhome.models.ResponseApi.ResponseListBookingService;
import com.vn.myhome.presenter.BookingPresenter;
import com.vn.myhome.presenter.InterfaceBooking;
import com.vn.myhome.presenter.InterfaceKindofPaid;
import com.vn.myhome.presenter.InterfaceMyHome;
import com.vn.myhome.presenter.KindofPairPresenter;
import com.vn.myhome.presenter.MyHomePresenter;
import com.vn.myhome.untils.KeyboardUtil;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class FragmentDatphong extends BaseFragment implements View.OnClickListener,
        InterfaceMyHome.View, InterfaceBooking.View, InterfaceKindofPaid.View {
    private static final String TAG = "FragmentSetup";
    public static FragmentDatphong fragment;
    @BindView(R.id.spinner_home)
    Spinner spinner_home;
    List<String> data_Home;
    public static Calendar myCalendar_start;
    public static Calendar myCalendar_end;
    @BindView(R.id.edt_date_start)
    EditText edt_date_start;
    @BindView(R.id.edt_date_end)
    EditText edt_date_end;
    @BindView(R.id.ll_date_start)
    ConstraintLayout ll_date_start;
    @BindView(R.id.ll_date_end)
    ConstraintLayout ll_date_end;
    MyHomePresenter mPresenterHome;
    public static BookingPresenter mPresenterBooking;
    List<ObjHomeStay> mListHome;
    @BindView(R.id.btn_tracuu)
    Button btn_tracuu;
    @BindView(R.id.view_pager_chunha)
    ViewPager viewPager;
    @BindView(R.id.txt_lichdatnha)
    TextView txt_lichdatnha;
    @BindView(R.id.txt_lichdonnha)
    TextView txt_lichdonnha;
    @BindView(R.id.txt_calendar)
    TextView txt_calendar;
    @BindView(R.id.btn_back)
    ImageView img_back;
    @BindView(R.id.txt_title)
    TextView txt_title;
    @BindView(R.id.txt_badger_notify)
    TextView txt_badger_notify;
    KindofPairPresenter mPresenterKindokPair;

    public static FragmentDatphong getInstance() {
        if (fragment == null) {
            synchronized (FragmentDatphong.class) {
                if (fragment == null)
                    fragment = new FragmentDatphong();
            }
        }
        return fragment;
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lichdatphong, container, false);
        ButterKnife.bind(this, view);
        Log.e(TAG, "onCreateView: Setup");
        mPresenterHome = new MyHomePresenter(this);
        mPresenterBooking = new BookingPresenter(this);
        mPresenterKindokPair = new KindofPairPresenter(this);
        data_Home = new ArrayList<>();
        mListHome = new ArrayList<>();
        mListBooking = new ArrayList<>();
        get_time();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initAppbar();
        initData();
        // set_data_spinner();
        initEvent();
        //load_tab_lich();
        set_title_tab();
        select_lichdatnha();
        check_title_notify();
        return view;
    }

    private void set_title_tab() {
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        if (objLogin.getUSER_TYPE().equals(Constants.UserType.DICHVU)
                || objLogin.getUSER_TYPE().equals(Constants.UserType.CTV)) {
            txt_lichdonnha.setText("Ngày");
            txt_lichdatnha.setText("Chi tiết");
            txt_calendar.setText("Lịch");
        } else {
            txt_lichdatnha.setText("Lịch");
            txt_lichdonnha.setText("Ngày");
            txt_calendar.setText("Chi tiết");
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.RELOAD_NOTIFY)) {
            Log.e(TAG, "onMessageEvent: " + App.sTotalNotify);
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

    private void initAppbar() {
        img_back.setVisibility(View.VISIBLE);
        img_back.setImageResource(R.drawable.ic_notify);
        txt_title.setText("LỊCH NHÀ");
    }

    public void get_time() {
        myCalendar_start = Calendar.getInstance();
        myCalendar_end = Calendar.getInstance();
        int dayOfMonth_start = myCalendar_start.get(Calendar.DAY_OF_MONTH);
        myCalendar_start.add(Calendar.DAY_OF_MONTH, -(dayOfMonth_start - 1));
        update_start_date();
        int dayOfMonth = myCalendar_end.get(Calendar.DAY_OF_MONTH);
        myCalendar_end.add(Calendar.MONTH, +2);
        update_end_date();
        initViewpager();
    }

    AdapterViewpager adapterViewpager;

    private void initViewpager() {
        adapterViewpager = new AdapterViewpager(getChildFragmentManager());
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        if (objLogin.getUSER_TYPE().equals(Constants.UserType.DICHVU)
                || objLogin.getUSER_TYPE().equals(Constants.UserType.CTV)) {
            adapterViewpager.addFragment(new FragmentDatphongChitiet(), "");
            adapterViewpager.addFragment(new FragmentDateDatphong(), "");
            adapterViewpager.addFragment(new FragmentLichdatphong(), "");
        } else {
            adapterViewpager.addFragment(new FragmentLichdatphong(), "");
            adapterViewpager.addFragment(new FragmentDateDatphong(), "");
            adapterViewpager.addFragment(new FragmentDatphongChitiet(), "");
        }


        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapterViewpager);
        // tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        select_lichdatnha();
                        break;
                    case 1:
                        select_lichdonnha();
                        break;
                    case 2:
                        select_calendar();
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        try {
            String sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
            ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
            if (objLogin.getUSER_TYPE().equals(Constants.UserType.CTV)
                    || objLogin.getUSER_TYPE().equals(Constants.UserType.DICHVU)) {
                data_Home.add("Tất cả");
                set_data_spinner();
                get_api_list_booking();
            } else {
                if (sUser != null) {
                    mPresenterHome.api_get_mylist_room(sUser, "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //load_tab_lich();
    }

    private void select_lichdatnha() {
        txt_lichdatnha.setSelected(true);
        txt_lichdonnha.setSelected(false);
        txt_calendar.setSelected(false);
        txt_lichdatnha.setTextColor(getResources().getColor(R.color.White));
        txt_lichdonnha.setTextColor(getResources().getColor(R.color.Black));
        txt_calendar.setTextColor(getResources().getColor(R.color.Black));
    }

    private void select_lichdonnha() {
        txt_lichdatnha.setSelected(false);
        txt_lichdonnha.setSelected(true);
        txt_calendar.setSelected(false);
        txt_lichdatnha.setTextColor(getResources().getColor(R.color.Black));
        txt_lichdonnha.setTextColor(getResources().getColor(R.color.White));
        txt_calendar.setTextColor(getResources().getColor(R.color.Black));
    }

    private void select_calendar() {
        txt_lichdatnha.setSelected(false);
        txt_lichdonnha.setSelected(false);
        txt_calendar.setSelected(true);
        txt_lichdatnha.setTextColor(getResources().getColor(R.color.Black));
        txt_lichdonnha.setTextColor(getResources().getColor(R.color.Black));
        txt_calendar.setTextColor(getResources().getColor(R.color.White));

    }

    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityListNotify.class);
                startActivity(intent);
            }
        });
        btn_tracuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_api_list_booking();
            }
        });
        ll_date_start.setOnClickListener(this);
        ll_date_end.setOnClickListener(this);
        txt_lichdatnha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        txt_lichdonnha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        txt_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });
    }

    private void set_data_spinner() {
        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.item_spinner, data_Home);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_home.setAdapter(adapter);
        spinner_home.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mListHome != null && mListHome.size() > 0) {
                    objHome = mListHome.get(position);
                    get_api_list_booking();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static ObjHomeStay objHome;

    private void get_api_list_booking() {
        String sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        String sDateStart = "", sDateEnd = "";

        if (edt_date_start.getText().toString().length() == 0) {
            showAlertDialog("Thông báo", "Bạn chưa nhập vào ngày bắt đầu tìm kiếm.");
            KeyboardUtil.requestKeyboard(edt_date_start);
            return;
        } else {
            sDateStart = edt_date_start.getText().toString().trim();
        }
        if (edt_date_end.getText().toString().length() == 0) {
            showAlertDialog("Thông báo", "Bạn chưa nhập vào ngày kết thúc tìm kiếm");
            KeyboardUtil.requestKeyboard(edt_date_end);
            return;
        } else {
            sDateEnd = edt_date_end.getText().toString().trim();
        }
        showDialogLoading();
        mListBooking.clear();
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        if (objLogin.getUSER_TYPE().equals(Constants.UserType.DICHVU)
                || objLogin.getUSER_TYPE().equals(Constants.UserType.CTV)) {
            mPresenterBooking.api_list_bookroom(sUsername, "", "", "",
                    sDateStart, sDateEnd, "", "", "");
        } else {
            if (objHome != null && objHome.getNAME() != null && objHome.getGENLINK() != null) {
                mPresenterBooking.api_list_bookroom(sUsername, "", objHome.getNAME(), "",
                        sDateStart, sDateEnd, "", "", objHome.getGENLINK());
            } else {
                mPresenterBooking.api_list_bookroom(sUsername, "", "", "",
                        sDateStart, sDateEnd, "", "", "");
            }
        }
    }

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
            case R.id.btn_search:
                // get_api_search();

                break;
        }
    }

    @Override
    public void show_error_api(String sService) {
        hideDialogLoading();
    }

    @Override
    public void show_lock_room(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            get_api_list_booking();
            showDialogComfirm("Thông báo",
                    "Khóa nhà thành công,bạn có muốn đặt dịch vụ dọn dẹp không?",
                    true,
                    new ClickDialog() {
                        @Override
                        public void onClickYesDialog() {
                            get_api_book_service(mGetLink, mStartDay, mEndDay, objError.getID_BOOKROOM());
                        }

                        @Override
                        public void onClickNoDialog() {

                        }
                    });
        } else {
            showAlertDialog("Thông báo", objError.getRESULT());
        }
    }

    public static List<ObjBooking> mListBooking;

    @Override
    public void show_list_bookroom(ListBookingResponse objRes) {
        hideDialogLoadingDelay();
        mListBooking.clear();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            if (objRes.getINFO() != null && objRes.getINFO().size() > 0) {
                mListBooking.addAll(objRes.getINFO());
            }
        }
        Log.e(TAG, "show_list_bookroom: " + myCalendar_end);
        int iMonth_End = FragmentDatphong.myCalendar_end.get(Calendar.MONTH) + 1;
        Log.e(TAG, "show_list_bookroom: " + iMonth_End);
        EventBus.getDefault().post(new MessageEvent(Constants.EventBus.TAB_LICH, 0, 0));
        //  load_tab_lich();
    }

    @Override
    public void show_api_get_list_day(ListBookingResponse objRes) {

    }

    @Override
    public void show_booking(ObjErrorApi objError) {

    }

    @Override
    public void show_change_booking(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            Toast.makeText(getContext(), "Mở khóa nhà thành công", Toast.LENGTH_SHORT).show();
            get_api_list_booking();
        } else {
            showAlertDialog("Thông báo", objError.getRESULT());
        }
    }

    @Override
    public void show_booking_services(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            String sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
            Toast.makeText(getContext(), "Đặt dọn phòng thành công", Toast.LENGTH_SHORT).show();
            showDialogComfirm("Thông báo",
                    "Đặt dịch vụ dọn dẹp thành công, mời bạn chọn hình thức thanh toán.",
                    "Thanh toán trước",
                    "Thanh toán trả sau",
                    true,
                    new ClickDialog() {
                        @Override
                        public void onClickYesDialog() {
                            Intent intent = new Intent(getContext(), ActivityThongtinChuyenkhoan.class);
                            intent.putExtra(Constants.KEY_SEND_PRICE_THANHTOAN, objError.getPRICE());
                            intent.putExtra(Constants.KEY_SEND_CONTENT_THANHTOAN, objError.getCONTENT());
                            intent.putExtra(Constants.KEY_SEND_ID_BOOKSERVICE_THANHTOAN, objError.getID_BOOK_SERVICE());
                            startActivityForResult(intent, Constants.RequestCode.GET_BOOKING);
                            mPresenterKindokPair.api_change_kind_of_paid(sUsername, objError.getID_BOOK_SERVICE(),
                                    "0");
                        }

                        @Override
                        public void onClickNoDialog() {
                            mPresenterKindokPair.api_change_kind_of_paid(sUsername, objError.getID_BOOK_SERVICE(), "1");
                        }
                    });
            get_api_list_booking();

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

    public static String mStartDay = "", mEndDay = "", mGetLink = "";

    public static void get_api_lock_room(String sGetlink, String sStartDay, String sEndDay) {
        if (objHome != null && objHome.getGENLINK() != null) {
            String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
            mPresenterBooking.api_lock_room(sUserName, objHome.getGENLINK(),
                    TimeUtils.convent_date(sStartDay, "EEEE dd-MMM-yyyy", "dd/MM/yyyy"),
                    TimeUtils.convent_date(sEndDay, "EEEE dd-MMM-yyyy", "dd/MM/yyyy")
            );
            mStartDay = TimeUtils.convent_date(sStartDay, "EEEE dd-MMM-yyyy", "dd/MM/yyyy");
            mEndDay = TimeUtils.convent_date(sEndDay, "EEEE dd-MMM-yyyy", "dd/MM/yyyy");
            mGetLink = objHome.getGENLINK();
            // get_api_book_service(sGetlink, sStartDay, sEndDay, "");
        }
    }

    public static void get_api_open_lock(String ID_BOOKROOM, String BOOKING_STATUS) {
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenterBooking.api_change_booking(sUserName, ID_BOOKROOM, BOOKING_STATUS);
    }

    public static void get_api_book_service(String sGetLink, String CHECKIN, String CHECKOUT, String ID_BOOKROOM) {
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenterBooking.api_booking_services2(sUserName, sGetLink, CHECKIN, CHECKOUT, ID_BOOKROOM);
    }

    @Override
    public void show_get_mylist_room(GetRoomResponse objRes) {
        try {
            if (objRes != null && objRes.getERROR().equals("0000")) {
                if (objRes.getINFO() != null) {
                    mListHome.addAll(objRes.getINFO());
                    for (ObjHomeStay objHome : objRes.getINFO()) {
                        if (objHome.getNAME() != null)
                            data_Home.add(objHome.getNAME());
                    }
                }
            }
            set_data_spinner();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void show_room_detail(ObjHomeStay objRes) {

    }

    @Override
    public void show_new_room(ObjErrorApi objError) {

    }

    @Override
    public void show_edit_room(ObjErrorApi objError) {

    }

    @Override
    public void show_delete_room(ObjErrorApi objError) {

    }

    @Override
    public void show_update_state_room(ObjErrorApi objError) {

    }

    @Override
    public void show_get_album_image(ResponListImageHome objRes) {

    }

    @Override
    public void show_error_api(ObjErrorApi sService) {

    }

    @Override
    public void show_change_kind_of_paid(ObjErrorApi sService) {

    }
}
