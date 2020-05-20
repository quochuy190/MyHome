package com.vn.myhome.fragment.myhome;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.activity.myhome.ActivityNewRoom;
import com.vn.myhome.adapter.AdapterAmenities;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.AmenitiesObj;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ResponseApi.AmentiniesResponse;
import com.vn.myhome.presenter.AmentiniesPresenter;
import com.vn.myhome.presenter.InterfaceAmenities;
import com.vn.myhome.untils.SharedPrefs;

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
public class FragmentAmenitiesNewRoom extends BaseFragment implements InterfaceAmenities.View {
    private static final String TAG = "FragmentSetup";
    public static FragmentAmenitiesNewRoom fragment;
    @BindView(R.id.rcv_list_amenities)
    RecyclerView rcv_list_amenities;
    @BindView(R.id.btn_update_amenities)
    Button btn_update_amenities;
    private List<AmenitiesObj> mList;
    private AdapterAmenities adapterService;
    RecyclerView.LayoutManager mLayoutManager;
    AmentiniesPresenter mPresenterMmenities;
    String sGetlink;

    public static FragmentAmenitiesNewRoom getInstance() {
        if (fragment == null) {
            synchronized (FragmentAmenitiesNewRoom.class) {
                if (fragment == null)
                    fragment = new FragmentAmenitiesNewRoom();
            }
        }
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amenities_newhome, container, false);
        ButterKnife.bind(this, view);
        Log.e(TAG, "onCreateView: Setup");
        mPresenterMmenities = new AmentiniesPresenter(this);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        init();
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
           // get_api_update();
        }
    }
    String sUsername = "";

    private void initData() {
        if (ActivityNewRoom.objMyhome != null && ActivityNewRoom.objMyhome.getGENLINK() != null) {
            sGetlink = ActivityNewRoom.objMyhome.getGENLINK();
        } else {
            sGetlink = ActivityNewRoom.sGetlink;
        }
        showDialogLoading();
        sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenterMmenities.api_get_amenities_room(sUsername, sGetlink);
    }

    AmentiniesResponse objRes;

    private void init() {
        objRes = SharedPrefs.getInstance().get(Constants.KEY_SAVE_AMENITIES, AmentiniesResponse.class);
        mList = new ArrayList<>();
        mList.addAll(objRes.getINFO());
        if (mList.size() > 0) {
            String stype = mList.get(0).getTYPE_AMENITIES();
            mList.add(0, new AmenitiesObj(mList.get(0).getDES(), true));
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getTYPE_AMENITIES() != null) {
                    int icurent = Integer.parseInt(stype);
                    int item = Integer.parseInt(mList.get(i).getTYPE_AMENITIES());
                    if (icurent != item) {
                        stype = mList.get(i).getTYPE_AMENITIES();
                        mList.add(i, new AmenitiesObj(mList.get(i).getDES(), true));
                    }
                }
            }
        }
        adapterService = new AdapterAmenities(mList, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        rcv_list_amenities.setNestedScrollingEnabled(false);
        rcv_list_amenities.setHasFixedSize(true);
        rcv_list_amenities.setLayoutManager(mLayoutManager);
        rcv_list_amenities.setItemAnimator(new DefaultItemAnimator());
        rcv_list_amenities.setAdapter(adapterService);

        adapterService.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                AmenitiesObj obj = (AmenitiesObj) item;
                if (!obj.isHeader()) {
                    mList.get(position).setChecked(!mList.get(position).isChecked());
                    adapterService.notifyDataSetChanged();
                }
            }
        });
    }

    private void initEvent() {
        btn_update_amenities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLoading();
                get_api_update();
               // EventBus.getDefault().post(new MessageEvent(Constants.EventBus.KEY_UPDATE_MYHOME, 1, 0));
            }
        });
    }

    String arrayAmenities = "";

    private void get_api_update() {
        for (AmenitiesObj obj : mList) {
            if (obj.isChecked())
                arrayAmenities = arrayAmenities + obj.getID() + ",";
        }
        if (arrayAmenities.length() > 0)
            arrayAmenities = arrayAmenities.substring(0, arrayAmenities.length() - 1);

        mPresenterMmenities.api_update_amenities(sUsername, sGetlink, arrayAmenities);
    }

    @Override
    public void show_error_api(String sService) {

    }

    @Override
    public void show_get_amenities() {

    }

    @Override
    public void show_amenities_room(AmentiniesResponse objRes) {
        hideDialogLoading();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            if (objRes.getINFO() != null && objRes.getINFO().size() > 0) {
                for (AmenitiesObj objAmen : objRes.getINFO()) {
                    for (AmenitiesObj obj : mList) {
                        if (objAmen.getID().equals(obj.getID())) {
                            obj.setChecked(true);
                        }
                    }
                }
            }
        }
        adapterService.notifyDataSetChanged();
    }

    @Override
    public void show_update_amenities(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            Toast.makeText(getContext(), "Cập nhật tiện ích thành công", Toast.LENGTH_SHORT).show();
        } else
            showAlertDialog("Thông báo", objError.getRESULT());
    }
}
