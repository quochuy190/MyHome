package com.vn.myhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.models.AmenitiesObj;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 25-July-2019
 * Time: 15:59
 * Version: 1.0
 */
public class AdapterAmenities extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AmenitiesObj> mList;
    private ItemClickListener itemClickListener;
    private Context context;
    public static final int ITEM_VIEW_HOLDER = 0;
    public static final int ITEM_VIEW_LOADMORE = 1;

    public AdapterAmenities(List<AmenitiesObj> mList, Context mContext) {
        this.mList = mList;
        this.context = mContext;
    }

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ITEM_VIEW_HOLDER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_amenities,
                        parent, false);
                return new TopicViewHoder(view);
            case ITEM_VIEW_LOADMORE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hearder_amenities,
                        parent, false);
                return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderAll, int position) {
        AmenitiesObj obj = mList.get(position);
        if (!obj.isHeader()) {
            TopicViewHoder holder = (TopicViewHoder) holderAll;
            if (obj.getNAME_AMENITIES() != null) {
                holder.txt_name_amenities.setText(obj.getNAME_AMENITIES());
            }
            if (obj.isChecked()) {
                holder.img_checkbox_amenities.setImageResource(R.drawable.ic_checked);
            } else
                holder.img_checkbox_amenities.setImageResource(R.drawable.ic_checker);
        } else {
            LoadingViewHolder holder = (LoadingViewHolder) holderAll;
            if (obj.getDES() != null)
                holder.title_header.setText(obj.getDES());
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mList != null) {
            AmenitiesObj object = mList.get(position);
            if (object != null && object.isHeader()) {
                return ITEM_VIEW_LOADMORE;
            } else
                return ITEM_VIEW_HOLDER;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_name_amenities)
        TextView txt_name_amenities;
        @BindView(R.id.img_checkbox_amenities)
        ImageView img_checkbox_amenities;

        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClickItem(getLayoutPosition(), mList.get(getLayoutPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_header)
        TextView title_header;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
