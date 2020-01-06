package com.vn.myhome.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.guilhe.views.SeekBarRangedView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.activity.home.ActivityListHomeStay;
import com.vn.myhome.activity.home.ActivityRoomDetail;
import com.vn.myhome.activity.home.InterfaceRoom;
import com.vn.myhome.activity.home.RoomPresenter;
import com.vn.myhome.adapter.AdapterHomeStay_Search;
import com.vn.myhome.adapter.AdapterSliderHome;
import com.vn.myhome.adapter.AdapterViewPagerHomeImage;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ResponseApi.GetAlbumImageHomeResponse;
import com.vn.myhome.models.ResponseApi.GetImageCoverResponse;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.models.ResponseApi.ResponGetListNotify;
import com.vn.myhome.presenter.InterfaceNotify;
import com.vn.myhome.presenter.NotifyPresenter;
import com.vn.myhome.untils.KeyboardUtil;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.StringUtil;
import com.vn.myhome.untils.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 22-April-2019
 * Time: 10:30
 * Version: 1.0
 */
public class FragmentHome extends BaseFragment implements InterfaceRoom.View, View.OnClickListener, InterfaceNotify.View {
    private static final String TAG = "FragmentSetup";
    public static FragmentHome fragment;
    @BindView(R.id.rcv_homestay_home)
    RecyclerView rcv_home;
    /*  @BindView(R.id.viewpager_home)
      AutoScrollViewPager viewpager_home;
      @BindView(R.id.tab_auto_scroll)
      TabLayout tab_auto_scroll;*/
    @BindView(R.id.imageSlider)
    SliderView sliderView;
    @BindView(R.id.txt_price_start)
    TextView txt_price_start;
    @BindView(R.id.txt_price_end)
    TextView txt_price_end;
    @BindView(R.id.seekbar_price)
    SeekBarRangedView seekbar_price;
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
    @BindView(R.id.edt_name_homestay)
    EditText edt_name_homestay;
    @BindView(R.id.edt_quantity)
    EditText edt_quantity;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterHomeStay_Search mAdapter;
    List<ObjHomeStay> mList;
    int PAGE = 1;
    int NUMBER = 200;
    NotifyPresenter mPresenterNotify;
    Calendar myCalendar_start = Calendar.getInstance();
    Calendar myCalendar_end = Calendar.getInstance();
    private String sFromDate = "", sToDate = "";

    public static FragmentHome getInstance() {
        if (fragment == null) {
            synchronized (FragmentHome.class) {
                if (fragment == null)
                    fragment = new FragmentHome();
            }
        }
        return fragment;
    }

    RoomPresenter mPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_new, container, false);
        ButterKnife.bind(this, view);
        KeyboardUtil.hideSoftKeyboard(getActivity());
        Log.e(TAG, "onCreateView: Setup");
        mPresenter = new RoomPresenter(this);
        mPresenterNotify = new NotifyPresenter(this);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mListImage = new ArrayList<>();
        price_start = 0;
        price_end = 150;
        init();
        initSeekbar();
        initData();
        initEvent();
        set_price();
        return view;
    }

    long price_start = 0;
    long price_end = 150;

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

    private void initEvent() {
        ll_date_start.setOnClickListener(this);
        ll_date_end.setOnClickListener(this);
        edt_date_start.setOnClickListener(this);
        edt_date_end.setOnClickListener(this);
        btn_search.setOnClickListener(this);

    }

    String sUserName;

    private void initData() {
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        if (sUserName != null) {
            showDialogLoading();
            PAGE = 1;
            get_api();
            mPresenter.api_get_cover_idx(sUserName);
            mPresenterNotify.api_get_list_notifi(sUserName, "1", "500");

        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.KEY_SEND_NOTIFY)) {
            sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
            mPresenterNotify.api_get_list_notifi(sUserName, "1", "500");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    private void get_api() {
        mPresenter.api_get_listroom_idx(sUserName, "" + PAGE, "" + NUMBER);
    }
    boolean isRefresh = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean isLoading = true;
    private void init() {
        mList = new ArrayList<>();
        mAdapter = new AdapterHomeStay_Search(mList, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        rcv_home.setNestedScrollingEnabled(true);
        rcv_home.setHasFixedSize(true);
        rcv_home.setLayoutManager(mLayoutManager);
        rcv_home.setItemAnimator(new DefaultItemAnimator());
        rcv_home.setAdapter(mAdapter);

        mAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                Intent intent = new Intent(getContext(), ActivityRoomDetail.class);
                ObjHomeStay obj = (ObjHomeStay) item;
                intent.putExtra(Constants.KEY_SEND_ROOM_DETAIL, obj);
                startActivity(intent);
            }
        });
        // loadmore
        rcv_home.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
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
                            mList.add(null);
                            mAdapter.notifyDataSetChanged();
                            //  key = ed_key_search_fragment.getText().toString();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    get_api();
                                }
                            }, 1000);
                        }
                    }
                }
            }
        });
    }

    List<String> mListImage;
    AdapterViewPagerHomeImage objAdapter;

    private void initAutoScroll() {
      /*  objAdapter = new AdapterViewPagerHomeImage(mListImage, R.layout.imgviewpager, getContext());
        viewpager_home.setAdapter(objAdapter);
        viewpager_home.startAutoScroll();
        viewpager_home.setSlideDuration(5000);
        viewpager_home.setCycle(true);
        tab_auto_scroll.setupWithViewPager(viewpager_home);

        objAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {

            }
        });*/
        AdapterSliderHome adapter = new AdapterSliderHome(getContext(), mListImage);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(5); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }

    @Override
    public void show_error_api(String sService) {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_list_notifi(ResponGetListNotify objError) {
        hideDialogLoading();

    }

    @Override
    public void show_update_list_notifi(ObjErrorApi objError) {

    }

    @Override
    public void show_get_listroom_idx(GetRoomResponse objRes) {
        hideDialogLoading();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            isLoading = false;
            if (PAGE == 1) {
                mList.clear();
            } else {
                if (PAGE > 1) {
                    mList.remove(mList.size() - 1);
                }
            }
            mList.addAll(objRes.getINFO());
            mAdapter.notifyDataSetChanged();
        } else {

        }
    }

    @Override
    public void show_get_cover_idx(GetImageCoverResponse obj) {
        hideDialogLoading();
        if (obj != null && obj.getERROR().equals("0000")) {
            if (obj.getINFO() != null) {
                mListImage.addAll(obj.getINFO());
                initAutoScroll();
            }
        }
    }

    @Override
    public void show_search_home(GetRoomResponse objRes) {
        hideDialogLoading();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            if (objRes.getINFO() != null) {
                App.mListHomeStay.clear();
                App.mListHomeStay = objRes.getINFO();
                Intent intent = new Intent(getContext(), ActivityListHomeStay.class);
                startActivity(intent);
            }
        } else {
            showAlertDialog("Thông báo", objRes.getRESULT());
        }
    }

    @Override
    public void show_get_room_detail(ObjHomeStay objRoom) {

    }

    @Override
    public void show_get_album_image(GetAlbumImageHomeResponse objRes) {

    }

    @Override
    public void show_check_lock(ObjErrorApi objRes) {

    }

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

    String sDateStart = "", sDateEnd = "";
    String sLOCATION = "", sPEOPLE = "", sPRICE_FROM = "", sPRICE_TO = "", sAMENITIES = "";


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_date_start:
                new DatePickerDialog(getContext(), R.style.MyDatePickerStyle, start_date, myCalendar_start
                        .get(Calendar.YEAR), myCalendar_start.get(Calendar.MONTH),
                        myCalendar_start.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.ll_date_end:
                new DatePickerDialog(getContext(), R.style.MyDatePickerStyle, end_date, myCalendar_end
                        .get(Calendar.YEAR), myCalendar_end.get(Calendar.MONTH),
                        myCalendar_end.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.edt_date_start:
                new DatePickerDialog(getContext(), R.style.MyDatePickerStyle, start_date, myCalendar_start
                        .get(Calendar.YEAR), myCalendar_start.get(Calendar.MONTH),
                        myCalendar_start.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.edt_date_end:
                new DatePickerDialog(getContext(), R.style.MyDatePickerStyle, end_date, myCalendar_end
                        .get(Calendar.YEAR), myCalendar_end.get(Calendar.MONTH),
                        myCalendar_end.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.btn_search:
                 get_api_search();

                break;
        }
    }

    private void get_api_search() {
        sDateStart = edt_date_start.getText().toString().trim();
        sDateEnd = edt_date_end.getText().toString().trim();
        sLOCATION = edt_name_homestay.getText().toString().trim();
        sPEOPLE = edt_quantity.getText().toString().trim();
        sPRICE_FROM = txt_price_start.getText().toString().trim();
        sPRICE_FROM = sPRICE_FROM.replaceAll(",", "");
        sPRICE_FROM = sPRICE_FROM.replaceAll("VND", "");
        sPRICE_TO = txt_price_end.getText().toString().trim();
        sPRICE_TO = sPRICE_TO.replaceAll(",", "");
        sPRICE_TO = sPRICE_TO.replaceAll("VND", "");
        if (sLOCATION.length() == 0) {
            showAlertDialog("Thông báo", "Bạn chưa nhập địa điểm muốn đến");
            return;
        }
        if (sPEOPLE.length() == 0) {
            showAlertDialog("Thông báo", "Bạn chưa nhập vào số lượng người");
            return;
        }
        if (sDateStart.length() == 0) {
            showAlertDialog("Thông báo", "Bạn chưa nhập vào ngày đến");
            return;
        }
        if (sDateEnd.length() == 0) {
            showAlertDialog("Thông báo", "Bạn chưa nhập vào ngày đi");
            return;
        }
        if (TimeUtils.compare_two_date(sDateStart, sDateEnd,
                "dd/MM/yyyy", "dd/MM/yyyy")) {
            showDialogLoading();
            sPRICE_TO = sPRICE_TO.replaceAll("\\.", "").replaceAll(",","");
            sPRICE_FROM = sPRICE_FROM.replaceAll("\\.","").replaceAll(",","");
            mPresenter.api_search_home(sUserName, sLOCATION, sDateStart, sDateEnd, sPEOPLE,
                    sPRICE_FROM, sPRICE_TO, "");
        } else {
            showAlertDialog("Thông báo", "Ngày đi phải nhỏ hơn ngày đến");
        }
    }
}
