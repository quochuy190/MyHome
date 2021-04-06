package com.vn.myhome.activity.user_manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.activity.login.InterfaceLogin;
import com.vn.myhome.activity.login.PresenterLogin;
import com.vn.myhome.activity.login.RegisterActivity;
import com.vn.myhome.adapter.AdapterListUser;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.fragment.tabCheckinCheckout.PresenterTabCheckinCheckout;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ObjTypeUser;
import com.vn.myhome.models.ResponseApi.CityResponse;
import com.vn.myhome.models.ResponseApi.GetTypeResponse;
import com.vn.myhome.models.ResponseApi.ResponInfo;
import com.vn.myhome.presenter.UserPresenter;
import com.vn.myhome.untils.KeyboardUtil;
import com.vn.myhome.untils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-December-2019
 * Time: 10:31
 * Version: 1.0
 */
public class ActivityListUserCheckin extends BaseActivity implements InterfaceUser.View, InterfaceLogin.View {
    UserPresenter mPresenter;
    private List<ObjLogin> mList;
    private AdapterListUser adapter;
    @BindView(R.id.rcv_list_user)
    RecyclerView rcv_list_user;
    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_phone)
    EditText edt_phone;
    @BindView(R.id.btn_tracuu)
    Button btn_tracuu;    @BindView(R.id.txt_data_empty)
    TextView txtDataEmpty;
    @BindView(R.id.view_search_user)
    ConstraintLayout viewSearchUser;
    RecyclerView.LayoutManager mLayoutManager;
    String USER_TYPE = "5", sSTATE = "", sFullName = "", sPhone = "";
    PresenterLogin mPresenterLogin;
    private String mContent ="", mGetLink ="";
    PresenterTabCheckinCheckout mPresenterTabCheckinCheckout;
    @Override
    public int setContentViewId() {
        return R.layout.activity_list_user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new UserPresenter(this);
        mPresenterLogin = new PresenterLogin(this);
        mPresenterTabCheckinCheckout = new PresenterTabCheckinCheckout(this);
        KeyboardUtil.hideSoftKeyboard(this);
        viewSearchUser.setVisibility(View.GONE);
        init();
        initAppbar();
        // set_class_user_spinner();
        //   set_status_user_spinner();
        initData();
        initEvent();
    }

    TextView txt_title;
    @BindView(R.id.img_home)
    ImageView img_home;

    private void initAppbar() {
        ImageView img_back = findViewById(R.id.btn_back);
        txt_title = findViewById(R.id.txt_title);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_title.setText("DANH SÁCH TK CHECKIN");
        img_home.setVisibility(View.INVISIBLE);
        img_home.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityListUserCheckin.this, RegisterActivity.class);
                intent.putExtra(Constants.KEY_IS_REGISTER_ADMIN, true);
                startActivityForResult(intent, Constants.RequestCode.START_REGISTER_ADMIN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestCode.START_REGISTER_ADMIN) {
            if (resultCode == RESULT_OK) {
                initData();
            }
        }
        if (requestCode == Constants.RequestCode.GET_MY_HOME) {
            if (resultCode == RESULT_OK) {
                initData();
            }
        }
    }

    private void initEvent() {
        btn_tracuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardUtil.hideSoftKeyboard(ActivityListUserCheckin.this);
                initData();
            }
        });
    }

    private void initData() {
        mContent = getIntent().getStringExtra(Constants.KEY_SEND_CONTENT_BOOK_SERVICE);
        mGetLink = getIntent().getStringExtra(Constants.KEY_SEND_GETLINK);
        txtDataEmpty.setVisibility(View.GONE);
        showDialogLoading();
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        if (edt_name.getText().toString().length() > 0) {
            sFullName = edt_name.getText().toString();
        } else {
            sFullName = "";
        }
        if (edt_phone.getText().toString().length() > 0) {
            sPhone = edt_phone.getText().toString();
        } else {
            sPhone = "";
        }
        mPresenter.api_get_listuser(sUserName, "", sPhone, "",
                sFullName, USER_TYPE, sSTATE, "",mGetLink,"1", "100");
        //  mPresenterLogin.api_get_type(sUserName);
    }

    private void init() {
        mList = new ArrayList<>();
        adapter = new AdapterListUser(mList, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        rcv_list_user.setNestedScrollingEnabled(false);
        rcv_list_user.setHasFixedSize(true);
        rcv_list_user.setLayoutManager(mLayoutManager);
        rcv_list_user.setItemAnimator(new DefaultItemAnimator());
        rcv_list_user.setAdapter(adapter);

        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                ObjLogin obj = (ObjLogin) item;
                showDialogComfirm("Thông báo",
                        "Bạn có chắc chắn muốn chia đơn dọn dẹp cho nhân viên "+obj.getFULL_NAME()+" không?",
                        true, new ClickDialog() {
                    @Override
                    public void onClickYesDialog() {
                        showDialogLoading();
                        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                        mPresenterTabCheckinCheckout.api_split_book_services(sUserName, mContent, obj.getUSERID());
                    }

                    @Override
                    public void onClickNoDialog() {

                    }
                });
            }
        });
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
        hideDialogLoading();
        if (objRes.getERROR().equals("0000")) {
            set_class_user_spinner(objRes.getINFO());
        }
    }

    @Override
    public void show_get_city(CityResponse objResCity) {

    }

    @Override
    public void show_update_device(ObjErrorApi objError) {

    }

    @Override
    public void show_get_user_info(ResponInfo objLogin) {

    }

    @Override
    public void show_update_user_info(ObjErrorApi objError) {

    }

    @Override
    public void show_get_listuser(ResponInfo objRes) {
        hideDialogLoading();
        mList.clear();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            txtDataEmpty.setVisibility(View.GONE);
            if (objRes.getINFO() != null)
                mList.addAll(objRes.getINFO());
        }else {
            txtDataEmpty.setVisibility(View.VISIBLE);
            txtDataEmpty.setText(objRes.getRESULT());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void show_change_pass(ObjErrorApi objError) {

    }

    List<String> data_class_user;
    @BindView(R.id.spinner_class_user)
    Spinner spinner_class_user;

    private void set_class_user_spinner(List<ObjTypeUser> mList) {
        try {
            data_class_user = new ArrayList<>();
            data_class_user.add("- Tất cả -");
            for (ObjTypeUser obj : mList) {
                data_class_user.add(obj.getTYPE_NAME());
            }
         /*   data_class_user.add("- Tất cả -");
            data_class_user.add("Chủ nhà");
            data_class_user.add("Cộng tác viên");
            data_class_user.add("Dịch vụ");
            data_class_user.add("Supper Gold");*/
            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_spinner, data_class_user);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spinner_class_user.setAdapter(adapter);
            spinner_class_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        if (position == 0) {
                            USER_TYPE = "";
                        } else {
                            USER_TYPE = mList.get(position - 1).getUSER_TYPE();
                        }
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

    List<String> data_status_user;
    @BindView(R.id.spinner_status_user)
    Spinner spinner_status_user;

    private void set_status_user_spinner() {
        try {
            data_status_user = new ArrayList<>();
            data_status_user.add("- Tất cả -");
            data_status_user.add("Đã kích hoạt");
            data_status_user.add("Đang bị khóa");
            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_spinner, data_status_user);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spinner_status_user.setAdapter(adapter);
            spinner_status_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        switch (position) {
                            case 0:
                                sSTATE = "";
                                break;
                            case 1:
                                sSTATE = "1";
                                break;
                            case 2:
                                sSTATE = "0";
                                break;

                        }
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
    public void show_api_chiadon(ObjErrorApi obj){
        hideDialogLoading();
        if (obj.getERROR().equals("0000")){
            Toast.makeText(this, "Chia đơn dọn dẹp thành công", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
