package com.vn.myhome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.activity.book_room.ActivityUpdateDiscountMyhome;
import com.vn.myhome.activity.myhome.ActivityNewRoom;
import com.vn.myhome.activity.notifycation.ActivityListNotify;
import com.vn.myhome.activity.user_manager.Activity_Info_User;
import com.vn.myhome.adapter.AdapterMyRoom;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.callback.setOnItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.models.ResponseApi.ResponGetListNotify;
import com.vn.myhome.models.ResponseApi.ResponListImageHome;
import com.vn.myhome.presenter.InterfaceMyHome;
import com.vn.myhome.presenter.InterfaceNotify;
import com.vn.myhome.presenter.MyHomePresenter;
import com.vn.myhome.presenter.NotifyPresenter;
import com.vn.myhome.untils.SharedPrefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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
public class FragmentMyHome extends BaseFragment implements InterfaceMyHome.View,
        SwipeRefreshLayout.OnRefreshListener, InterfaceNotify.View {
    private static final String TAG = "FragmentMyHome";
    public static FragmentMyHome fragment;
    private List<ObjHomeStay> mLisHomeStay;
    private List<ObjHomeStay> mLisHomeTemp;
    private AdapterMyRoom adapter;
    @BindView(R.id.recycle_list_service)
    RecyclerView recycle_service;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout pull_refresh_product;
    RecyclerView.LayoutManager mLayoutManager;
    MyHomePresenter mPresenter;
    NotifyPresenter mPresenterNotify;
    @BindView(R.id.txt_badger_notify)
    TextView txt_badger_notify;
    @BindView(R.id.spinner_home)
    Spinner spinner_home;
    List<String> data_Home;

    public static FragmentMyHome getInstance() {
        if (fragment == null) {
            synchronized (FragmentMyHome.class) {
                if (fragment == null)
                    fragment = new FragmentMyHome();
            }
        }
        return fragment;
    }

    ImageView img_home;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myhome, container, false);
        ButterKnife.bind(this, view);
        Log.e(TAG, "onCreateView: Setup");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mPresenter = new MyHomePresenter(this);
        mPresenterNotify = new NotifyPresenter(this);
        ImageView img_back = view.findViewById(R.id.btn_back);
        img_home = view.findViewById(R.id.img_home);
        TextView txt_title = view.findViewById(R.id.txt_title);
        img_back.setVisibility(View.VISIBLE);
        img_back.setImageResource(R.drawable.ic_notify);
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        if (objLogin != null && objLogin.getUSER_TYPE().equals(Constants.UserType.CHUNHA)) {
            txt_title.setText("DANH SÁCH NHÀ CỦA TÔI");
            img_home.setVisibility(View.VISIBLE);
            img_home.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
        } else {
            txt_title.setText("DANH SÁCH NHÀ");
            img_home.setVisibility(View.INVISIBLE);
        }

        initPulltoRefesh();
        init();
        set_data_spinner();
        initEvent();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityListNotify.class);
                startActivity(intent);
            }
        });
        check_title_notify();

        return view;
    }

    private void set_data_spinner() {
        data_Home = new ArrayList<>();
        data_Home.add("- Tất cả - ");
        data_Home.add("Chờ duyệt");
        data_Home.add("Đã duyệt");
        ArrayAdapter adapterSpinner = new ArrayAdapter(getContext(), R.layout.item_spinner, data_Home);
        adapterSpinner.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_home.setAdapter(adapterSpinner);
        spinner_home.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mLisHomeStay.clear();
                String sStatus = "";
                if (position == 0) {
                    mLisHomeStay.addAll(mLisHomeTemp);
                } else if (position == 1) {
                    sStatus = "6";
                } else if (position == 2) {
                    sStatus = "7";
                }
                for (ObjHomeStay obj : mLisHomeTemp) {
                    if (obj.getSTATE().equals(sStatus))
                        mLisHomeStay.add(obj);
                }
                adapter.notifyDataSetChanged();
                recycle_service.scrollToPosition(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.RELOAD_NOTIFY)) {
            Log.e(TAG, "onMessageEvent: " + App.sTotalNotify);
            check_title_notify();
        }
        if (event.message.equals(Constants.EventBus.KEY_UPDATE_API_LIST_MYHOME)) {
            mLisHomeStay.clear();
            mLisHomeTemp.clear();
            if (App.mListHomeStay != null && App.mListHomeStay.size() > 0) {
                mLisHomeStay.addAll(App.mListHomeStay);
                mLisHomeTemp.addAll(mLisHomeStay);
            }
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


    private void initPulltoRefesh() {
        pull_refresh_product.setOnRefreshListener(this);
    }

    private void initEvent() {
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityNewRoom.class);
                intent.putExtra(Constants.KEY_IS_UPDATE_MYHOME, false);
                //startActivity(intent);
                startActivityForResult(intent, Constants.RequestCode.GET_MY_HOME);
            }
        });
    }

    private void init() {
        mLisHomeStay = new ArrayList<>();
        mLisHomeTemp = new ArrayList<>();
        adapter = new AdapterMyRoom(mLisHomeStay, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recycle_service.setNestedScrollingEnabled(false);
        recycle_service.setHasFixedSize(true);
        recycle_service.setLayoutManager(mLayoutManager);
        recycle_service.setItemAnimator(new DefaultItemAnimator());
        recycle_service.setAdapter(adapter);
      /*  adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                Intent intent = new Intent(getContext(), ActivityNewRoom.class);
                ObjHomeStay obj = (ObjHomeStay) item;
                intent.putExtra(Constants.KEY_SEND_MYHOME, obj);
                //startActivity(intent);
                startActivityForResult(intent, Constants.RequestCode.GET_MY_HOME);
            }
        });*/
        adapter.setOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                Intent intent = new Intent(getContext(), ActivityNewRoom.class);
                intent.putExtra(Constants.KEY_SEND_MYHOME, mLisHomeStay.get(position));
                intent.putExtra(Constants.KEY_IS_UPDATE_MYHOME, true);
                //startActivity(intent);
                startActivityForResult(intent, Constants.RequestCode.GET_MY_HOME);
            }

            @Override
            public void OnLongItemClickListener(int position) {
                // duyệt phòng
              /*  Intent intent = new Intent(getContext(), Activity_calendar_booking.class);
                intent.putExtra(Constants.KEY_SEND_ROOM_BOOKING, mLisHomeStay.get(position));
                startActivity(intent);*/
                ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
                if (objLogin.getUSER_TYPE().equals(Constants.UserType.ADMIN)) {
                    change_state_myhome(mLisHomeStay.get(position));
                } else {
                    Intent intent = new Intent(getContext(), ActivityUpdateDiscountMyhome.class);
                    intent.putExtra(Constants.KEY_SEND_MYHOME, mLisHomeStay.get(position));
                    //startActivity(intent);
                    startActivityForResult(intent, Constants.RequestCode.GET_MY_HOME);
                }

            }
        });
        adapter.setClick_namehost(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                ObjHomeStay obj = (ObjHomeStay) item;
                Intent intent = new Intent(getContext(), Activity_Info_User.class);
                intent.putExtra(Constants.KEY_SEND_INFO_USERID, obj.getUSER_ID());
                intent.putExtra(Constants.KEY_SEND_INFO_USERID_TITLE, "Thông tin chủ nhà");
                //startActivity(intent);
                startActivityForResult(intent, Constants.RequestCode.GET_MY_HOME);
            }
        });

    }

    private void change_state_myhome(ObjHomeStay objHomeStay) {
        //   showDialogLoading();
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        if (objHomeStay != null && objHomeStay.getSTATE().equals("6")) {
            //Đang chờ duyệt
            mPresenter.api_update_state_room(sUserName, objHomeStay.getGENLINK(), "7");
        } else if (objHomeStay != null && objHomeStay.getSTATE().equals("7")) {
            //Hủy duyệt
            mPresenter.api_update_state_room(sUserName, objHomeStay.getGENLINK(), "6");
        }

    }

    private void initData() {
        String sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        if (sUser != null) {
            showDialogLoading();
            mPresenter.api_get_mylist_room(sUser, "");
            mPresenterNotify.api_get_list_notifi(sUser, "1", "500");
        }
    }


    @Override
    public void show_error_api(String sService) {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_list_notifi(ResponGetListNotify objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            if (objError.getINFO() != null) {
                App.sTotalNotify = objError.getINFO().get(0).getTONG();
            }
        }
    }

    @Override
    public void show_update_list_notifi(ObjErrorApi objError) {

    }

    @Override
    public void show_get_mylist_room(GetRoomResponse objRes) {
        hideDialogLoading();
        mLisHomeStay.clear();
        mLisHomeTemp.clear();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            if (objRes.getINFO() != null) {
                mLisHomeStay.addAll(objRes.getINFO());
                mLisHomeTemp.addAll(objRes.getINFO());
            }
        } else
            showAlertDialog("Thông báo", objRes.getRESULT());
        adapter.notifyDataSetChanged();
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
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            Toast.makeText(getContext(), "Cập nhật trạng thái nhà thành công", Toast.LENGTH_SHORT).show();
            initData();
        }
    }

    @Override
    public void show_get_album_image(ResponListImageHome objRes) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_MY_HOME:
                if (resultCode == RESULT_OK) {
                    initData();
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                pull_refresh_product.setRefreshing(false);
            }
        }, 500);
    }
}
