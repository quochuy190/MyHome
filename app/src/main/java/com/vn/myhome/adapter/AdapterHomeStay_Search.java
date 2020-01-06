package com.vn.myhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vn.myhome.R;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Config;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.untils.StringUtil;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
public class AdapterHomeStay_Search extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ObjHomeStay> mList;
    private ItemClickListener itemClickListener;
    private Context context;
    public static final int ITEM_VIEW_HOLDER = 0;
    public static final int ITEM_VIEW_LOADMORE = 1;

    public AdapterHomeStay_Search(List<ObjHomeStay> mList, Context mContext) {
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homestay_home,
                        parent, false);
                return new TopicViewHoder(view);
            case ITEM_VIEW_LOADMORE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,
                        parent, false);
                return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderAll, int position) {
        try {
            ObjHomeStay obj = mList.get(position);
            if (obj == null) {
                ((LoadingViewHolder) holderAll).progressBar.setVisibility(View.VISIBLE);
            } else {
                TopicViewHoder holder = (TopicViewHoder) holderAll;
                if (obj != null) {
                    if (obj != null && obj.getNAME().length() > 0)
                        holder.txt_name.setText(obj.getNAME());
                    if (obj != null && obj.getPRICE().length() > 0)
                        holder.txt_price.setText(StringUtil.conventMonney_Long(obj.getPRICE()));
                    if (obj != null && obj.getADDRESS().length() > 0)
                        holder.txt_address.setText(obj.getADDRESS());
                    if (obj.getCOVER() != null) {
                        try {
                            String sUrl = Config.BASE_URL_MEDIA + obj.getCOVER();
                            URL url = new URL(sUrl);
                            URI urlinfo = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
                                    url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                            url = urlinfo.toURL();
                            RequestOptions options = new RequestOptions()
                                    .placeholder(R.drawable.img_defaul)
                                    .error(R.drawable.img_defaul);
                            Glide.with(context).load(url).apply(options).into(holder.img_homestay);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }

                    } else
                        Glide.with(context).load(R.drawable.img_defaul).into(holder.img_homestay);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (mList != null) {
            ObjHomeStay object = mList.get(position);
            if (object != null) {
                return ITEM_VIEW_HOLDER;
            } else
                return ITEM_VIEW_LOADMORE;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_name_homestay)
        TextView txt_name;
        @BindView(R.id.txt_address)
        TextView txt_address;
        @BindView(R.id.txt_price)
        TextView txt_price;
        @BindView(R.id.txt_status)
        TextView txt_status;
        @BindView(R.id.img_homestay)
        ImageView img_homestay;

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

        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }
}
