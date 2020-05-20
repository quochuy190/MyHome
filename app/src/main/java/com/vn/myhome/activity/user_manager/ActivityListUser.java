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

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.activity.login.RegisterActivity;
import com.vn.myhome.adapter.AdapterListUser;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjLogin;
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
public class ActivityListUser extends BaseActivity implements InterfaceUser.View {
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
    Button btn_tracuu;
    RecyclerView.LayoutManager mLayoutManager;
    String USER_TYPE = "", sSTATE = "", sFullName = "", sPhone = "";

    @Override
    public int setContentViewId() {
        return R.layout.activity_list_user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new UserPresenter(this);
        KeyboardUtil.hideSoftKeyboard(this);
        init();
        initAppbar();
        set_class_user_spinner();
        set_status_user_spinner();
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
        txt_title.setText("DANH SÁCH NGƯỜI DÙNG");
        img_home.setVisibility(View.VISIBLE);
        img_home.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityListUser.this, RegisterActivity.class);
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
                KeyboardUtil.hideSoftKeyboard(ActivityListUser.this);
                initData();
            }
        });
    }

    private void initData() {
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
                sFullName, USER_TYPE, sSTATE, "", "1", "500");
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
                ObjLogin obj = mList.get(position);
                Intent intent = new Intent(ActivityListUser.this, Activity_Info_User.class);
                intent.putExtra(Constants.KEY_SEND_INFO_USERID, obj.getUSERID());
                intent.putExtra(Constants.KEY_SEND_INFO_USERID_TITLE, "Thông tin người dùng");
                //startActivity(intent);
                startActivityForResult(intent, Constants.RequestCode.GET_MY_HOME);
            }
        });
    }

    @Override
    public void show_error_api(String sService) {
        hideDialogLoading();
        showAlertErrorNetwork();
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
            if (objRes.getINFO() != null)
                mList.addAll(objRes.getINFO());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void show_change_pass(ObjErrorApi objError) {

    }

    List<String> data_class_user;
    @BindView(R.id.spinner_class_user)
    Spinner spinner_class_user;

    private void set_class_user_spinner() {
        try {
            data_class_user = new ArrayList<>();
            data_class_user.add("- Tất cả -");
            data_class_user.add("Chủ nhà");
            data_class_user.add("Cộng tác viên");
            data_class_user.add("Dịch vụ");
            data_class_user.add("Supper Gold");
            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_spinner, data_class_user);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spinner_class_user.setAdapter(adapter);
            spinner_class_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        switch (position) {
                            case 0:
                                USER_TYPE = "";
                                break;
                            case 1:
                                USER_TYPE = Constants.UserType.CHUNHA;
                                break;
                            case 2:
                                USER_TYPE = Constants.UserType.CTV;
                                break;
                            case 3:
                                USER_TYPE = Constants.UserType.DICHVU;
                                break;
                            case 4:
                                USER_TYPE = Constants.UserType.GOLD;
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
}
