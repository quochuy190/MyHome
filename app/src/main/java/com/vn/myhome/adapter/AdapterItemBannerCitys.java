package com.vn.myhome.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vn.myhome.R;
import com.vn.myhome.activity.home.ActivityListHomeStaySearch;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Config;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.untils.Utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterItemBannerCitys extends RecyclerView.Adapter<AdapterItemBannerCitys.ViewHoderItem> {
    private static final String TAG = "AdapterHomeStay";
    private List<ObjHomeStay> mList;
    private Context context;
    private ItemClickListener OnIListener;

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterItemBannerCitys(Context context) {
        this.context = context;
    }

    @Override
    public ViewHoderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_banner_city, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) (Utils.get_screen_height((Activity) context) * 0.38);
        view.setLayoutParams(layoutParams);
        return new ViewHoderItem(view);
    }

    @Override
    public void onBindViewHolder(ViewHoderItem holder, int position) {
        ObjHomeStay obj = mList.get(position);
        if (obj.getURL_IMAGE() != null) {
            try {
                String sUrl = Config.BASE_URL_MEDIA + obj.getURL_IMAGE();
                URL url = new URL(sUrl);
                URI urlinfo = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
                        url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                url = urlinfo.toURL();
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.img_defaul)
                        .error(R.drawable.img_defaul);
                Glide.with(context).load(url).apply(options).into(holder.img_city);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        } else
            Glide.with(context).load(R.drawable.img_defaul).into(holder.img_city);
        if (obj.getCITY_NAME() != null)
            holder.txt_name_city.setText(obj.getCITY_NAME());
        if (obj.getTONG() != null)
            holder.txt_total.setText(obj.getTONG() + " chỗ ở");

    }

    @Override
    public int getItemCount() {
        if (mList != null && mList.size() > 0)
            return mList.size();
        else return 0;
    }

    public class ViewHoderItem extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.img_city)
        ImageView img_city;
        @BindView(R.id.txt_name_city)
        TextView txt_name_city;
        @BindView(R.id.txt_total)
        TextView txt_total;

        public ViewHoderItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
         //   OnIListener.onClickItem(getLayoutPosition(), mList.get(getLayoutPosition()));
            Intent intent = new Intent(context, ActivityListHomeStaySearch.class);
            intent.putExtra(Constants.KEY_SEND_ID_PROVINCE, mList.get(getLayoutPosition()).getID_PROVINCE());
            intent.putExtra(Constants.KEY_SEND_NAME_PROVINCE, mList.get(getLayoutPosition()).getCITY_NAME());
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public void setData(List<ObjHomeStay> data, Context context) {
        this.context = context;
        mList = new ArrayList<>();
        if (mList != data) {
            mList = data;
            notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }
}
