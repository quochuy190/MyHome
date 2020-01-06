package com.vn.myhome.fragment.lich_datphong;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.activity.user_manager.Activity_Info_User;
import com.vn.myhome.activity.user_manager.InterfaceUser;
import com.vn.myhome.adapter.AdapterItemDayDetail;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.callback.ItemClickListenerTabDate;
import com.vn.myhome.config.Constants;
import com.vn.myhome.fragment.FragmentDatphong;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjBooking;
import com.vn.myhome.models.ObjCalendar;
import com.vn.myhome.models.ObjDayCustom;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ResponseApi.ResponInfo;
import com.vn.myhome.presenter.UserPresenter;
import com.vn.myhome.untils.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
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
public class FragmentDateDatphong extends BaseFragment implements InterfaceUser.View {
    private static final String TAG = "FragmentSetup";
    public static FragmentDateDatphong fragment;
    private List<ObjCalendar> mLisCalendar;
    private AdapterItemDayDetail adapter;
    @BindView(R.id.rcv_day_list)
    RecyclerView rcv_day_list;
    RecyclerView.LayoutManager mLayoutManager;
    UserPresenter mPresenterUser;

    public static FragmentDateDatphong getInstance() {
        if (fragment == null) {
            synchronized (FragmentDateDatphong.class) {
                if (fragment == null)
                    fragment = new FragmentDateDatphong();
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
        View view = inflater.inflate(R.layout.fragment_tab_day_datphong, container, false);
        ButterKnife.bind(this, view);
        Log.e(TAG, "onCreateView: Setup");
        mPresenterUser = new UserPresenter(this);
        init();
        initData();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        init_get_list_calendar();
        return view;
    }

    private void initData() {

    }

    private void init() {
        mLisCalendar = new ArrayList<>();
        adapter = new AdapterItemDayDetail(getContext(), mLisCalendar, new ItemClickListenerTabDate() {
            @Override
            public void onClickItem(int position, Object item) {
                ObjDayCustom objDay = (ObjDayCustom) item;
                if (objDay.getCONTENT() != null) {
                    Intent intent = new Intent(getContext(), Activity_Info_User.class);
                    intent.putExtra(Constants.KEY_SEND_INFO_USERID, objDay.getUSERID());
                    intent.putExtra(Constants.KEY_SEND_INFO_USERID_TITLE, "Thông tin người đặt");
                    //startActivity(intent);
                    startActivityForResult(intent, Constants.RequestCode.GET_MY_HOME);
                }
            }

            @Override
            public void onClickItem_Lock(int position, Object item) {
                showDialogComfirm("Mở khóa nhà",
                        "Bạn có chắc chắn muốn mở khóa nhà này không?",
                        true, new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                ObjDayCustom objDay = (ObjDayCustom) item;
                                FragmentDatphong.get_api_open_lock(objDay.getID_BOOKING(), "3");
                            }

                            @Override
                            public void onClickNoDialog() {

                            }
                        });
            }
        }, false);
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        rcv_day_list.setNestedScrollingEnabled(true);
        rcv_day_list.setHasFixedSize(true);
        rcv_day_list.setLayoutManager(mLayoutManager);
        rcv_day_list.setItemAnimator(new DefaultItemAnimator());
        rcv_day_list.setAdapter(adapter);

        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {

            }
        });
    }

    int iMonth, iYear, iMonth_End, iMonth_Max;

    private void init_get_list_calendar() {
        mLisCalendar.clear();
 /*       Calendar c = Calendar.getInstance();
        iYear = c.get(Calendar.YEAR);
        iMonth = c.get(Calendar.MONTH) + 1;*/
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
        adapter.notifyDataSetChanged();
    }

    public List<ObjDayCustom> getWeeksOfMonth(int month, int year) {
        List mListDay = new ArrayList();
        try {
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
                            objDayCustom.setsStartBooking(objBooking.getSTART_TIME());
                            objDayCustom.setBILLING_STATUS(objBooking.getBILLING_STATUS());
                            objDayCustom.setBOOK_STATUS(objBooking.getBOOK_STATUS());
                            objDayCustom.setBOOK_NAME(objBooking.getBOOK_NAME());
                            objDayCustom.setID_BOOKING(objBooking.getID());
                            objDayCustom.setCONTENT(objBooking.getCONTENT());
                            objDayCustom.setUSERID(objBooking.getUSERID());
                            objDayCustom.setBOOK_STATUS_NAME(objBooking.getBOOK_STATUS_NAME());
                            // objDayCustom.set(objBooking.getBOOK_STATUS_NAME());
                        }
                        if (TimeUtils.CompareDates_Two(day, objBooking.getEND_TIME()).equals("1")) {
                            objDayCustom.setsEndBooking(objBooking.getEND_TIME());
                            objDayCustom.setBILLING_STATUS(objBooking.getBILLING_STATUS());
                            objDayCustom.setBOOK_STATUS(objBooking.getBOOK_STATUS());
                            objDayCustom.setBOOK_NAME(objBooking.getBOOK_NAME());
                            objDayCustom.setID_BOOKING(objBooking.getID());
                            objDayCustom.setCONTENT(objBooking.getCONTENT());
                            objDayCustom.setUSERID(objBooking.getUSERID());
                            objDayCustom.setBOOK_STATUS_NAME(objBooking.getBOOK_STATUS_NAME());
                        }
                        if (TimeUtils.CompareDates_Three(objBooking.getSTART_TIME(), objBooking.getEND_TIME(), day)) {
                            objDayCustom.setBooked(true);
                            objDayCustom.setBILLING_STATUS(objBooking.getBILLING_STATUS());
                            objDayCustom.setBOOK_STATUS(objBooking.getBOOK_STATUS());
                            objDayCustom.setBOOK_NAME(objBooking.getBOOK_NAME());
                            objDayCustom.setID_BOOKING(objBooking.getID());
                            objDayCustom.setCONTENT(objBooking.getCONTENT());
                            objDayCustom.setUSERID(objBooking.getUSERID());
                            objDayCustom.setBOOK_STATUS_NAME(objBooking.getBOOK_STATUS_NAME());
                        }
                    }
                    objDayCustom.setData(FragmentDatphong.mListBooking);
                }
            /*mListDay.add(new ObjDayCustom(day, iday, false, "",
                    "", sWeekDay));*/
                Date currentTime = Calendar.getInstance().getTime();
                currentTime.setHours(0);
                currentTime.setMinutes(0);
                currentTime.setSeconds(0);
                SimpleDateFormat format = new SimpleDateFormat("EEEE dd-MMM-yyyy");
                String pattern = "EEEE dd-MMM-yyyy";
                DateFormat df = new SimpleDateFormat(pattern);
                Date today = Calendar.getInstance().getTime();
                String todayAsString = df.format(today);
                if (!TimeUtils.CompareDates(todayAsString, objDayCustom.getsDay()).equals("2")) {
                    mListDay.add(objDayCustom);
                }
                //mListDay.add(new ObjDayCustom(day, iday));
                cal.add(Calendar.DATE, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mListDay;
    }

    @Override
    public void show_error_api(String sService) {

    }

    @Override
    public void show_get_user_info(ResponInfo objLogin) {
        hideDialogLoading();
        if (objLogin != null && objLogin.getERROR().equals("0000")) {

        }
    }

    @Override
    public void show_update_user_info(ObjErrorApi objError) {

    }

    @Override
    public void show_get_listuser(ResponInfo objRes) {

    }

    @Override
    public void show_change_pass(ObjErrorApi objError) {

    }
}
