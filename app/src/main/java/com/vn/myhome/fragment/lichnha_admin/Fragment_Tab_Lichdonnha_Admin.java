package com.vn.myhome.fragment.lichnha_admin;

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

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.vn.myhome.R;
import com.vn.myhome.activity.home.ActivityRoomDetail;
import com.vn.myhome.activity.user_manager.Activity_Info_User;
import com.vn.myhome.adapter.AdapterItemLichdonphongAdmin;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.callback.OnItemClickListennerTwoBtn;
import com.vn.myhome.callback.setOnItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.fragment.FragmentLichnhaAdmin;
import com.vn.myhome.models.ObjBookingService;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ResponseApi.ListBookingResponse;
import com.vn.myhome.models.ResponseApi.ResponseListBookingService;
import com.vn.myhome.presenter.BookingPresenter;
import com.vn.myhome.presenter.InterfaceBooking;
import com.vn.myhome.untils.SharedPrefs;

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
public class Fragment_Tab_Lichdonnha_Admin extends BaseFragment implements View.OnClickListener, InterfaceBooking.View {
    private static final String TAG = "FragmentSetup";
    public static Fragment_Tab_Lichdonnha_Admin fragment;

    public static Fragment_Tab_Lichdonnha_Admin getInstance() {
        if (fragment == null) {
            synchronized (Fragment_Tab_Lichdonnha_Admin.class) {
                if (fragment == null)
                    fragment = new Fragment_Tab_Lichdonnha_Admin();
            }
        }
        return fragment;
    }

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
    List<ObjHomeStay> mListHome;
    private List<ObjBookingService> mList;
    private AdapterItemLichdonphongAdmin adapter;
    @BindView(R.id.rcv_list_lichdonnha_admin)
    RecyclerView rcv_day_list;
    RecyclerView.LayoutManager mLayoutManager;
    BookingPresenter mPresenter;
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
        View view = inflater.inflate(R.layout.fragment_lichdonnha_admin, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new BookingPresenter(this);
        mListHome = new ArrayList<>();
        data_Home = new ArrayList<>();
        Log.e(TAG, "onCreateView: Setup");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        init();
        get_time();
        set_data_spinner();
        set_data_spinner_thanhtoan_donnha();
        set_data_spinner_trangthai_don();
        initEvent();
        get_list_donphong();
        return view;
    }

    private void initEvent() {
        ll_date_start.setOnClickListener(this);
        ll_date_end.setOnClickListener(this);
        btn_tracuu.setOnClickListener(this);
    }
    List<String> data_trangthai_thanhtoan;
    @BindView(R.id.spinner_trangthai_thanhtoan_donnha)
    Spinner spinner_trangthai_thanhtoan;
    String BILLING_STATUS = "";// 0: chua tt ;1: da tt
    private void set_data_spinner_thanhtoan_donnha() {
        data_trangthai_thanhtoan = new ArrayList<>();
        // add trạng thái thanh toán
        data_trangthai_thanhtoan.add("- Tất cả - ");
        data_trangthai_thanhtoan.add("Chưa thanh toán");
        data_trangthai_thanhtoan.add("Đã thanh toán");
        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.item_spinner, data_trangthai_thanhtoan);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_trangthai_thanhtoan.setAdapter(adapter);
        spinner_trangthai_thanhtoan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    BILLING_STATUS = "";
                } else
                    BILLING_STATUS = "" + (position - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    List<String> data_trangthai_bookphong;
    @BindView(R.id.spinner_trangthai_don)
    Spinner spinner_trangthai_bookphong;
    String BOOKING_STATUS = "";//1: duyet ok ;2 :lock ;3 huy
    private void set_data_spinner_trangthai_don() {
        data_trangthai_bookphong = new ArrayList<>();
        data_trangthai_bookphong.add("- Tất cả - ");
        data_trangthai_bookphong.add("Đang chờ");
        data_trangthai_bookphong.add("Check-In");
        data_trangthai_bookphong.add("Check-Out");
        data_trangthai_bookphong.add("Hoàn thành");
        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.item_spinner, data_trangthai_bookphong);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_trangthai_bookphong.setAdapter(adapter);
        spinner_trangthai_bookphong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // sStatusBook = "" + position;
               switch (position){
                   case 0:
                       BOOKING_STATUS = "";
                       break;
                   case 1:
                       BOOKING_STATUS = "0";
                       break;
                   case 2:
                       BOOKING_STATUS = "1";
                       break;
                   case 3:
                       BOOKING_STATUS = "2";
                       break;
                   case 4:
                       BOOKING_STATUS = "4";
                       break;
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void init() {
        mList = new ArrayList<>();
        //  mList.addAll(FragmentDatphong.mListBooking);
        adapter = new AdapterItemLichdonphongAdmin(mList, getContext());
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
                // trạng thái nhà
                show_bottom_dialog(mList.get(position));
            }

            @Override
            public void OnItemClickListener_Two_Btn(int position) {
                // cập nhật thanh toán
                show_bottom_dialog_thanhtoan(mList.get(position));
            }
        });
        adapter.setClick_lable(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                Intent intent = new Intent(getContext(), ActivityRoomDetail.class);
                ObjBookingService objBookingService = mList.get(position);
                ObjHomeStay obj = new ObjHomeStay("", objBookingService.getGENLINK());
                intent.putExtra(Constants.KEY_SEND_ROOM_DETAIL, obj);
                startActivity(intent);
            }

            @Override
            public void OnLongItemClickListener(int position) {
                ObjBookingService obj = mList.get(position);
                Intent intent = new Intent(getContext(), Activity_Info_User.class);
                intent.putExtra(Constants.KEY_SEND_INFO_USERID, obj.getUSERID());
                intent.putExtra(Constants.KEY_SEND_INFO_USERID_TITLE, "Thông tin người đặt");
                //startActivity(intent);
                startActivityForResult(intent, Constants.RequestCode.GET_MY_HOME);
            }
        });
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
            case R.id.btn_tracuu:
                showDialogLoading();
                get_list_donphong();
                break;

        }
    }

    private void get_list_donphong() {
        String sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        String sStartDate = edt_date_start.getText().toString();
        String sEndDate = edt_date_end.getText().toString();
        mPresenter.api_get_booking_services(sUser, sGetLink, sStartDate, sEndDate, BOOKING_STATUS, BILLING_STATUS);

    }

    List<String> data_Home;
    @BindView(R.id.spinner_home)
    Spinner spinner_home;
    String sGetLink = "";


    private void set_data_spinner() {
        try {
            data_Home.add("- Tất cả -");
            mListHome.add(new ObjHomeStay("- Tất cả -", ""));
            if (FragmentLichnhaAdmin.mListMyhome.size() > 0) {
                for (ObjHomeStay obj : FragmentLichnhaAdmin.mListMyhome) {
                    mListHome.add(obj);
                    data_Home.add(obj.getNAME());
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

    public void get_time() {
        int dayOfMonth_start = myCalendar_start.get(Calendar.DAY_OF_MONTH);
        myCalendar_start.add(Calendar.DAY_OF_MONTH, -(dayOfMonth_start - 1));
        update_start_date();
        int dayOfMonth = myCalendar_end.get(Calendar.DAY_OF_MONTH);
        myCalendar_end.add(Calendar.MONTH, +4);
        update_end_date();
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

    }

    @Override
    public void show_get_booking_services(ResponseListBookingService objRes) {
        hideDialogLoading();
        mList.clear();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            mList.addAll(objRes.getINFO());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void show_change_billing(ObjErrorApi objError) {

    }

    @Override
    public void show_change_booking_services(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            showDialogLoading();
            get_list_donphong();
            Toast.makeText(getContext(), "Thay đổi trạng thái thành công", Toast.LENGTH_SHORT).show();
        }
    }

    BottomSheetDialog mBottomSheetDialog;


    /* when state=0 then ''Chờ xác nhận''
     when state=1 then ''CHECK-IN''
     when state=2 then ''CHECK-OUT''
     when state=3 then ''Đang dọn''
     when state=4 then ''Hoàn thành''
             else ''OTHER'' end*/
    private void show_bottom_dialog(ObjBookingService objBooking) {
        TextView txt_choxacnhan;
        TextView txt_checkin;
        TextView txt_checkout;
        TextView txt_dangdondep;
        TextView txt_dahoanthanh;
        TextView txt_title_bottom_dialog;
        TextView txt_dong;
        mBottomSheetDialog = new BottomSheetDialog(getContext());
        View sheetView = getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_status_pay, null);
        mBottomSheetDialog.setContentView(sheetView);
        txt_choxacnhan = sheetView.findViewById(R.id.txt_menu_dialog_1);
        txt_title_bottom_dialog = sheetView.findViewById(R.id.txt_title_bottom_dialog);
        txt_checkin = sheetView.findViewById(R.id.txt_menu_dialog_2);
        txt_checkout = sheetView.findViewById(R.id.txt_menu_dialog_3);
        txt_dangdondep = sheetView.findViewById(R.id.txt_menu_dialog_4);
        txt_dahoanthanh = sheetView.findViewById(R.id.txt_menu_dialog_5);
        txt_dong = sheetView.findViewById(R.id.txt_dong_dialog);
        View view_4 = sheetView.findViewById(R.id.view_dialog_4);
        txt_dahoanthanh.setVisibility(View.VISIBLE);
        txt_choxacnhan.setText("Chờ check - in");
        txt_checkin.setText("Check - in");
        txt_checkout.setText("Check - out");
        txt_dangdondep.setText("Đang dọn dẹp");
        txt_dahoanthanh.setText("Hoàn thành");
        txt_title_bottom_dialog.setText("Cập nhật trạng thái phòng");
        txt_dangdondep.setVisibility(View.GONE);
        view_4.setVisibility(View.GONE);
        txt_choxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_api_change_booking_services(objBooking.getID(), "",
                        Constants.STATE_BOOKING_SERVICE.CHO_XAC_NHAN);
                mBottomSheetDialog.dismiss();
            }
        });
        txt_checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  get_api_change_booking_services(objBooking.getID(), "1");
                get_api_change_booking_services(objBooking.getID(), "",
                        Constants.STATE_BOOKING_SERVICE.CHECK_IN);
                mBottomSheetDialog.dismiss();
            }
        });
        txt_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get_api_change_billing(objBooking.getID(), "2");
                get_api_change_booking_services(objBooking.getID(), "",
                        Constants.STATE_BOOKING_SERVICE.CHECK_OUT);
                mBottomSheetDialog.dismiss();
            }
        });
        txt_dangdondep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  get_api_change_billing(objBooking.getID(), "3");
                get_api_change_booking_services(objBooking.getID(), "",
                        Constants.STATE_BOOKING_SERVICE.CHECK_DANG_DON);
                mBottomSheetDialog.dismiss();
            }
        });
        txt_dahoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  get_api_change_billing(objBooking.getID(), "3");
                get_api_change_booking_services(objBooking.getID(), "",
                        Constants.STATE_BOOKING_SERVICE.CHECK_DA_HOANTHANH);
                mBottomSheetDialog.dismiss();
            }
        });
        txt_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();
    }

    private void get_api_change_booking_services(String ID_BOOK, String BILLING_STATUS, String STATE) {
        showDialogComfirm("Thay đổi trạng thái thanh toán",
                "Bạn có chắc chắn muốn thay đổi trạng thái dịch vụ?", true,
                new ClickDialog() {
                    @Override
                    public void onClickYesDialog() {
                        showDialogLoading();
                        String sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                        mPresenter.api_change_booking_services(sUsername, ID_BOOK, STATE, BILLING_STATUS);
                    }

                    @Override
                    public void onClickNoDialog() {

                    }
                });

    }

    private void show_bottom_dialog_thanhtoan(ObjBookingService objBooking) {
        TextView txt_choxacnhan;
        TextView txt_checkin;
        TextView txt_checkout;
        TextView txt_dangdondep;
        TextView txt_dahoanthanh;
        TextView txt_dong;
        mBottomSheetDialog = new BottomSheetDialog(getContext());
        View sheetView = getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_status_pay, null);
        mBottomSheetDialog.setContentView(sheetView);
        txt_choxacnhan = sheetView.findViewById(R.id.txt_menu_dialog_1);
        txt_checkin = sheetView.findViewById(R.id.txt_menu_dialog_2);
        txt_checkout = sheetView.findViewById(R.id.txt_menu_dialog_3);
        txt_dangdondep = sheetView.findViewById(R.id.txt_menu_dialog_4);
        txt_dahoanthanh = sheetView.findViewById(R.id.txt_menu_dialog_5);
        txt_dong = sheetView.findViewById(R.id.txt_dong_dialog);
        View view_5 = sheetView.findViewById(R.id.view_dialog_5);
        View view_4 = sheetView.findViewById(R.id.view_dialog_4);
        View view_3 = sheetView.findViewById(R.id.view_dialog_3);
        txt_dahoanthanh.setVisibility(View.GONE);
        txt_dangdondep.setVisibility(View.GONE);
        txt_checkout.setVisibility(View.GONE);
        view_3.setVisibility(View.GONE);
        view_4.setVisibility(View.GONE);
        view_5.setVisibility(View.GONE);
        txt_choxacnhan.setText("Chưa thanh toán");
        txt_checkin.setText("Đã thanh toán");
        txt_choxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_api_change_booking_services(objBooking.getID(), "0",
                        "");
                mBottomSheetDialog.dismiss();
            }
        });
        txt_checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  get_api_change_booking_services(objBooking.getID(), "1");
                get_api_change_booking_services(objBooking.getID(), "1",
                        "");
                mBottomSheetDialog.dismiss();
            }
        });
        txt_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();
    }

}
