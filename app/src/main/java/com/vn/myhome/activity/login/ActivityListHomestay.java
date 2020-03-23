package com.vn.myhome.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.adapter.AdapterListHomestaySearch;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.models.ResponseApi.ObjCity;
import com.vn.myhome.models.ResponseApi.ResponListImageHome;
import com.vn.myhome.presenter.InterfaceMyHome;
import com.vn.myhome.presenter.MyHomePresenter;
import com.vn.myhome.untils.KeyboardUtil;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 10/16/2017.
 */

public class ActivityListHomestay extends BaseActivity
        implements InterfaceMyHome.View {

    private List<ObjHomeStay> mList;
    private AdapterListHomestaySearch adapterService;
    @BindView(R.id.recycle_list_service)
    RecyclerView recycle_service;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.edt_search_appbar)
    EditText edt_search_service;
    @BindView(R.id.img_back)
    ImageView img_back;
    private List<ObjHomeStay> temp;
    String sUserId;
    MyHomePresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        KeyboardUtil.hideSoftKeyboard(this);
        //  initData();
        mPresenter = new MyHomePresenter(this);
        //initAppbar();
        init();
        initData();
        initEvent();
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_recycleview;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /*  @Nullable
          @Override
          public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
              View view= inflater.inflate(R.layout.fragment_service, container, false);


              view.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                  }
              });
              return view;
          }
      */
    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    void filter(String text) {
        temp.clear();
        for (ObjHomeStay d : mList) {
            String sName = StringUtil.removeAccent(d.getNAME().toLowerCase());
            String sInput = StringUtil.removeAccent(text.toLowerCase());
            if (sName.contains(sInput)) {
                //adding the element to filtered list
                temp.add(d);
            }
        }
        adapterService.updateList(temp);
        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                setResult(RESULT_OK, new Intent());
                App.mCity = (ObjCity) item;
                finish();
            }
        });
    }

    private void init() {
        mList = new ArrayList<>();
        temp = new ArrayList<>();
        adapterService = new AdapterListHomestaySearch(temp, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycle_service.setNestedScrollingEnabled(false);
        recycle_service.setHasFixedSize(true);
        recycle_service.setLayoutManager(mLayoutManager);
        recycle_service.setItemAnimator(new DefaultItemAnimator());
        recycle_service.setAdapter(adapterService);
        adapterService.updateList(temp);
      /*  adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                setResult(RESULT_OK, new Intent());
                App.mCity = (City) item;
            }
        });*/
        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                setResult(RESULT_OK, new Intent());
                App.mCity = (ObjCity) item;
                finish();
            }
        });

    }

    private void initData() {
        if (App.mListHomeStay != null && App.mListHomeStay.size() > 0) {
            mList.addAll(App.mListHomeStay);
            temp.addAll(mList);
            adapterService.notifyDataSetChanged();
        } else {
            showDialogLoading();
            String sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
            mPresenter.api_get_mylist_room(sUsername, "");
        }
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
    public void show_get_mylist_room(GetRoomResponse objRes) {
        hideDialogLoading();
        mList.clear();
        temp.clear();
        if (objRes.getINFO()!=null){
            mList.addAll(objRes.getINFO());
            temp.addAll(mList);
            adapterService.notifyDataSetChanged();
        }

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


}
