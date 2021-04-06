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
import com.vn.myhome.callback.OnListenerItemClickObjService;
import com.vn.myhome.callback.setOnItemClickListener;
import com.vn.myhome.config.Config;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ImageCheckinService;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.StringUtil;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterSubImageService extends RecyclerView.Adapter<AdapterSubImageService.FlightInfoViewHoder> {
    private static final String TAG = "AdapterSubImageService";
    private List<ImageCheckinService> mLisObjService = new ArrayList<>();
    private Context context;
    private setOnItemClickListener OnIListener;
    private OnListenerItemClickObjService onListenerItemClickObjService;
    private String sType;

    public setOnItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(setOnItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterSubImageService(Context context, OnListenerItemClickObjService onListenerItemClickObjService) {
        this.context = context;
        this.onListenerItemClickObjService = onListenerItemClickObjService;
    }


    @NonNull
    @Override
    public FlightInfoViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_service, parent, false);
        return new FlightInfoViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightInfoViewHoder holder, final int position) {
        ImageCheckinService obj = mLisObjService.get(position);
        Log.d(TAG, "onBindViewHolder: " + obj.getID());
        if (obj.getID().equals("addImage")) {
            holder.ic_delete.setVisibility(View.GONE);
            Glide.with(context).load(R.drawable.add_image).into(holder.img_path);
            holder.img_path.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onListenerItemClickObjService.onItemAddImageClick(position, sType);
                }
            });
        } else {
            ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
            if (!objLogin.getUSER_TYPE().equals(Constants.UserType.CHUNHA)) {
                holder.ic_delete.setVisibility(View.VISIBLE);
            } else
                holder.ic_delete.setVisibility(View.GONE);

            holder.ic_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onListenerItemClickObjService.onClickListener(obj);
                }
            });
            holder.img_path.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onListenerItemClickObjService.doShowImageZom(position, sType);
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
    }


    @Override
    public int getItemCount() {
        return mLisObjService.size();
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
            // itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            //   OnIListener.OnItemClickListener(getLayoutPosition());
            // onListenerItemClickObjService.onClickListener(mLisObjService.get(getLayoutPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            //   OnIListener.OnLongItemClickListener(getLayoutPosition());
            return false;
        }
    }

    public void setData(List<ImageCheckinService> data, String type) {
        Log.d(TAG, "setData: ");
        if (mLisObjService != data) {
            mLisObjService = data;
            sType = type;
            notifyDataSetChanged();
        }
    }
}
