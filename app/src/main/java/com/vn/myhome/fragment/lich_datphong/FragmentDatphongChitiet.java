package com.vn.myhome.fragment.lich_datphong;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.activity.book_room.ActivityThongtinChuyenkhoan;
import com.vn.myhome.activity.user_manager.Activity_Info_User;
import com.vn.myhome.adapter.AdapterTabChitietDatphong;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.callback.OnItemClickListennerTwoBtn;
import com.vn.myhome.callback.setOnItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.fragment.FragmentDatphong;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjBooking;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ResponseApi.ListBookingResponse;
import com.vn.myhome.models.ResponseApi.ResponseListBookingService;
import com.vn.myhome.presenter.BookingPresenter;
import com.vn.myhome.presenter.InterfaceBooking;
import com.vn.myhome.untils.SharedPrefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
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
public class FragmentDatphongChitiet extends BaseFragment implements InterfaceBooking.View {
    private static final String TAG = "FragmentSetup";
    public static FragmentDatphongChitiet fragment;
    private List<ObjBooking> mList;
    private AdapterTabChitietDatphong adapter;
    @BindView(R.id.rcv_day_list)
    RecyclerView rcv_day_list;
    RecyclerView.LayoutManager mLayoutManager;
    BookingPresenter mPresenter;

    public static FragmentDatphongChitiet getInstance() {
        if (fragment == null) {
            synchronized (FragmentDatphongChitiet.class) {
                if (fragment == null)
                    fragment = new FragmentDatphongChitiet();
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
            initData();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_day_datphong, container, false);
        ButterKnife.bind(this, view);
        Log.e(TAG, "onCreateView: Setup");
        mPresenter = new BookingPresenter(this);
        init();
        initData();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private void initData() {
        mList.clear();
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        if (objLogin.getUSER_TYPE().equals(Constants.UserType.DICHVU)
                || objLogin.getUSER_TYPE().equals(Constants.UserType.CTV)) {
            mList.addAll(FragmentDatphong.mListBooking);
        } else {
            for (ObjBooking objBooking : FragmentDatphong.mListBooking) {
                if (objBooking.getBILLING_STATUS().equals("2"))
                    mList.add(objBooking);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void init() {
        mList = new ArrayList<>();
        //  mList.addAll(FragmentDatphong.mListBooking);
        adapter = new AdapterTabChitietDatphong(mList, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        rcv_day_list.setNestedScrollingEnabled(true);
        rcv_day_list.setHasFixedSize(true);
        rcv_day_list.setLayoutManager(mLayoutManager);
        rcv_day_list.setItemAnimator(new DefaultItemAnimator());
        rcv_day_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnIListener(new OnItemClickListennerTwoBtn() {
            @Override
            public void OnItemClickListener_One_Btn(int position) {
                // Mở khỏa phòng
                showDialogComfirm("Mở khóa nhà",
                        "Bạn có chắc chắn muốn mở khóa nhà này không?",
                        true, new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                ObjBooking objBooking = mList.get(position);
                                FragmentDatphong.get_api_open_lock(objBooking.getID(), "3");
                            }

                            @Override
                            public void onClickNoDialog() {

                            }
                        });


            }

            @Override
            public void OnItemClickListener_Two_Btn(int position) {
                // đặt dọn phòng
                ObjBooking objBooking = mList.get(position);
                ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
                if (objLogin.getUSER_TYPE().equals(Constants.UserType.CTV)
                        || objLogin.getUSER_TYPE().equals(Constants.UserType.DICHVU)) {
                    showDialogComfirm("Thanh toán dịch vụ",
                            "Đặt nhà thành công. " +
                                    "Vui lòng thanh toán bằng cách chuyển khoản theo các thông tin sau",
                            true, new ClickDialog() {
                                @Override
                                public void onClickYesDialog() {
                                    Intent intent = new Intent(getContext(), ActivityThongtinChuyenkhoan.class);
                                    intent.putExtra(Constants.KEY_SEND_PRICE_THANHTOAN, "" + objBooking.getBOOKING_PRICE());
                                    intent.putExtra(Constants.KEY_SEND_CONTENT_THANHTOAN, objBooking.getCONTENT());
                                    intent.putExtra(Constants.KEY_SEND_ID_BOOKSERVICE_THANHTOAN, objBooking.getID_BOOK_SERVICES());
                                    startActivityForResult(intent, Constants.RequestCode.GET_BOOKING);
                                }

                                @Override
                                public void onClickNoDialog() {

                                }
                            });
                } else {
                    if (objBooking.getIS_BOOK_SERVICES() != null && objBooking.getIS_BOOK_SERVICES().equals("1")) {
                        showDialogComfirm("Thanh toán dịch vụ",
                                "Đặt dịch vụ dọn nhà thành công. " +
                                        "Vui lòng thanh toán bằng cách chuyển khoản theo các thông tin sau",
                                true, new ClickDialog() {
                                    @Override
                                    public void onClickYesDialog() {
                                        Intent intent = new Intent(getContext(), ActivityThongtinChuyenkhoan.class);
                                        intent.putExtra(Constants.KEY_SEND_PRICE_THANHTOAN, "" + objBooking.getMONEY_SERVICES());
                                        intent.putExtra(Constants.KEY_SEND_CONTENT_THANHTOAN, objBooking.getCONTENT_SERVICES());
                                        intent.putExtra(Constants.KEY_SEND_ID_BOOKSERVICE_THANHTOAN, objBooking.getID_BOOK_SERVICES());
                                        startActivityForResult(intent, Constants.RequestCode.GET_BOOKING);
                                    }

                                    @Override
                                    public void onClickNoDialog() {

                                    }
                                });

                    } else {
                      /*  showDialogComfirm("Thông báo",
                                "Bạn có chắc chắn muốn đặt dịch vụ dọn nhà?",
                                true, new ClickDialog() {
                                    @Override
                                    public void onClickYesDialog() {
                                        FragmentDatphong.get_api_book_service(objBooking.getGENLINK(),
                                                objBooking.getSTART_TIME(),
                                                objBooking.getEND_TIME(), objBooking.getID());

                                    }

                                    @Override
                                    public void onClickNoDialog() {
                                    }
                                });*/
                      showDialog_Note_Dondep(objBooking);

                    }
                }

            }
        });
        adapter.setClick_lable(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                ObjBooking obj = mList.get(position);
                Intent intent = new Intent(getContext(), Activity_Info_User.class);
                intent.putExtra(Constants.KEY_SEND_INFO_USERID, obj.getUSERID());
                intent.putExtra(Constants.KEY_SEND_INFO_USERID_TITLE, "Thông tin người đặt");
                //startActivity(intent);
                startActivityForResult(intent, Constants.RequestCode.GET_MY_HOME);
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }
    Dialog dialog;
    EditText tvPhoneDialog;
    EditText tvNameDialog;
    EditText tvNumberDialog;
    EditText tvTimeDialog;
    EditText tvNoteDialog;
    TextView btnBackDialog;
    TextView btnDoneDialog;

    public void showDialog_Note_Dondep(ObjBooking objBooking) {
        dialog = new Dialog(getContext(), R.style.Theme_Dialog);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_note_dondep);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tvPhoneDialog = dialog.findViewById(R.id.edt_dialog_phone);
        tvNameDialog = dialog.findViewById(R.id.edt_dialog_name);
        tvNumberDialog = dialog.findViewById(R.id.edt_dialog_number_guest);
        tvTimeDialog = dialog.findViewById(R.id.edt_dialog_time);
        tvNoteDialog = dialog.findViewById(R.id.edt_dialog_note);
        btnBackDialog = dialog.findViewById(R.id.txt_exit_dialog);
        btnDoneDialog = dialog.findViewById(R.id.txt_comfirm_dialog);
        tvTimeDialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(dialog.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String sTime = selectedHour+":"+selectedMinute;
                        tvTimeDialog.setText(sTime);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        btnBackDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnDoneDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sNote = "SĐT khách: "+tvPhoneDialog.getText().toString()+"\n"
                        +"Tên khách: "+tvNameDialog.getText().toString()+"\n"
                        +"Số lượng khách: "+tvNumberDialog.getText().toString()+"\n"
                        +"Thời gian check-in: "+tvTimeDialog.getText().toString()+"\n"
                        +"Ghi chú: "+tvNumberDialog.getText().toString()+"\n";
                FragmentDatphong.get_api_book_service(objBooking.getGENLINK(),
                        objBooking.getSTART_TIME(),
                        objBooking.getEND_TIME(), objBooking.getID(), sNote);
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void get_api(ObjBooking objBooking) {
        showDialogLoading();
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenter.api_booking_services2(sUserName, FragmentDatphong.objHome.getGENLINK(),
                objBooking.getSTART_TIME(), objBooking.getEND_TIME(), objBooking.getID(), "");
    }

    @Override
    public void show_error_api(String sService) {

    }

    @Override
    public void show_lock_room(ObjErrorApi objError) {

    }

    @Override
    public void show_list_bookroom(ListBookingResponse objRes) {

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
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            Toast.makeText(getContext(), "Đặt dọn phòng thành công", Toast.LENGTH_SHORT).show();

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
}
