package com.vn.myhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.vn.myhome.R;
import com.vn.myhome.config.Config;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 17-August-2019
 * Time: 09:18
 * Version: 1.0
 */
public class AdapterSliderHome extends SliderViewAdapter<AdapterSliderHome.SliderAdapterVH> {
    private Context context;
    private List<String> mList;

    public AdapterSliderHome(Context context, List<String> url) {
        this.context = context;
        this.mList = url;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.imgviewpager, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        String sUrlImage = mList.get(position);
        try {
            String sUrl =  sUrlImage;
            URL url = new URL(sUrl);
            URI urlinfo = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
                    url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            url = urlinfo.toURL();
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.img_defaul)
                    .error(R.drawable.img_defaul);
            Glide.with(context).load(url).apply(options).into(viewHolder.imageViewBackground);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.imgviewpager);
            this.itemView = itemView;
        }
    }
}
