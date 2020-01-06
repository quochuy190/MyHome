package com.vn.myhome.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.App;
import com.vn.myhome.BuildConfig;
import com.vn.myhome.R;
import com.vn.myhome.activity.contacts.ActivityListContact;
import com.vn.myhome.activity.login.LoginActivity;
import com.vn.myhome.activity.notifycation.ActivityListNotify;
import com.vn.myhome.activity.user_manager.ActivityListUser;
import com.vn.myhome.activity.user_manager.Activity_Info_User;
import com.vn.myhome.activity.user_manager.InterfaceUser;
import com.vn.myhome.adapter.AdapterSetupMain;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ObjSetupMain;
import com.vn.myhome.models.ResponseApi.ResponInfo;
import com.vn.myhome.presenter.UserPresenter;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
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
public class FragmentSetup extends BaseFragment implements InterfaceUser.View {
    private static final String TAG = "FragmentSetup";
    public static FragmentSetup fragment;
    private List<ObjSetupMain> mList;
    private AdapterSetupMain adapterSetupMain;
    @BindView(R.id.rcv_setup)
    RecyclerView rcv_setup;
    @BindView(R.id.btn_back)
    ImageView img_back;
    @BindView(R.id.txt_title)
    TextView txt_title;
    @BindView(R.id.txt_logout)
    TextView txt_logout;
    @BindView(R.id.txt_version_app)
    TextView txt_version_app;
    RecyclerView.LayoutManager mLayoutManager;
    String sUserName;

    public static FragmentSetup getInstance() {
        if (fragment == null) {
            synchronized (FragmentSetup.class) {
                if (fragment == null)
                    fragment = new FragmentSetup();
            }
        }
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.RELOAD_NOTIFY)) {
            Log.e(TAG, "onMessageEvent: ");
            check_title_notify();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup, container, false);
        ButterKnife.bind(this, view);
        Log.e(TAG, "onCreateView: Setup");
        img_back.setVisibility(View.VISIBLE);
        img_back.setImageResource(R.drawable.ic_notify);
        mPresenter = new UserPresenter(this);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityListNotify.class);
                startActivity(intent);
            }
        });
        init();
        initData();
        initEvent();
        check_title_notify();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    @BindView(R.id.txt_badger_notify)
    TextView txt_badger_notify;

    private void check_title_notify() {
        if (App.sTotalNotify.length() > 0) {
            if (!App.sTotalNotify.equals("0")) {
                txt_badger_notify.setText(App.sTotalNotify);
                txt_badger_notify.setVisibility(View.VISIBLE);
            } else
                txt_badger_notify.setVisibility(View.GONE);
        } else {
            txt_badger_notify.setVisibility(View.GONE);
        }
    }

    private void initEvent() {
        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogComfirm("Đăng xuất",
                        "Bạn có chắc chắn muốn đăng xuất?",
                        true, new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                SharedPrefs.getInstance().put(Constants.KEY_SAVE_IS_LOGIN, false);
                                startActivity(new Intent(getContext(), LoginActivity.class));
                                getActivity().finish();
                            }

                            @Override
                            public void onClickNoDialog() {

                            }
                        });

            }
        });
    }

    ObjLogin objLogin;

    private void initData() {

        txt_title.setText("CÀI ĐẶT");
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        txt_version_app.setText("Version: " + BuildConfig.VERSION_NAME);
    }

    private void init() {
        objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        mList = new ArrayList<>();
        mList.add(new ObjSetupMain(0, "Hồ sơ", R.drawable.ic_setup_1));
        mList.add(new ObjSetupMain(2, "Danh sách khách hàng", R.drawable.ic_setup_3));
        mList.add(new ObjSetupMain(3, "Chăm sóc khách hàng", R.drawable.ic_setup_4));
        mList.add(new ObjSetupMain(4, "Hỗ trợ", R.drawable.ic_setup_5));
        mList.add(new ObjSetupMain(5, "Chia sẻ ứng dụng", R.drawable.ic_setting_5));
        mList.add(new ObjSetupMain(6, "Đổi mật khẩu", R.drawable.ic_setting_6));
        if (objLogin.getUSER_TYPE().equals(Constants.UserType.ADMIN)) {
            mList.add(new ObjSetupMain(1, "Danh sách người dùng", R.drawable.ic_setup_2));
        }
        adapterSetupMain = new AdapterSetupMain(mList, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        rcv_setup.setNestedScrollingEnabled(true);
        rcv_setup.setHasFixedSize(true);
        rcv_setup.setLayoutManager(mLayoutManager);
        rcv_setup.setItemAnimator(new DefaultItemAnimator());
        rcv_setup.setAdapter(adapterSetupMain);
      /*  adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                setResult(RESULT_OK, new Intent());
                App.mCity = (City) item;
            }
        });*/
        adapterSetupMain.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                ObjSetupMain obj = (ObjSetupMain) item;
                switch (obj.getId()) {
                    case 0:
                        Intent intent = new Intent(getContext(), Activity_Info_User.class);
                        intent.putExtra(Constants.KEY_SEND_INFO_USERID, sUserName);
                        intent.putExtra(Constants.KEY_SEND_INFO_USERID_TITLE, "HỒ SƠ");
                        //startActivity(intent);
                        startActivityForResult(intent, Constants.RequestCode.GET_MY_HOME);
                        break;
                    case 1:
                        startActivity(new Intent(getContext(), ActivityListUser.class));
                        break;
                    case 6:
                        showDialog_Pass();
                        break;
                    case 4:
                        showDialog_CallPhone(new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                StringUtil.call_phone(getContext(), "0981045225");
                            }

                            @Override
                            public void onClickNoDialog() {

                            }
                        });
                        break;
                    case 2:
                        startActivity(new Intent(getContext(), ActivityListContact.class));
                        break;

                }
            }
        });

    }

    Dialog dialog_pass;
    String sPassOld = "";
    String sPassNew = "";
    String sPassComfirm = "";
    UserPresenter mPresenter;

    public void showDialog_Pass() {
        dialog_pass = new Dialog(getContext(), R.style.Theme_Dialog);
        dialog_pass.setCancelable(true);
        dialog_pass.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_pass.setContentView(R.layout.dialog_change_pass);
        dialog_pass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText edt_pass_old = dialog_pass.findViewById(R.id.edt_pass_old);
        EditText edt_pass_new = dialog_pass.findViewById(R.id.edt_pass_new);
        EditText edt_pass_comfirm = dialog_pass.findViewById(R.id.edt_pass_confirm);
        Button btn_comfirm = dialog_pass.findViewById(R.id.btn_update);

        btn_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_pass_old.getText().toString().length() == 0) {
                    Toast.makeText(getContext(), "Bạn chưa nhập vào mật khẩu cũ", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    sPassOld = edt_pass_old.getText().toString();
                }
                if (edt_pass_new.getText().toString().length() == 0) {
                    Toast.makeText(getContext(), "Bạn chưa nhập vào mật khẩu mới", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    sPassNew = edt_pass_new.getText().toString();
                }
                if (edt_pass_comfirm.getText().toString().length() == 0) {
                    Toast.makeText(getContext(),
                            "Bạn chưa nhập vào xác nhận mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    sPassComfirm = edt_pass_comfirm.getText().toString();
                }
                String sPass = SharedPrefs.getInstance().get(Constants.KEY_SAVE_PASSWORD, String.class);
                String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                if (sPassOld.equals(sPass)) {
                    if (sPassNew.equals(sPassComfirm)) {
                        showDialogLoading();
                        mPresenter.api_change_pass(sUserName, sPassOld, sPassNew);
                        dialog_pass.dismiss();
                    } else {
                        Toast.makeText(getContext(),
                                "Xác nhận mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(),
                            "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog_pass.show();

    }

    @Override
    public void show_error_api(String sService) {

    }

    @Override
    public void show_get_user_info(ResponInfo objLogin) {

    }

    @Override
    public void show_update_user_info(ObjErrorApi objError) {

    }

    @Override
    public void show_get_listuser(ResponInfo objRes) {

    }


    @Override
    public void show_change_pass(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_PASSWORD, sPassNew);
        } else {
            showAlertDialog("Thông báo", objError.getRESULT());
        }
    }
}
