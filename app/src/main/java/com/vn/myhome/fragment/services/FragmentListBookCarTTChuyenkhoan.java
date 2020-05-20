package com.vn.myhome.fragment.services;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.activity.book_room.ActivityThongtinChuyenkhoan;
import com.vn.myhome.adapter.AdapterListBookCar;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.callback.setOnItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjListBookCar;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ResponseApi.ResponseBookCarDetail;
import com.vn.myhome.models.ResponseApi.ResponsePriceEstimates;
import com.vn.myhome.models.ResponseApi.RouteResponse;
import com.vn.myhome.network.response.ResponGetListBookCar;
import com.vn.myhome.untils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 19-April-2020
 * Time: 10:09
 * Version: 1.0
 */
public class FragmentListBookCarTTChuyenkhoan extends BaseFragment implements InterfaceServices.View {
    private static final String TAG = "FragmentDanhsachDatxe";
    public static FragmentListBookCarTTChuyenkhoan fragment;

    public static FragmentListBookCarTTChuyenkhoan getInstance() {
        if (fragment == null) {
            synchronized (FragmentListBookCarTTChuyenkhoan.class) {
                if (fragment == null)
                    fragment = new FragmentListBookCarTTChuyenkhoan();
            }
        }
        return fragment;
    }

    PresenterServices mPresenter;
    private List<ObjListBookCar> mList;
    private AdapterListBookCar adapterService;
    @BindView(R.id.rcv_lich_datxe)
    RecyclerView rcv_lich_datxe;
    @BindView(R.id.txt_thongbao)
    TextView txt_thongbao;
    RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lichdatxe, container, false);
        ButterKnife.bind(this, view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mPresenter = new PresenterServices(this);
        init();
        initData();
        return view;
    }

    String sUserName;

    private void initData() {
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        txt_thongbao.setVisibility(View.INVISIBLE);
        mPresenter.api_get_list_bookcar_pre(sUserName);
        //mPresenter.api_get_list_bookcar_pre(sUserName);
    }

    private void init() {
        mList = new ArrayList<>();
        adapterService = new AdapterListBookCar(mList, getContext(), true);
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        rcv_lich_datxe.setNestedScrollingEnabled(false);
        rcv_lich_datxe.setHasFixedSize(true);
        rcv_lich_datxe.setLayoutManager(mLayoutManager);
        rcv_lich_datxe.setItemAnimator(new DefaultItemAnimator());
        rcv_lich_datxe.setAdapter(adapterService);
        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                ObjListBookCar objListBookCar = (ObjListBookCar) item;
                Intent intent = new Intent(getContext(), ActivityBookCarDetail.class);
                intent.putExtra(Constants.KEY_SEND_ID_BOOKCAR, objListBookCar.getId());
                startActivity(intent);
            }
        });
        adapterService.setClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
                ObjListBookCar obj = mList.get(position);
                if (!objLogin.getUSER_TYPE().equals(Constants.UserType.ADMIN)) {
                    Intent intent = new Intent(getContext(), ActivityThongtinChuyenkhoan.class);
                    intent.putExtra(Constants.KEY_SEND_PRICE_THANHTOAN, "" + obj.getTOTAL_PRICE());
                    intent.putExtra(Constants.KEY_SEND_CONTENT_THANHTOAN, obj.getBILL_CODE());
                    startActivityForResult(intent, Constants.RequestCode.GET_BOOKING);
                } else{
                    if (obj.getID()!=null){
                        showDialogLoading();
                        mPresenter.api_update_billing(sUserName, obj.getID());
                    }

                }




            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });

    }

    @Override
    public void show_error_api(String sService) {

    }

    @Override
    public void show_get_type_car(RouteResponse objError) {

    }

    @Override
    public void show_get_route_car(RouteResponse objError) {

    }

    @Override
    public void show_get_price_estimates(ResponsePriceEstimates objRes) {

    }

    @Override
    public void show_bookcar(ObjErrorApi objResCity) {

    }

    @Override
    public void show_get_bookcar_detail(ResponseBookCarDetail objError) {

    }


    @Override
    public void show_list_book_car(ResponGetListBookCar objError) {


    }

    @Override
    public void show_list_book_car_pre(ResponGetListBookCar objError) {
        hideDialogLoading();
        if (objError.getERROR().equals("0000")) {
            mList.clear();
            txt_thongbao.setVisibility(View.INVISIBLE);
            mList.addAll(objError.getINFO());
            adapterService.notifyDataSetChanged();
        } else {
            txt_thongbao.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void show_update_billing(ObjErrorApi objError) {

        if (objError.getERROR().equals("0000")){
            initData();
            Toast.makeText(getContext(), "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show();
        }else{
            showAlertDialog("Thông báo", objError.getRESULT());
        }

    }
}
