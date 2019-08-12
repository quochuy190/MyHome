package com.vn.myhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.vn.myhome.R;
import com.vn.myhome.callback.ItemClickListener;

import java.util.List;

/**
 * Created by QQ on 2/20/2017.
 */

public class AdapterViewPagerHomeImage extends PagerAdapter {
    Context context;
    int resorce;
    List<String> list;
    ItemClickListener itemClickListener;

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public AdapterViewPagerHomeImage(List<String> list, int resorce, Context context) {
        this.list = list;
        this.resorce = resorce;
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView;
        LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
        View itemView = layoutInflater.inflate(resorce, container, false);
        imageView = (ImageView) itemView.findViewById(R.id.imgviewpager);
        //imgView.setImageResource(m_objImageList.get(position).getiImage());
        Glide.with(context).load(list.get(position)).into(imageView);
        container.addView(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClickItem(position, list.get(position));
            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout) object;
    }
}
