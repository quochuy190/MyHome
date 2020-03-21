package com.vn.myhome.fragment.myhome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.vn.myhome.R;
import com.vn.myhome.activity.myhome.ActivityNewRoom;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.models.ResponseApi.ResponListImageHome;
import com.vn.myhome.presenter.InterfaceMyHome;
import com.vn.myhome.presenter.MyHomePresenter;
import com.vn.myhome.untils.KeyboardUtil;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.StringUtil;
import com.vn.myhome.untils.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 22-April-2019
 * Time: 10:30
 * Version: 1.0
 */
public class FragmentPriceNewRoom extends BaseFragment implements InterfaceMyHome.View {
    private static final String TAG = "FragmentSetup";
    public static FragmentPriceNewRoom fragment;
    @BindView(R.id.edt_price)
    EditText edt_price;
    @BindView(R.id.edt_price_weekend)
    EditText edt_PriceSpecial;
    @BindView(R.id.edt_clear_room)
    EditText edt_clear_room;
    @BindView(R.id.edt_number_guest)
    EditText edt_number_guest;
    @BindView(R.id.edt_max_guest)
    EditText edt_max_guest;
    @BindView(R.id.edt_price_add_number)
    EditText edt_price_add_number;
    @BindView(R.id.btn_update)
    Button btn_update;
    @BindView(R.id.switch_set_pass)
    SwitchCompat switch_set_pass;
    @BindView(R.id.ll_discount)
    ConstraintLayout ll_discount;
    @BindView(R.id.txt_discount_value)
    TextView txt_discount_value;
    @BindView(R.id.txt_percent_discount_value)
    TextView txt_percent_discount_value;
    @BindView(R.id.txt_starttime_discount_value)
    TextView txt_starttime_discount_value;
    @BindView(R.id.txt_endtime_discount_value)
    TextView txt_endtime_discount_value;

    MyHomePresenter mPresenter;
    String sUsername = "", sName = "", sAddress = "", sPrice = "", sPriceSpecial = "", sPriceExtra = "", sMaxGuest = "",
            sMaxRoom = "", sMaxBed = "", sClean_Room = "", sDesCription = "", sInformation = "", sPolicy_cancle = "",
            sGetlink = "", sProvinceId = "", sLocationId = "", sCover = "", sMaxGuest_Exst = "";

    public static FragmentPriceNewRoom getInstance() {
        if (fragment == null) {
            synchronized (FragmentPriceNewRoom.class) {
                if (fragment == null)
                    fragment = new FragmentPriceNewRoom();
            }
        }
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_price_new_room, container, false);
        ButterKnife.bind(this, view);
        Log.e(TAG, "onCreateView: Setup");
        mPresenter = new MyHomePresenter(this);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initData();
        initEvent();
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.KEY_UPDATE_MYHOME)) {
            get_api();
        }
    }

    private void initEvent() {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLoading();
                check_get_api();
                EventBus.getDefault().post(new MessageEvent(Constants.EventBus.KEY_UPDATE_MYHOME, 1, 0));
            }
        });
    }

    private void check_get_api() {
        if (edt_price.getText().toString().length() == 0) {
            hideDialogLoading();
            showAlertDialog("Thông báo", "Mời bạn nhập vào giá nhà.");
            KeyboardUtil.requestKeyboard(edt_price);
            return;
        }
        if (edt_PriceSpecial.getText().toString().length() == 0) {
            hideDialogLoading();
            showAlertDialog("Thông báo", "Mời bạn nhập vào giá nhà cuối tuần.");
            KeyboardUtil.requestKeyboard(edt_PriceSpecial);
            return;
        }
        if (edt_clear_room.getText().toString().length() == 0) {
            hideDialogLoading();
            showAlertDialog("Thông báo", "Mời bạn nhập vào giá dọn dẹp.");
            KeyboardUtil.requestKeyboard(edt_clear_room);
            return;
        }
        if (edt_number_guest.getText().toString().length() == 0) {
            hideDialogLoading();
            showAlertDialog("Thông báo", "Mời bạn nhập vào số khách tiêu chuẩn.");
            KeyboardUtil.requestKeyboard(edt_number_guest);
            return;
        }
        if (edt_max_guest.getText().toString().length() == 0) {
            hideDialogLoading();
            showAlertDialog("Thông báo", "Mời bạn nhập vào số khách tiêu chuẩn.");
            KeyboardUtil.requestKeyboard(edt_max_guest);
            return;
        }
        if (edt_price_add_number.getText().toString().length() == 0) {
            hideDialogLoading();
            showAlertDialog("Thông báo", "Mời bạn nhập vào số khách tiêu chuẩn.");
            KeyboardUtil.requestKeyboard(edt_price_add_number);
            return;
        }
        get_api();
    }

    private void get_api() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                sPrice = edt_price.getText().toString().trim().replaceAll(",", "")
                        .replaceAll("\\.", "");
                sPriceSpecial = edt_PriceSpecial.getText().toString().trim()
                        .replaceAll(",", "").replaceAll("\\.", "");
                sClean_Room = edt_clear_room.getText().toString().trim()
                        .replaceAll(",", "").replaceAll("\\.", "");
                sMaxGuest = edt_number_guest.getText().toString();
                sMaxGuest_Exst = edt_max_guest.getText().toString();
                sPriceExtra = edt_price_add_number.getText().toString().trim()
                        .replaceAll(",", "").replaceAll("\\.", "");
                if (switch_set_pass.isChecked()) {
                    sPolicy_cancle = "1";
                } else
                    sPolicy_cancle = "0";
                mPresenter.api_edit_room(sUsername, sName, sAddress, sPrice, sPriceSpecial, sPriceExtra, sMaxGuest,
                        sMaxRoom, sMaxBed, sClean_Room, sDesCription, sInformation, sPolicy_cancle, sGetlink,
                        sProvinceId, sLocationId, sCover, sMaxGuest_Exst);
            }
        }, 200);

    }

    private void initData() {
        if (ActivityNewRoom.objMyhome != null) {
            setDataMyHome(ActivityNewRoom.objMyhome);
        } else {
            sGetlink = ActivityNewRoom.sGetlink;
        }
    }

    private void setDataMyHome(ObjHomeStay objMyhome) {
        try {
            if (objMyhome.getPOLICY_CANCLE().equals("1")) {
                switch_set_pass.setChecked(true);
            } else
                switch_set_pass.setChecked(false);
            if (objMyhome.getPRICE() != null)
                edt_price.setText(StringUtil.formatNumber(objMyhome.getPRICE()));
            if (objMyhome.getPRICE_SPECIAL() != null)
                edt_PriceSpecial.setText(StringUtil.formatNumber(objMyhome.getPRICE_SPECIAL()));
            if (objMyhome.getCLEAN_ROOM() != null)
                edt_clear_room.setText(StringUtil.formatNumber(objMyhome.getCLEAN_ROOM()));
            if (objMyhome.getMAX_GUEST() != null)
                edt_number_guest.setText(objMyhome.getMAX_GUEST());
            if (objMyhome.getMAX_GUEST_EXIST() != null)
                edt_max_guest.setText(objMyhome.getMAX_GUEST_EXIST());
            if (objMyhome.getPRICE_EXTRA() != null)
                edt_price_add_number.setText(StringUtil.formatNumber(objMyhome.getPRICE_EXTRA()));
            sGetlink = objMyhome.getGENLINK();
            if (objMyhome.getDISCOUNT() != null && objMyhome.getDISCOUNT().length() > 0) {
                txt_discount_value.setText(StringUtil.conventMonney_Long(objMyhome.getDISCOUNT()));
                ll_discount.setVisibility(View.VISIBLE);
            } else ll_discount.setVisibility(View.GONE);
            if (objMyhome.getPERCENT() != null && objMyhome.getPERCENT().length() > 0) {
                txt_percent_discount_value.setText(objMyhome.getPERCENT() + "%");
            }
            if (objMyhome.getPROMO_ST_TIME() != null && objMyhome.getPROMO_ED_TIME() != null) {
                txt_starttime_discount_value.setText(TimeUtils.convent_date(objMyhome.getPROMO_ST_TIME(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                        "dd/MM/yyyy"));
                txt_endtime_discount_value.setText(TimeUtils.convent_date(objMyhome.getPROMO_ED_TIME(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                        "dd/MM/yyyy"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void show_error_api(String sService) {

    }

    @Override
    public void show_get_mylist_room(GetRoomResponse objRes) {

    }

    @Override
    public void show_room_detail(ObjHomeStay objRes) {

    }

    @Override
    public void show_new_room(ObjErrorApi objError) {

    }

    @Override
    public void show_edit_room(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            Toast.makeText(getActivity(), "Cập nhật nhà thành công.", Toast.LENGTH_SHORT).show();
            ActivityNewRoom.isUpdate = true;
            getActivity().setResult(RESULT_OK, new Intent());
            getActivity().finish();
        }
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
}
