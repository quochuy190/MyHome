package com.vn.myhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.models.ObjSetupMain;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterSetupMain extends RecyclerView.Adapter<AdapterSetupMain.ViewHoderItem> {
    private List<ObjSetupMain> mList;
    private Context context;
    private ItemClickListener OnIListener;


    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterSetupMain(List<ObjSetupMain> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public ViewHoderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_setup_main, parent, false);
        return new ViewHoderItem(view);
    }

    @Override
    public void onBindViewHolder(ViewHoderItem holder, int position) {
        ObjSetupMain obj = mList.get(position);
        if (obj != null && obj.getsName().length() > 0)
            holder.txt_name.setText(obj.getsName());
        if (obj.getiIcon() > 0) {
            holder.icon_left_menu.setImageResource(obj.getiIcon());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHoderItem extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_name_setup)
        TextView txt_name;
        @BindView(R.id.img_setup_1)
        ImageView icon_left_menu;

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

}
