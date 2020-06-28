package com.vn.myhome.activity.book_room;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vn.myhome.R;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.config.Config;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ResponseApi.ListBookingResponse;
import com.vn.myhome.models.ResponseApi.ResponseListBookingService;
import com.vn.myhome.presenter.BookingPresenter;
import com.vn.myhome.presenter.InterfaceBooking;
import com.vn.myhome.untils.KeyboardUtil;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.StringUtil;
import com.vn.myhome.untils.TimeUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 16-October-2019
 * Time: 16:00
 * Version: 1.0
 */
public class ActivityBooking extends BaseActivity implements InterfaceBooking.View {
    ObjHomeStay objHomeStay;
    String sStartDay;
    String sEndDay;
    @BindView(R.id.img_cover)
    ImageView img_cover;
    @BindView(R.id.txt_name_home)
    TextView txt_name_home;
    @BindView(R.id.txt_address_home)
    TextView txt_address_home;
    @BindView(R.id.txt_date_nhanphong)
    TextView txt_date_nhanphong;
    @BindView(R.id.txt_date_traphong)
    TextView txt_date_traphong;
    @BindView(R.id.txt_total_date)
    TextView txt_total_date;
    @BindView(R.id.txt_total)
    TextView txt_total;
    @BindView(R.id.txt_title_price)
    TextView txt_title_price;
    @BindView(R.id.txt_total_price)
    TextView txt_total_price;
    @BindView(R.id.txt_total_price_ngaythuong)
    TextView txt_total_price_ngaythuong;
    @BindView(R.id.txt_total_price_cuoituan)
    TextView txt_total_price_cuoituan;
    @BindView(R.id.txt_price_clearroom)
    TextView txt_price_clearroom;
    @BindView(R.id.txt_phikhac)
    TextView txt_phikhac;
    @BindView(R.id.txt_price_themnguoi)
    TextView txt_price_themnguoi;
    @BindView(R.id.edt_num_guest)
    TextView edt_num_guest;
    @BindView(R.id.txt_add)
    TextView txt_add;
    @BindView(R.id.txt_minus)
    TextView txt_minus;
    @BindView(R.id.btn_next)
    TextView btn_next;
    @BindView(R.id.txt_title_price_ngaythuong_discount)
    TextView txt_title_price_ngaythuong_discount;
    @BindView(R.id.txt_price_ngaythuong_discount)
    TextView txt_price_ngaythuong_discount;
    @BindView(R.id.txt_title_price_cuoituan_discount)
    TextView txt_title_price_cuoituan_discount;
    @BindView(R.id.txt_price_cuoituan_discount)
    TextView txt_price_cuoituan_discount;
    @BindView(R.id.edt_fulname)
    EditText edt_fulname;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.edt_birthday)
    EditText edt_birthday;
    @BindView(R.id.edt_phone)
    EditText edt_phone;
    @BindView(R.id.edt_cmnd)
    EditText edt_cmnd;
    @BindView(R.id.edt_address)
    EditText edt_address;
    @BindView(R.id.img_date)
    ImageView img_date;
    BookingPresenter mPresenterBooking;
    String sName = "", sEMAIL = "", sMOBILE = "", sBIRTHDAY = "", sNUM_OF_GUEST = "", sPRICE_TOTAL_NIGHT = "",
            sCLEAN_FEE = "", sADD_GUEST_FEE = "", sCMT = "", sADDRESS = "";

    @Override
    public int setContentViewId() {
        return R.layout.activity_booking;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenterBooking = new BookingPresenter(this);
        initAppbar();
        initData();
        initEvent();
    }

    private void initAppbar() {
        ImageView img_back = findViewById(R.id.btn_back);
        TextView txt_title = findViewById(R.id.txt_title);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent());
                finish();
            }
        });
        txt_title.setText("ĐẶT PHÒNG");
    }

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener to_date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTodate();
        }
    };

    private void updateTodate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date strDate = myCalendar.getTime();
        Date strDateStart = myCalendar.getTime();
        if (strDate.getTime() < System.currentTimeMillis()) {
            if (strDate.getTime() >= strDateStart.getTime()) {
                edt_birthday.setText(sdf.format(myCalendar.getTime()));
            } else
                showAlertDialog("Thông báo", "Thời gian không hợp lệ, mời chọn lại ");
        } else {
            showAlertDialog("Thông báo", "Thời gian không hợp lệ, mời chọn lại ");
        }
    }

    private void initEvent() {
        img_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ActivityBooking.this, R.style.MyDatePickerStyle, to_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        edt_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ActivityBooking.this, R.style.MyDatePickerStyle, to_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booking();
            }
        });

        txt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int guest = Integer.parseInt(edt_num_guest.getText().toString());
                guest++;
                int max = Integer.parseInt(objHomeStay.getMAX_GUEST_EXIST());
                if (guest >max){
                    showDialogNotify("Thông báo", "Đã đủ số khách tối đa.");
                }else {
                    edt_num_guest.setText("" + guest);
                    set_price_total();
                }

            }
        });
        txt_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int guest = Integer.parseInt(edt_num_guest.getText().toString());
                if (guest > 1) {
                    guest--;
                    edt_num_guest.setText("" + guest);
                } else {
                    guest = 1;
                    edt_num_guest.setText("" + guest);
                }
                set_price_total();

            }
        });
    }

    private void booking() {
        if (edt_fulname.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào tên đăng nhập.");
            KeyboardUtil.requestKeyboard(edt_fulname);
            return;
        } else {
            sName = edt_fulname.getText().toString().trim();
        }
        if (edt_email.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào tên đăng nhập.");
            KeyboardUtil.requestKeyboard(edt_email);
            return;
        } else {
            sEMAIL = edt_email.getText().toString().trim();
        }
        if (edt_phone.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào tên đăng nhập.");
            KeyboardUtil.requestKeyboard(edt_phone);
            return;
        } else {
            sMOBILE = edt_email.getText().toString().trim();
        }
        if (edt_birthday.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào tên đăng nhập.");
            KeyboardUtil.requestKeyboard(edt_birthday);
            return;
        } else {
            sBIRTHDAY = edt_birthday.getText().toString().trim();
        }
        if (edt_cmnd.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào tên đăng nhập.");
            KeyboardUtil.requestKeyboard(edt_cmnd);
            return;
        } else {
            sCMT = edt_cmnd.getText().toString().trim();
        }
        if (edt_address.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào tên đăng nhập.");
            KeyboardUtil.requestKeyboard(edt_address);
            return;
        } else {
            sADDRESS = edt_address.getText().toString().trim();
        }
        get_api();
    }

    private void get_api() {
        showDialogLoading();
        String check_in = TimeUtils.convent_date(sStartDay, "EEEE dd-MMM-yyyy",
                "dd/MM/yyyy");
        String check_out = TimeUtils.convent_date(sEndDay, "EEEE dd-MMM-yyyy",
                "dd/MM/yyyy");
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenterBooking.api_booking(sUserName, objHomeStay.getGENLINK(), check_in, check_out,
                "" + price_total,
                "", sName, sEMAIL, sMOBILE, sBIRTHDAY, sNUM_OF_GUEST, "" + price_room,
                "" + price_clear,
                "" + price_max_guest, sCMT, sADDRESS);
    }

    private void initData() {
        objHomeStay = (ObjHomeStay) getIntent().getSerializableExtra(Constants.KEY_SEND_ROOM_BOOKING);
        sStartDay = getIntent().getStringExtra(Constants.KEY_SEND_STARTDAY_BOOKING);
        sEndDay = getIntent().getStringExtra(Constants.KEY_SEND_ENDDAY_BOOKING);
        if (sStartDay != null) {
            txt_date_nhanphong.setText(sStartDay);
        }
        if (sEndDay != null) {
            txt_date_traphong.setText(sEndDay);
        }
        set_price_total();

        if (objHomeStay != null) {
            if (objHomeStay.getIMG() != null && objHomeStay.getIMG().length() > 0) {
                try {
                    String sUrl = Config.BASE_URL_MEDIA + objHomeStay.getIMG();
                    URL url = new URL(sUrl);
                    URI urlinfo = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
                            url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                    url = urlinfo.toURL();
                    RequestOptions options = new RequestOptions()
                            .placeholder(R.drawable.img_defaul)
                            .error(R.drawable.img_defaul);
                    Glide.with(this).load(url).apply(options).into(img_cover);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else
                Glide.with(this).load(R.drawable.img_defaul).into(img_cover);
            if (objHomeStay.getNAME() != null) {
                txt_name_home.setText(objHomeStay.getNAME());
            }
            if (objHomeStay.getADDRESS() != null) {
                txt_address_home.setText(objHomeStay.getADDRESS());
            }
        }

    }

    long iPrice_special = 0;
    long price_thuong = 0;
    long price_room = 0;
    long price_clear = 0;
    long price_max_guest = 0;
    long price_phikhac = 0;
    long price_total = 0;

    private void set_price_total() {
        if (sEndDay != null && sStartDay != null) {
            List<Date> lisDate = TimeUtils.get_list_date(sStartDay, sEndDay);
            if (lisDate.size() > 0) {
                txt_total_date.setText(lisDate.size() + " Ngày " + (lisDate.size() - 1) + " Đêm");
                int iWeekend = 0;
                int iWeekend_discount = 0;
                int iDay_discount = 0;
                int iNoWeekend = 0;
                price_thuong =0;
                iPrice_special = 0;
                price_room = 0;
                price_clear=0;
                price_max_guest=0;
                price_phikhac=0;
                price_total=0;
                for (int i = 0; i < lisDate.size() - 1; i++) {
                    if (TimeUtils.check_weekend(lisDate.get(i))) {
                        if (TimeUtils.check_discount(lisDate.get(i), TimeUtils.convent_date(objHomeStay.getPROMO_ST_TIME(),
                                "yyyy-MM-dd'T'HH:mm:ss.'000Z'", "EEEE dd-MMM-yyyy"),
                                TimeUtils.convent_date(objHomeStay.getPROMO_ED_TIME(), "yyyy-MM-dd'T'HH:mm:ss.'000Z'", "EEEE dd-MMM-yyyy"))) {
                            iWeekend_discount++;
                        } else
                            iWeekend++;
                    } else {
                        if (TimeUtils.check_discount(lisDate.get(i), TimeUtils.convent_date(objHomeStay.getPROMO_ST_TIME(),
                                "yyyy-MM-dd'T'HH:mm:ss.'000Z'", "EEEE dd-MMM-yyyy"),
                                TimeUtils.convent_date(objHomeStay.getPROMO_ED_TIME(), "yyyy-MM-dd'T'HH:mm:ss.'000Z'", "EEEE dd-MMM-yyyy"))) {
                            iDay_discount++;
                        } else
                            iNoWeekend++;
                    }
                }

                if (iWeekend > 0) {
                    iPrice_special = iWeekend * (Long.parseLong(objHomeStay.getPRICE_SPECIAL()));
                }
                if (iNoWeekend > 0) {
                    price_thuong = iNoWeekend * (Long.parseLong(objHomeStay.getPRICE()));
                }
                if (iWeekend_discount > 0) {
                    long price = Long.parseLong(objHomeStay.getPRICE_SPECIAL()) -
                            (Integer.parseInt(objHomeStay.getPERCENT()) * Long.parseLong(objHomeStay.getPRICE_SPECIAL()) / 100);
                    iPrice_special = iPrice_special + (iWeekend_discount * price);
                    txt_title_price_cuoituan_discount.setVisibility(View.VISIBLE);
                    txt_price_cuoituan_discount.setText(iWeekend_discount + " * " + StringUtil.conventMonney_Long(price + ""));
                } else {
                    txt_title_price_cuoituan_discount.setVisibility(View.GONE);
                }
                if (iDay_discount > 0) {
                    long price = Long.parseLong(objHomeStay.getPRICE()) -
                            Long.parseLong(objHomeStay.getDISCOUNT());
                    txt_title_price_ngaythuong_discount.setVisibility(View.VISIBLE);
                    price_thuong = price_thuong+(iDay_discount * price);
                    txt_price_ngaythuong_discount.setText(iDay_discount + " * " + StringUtil.conventMonney_Long(price + ""));
                } else
                    txt_title_price_ngaythuong_discount.setVisibility(View.GONE);
                price_room = iPrice_special + price_thuong;
                txt_title_price.setText("Giá phòng (" + (lisDate.size() - 1) + ") đêm");
                txt_total_price.setText(StringUtil.conventMonney_Long("" + (iPrice_special + price_thuong)));
                txt_total_price_cuoituan.setText(iWeekend + " * " + StringUtil.conventMonney_Long(objHomeStay.getPRICE_SPECIAL()));
                txt_total_price_ngaythuong.setText(iNoWeekend + " * " + StringUtil.conventMonney_Long(objHomeStay.getPRICE()));
                int total_day = iWeekend + iNoWeekend+iDay_discount+iWeekend_discount;
                if (total_day>0&&total_day<=3){
                    price_clear = (1) * Long.parseLong(objHomeStay.getCLEAN_ROOM());
                    txt_price_clearroom.setText((1) + " * " + StringUtil.conventMonney_Long(objHomeStay.getCLEAN_ROOM()));
                }else if (total_day>3&&total_day<=6){
                    price_clear = (2) * Long.parseLong(objHomeStay.getCLEAN_ROOM());
                    txt_price_clearroom.setText((2) + " * " + StringUtil.conventMonney_Long(objHomeStay.getCLEAN_ROOM()));
                }else if (total_day>6){
                    price_clear = (3) * Long.parseLong(objHomeStay.getCLEAN_ROOM());
                    txt_price_clearroom.setText((3) + " * " + StringUtil.conventMonney_Long(objHomeStay.getCLEAN_ROOM()));
                }
                txt_phikhac.setText(StringUtil.conventMonney_Long(price_clear + ""));

                int iGuest = Integer.parseInt(objHomeStay.getMAX_GUEST());
                int maxGuest = Integer.parseInt(edt_num_guest.getText().toString());
                if (maxGuest > iGuest) {
                    price_max_guest = (maxGuest - iGuest) * Long.parseLong(objHomeStay.getPRICE_EXTRA());
                    txt_price_themnguoi.setText((maxGuest - iGuest) + " * " + StringUtil.conventMonney_Long(objHomeStay.getPRICE_EXTRA()));
                } else
                    txt_price_themnguoi.setText("0 * " + StringUtil.conventMonney_Long(objHomeStay.getPRICE_EXTRA()));
                txt_phikhac.setText(StringUtil.conventMonney_Long((price_clear + price_max_guest) + ""));
                price_phikhac = price_clear + price_max_guest;
                txt_total.setText(StringUtil.conventMonney_Long((price_room + price_phikhac) + ""));
                price_total = price_phikhac + price_room;
            }
        }
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
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            setResult(RESULT_OK, new Intent());
            Toast.makeText(this, "Đặt nhà thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ActivityBooking.this, ActivityThongtinChuyenkhoan.class);
            intent.putExtra(Constants.KEY_SEND_PRICE_THANHTOAN, "" + price_total);
            intent.putExtra(Constants.KEY_SEND_CONTENT_THANHTOAN, objError.getCONTENT());
            startActivityForResult(intent, Constants.RequestCode.GET_BOOKING);
            finish();
        } else {
            showDialogNotify("Thông báo", objError.getRESULT());
        }
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
