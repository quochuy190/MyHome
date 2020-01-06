package com.vn.myhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.callback.setOnItemClickListener;
import com.vn.myhome.models.ObjReportDetail;
import com.vn.myhome.untils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterItemReportDetail extends RecyclerView.Adapter<AdapterItemReportDetail.FlightInfoViewHoder> {
    private List<ObjReportDetail> mList;
    private Context context;
    private setOnItemClickListener OnIListener;
    private ItemClickListener onListenerItemClickObjService;

    public setOnItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(setOnItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterItemReportDetail(ItemClickListener onListenerItemClickObjService) {
       // this.context = context;
        this.onListenerItemClickObjService = onListenerItemClickObjService;
    }


    @NonNull
    @Override
    public FlightInfoViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_report_detail, parent, false);
        return new FlightInfoViewHoder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightInfoViewHoder holder, final int position) {
        try {
            ObjReportDetail objService = mList.get(position);
            holder.txt_booking.setText(StringUtil.conventNumber(objService.getBOOK_PRICE()));
            holder.txt_chiphi_tong.setText(StringUtil.conventNumber(objService.getOTHER_COSTS()));
            holder.txt_loinhuan.setText(StringUtil.conventNumber(objService.getREVENUE()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    public class FlightInfoViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_booking)
        TextView txt_booking;
        @BindView(R.id.txt_chiphi_tong)
        TextView txt_chiphi_tong;
        @BindView(R.id.txt_loinhuan)
        TextView txt_loinhuan;
        public FlightInfoViewHoder(View itemView) {
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

    public void setData(List<ObjReportDetail> data, Context context) {
        this.context = context;
        mList = new ArrayList<>();
        if (mList != data) {
            mList = data;
            notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }
}
