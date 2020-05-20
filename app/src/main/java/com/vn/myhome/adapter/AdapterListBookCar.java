package com.vn.myhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.callback.setOnItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjListBookCar;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.StringUtil;
import com.vn.myhome.untils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterListBookCar extends RecyclerView.Adapter<AdapterListBookCar.TopicViewHoder> {
    private List<ObjListBookCar> mList;
    private Context context;
    private ItemClickListener OnIListener;
    private setOnItemClickListener ClickListener;
    private boolean isChuyenkhoan;

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public setOnItemClickListener getClickListener() {
        return ClickListener;
    }

    public void setClickListener(setOnItemClickListener clickListener) {
        ClickListener = clickListener;
    }

    public AdapterListBookCar(List<ObjListBookCar> listAirport, Context context) {
        this.mList = listAirport;
        this.context = context;
    }

    public AdapterListBookCar(List<ObjListBookCar> mList, Context context, boolean isChuyenkhoan) {
        this.mList = mList;
        this.context = context;
        this.isChuyenkhoan = isChuyenkhoan;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_book_car, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        ObjListBookCar obj = mList.get(position);
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        try {
            if (isChuyenkhoan) {
                holder.txt_hanhtrinh_.setText("Trạng thái thanh toán");
                holder.btn_duyet_bookcar.setVisibility(View.VISIBLE);
                holder.txt_state.setVisibility(View.INVISIBLE);
                if (obj.getBILL_CODE() != null)
                    holder.txt_madh.setText("Mã ĐH: " + obj.getBILL_CODE());
                else
                    holder.txt_madh.setText("Mã ĐH: " + obj.getID_BOOK());
                if (obj.getBILL_STATUS() != null && obj.getBILL_STATUS().equals("1")) {
                    holder.btn_duyet_bookcar.setVisibility(View.GONE);
                    holder.txt_nguoidat.setText(obj.getBILL_NAME());
                    holder.txt_nguoidat.setTextColor(context.getResources().getColor(R.color.Green));
                } else {
                    holder.txt_nguoidat.setText(obj.getBILL_NAME());
                    holder.txt_nguoidat.setTextColor(context.getResources().getColor(R.color.OrangeRed));
                    holder.btn_duyet_bookcar.setVisibility(View.VISIBLE);
                }

                holder.txt_tongtien.setText(StringUtil.conventMonney_Long(obj.getTOTAL_PRICE()));
                holder.txt_name_customer.setText(obj.getCUSTOMER_NAME());
                holder.txt_phone_customer.setText(obj.getCUSTOMER_TEL());
                holder.txt_thoigian.setText(TimeUtils.convent_date(obj.getTIME_SCHEDULE(),
                        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd/MM/yyyy HH:mm"));
                if (!objLogin.getUSER_TYPE().equals(Constants.UserType.ADMIN)) {
                    holder.btn_duyet_bookcar.setText("Thông tin Chuyển khoản");
                } else
                    holder.btn_duyet_bookcar.setText("Duyệt đơn");

                holder.btn_duyet_bookcar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClickListener.OnItemClickListener(position);
                    }
                });
            } else {
                holder.btn_duyet_bookcar.setVisibility(View.GONE);
                switch (obj.getState()) {
                    case "1":
                        holder.txt_state.setVisibility(View.VISIBLE);
                        holder.txt_state.setBackground(context.getResources().getDrawable(R.drawable.spr_btn_login));
                        holder.txt_state.setText("Đã có lái xe tiếp nhận");
                        break;
                    case "6":
                        holder.txt_state.setVisibility(View.VISIBLE);
                        holder.txt_state.setBackground(context.getResources().getDrawable(R.drawable.spr_btn_orange));
                        holder.txt_state.setText("Đã hoàn thành chuyến đi");
                        break;
                    case "-1":
                        holder.txt_state.setVisibility(View.VISIBLE);
                        holder.txt_state.setText("Khách hàng hủy đơn");
                        break;
                    case "5":
                        holder.txt_state.setVisibility(View.VISIBLE);
                        holder.txt_state.setText("Không tìm được lái xe phù hợp");
                        break;

                }
                holder.txt_madh.setText("Mã ĐH: " + obj.getId());
                holder.txt_nguoidat.setText(obj.getRoute_name());
                holder.txt_thoigian.setText(TimeUtils.convent_date(obj.getAppoint_date(),
                        "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm"));
                holder.txt_tongtien.setText(StringUtil.conventMonney_Long(obj.getCost()));
                holder.txt_name_customer.setText(obj.getCustomer_name());
                holder.txt_phone_customer.setText(obj.getCustomer_phone());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        else return 0;
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_madh)
        TextView txt_madh;
        @BindView(R.id.txt_hanhtrinh_)
        TextView txt_hanhtrinh_;
        @BindView(R.id.txt_nguoidat)
        TextView txt_nguoidat;
        @BindView(R.id.txt_hanhtrinh)
        TextView txt_hanhtrinh;
        @BindView(R.id.txt_thoigian)
        TextView txt_thoigian;
        @BindView(R.id.txt_tongtien)
        TextView txt_tongtien;
        @BindView(R.id.txt_name_customer)
        TextView txt_name_customer;
        @BindView(R.id.txt_phone_customer)
        TextView txt_phone_customer;
        @BindView(R.id.txt_state)
        TextView txt_state;
        @BindView(R.id.btn_duyet_bookcar)
        Button btn_duyet_bookcar;

        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            OnIListener.onClickItem(getLayoutPosition(), mList.get(getLayoutPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public void updateList(List<ObjListBookCar> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
