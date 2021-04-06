package com.vn.myhome.activity.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.guilhe.views.SeekBarRangedView;
import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.activity.login.ActivityListCity;
import com.vn.myhome.activity.login.InterfaceLogin;
import com.vn.myhome.adapter.AdapterHomeStay;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ResponseApi.CityResponse;
import com.vn.myhome.models.ResponseApi.GetTypeResponse;
import com.vn.myhome.untils.KeyboardUtil;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 10/16/2017.
 */

public class ActivityListHomeStaySearch extends BaseActivity
        implements InterfaceLogin.View, View.OnClickListener {

    private List<ObjHomeStay> mLisHomeStay;
    public AdapterHomeStay adapter;
    @BindView(R.id.recycle_list_service)
    RecyclerView recycle_service;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.edt_search_appbar)
    EditText edt_search_service;
    @BindView(R.id.img_back)
    ImageView img_back;
    private List<ObjHomeStay> tempRoom;
    String sUserId;
    @BindView(R.id.img_search)
    ImageView img_search;
    @BindView(R.id.edt_name_city)
    EditText edt_name_city;
    @BindView(R.id.ll_city)
    ConstraintLayout ll_city;
    Calendar myCalendar_start = Calendar.getInstance();
    Calendar myCalendar_end = Calendar.getInstance();
    ListHomeSearchPresenter mPresenter;
    String LOCATION = "", CHECKIN = "", CHECKOUT = "", PEOPLE = "", PRICE_FROM = "",
            PRICE_TO = "", AMENITIES = "", ID_PROVINCE = "", NAME_PROVINCE = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.fragment_service);
        ButterKnife.bind(this);
        KeyboardUtil.hideSoftKeyboard(this);
        mPresenter = new ListHomeSearchPresenter(this);
        img_search.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.ic_filter).into(img_search);
        //initAppbar();
        init();
        initSeekbar();
        get_intent();
       // initData();
        initEvent();
    }

    private void get_intent() {
        NAME_PROVINCE = getIntent().getStringExtra(Constants.KEY_SEND_NAME_PROVINCE);
        ID_PROVINCE = getIntent().getStringExtra(Constants.KEY_SEND_ID_PROVINCE);
        if (NAME_PROVINCE != null && NAME_PROVINCE.length() > 0)
            edt_name_city.setText(NAME_PROVINCE);
        if (ID_PROVINCE != null && ID_PROVINCE.equals("home")) {
            ll_dialog.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_recycleview;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initEvent() {
        btn_search.setOnClickListener(this);
        ll_city.setOnClickListener(this);
        ll_date_end.setOnClickListener(this);
        ll_date_start.setOnClickListener(this);
        ll_dialog.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
        img_search.setOnClickListener(this);
        img_back.setOnClickListener(this);
        edt_date_start.setOnClickListener(this);
        edt_date_end.setOnClickListener(this);
        edt_search_service.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_date_start:
                new DatePickerDialog(this, R.style.MyDatePickerStyle, start_date, myCalendar_start
                        .get(Calendar.YEAR), myCalendar_start.get(Calendar.MONTH),
                        myCalendar_start.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.ll_date_end:
                new DatePickerDialog(this, R.style.MyDatePickerStyle, end_date, myCalendar_end
                        .get(Calendar.YEAR), myCalendar_end.get(Calendar.MONTH),
                        myCalendar_end.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.edt_date_start:
                new DatePickerDialog(this, R.style.MyDatePickerStyle, start_date, myCalendar_start
                        .get(Calendar.YEAR), myCalendar_start.get(Calendar.MONTH),
                        myCalendar_start.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.edt_date_end:
                new DatePickerDialog(this, R.style.MyDatePickerStyle, end_date, myCalendar_end
                        .get(Calendar.YEAR), myCalendar_end.get(Calendar.MONTH),
                        myCalendar_end.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.btn_search:
                get_api_search();
                break;
            case R.id.btn_exit:
                ll_dialog.setVisibility(View.GONE);
                break;
            case R.id.img_search:
                ll_dialog.setVisibility(View.VISIBLE);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_dialog:

                break;
            case R.id.ll_city:
                Intent intent_city = new Intent(ActivityListHomeStaySearch.this, ActivityListCity.class);
                startActivityForResult(intent_city, Constants.RequestCode.GET_CITY);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_CITY:
                if (resultCode == RESULT_OK) {
                    edt_name_city.setText(App.mCity.getNAME());
                    ID_PROVINCE = App.mCity.getMATP();
                }
                break;
        }
    }

    void filter(String text) {
        tempRoom.clear();
        for (ObjHomeStay d : mLisHomeStay) {
            String sName = StringUtil.removeAccent(d.getNAME().toLowerCase());
            String sInput = StringUtil.removeAccent(text.toLowerCase());
            if (sName.contains(sInput)) {
                //adding the element to filtered list
                tempRoom.add(d);
            }
        }
        adapter.setData(tempRoom, this);
        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                Intent intent = new Intent(ActivityListHomeStaySearch.this, ActivityRoomDetail.class);
                ObjHomeStay obj = (ObjHomeStay) item;
                intent.putExtra(Constants.KEY_SEND_ROOM_DETAIL, obj);
                startActivity(intent);
            }
        });
    }

    private void init() {
        edt_search_service.setHint("Nhập vào tên nhà");
        mLisHomeStay = new ArrayList<>();
        tempRoom = new ArrayList<>();
        adapter = new AdapterHomeStay(this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycle_service.setNestedScrollingEnabled(false);
        recycle_service.setHasFixedSize(true);
        recycle_service.setLayoutManager(mLayoutManager);
        recycle_service.setItemAnimator(new DefaultItemAnimator());
        recycle_service.setAdapter(adapter);
        adapter.setData(tempRoom, this);
      /*  adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                setResult(RESULT_OK, new Intent());
                App.mCity = (City) item;
            }
        });*/
        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                Intent intent = new Intent(ActivityListHomeStaySearch.this, ActivityRoomDetail.class);
                ObjHomeStay obj = (ObjHomeStay) item;
                intent.putExtra(Constants.KEY_SEND_ROOM_DETAIL, obj);
                startActivity(intent);
            }
        });

    }

    public void updateData(List<ObjHomeStay> mList) {
        mLisHomeStay.clear();
        tempRoom.clear();
        mLisHomeStay.addAll(mList);
        tempRoom.addAll(mList);
        adapter.setData(mList, this);
    }

    private void initData() {
        if (ID_PROVINCE != null && ID_PROVINCE.equals("home")) {
            ID_PROVINCE="";
        }
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenter.get_api_search_home(sUserName, LOCATION, CHECKIN,
                CHECKOUT, PEOPLE, PRICE_FROM, PRICE_TO, "", ID_PROVINCE);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void show_error_api(String sService) {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_login(ObjLogin objLogin) {

    }

    @Override
    public void show_reg_user(ObjErrorApi objError) {

    }

    @Override
    public void show_get_type(GetTypeResponse objRes) {

    }

    @Override
    public void show_get_city(CityResponse objResCity) {
        hideDialogLoading();
    }

    @Override
    public void show_update_device(ObjErrorApi objError) {

    }

    @BindView(R.id.edt_name_homestay)
    EditText edt_name_homestay;
    @BindView(R.id.edt_quantity)
    EditText edt_quantity;
    @BindView(R.id.ll_date_start)
    ConstraintLayout ll_date_start;
    @BindView(R.id.ll_date_end)
    ConstraintLayout ll_date_end;
    @BindView(R.id.edt_date_start)
    EditText edt_date_start;
    @BindView(R.id.edt_date_end)
    EditText edt_date_end;
    @BindView(R.id.btn_search)
    Button btn_search;
    @BindView(R.id.btn_exit)
    Button btn_exit;
    @BindView(R.id.txt_price_start)
    TextView txt_price_start;
    @BindView(R.id.txt_price_end)
    TextView txt_price_end;
    @BindView(R.id.seekbar_price)
    SeekBarRangedView seekbar_price;
    @BindView(R.id.ll_dialog)
    ConstraintLayout ll_dialog;

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

    long price_start = 0;
    long price_end = 150;

    public void showDialog_Filter() {
        ll_dialog.setVisibility(View.VISIBLE);
    }

    private void initSeekbar() {
        seekbar_price.setProgressColor(getResources().getColor(R.color.app_main));
        seekbar_price.setOnSeekBarRangedChangeListener(new SeekBarRangedView.OnSeekBarRangedChangeListener() {
            @Override
            public void onChanged(SeekBarRangedView view, float minValue, float maxValue) {
                //  Toast.makeText(getContext(), "" + minValue + "và" + maxValue, Toast.LENGTH_SHORT).show();
                price_start = (long) minValue;
                price_end = (long) maxValue;
                set_price();
            }

            @Override
            public void onChanging(SeekBarRangedView view, float minValue, float maxValue) {

            }
        });
    }

    private void set_price() {
        price_start = price_start * 100000;
        txt_price_start.setText(StringUtil.conventMonney_Long("" + price_start));
        price_end = price_end * 100000;
        txt_price_end.setText(StringUtil.conventMonney_Long("" + price_end));
    }


    private void get_api_search() {
      /*  LOCATION, CHECKIN,
                CHECKOUT, PEOPLE, PRICE_FROM, PRICE_TO, "", ID_PROVINCE*/

        LOCATION = edt_name_homestay.getText().toString();
        CHECKIN = edt_date_start.getText().toString();
        CHECKOUT = edt_date_end.getText().toString();
        PEOPLE = edt_quantity.getText().toString();
        PRICE_FROM = txt_price_start.getText().toString();
        PRICE_FROM = PRICE_FROM.replaceAll(",", "").replaceAll("\\.", "");
        PRICE_FROM = PRICE_FROM.replaceAll("VND", "");
        PRICE_TO = txt_price_end.getText().toString().trim();
        PRICE_TO = PRICE_TO.replaceAll(",", "").replaceAll("\\.", "");
        PRICE_TO = PRICE_TO.replaceAll("VND", "");
       // ID_PROVINCE =
        ll_dialog.setVisibility(View.GONE);
        initData();
    }
}
