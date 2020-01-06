package com.vn.myhome.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vn.myhome.R;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.callback.OnItemClickListennerTwoBtn;
import com.vn.myhome.config.Config;
import com.vn.myhome.models.ObjImageHome;

import java.io.File;
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

public class AdapterImagePath extends RecyclerView.Adapter<AdapterImagePath.ViewHoderItem> {
    private static final String TAG = "AdapterHomeStay";
    private List<ObjImageHome> mList;
    private Context context;
    private ItemClickListener OnIListener;
    private OnItemClickListennerTwoBtn click_delete;

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public OnItemClickListennerTwoBtn getClick_delete() {
        return click_delete;
    }

    public void setClick_delete(OnItemClickListennerTwoBtn click_delete) {
        this.click_delete = click_delete;
    }

    public AdapterImagePath(List<ObjImageHome> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public ViewHoderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_path, parent, false);
        return new ViewHoderItem(view);
    }

    @Override
    public void onBindViewHolder(ViewHoderItem holder, int position) {
        ObjImageHome obj = mList.get(position);
        if (obj == null) {
            holder.ic_delete.setVisibility(View.GONE);
            Glide.with(context).load(R.drawable.add_image).into(holder.img_path);
        } else {
            holder.ic_delete.setVisibility(View.VISIBLE);
           /* if (obj.getsPath() != null) {
                holder.ic_delete.setVisibility(View.VISIBLE);
            } else
                holder.ic_delete.setVisibility(View.GONE);*/
            try {
                if (obj.getsPath() != null) {
                    File imgFile = new File(mList.get(position).getsPath());
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        holder.img_path.setImageBitmap(myBitmap);
                    }
                } else {
                    String sUrl = Config.BASE_URL_MEDIA + obj.getIMG();
                    URL url = new URL(sUrl);
                    URI urlinfo = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
                            url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                    url = urlinfo.toURL();
                    RequestOptions options = new RequestOptions()
                            .placeholder(R.drawable.img_defaul)
                            .error(R.drawable.img_defaul);
                    Glide.with(context).load(url).apply(options).into(holder.img_path);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            holder.ic_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click_delete.OnItemClickListener_One_Btn(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHoderItem extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.img_path)
        ImageView img_path;
        @BindView(R.id.ic_delete)
        ImageView ic_delete;

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

    public void updateList(List<ObjImageHome> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
