package com.vn.myhome.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.activity.home.ActivityListHomestayShowAll;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjFragmentSearchHome;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterFragmentSearchHome
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ObjFragmentSearchHome> mList;
    private Context mContext;
    private static final int ITEM_BANNER_CITY = 0;
    private static final int ITEM_LIST_ROOM = 1;

    public AdapterFragmentSearchHome(List<ObjFragmentSearchHome> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

 /* @NonNull
    @Override
    public ViewHoderFragmentSearchHome onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_BANNER_CITY) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_seach_home,
                    parent, false);
            return new ViewHoderFragmentSearchHome(view);
        } else if (viewType == ITEM_LIST_ROOM)  {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_seach_home,
                    parent, false);
            return new ViewHoderLisHomestay(view);
        }
        return null;


    }*/

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_BANNER_CITY) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_seach_home,
                    parent, false);
            return new ViewHoderFragmentSearchHome(view);
        } else if (viewType == ITEM_LIST_ROOM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_seach_home,
                    parent, false);
            return new ViewHoderLisHomestay(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ObjFragmentSearchHome obj = mList.get(position);
        if (obj != null && obj.getTITLE().equals(Constants.DIEM_DEN_YEU_THICH)) {
            ViewHoderFragmentSearchHome holderBanner = (ViewHoderFragmentSearchHome) holder;
            if (obj.getmListHomeStay() != null)
                holderBanner.bannerCityAdapter.setData(obj.getmListHomeStay(), mContext);
            if (obj.getTITLE() != null)
                holderBanner.title_header_city.setText(obj.getTITLE());
        } else {
            ViewHoderLisHomestay holderListRoom = (ViewHoderLisHomestay) holder;
            holderListRoom.txt_xemthem.setVisibility(View.VISIBLE);
            if (obj.getmListHomeStay() != null)
                holderListRoom.adapterListHomestay.setData(obj.getmListHomeStay(), mContext);
            if (obj.getTITLE() != null)
                holderListRoom.title_header_city.setText(obj.getTITLE());
            holderListRoom.txt_xemthem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ActivityListHomestayShowAll.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    /*@Override
    public void onBindViewHolder(@NonNull ViewHoderFragmentSearchHome holder, int position) {
        holder.bannerCityAdapter.setData(mSearchHome.getmListCity(),mContext);
        holder.adapterListHomestay.setData(mSearchHome.getmListHomeStay(),mContext);
        holder.title_header_city.setText(mSearchHome.getTITLE_BANNER_CITY());
       // holder.title_header_room.setText(mSearchHome.getTITLE_LIST_ROOM());
    }*/

    @Override
    public int getItemViewType(int position) {
        if (mList != null) {
            ObjFragmentSearchHome object = mList.get(position);
            if (object != null && object.getTITLE().equals(Constants.DIEM_DEN_YEU_THICH)) {
                return ITEM_BANNER_CITY;
            } else
                return ITEM_LIST_ROOM;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHoderFragmentSearchHome extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.title_header_city)
        TextView title_header_city;
        @BindView(R.id.rcv_banner_city)
        RecyclerView rcv_banner_city;
        private AdapterItemBannerCitys bannerCityAdapter;
        private AdapterHomeStay adapterListHomestay;

        public ViewHoderFragmentSearchHome(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            // itemView.setOnClickListener(this);
            rcv_banner_city.setLayoutManager(new GridLayoutManager(mContext, 1, GridLayoutManager.HORIZONTAL,
                    false));
            bannerCityAdapter = new AdapterItemBannerCitys(mContext);
            rcv_banner_city.setAdapter(bannerCityAdapter);

          /*  rcv_list_room.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL,
                    false));
            adapterListHomestay = new AdapterHomeStay( mContext);
            rcv_list_room.setAdapter(adapterListHomestay);*/
        }

        @Override
        public void onClick(View v) {
            //OnIListener.onClickItem(getLayoutPosition(), mList.get(getLayoutPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public class ViewHoderLisHomestay extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.title_header_city)
        TextView title_header_city;
        @BindView(R.id.txt_xemthem)
        TextView txt_xemthem;
        @BindView(R.id.rcv_banner_city)
        RecyclerView rcv_banner_city;
        private AdapterItemBannerCitys bannerCityAdapter;
        private AdapterHomeStaySmall adapterListHomestay;

        public ViewHoderLisHomestay(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            // itemView.setOnClickListener(this);
            rcv_banner_city.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL,
                    false));
            adapterListHomestay = new AdapterHomeStaySmall(mContext);
            rcv_banner_city.setAdapter(adapterListHomestay);

        }

        @Override
        public void onClick(View v) {
            //OnIListener.onClickItem(getLayoutPosition(), mList.get(getLayoutPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

}
