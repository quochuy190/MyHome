package com.vn.myhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Created by QQ on 7/7/2017.
 */

public class AdapterHomeStay extends RecyclerView.Adapter<AdapterHomeStay.ViewHoderItem> {
    private static final String TAG = "AdapterHomeStay";
    private List<ObjHomeStay> mList;
    private Context context;
    private ItemClickListener OnIListener;
    public static final int ITEM_VIEW_HOLDER = 0;
    public static final int ITEM_VIEW_LOADMORE = 1;

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterHomeStay(List<ObjHomeStay> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public ViewHoderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_homestay_home, parent, false);
        return new ViewHoderItem(view);
    }

    @Override
    public void onBindViewHolder(ViewHoderItem holder, int position) {
        ObjHomeStay obj = mList.get(position);
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

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHoderItem extends RecyclerView.ViewHolder implements
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

        public ViewHoderItem(View itemView) {
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
    public void updateList(List<ObjHomeStay> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
