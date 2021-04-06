package com.vn.myhome.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vn.myhome.R;
import com.vn.myhome.callback.setOnItemClickListener;
import com.vn.myhome.config.Config;
import com.vn.myhome.models.ObjImageHome;
import com.vn.myhome.untils.StringUtil;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterReviewImageService extends RecyclerView.Adapter<AdapterReviewImageService.FlightInfoViewHoder> {
    private static final String TAG = "AdapterSubImageService";
    private List<ObjImageHome> mList;
    private Context context;
    private setOnItemClickListener OnIListener;

    public setOnItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(setOnItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterReviewImageService(List<ObjImageHome> mLisObjService, Context context) {
        this.mList = mLisObjService;
        this.context = context;
    }

    @NonNull
    @Override
    public FlightInfoViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review_image_service, parent, false);
        return new FlightInfoViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightInfoViewHoder holder, final int position) {
        ObjImageHome obj = mList.get(position);
        Log.d(TAG, "onBindViewHolder: "+obj.getID());
        if (obj.getsPath()==null) {
            holder.ic_delete.setVisibility(View.GONE);
            Glide.with(context).load(R.drawable.add_image).into(holder.img_path);
            holder.img_path.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnIListener.OnItemClickListener(position);
                }
            });
        } else {
            holder.ic_delete.setVisibility(View.VISIBLE);
            holder.ic_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnIListener.OnLongItemClickListener(position);
                }
            });
            try {
                if (obj.getsPath() != null) {
                    File imgFile = new File(obj.getsPath());
                    if (imgFile.exists()) {
                        //  Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        Bitmap myBitmap =
                                StringUtil.decodeSampledBitmapFromUri(context,
                                        Uri.fromFile(new File(imgFile.getAbsolutePath())), 500, 500);
                        holder.img_path.setImageBitmap(myBitmap);
                    }
                } else if (obj.getIMG() != null) {
                    String sUrl = Config.BASE_URL_MEDIA + obj.getIMG();
                    URL url = new URL(sUrl);
                    URI urlinfo = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
                            url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                    url = urlinfo.toURL();
                    RequestOptions options = new RequestOptions()
                            .placeholder(R.drawable.img_defaul)
                            .error(R.drawable.img_defaul);
                    Glide.with(context).load(url).apply(options).into(holder.img_path);

                } else
                    Glide.with(context).load(R.drawable.img_defaul).into(holder.img_path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*holder.txt_more_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListenerItemClickObjService.onItemXemthemClick(mLisObjService.get(position));
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class FlightInfoViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.img_service_checkin)
        ImageView img_path;
        @BindView(R.id.ic_delete)
        ImageView ic_delete;

        public FlightInfoViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            //   OnIListener.OnItemClickListener(getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            //   OnIListener.OnLongItemClickListener(getLayoutPosition());
            return false;
        }
    }
}
