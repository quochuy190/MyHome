package com.vn.myhome.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vn.myhome.R;
import com.vn.myhome.adapter.AdapterHomeStaySmall;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.presenter.HomePresenterListShowAll;
import com.vn.myhome.untils.KeyboardUtil;
import com.vn.myhome.untils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 10/16/2017.
 */

public class ActivityListHomestayShowAll extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private List<ObjHomeStay> mList;
    private AdapterHomeStaySmall adapterService;
    @BindView(R.id.rcv_list_all_homestay)
    RecyclerView recycle_service;
    RecyclerView.LayoutManager mLayoutManager;
    HomePresenterListShowAll mPresenter;
    int PAGE = 1;
    @BindView(R.id.pull_refresh_product)
    SwipeRefreshLayout pull_refresh_product;
    boolean isRefresh = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.fragment_service);
        ButterKnife.bind(this);
        KeyboardUtil.hideSoftKeyboard(this);
        mPresenter = new HomePresenterListShowAll(this);
        //  initData();
        initAppbar();
        init();
        showDialogLoading();
        initData();
        initEvent();
        initPulltoRefesh();
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
        txt_title.setText("DANH SÁCH PHÒNG");
    }
    @Override
    public int setContentViewId() {
        return R.layout.activity_list_homestay_all;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean isLoading = true;
    private void initEvent() {
        // loadmore
        recycle_service.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isRefresh) {
                    if (dy > 0) {
                        GridLayoutManager layoutmanager = (GridLayoutManager) recyclerView
                                .getLayoutManager();
                        visibleItemCount = layoutmanager.getChildCount();
                        totalItemCount = layoutmanager.getItemCount();
                        pastVisiblesItems = layoutmanager.findFirstVisibleItemPosition();
                        //Log.i(TAG, visibleItemCount + " " + totalItemCount + " " + presenter_detail_ringtunes);
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            if (!isLoading) {
                                isLoading = true;
                                PAGE++;
                                //  key = ed_key_search_fragment.getText().toString();
                                showDialogLoading();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        initData();
                                    }
                                }, 500);
                            }
                        }
                    }
                }
            }
        });
    }

    private void init() {
        mList = new ArrayList<>();
        adapterService = new AdapterHomeStaySmall(mList, this, new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {

            }
        });
        mLayoutManager = new GridLayoutManager(this, 2);
        recycle_service.setNestedScrollingEnabled(false);
        recycle_service.setHasFixedSize(true);
        recycle_service.setLayoutManager(mLayoutManager);
        recycle_service.setItemAnimator(new DefaultItemAnimator());
        recycle_service.setAdapter(adapterService);

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
              /*  setResult(RESULT_OK, new Intent());
                App.mCity = (ObjCity) item;
                finish();*/
            }
        });

    }

    private void initData() {
        String sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);

        mPresenter.getListHomeStay(sUser, PAGE + "");
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void update_data(List<ObjHomeStay> mListHome) {
        isLoading = false;
        mList.addAll(mListHome);
        adapterService.notifyDataSetChanged();
    }

    private void initPulltoRefesh() {
        pull_refresh_product.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isRefresh = false;
                mList.clear();
                PAGE = 1;
                showDialogLoading();
                initData();
                pull_refresh_product.setRefreshing(false);
            }
        }, 1000);
    }
}
