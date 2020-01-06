package com.vn.myhome.fragment.lichnha_admin;

import android.annotation.SuppressLint;
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
import com.vn.myhome.activity.user_manager.Activity_Info_User;
import com.vn.myhome.adapter.AdapterItemDatphongAdmin;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.callback.OnItemClickListennerTwoBtn;
import com.vn.myhome.callback.setOnItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.fragment.FragmentLichnhaAdmin;
import com.vn.myhome.models.ObjBooking;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.models.ResponseApi.ListBookingResponse;
import com.vn.myhome.models.ResponseApi.ResponListImageHome;
import com.vn.myhome.models.ResponseApi.ResponseListBookingService;
import com.vn.myhome.presenter.BookingPresenter;
import com.vn.myhome.presenter.InterfaceBooking;
import com.vn.myhome.presenter.InterfaceMyHome;
import com.vn.myhome.presenter.MyHomePresenter;
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
public class Fragment_TabLichdatnha_Admin extends BaseFragment implements InterfaceMyHome.View, View.OnClickListener, InterfaceBooking.View {
    private static final String TAG = "Fragment_TabLichdatnha_Admin";
    public static Fragment_TabLichdatnha_Admin fragment;

    public static Fragment_TabLichdatnha_Admin getInstance() {
        if (fragment == null) {
            synchronized (Fragment_TabLichdatnha_Admin.class) {
                if (fragment == null)
                    fragment = new Fragment_TabLichdatnha_Admin();
            }
        }
        return fragment;
    }

    @BindView(R.id.edt_date_start)
    EditText edt_date_start;
    @BindView(R.id.edt_date_end)
    EditText edt_date_end;
    @BindView(R.id.img_date_end)
    ImageView img_date_end;
    @BindView(R.id.img_date_start)
    ImageView img_date_start;
    Calendar myCalendar_start = Calendar.getInstance();
    Calendar myCalendar_end = Calendar.getInstance();
    MyHomePresenter mPresenter;
    List<ObjHomeStay> mListHome;
    @BindView(R.id.ll_date_start)
    ConstraintLayout ll_date_start;
    @BindView(R.id.ll_date_end)
    ConstraintLayout ll_date_end;
    @BindView(R.id.btn_tracuu)
    Button btn_tracuu;
    BookingPresenter mPresenterBooking;
    String sStatusPay = "";// 0: chua tt ;1 coc ; 2: da tt
    String sStatusBook = "";//1: duyet ok ;2 :lock ;3 huy
    private List<ObjBooking> mList;
    private AdapterItemDatphongAdmin adapter;
    @BindView(R.id.rcv_list_lichdatphong_admin)
    RecyclerView rcv_day_list;
    RecyclerView.LayoutManager mLayoutManager;
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
        View view = inflater.inflate(R.layout.fragment_lichdatnha_admin_home, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new MyHomePresenter(this);
        mPresenterBooking = new BookingPresenter(this);
        mListHome = new ArrayList<>();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        init();
        get_time();
        initData();
        // get_api_list_home();
        set_data_spinner();
        set_data_spinner_bookphong();
        set_data_spinner_thanhtoan();
        initEvent();
      //  showDialogLoading();
        get_api_tracuu();
        return view;
    }

    private void init() {
        mList = new ArrayList<>();
        //  mList.addAll(FragmentDatphong.mListBooking);
        adapter = new AdapterItemDatphongAdmin(mList, getContext());
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
                //   show_bottom_dialog();
                ObjBooking objBooking = mList.get(position);
                if (objBooking.getCONTENT() != null) {
                    show_bottom_dialog(mList.get(position));
                } else {
                    get_api_change_booking(objBooking.getID(), "3");
                }

            }

            @Override
            public void OnItemClickListener_Two_Btn(int position) {
                // đặt dọn phòng
                ObjBooking objBooking = mList.get(position);
                show_bottom_dialog(mList.get(position));
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

    private void get_api_list_home() {
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        if (sUserName != null) {
            mPresenter.api_get_mylist_room(sUserName, "");
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

    private void initEvent() {
        ll_date_end.setOnClickListener(this);
        ll_date_start.setOnClickListener(this);
        btn_tracuu.setOnClickListener(this);
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
                get_api_tracuu();
                break;
           /* case R.id.txt_dathanhtoan_dialog:

                break;
            case R.id.txt_dadatcoc_dialog:

                break;
            case R.id.txt_chuathanhtoan_dialog:

                break;
            case R.id.txt_huydatphong_dialog:

                break;
            case R.id.txt_dong_dialog:

                break;*/

        }
    }

    private void get_api_tracuu() {
        String sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        String sDateStart = edt_date_start.getText().toString();
        String sDateEnd = edt_date_end.getText().toString();
        mPresenterBooking.api_list_bookroom(sUser,
                "", "",
                "", sDateStart, sDateEnd,
                sStatusPay,
                sStatusBook,
                sGetLink);
    }

    private void initData() {
        data_Home = new ArrayList<>();
        data_trangthai_thanhtoan = new ArrayList<>();
        data_trangthai_bookphong = new ArrayList<>();
        // add trạng thái thanh toán
        data_trangthai_thanhtoan.add("- Tất cả - ");
        data_trangthai_thanhtoan.add("Chưa thanh toán");
        data_trangthai_thanhtoan.add("Đã đặt cọc");
        data_trangthai_thanhtoan.add("Đã thanh toán");
        //add trạng thái book phòng
        data_trangthai_bookphong.add("- Tất cả - ");
        data_trangthai_bookphong.add("Book thành công");
        data_trangthai_bookphong.add("Khóa nhà");
        data_trangthai_bookphong.add("Hủy book");

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

    List<String> data_trangthai_thanhtoan;
    @BindView(R.id.spinner_trangthai_thanhtoan)
    Spinner spinner_trangthai_thanhtoan;

    private void set_data_spinner_thanhtoan() {
        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.item_spinner, data_trangthai_thanhtoan);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_trangthai_thanhtoan.setAdapter(adapter);
        spinner_trangthai_thanhtoan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    sStatusPay = "";
                } else
                    sStatusPay = "" + (position - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    List<String> data_trangthai_bookphong;
    @BindView(R.id.spinner_trangthai_bookphong)
    Spinner spinner_trangthai_bookphong;

    private void set_data_spinner_bookphong() {
        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.item_spinner, data_trangthai_bookphong);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_trangthai_bookphong.setAdapter(adapter);
        spinner_trangthai_bookphong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // sStatusBook = "" + position;
                if (position == 0) {
                    sStatusBook = "";
                } else
                    sStatusBook = "" + (position - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void show_error_api(String sService) {

    }

    @Override
    public void show_lock_room(ObjErrorApi objError) {
        hideDialogLoading();
    }

    @Override
    public void show_list_bookroom(ListBookingResponse objRes) {
        hideDialogLoading();
        mList.clear();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            //Toast.makeText(getContext(), "" + objRes.getINFO().size(), Toast.LENGTH_SHORT).show();
            mList.addAll(objRes.getINFO());
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
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            Toast.makeText(getContext(), "Hủy book phòng thành công",
                    Toast.LENGTH_SHORT).show();
            get_api_tracuu();
        } else
            showAlertDialog("Thông báo", objError.getRESULT());
    }

    @Override
    public void show_booking_services(ObjErrorApi objError) {

    }

    @Override
    public void show_get_booking_services(ResponseListBookingService objRes) {

    }

    @Override
    public void show_change_billing(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            Toast.makeText(getContext(), "Thay đổi trạng thái thanh toán thành công",
                    Toast.LENGTH_SHORT).show();
            get_api_tracuu();
        } else
            showAlertDialog("Thông báo", objError.getRESULT());
    }

    @Override
    public void show_change_booking_services(ObjErrorApi objError) {

    }

    @SuppressLint("LongLogTag")
    @Override
    public void show_get_mylist_room(GetRoomResponse objRes) {
        hideDialogLoading();
        mListHome.clear();
        data_Home.clear();
        Log.e(TAG, "show_get_mylist_room: " + objRes.getINFO().size());
        if (objRes != null && objRes.getERROR().equals("0000")) {

            if (objRes.getINFO() != null) {
                for (ObjHomeStay objHomeStay : objRes.getINFO()) {
                    if (objHomeStay.getSTATE().equals("7")) {
                        mListHome.add(objHomeStay);
                    }
                }
                mListHome.add(0, new ObjHomeStay("- Tất cả -", ""));
                for (ObjHomeStay obj : mListHome) {
                    data_Home.add(obj.getNAME());
                }
            }
        } else {
            showAlertDialog("Thông báo", objRes.getRESULT());
        }
        // set_data_spinner();
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

    BottomSheetDialog mBottomSheetDialog;
    TextView txt_chuathanhtoan;
    TextView txt_dadatcoc;
    TextView txt_dathanhtoan;
    TextView txt_huydatphong;
    TextView txt_dong;

    private void show_bottom_dialog(ObjBooking objBooking) {
        mBottomSheetDialog = new BottomSheetDialog(getContext());
        View sheetView = getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_status_pay, null);
        mBottomSheetDialog.setContentView(sheetView);
        txt_chuathanhtoan = sheetView.findViewById(R.id.txt_menu_dialog_1);
        txt_dadatcoc = sheetView.findViewById(R.id.txt_menu_dialog_2);
        txt_dathanhtoan = sheetView.findViewById(R.id.txt_menu_dialog_3);
        txt_huydatphong = sheetView.findViewById(R.id.txt_menu_dialog_4);
        txt_dong = sheetView.findViewById(R.id.txt_dong_dialog);
        txt_chuathanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_api_change_billing(objBooking.getID(), "0");
                mBottomSheetDialog.dismiss();
            }
        });
        txt_dadatcoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_api_change_billing(objBooking.getID(), "1");
                mBottomSheetDialog.dismiss();
            }
        });
        txt_dathanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_api_change_billing(objBooking.getID(), "2");
                mBottomSheetDialog.dismiss();
            }
        });
        txt_huydatphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_api_change_booking(objBooking.getID(), "3");
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

    private void get_api_change_billing(String ID_BOOKROOM, String BILLING_STATUS) {
        showDialogComfirm("Thay đổi trạng thái thanh toán",
                "Bạn có chắc chắn muốn thay đổi trạng thái thanh toán không?", true,
                new ClickDialog() {
                    @Override
                    public void onClickYesDialog() {
                        showDialogLoading();
                        String sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                        mPresenterBooking.api_change_billing(sUsername, ID_BOOKROOM, BILLING_STATUS);
                    }

                    @Override
                    public void onClickNoDialog() {

                    }
                });

    }

    private void get_api_change_booking(String ID_BOOKROOM, String BOOKING_STATUS) {
        showDialogComfirm("Hủy đặt phòng",
                "Bạn có chắc chắn muốn hủy đơn đặt phòng này không?", true,
                new ClickDialog() {
                    @Override
                    public void onClickYesDialog() {
                        showDialogLoading();
                        String sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                        mPresenterBooking.api_change_booking(sUsername, ID_BOOKROOM, BOOKING_STATUS);
                    }

                    @Override
                    public void onClickNoDialog() {

                    }
                });

    }
}
