package com.vn.myhome.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.untils.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterListHomestaySearch extends RecyclerView.Adapter<AdapterListHomestaySearch.TopicViewHoder> {
    private List<ObjHomeStay> mList;
    private Context context;
    private ItemClickListener OnIListener;


    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterListHomestaySearch(List<ObjHomeStay> listAirport, Context context) {
        this.mList = listAirport;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_listview, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        ObjHomeStay obj = mList.get(position);
        if (obj != null && obj.getNAME().length() > 0)
            holder.txt_name.setText(Html.fromHtml(StringUtil.convert_html(obj.getNAME())));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_name)
        TextView txt_name;

        public TopicViewHoder(View itemView) {
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
