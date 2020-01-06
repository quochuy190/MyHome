package com.vn.myhome.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.callback.ItemClickListenerTabDate;
import com.vn.myhome.callback.setOnItemClickListener;
import com.vn.myhome.models.ObjDayCustom;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 12-October-2019
 * Time: 09:58
 * Version: 1.0
 */
public class AdapterItemTabDayDetail extends RecyclerView.Adapter<AdapterItemTabDayDetail.CustomDayViewHolder> {
    private List<ObjDayCustom> mList;
    private Context context;
    private setOnItemClickListener OnIListener;
    private ItemClickListenerTabDate onListenerItemClickObjService;

    public setOnItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(setOnItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterItemTabDayDetail(Context context, ItemClickListenerTabDate onListenerItemClickObjService) {
        this.context = context;
        this.onListenerItemClickObjService = onListenerItemClickObjService;
    }

    @NonNull
    @Override
    public CustomDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day_detail_datphong, parent, false);
        return new CustomDayViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CustomDayViewHolder holder, int position) {
        try {
            String sDateStartNext = "";
            ObjDayCustom objService = mList.get(position);
            if (objService != null) {
                if (objService.getsDay() != null) {
                    holder.txt_day.setText("" + objService.getiDayofMonth());
                }
                if (objService.getCONTENT() != null) {
                    holder.ic_lock.setVisibility(View.GONE);
                    if (objService.getBOOK_NAME() != null) {
                        holder.txt_name_booker.setText("Người đặt: " + objService.getBOOK_NAME());
                    }
                    if (objService.getBILLING_STATUS() != null) {
                        holder.txt_status_pay.setVisibility(View.VISIBLE);
                        if (objService.getBILLING_STATUS().equals("2")) {
                            String styledText = "Trạng thái thanh toán: " +
                                    "<font color='#009900'><b>Đã thanh toán</b></font>";
                            holder.txt_status_pay.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                            // holder.txt_status_pay.setText("Trạng thái thanh toán: Đã thanh toán");
                        } else {
                            String styledText = "Trạng thái thanh toán: <font color='#FF3300'><b>Chưa thanh toán</b></font>.";
                            holder.txt_status_pay.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                            //   holder.txt_status_pay.setText("Trạng thái thanh toán: Chưa thanh toán");
                        }
                    } else
                        holder.txt_status_pay.setVisibility(View.GONE);
                    if (objService.getBOOK_STATUS() != null) {
                        holder.txt_status_room.setVisibility(View.VISIBLE);
                        if (objService.getBOOK_STATUS().equals("2")) {
                            holder.txt_status_room.setText("Trạng thái khóa nhà: Đã khóa");
                        } else {
                            holder.txt_status_room.setText("Trạng thái khóa nhà: Đã duyệt");
                        }
                    } else
                        holder.txt_status_room.setVisibility(View.GONE);
                    holder.txt_status_room.setText("Trạng thái khóa nhà: "+objService.getBOOK_STATUS_NAME());
                } else {
                    if (objService.getBOOK_NAME() != null) {
                        holder.ic_lock.setVisibility(View.VISIBLE);
                        holder.txt_name_booker.setText("Chủ nhà tự đặt");
                        holder.txt_status_room.setVisibility(View.VISIBLE);
                        holder.txt_status_room.setText("(Bấm để mở khóa)");
                    }
                }
            }
            holder.ic_lock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onListenerItemClickObjService.onClickItem_Lock(position, objService);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CustomDayViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_day)
        TextView txt_day;
        @BindView(R.id.txt_name_booker)
        TextView txt_name_booker;
        @BindView(R.id.txt_status_room)
        TextView txt_status_room;
        @BindView(R.id.txt_status_pay)
        TextView txt_status_pay;
        @BindView(R.id.ic_lock)
        ImageView ic_lock;


        public CustomDayViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListenerItemClickObjService.onClickItem(getLayoutPosition(), mList.get(getLayoutPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            //  OnIListener.OnLongItemClickListener(getLayoutPosition());
            return false;
        }
    }

    public void setData(List<ObjDayCustom> data) {
        mList = new ArrayList<>();
        if (mList != data) {
            mList = data;
            notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }
}
