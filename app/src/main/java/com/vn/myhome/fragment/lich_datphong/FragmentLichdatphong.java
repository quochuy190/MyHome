package com.vn.myhome.fragment.lich_datphong;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.adapter.AdapterListTabLich;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.fragment.FragmentDatphong;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjBooking;
import com.vn.myhome.models.ObjCalendar;
import com.vn.myhome.models.ObjDayCustom;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 22-April-2019
 * Time: 10:30
 * Version: 1.0
 */
public class FragmentLichdatphong extends BaseFragment {
    private static final String TAG = "FragmentLichdatphong";
    public static FragmentLichdatphong fragment;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterListTabLich adapter;
    @BindView(R.id.rcv_list_tab_lich)
    RecyclerView rcv_list_calendar_custom;

    public static FragmentLichdatphong getInstance() {
        if (fragment == null) {
            synchronized (FragmentLichdatphong.class) {
                if (fragment == null)
                    fragment = new FragmentLichdatphong();
            }
        }
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.TAB_LICH)) {
            Log.e(TAG, "onMessageEvent: ");
            init_get_list_calendar();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_lich, container, false);
        ButterKnife.bind(this, view);
        Log.e(TAG, "onCreateView: lich: " + FragmentDatphong.mListBooking.size());
        mLisCalendar = new ArrayList<>();
        init();
        initData();
        initCalender();
        init_get_list_calendar();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private void initData() {

    }

    private void init() {

    }

    List<ObjCalendar> mLisCalendar;
    int iMonth, iYear, iMonth_End, iMonth_Max;

    private void init_get_list_calendar() {
        mLisCalendar.clear();
        Calendar c = Calendar.getInstance();
        iYear = FragmentDatphong.myCalendar_start.get(Calendar.YEAR);
        iMonth = FragmentDatphong.myCalendar_start.get(Calendar.MONTH) + 1;
        iMonth_End = FragmentDatphong.myCalendar_end.get(Calendar.MONTH) + 1;
        if (iMonth_End > iMonth) {
            iMonth_Max = iMonth_End - iMonth + 1;
        } else {
            iMonth_Max = (12 - iMonth) + iMonth_End + 1;
        }
        for (int i = 0; i < iMonth_Max; i++) {
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
            if (FragmentDatphong.mListBooking.size() > 0) {
                for (int j = 0; j < FragmentDatphong.mListBooking.size(); j++) {
                    ObjBooking objBooking = FragmentDatphong.mListBooking.get(j);
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
                objDayCustom.setData(FragmentDatphong.mListBooking);
            }
            /*mListDay.add(new ObjDayCustom(day, iday, false, "",
                    "", sWeekDay));*/

            mListDay.add(objDayCustom);
            //mListDay.add(new ObjDayCustom(day, iday));
            cal.add(Calendar.DATE, 1);
        }
        return mListDay;
    }

    String sStartBookingDay = "";
    String sEndBookingDay = "";

    private void initCalender() {
        adapter = new AdapterListTabLich(getContext(), mLisCalendar, new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
                if (objLogin != null && objLogin.getUSER_TYPE().equals(Constants.UserType.CHUNHA)) {
                    ObjDayCustom obj = (ObjDayCustom) item;
                    if (!obj.isBooked()) {
                        if (sStartBookingDay.length() == 0) {
                            sStartBookingDay = obj.getsDay();
                            for (int i = 0; i < iMonth_Max; i++) {
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
                                for (int i = 0; i < iMonth_Max; i++) {
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
                                                        FragmentDatphong.
                                                                get_api_lock_room(obj.getGENLINK(),
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
        for (int i = 0; i < iMonth_Max; i++) {
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
}
