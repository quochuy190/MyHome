package com.vn.myhome.activity.notifycation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.adapter.AdapterListNotify;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjNotify;
import com.vn.myhome.models.ResponseApi.ResponGetListNotify;
import com.vn.myhome.presenter.InterfaceNotify;
import com.vn.myhome.presenter.NotifyPresenter;
import com.vn.myhome.untils.SharedPrefs;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 20-November-2019
 * Time: 18:24
 * Version: 1.0
 */
public class ActivityListNotify extends BaseActivity implements InterfaceNotify.View {
    @BindView(R.id.rcv_list_notify)
    RecyclerView rcv_list_notify;
    AdapterListNotify adapter;
    List<ObjNotify> mList;
    RecyclerView.LayoutManager mLayoutManager;
    NotifyPresenter mPresenter;
    int page = 1;
    int number = 200;

    @Override
    public int setContentViewId() {
        return R.layout.activity_list_notify;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();
        mPresenter = new NotifyPresenter(this);
        initAppbar();
        init();
        initEvent();
    }

    private void initEvent() {
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogComfirm("Thông báo",
                        "Bạn có chắc chắn muốn chuyển toàn bộ tin nhắn sang trạng thái đã đọc không?",
                        true, new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                lisIdNotify = lisIdNotify.substring(0, (lisIdNotify.length() - 1));
                                String sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                                mPresenter.api_update_list_notifi(sUser, lisIdNotify);
                            }

                            @Override
                            public void onClickNoDialog() {

                            }
                        });

            }
        });
    }

    ImageView img_home;

    private void initAppbar() {
        ImageView img_back = findViewById(R.id.btn_back);
        TextView txt_title = findViewById(R.id.txt_title);
        TextView txt_badger_notify = findViewById(R.id.txt_badger_notify);
        img_home = findViewById(R.id.img_home);
        img_home.setVisibility(View.VISIBLE);
        img_home.setImageResource(R.drawable.ic_open_book);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
               // EventBus.getDefault().post(new MessageEvent(Constants.EventBus.RELOAD_NOTIFY, 0, 0));
            }
        });
        txt_title.setText("DANH SÁCH THÔNG BÁO");
    }

    private void initData() {
        showDialogLoading();
        String sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenter.api_get_list_notifi(sUser, "" + page, "" + number);
    }

    private void init() {
        adapter = new AdapterListNotify(mList, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        rcv_list_notify.setNestedScrollingEnabled(false);
        rcv_list_notify.setHasFixedSize(true);
        rcv_list_notify.setLayoutManager(mLayoutManager);
        rcv_list_notify.setItemAnimator(new DefaultItemAnimator());
        rcv_list_notify.setAdapter(adapter);

        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
            //    EventBus.getDefault().post(new MessageEvent(Constants.EventBus.RELOAD_NOTIFY, 0, 0));
                ObjNotify obj = (ObjNotify) item;
                Intent intent = new Intent(ActivityListNotify.this, Activity_Notify_Detail.class);
                intent.putExtra(Constants.KEY_SEND_NOTIFY_DETAIL, obj);
                startActivityForResult(intent, Constants.RequestCode.GET_NOTIFY);
            }
        });
    }

    @Override
    public void show_error_api(String sService) {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    boolean isCheck = false;
    String lisIdNotify = "";
    private static final String TAG = "ActivityListNotify";
    @Override
    public void show_list_notifi(ResponGetListNotify objError) {
        hideDialogLoading();
        lisIdNotify = "";
        mList.clear();
        if (objError != null && objError.getERROR().equals("0000")) {
            if (objError.getINFO() != null) {
                mList.addAll(objError.getINFO());
                App.sTotalNotify = objError.getINFO().get(0).getTONG();
                Log.e(TAG, "show_list_notifi: "+App.sTotalNotify );
                EventBus.getDefault().post(new MessageEvent(Constants.EventBus.RELOAD_NOTIFY, 0, 0));
            }
        }
        if (mList.size() > 0) {
            for (ObjNotify objMess : mList) {
                if (objMess.getIS_READ() != null && objMess.getIS_READ().equals("0")) {
                    isCheck = true;
                    lisIdNotify = lisIdNotify + objMess.getID() + "#";
                }
            }
        } else {
            isCheck = false;
        }
        if (isCheck) {
            img_home.setAlpha((float) 1);
            img_home.setEnabled(true);
        } else {
            img_home.setAlpha((float) 0.1);
            img_home.setEnabled(false);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void show_update_list_notifi(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            Toast.makeText(this, "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show();
            initData();
        } else
            showDialogNotify("Thông báo", objError.getRESULT());
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_CONTACT:
                if (resultCode == RESULT_OK) {
                    initData();
                }
                break;
        }
    }
}
