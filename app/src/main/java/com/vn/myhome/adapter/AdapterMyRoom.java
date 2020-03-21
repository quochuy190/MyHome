package com.vn.myhome.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vn.myhome.R;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.callback.setOnItemClickListener;
import com.vn.myhome.config.Config;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.untils.SharedPrefs;
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

public class AdapterMyRoom extends RecyclerView.Adapter<AdapterMyRoom.ViewHoderItem> {
    private static final String TAG = "AdapterHomeStay";
    private List<ObjHomeStay> mList;
    private Context context;
    private ItemClickListener click_namehost;
    private setOnItemClickListener onItemClickListener;


    public setOnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(setOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ItemClickListener getClick_namehost() {
        return click_namehost;
    }

    public void setClick_namehost(ItemClickListener click_namehost) {
        this.click_namehost = click_namehost;
    }

    public AdapterMyRoom(List<ObjHomeStay> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public ViewHoderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_room, parent, false);
        return new ViewHoderItem(view);
    }

    @Override
    public void onBindViewHolder(ViewHoderItem holder, int position) {
        ObjHomeStay obj = mList.get(position);
        if (obj != null && obj.getNAME().length() > 0)
            holder.txt_name.setText(obj.getNAME());
        if (obj != null && obj.getADDRESS().length() > 0)
            holder.txt_address.setText("Địa chỉ: " + obj.getADDRESS());
        else
            holder.txt_address.setText("Địa chỉ: ...");
        if (obj != null && obj.getNAME_HOST() != null)
            holder.txt_hostname.setText(Html.fromHtml("<u>" + obj.getNAME_HOST() + "</u>"));
        else
            holder.txt_hostname.setText("");
        if (obj != null && obj.getSTATE() != null) {
            if (obj.getSTATE().equals("6")) {
                holder.btn_status.setTextColor(context.getResources().getColor(R.color.White));
                holder.btn_status.setText("Đang chờ duyệt");
                holder.btn_status.setBackgroundColor(context.getResources().getColor(R.color.Orange));
                holder.btn_duyetphong.setText("Duyệt phòng");
            } else if (obj.getSTATE().equals("7")) {
                holder.btn_status.setText("Đã duyệt");
                holder.btn_status.setBackgroundColor(context.getResources().getColor(R.color.app_main));
                holder.btn_duyetphong.setText("Hủy duyệt");
            }
        }
        if (obj != null && obj.getPRICE().length() > 0)
            holder.txt_price.setText(StringUtil.conventMonney_Long(obj.getPRICE()));
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
        holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClickListener(position);
            }
        });
        holder.btn_duyetphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnLongItemClickListener(position);
            }
        });
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        if (objLogin.getUSER_TYPE().equals(Constants.UserType.ADMIN)) {
         /*   if (obj.getSTATE().equals("6")) {
                holder.btn_update.setVisibility(View.VISIBLE);
            } else if (obj.getSTATE().equals("7")) {
                holder.btn_update.setVisibility(View.GONE);
            }*/
            holder.btn_duyetphong.setVisibility(View.VISIBLE);
            if (obj != null && obj.getSTATE() != null) {
                if (obj.getSTATE().equals("6")) {
                    holder.btn_update.setVisibility(View.VISIBLE);
                } else {
                    holder.btn_update.setVisibility(View.INVISIBLE);
                }
            }
        } else {
            holder.btn_duyetphong.setText("Giảm giá");
            holder.btn_duyetphong.setVisibility(View.VISIBLE);
        }
        holder.txt_hostname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click_namehost.onClickItem(position, mList.get(position));
            }
        });
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
        @BindView(R.id.txt_hostname)
        TextView txt_hostname;
        @BindView(R.id.txt_price)
        TextView txt_price;
        @BindView(R.id.txt_status)
        TextView btn_status;
        @BindView(R.id.btn_duyetphong)
        Button btn_duyetphong;
        @BindView(R.id.btn_update)
        Button btn_update;
        @BindView(R.id.img_homestay)
        ImageView img_homestay;

        public ViewHoderItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

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
