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
import com.vn.myhome.models.ObjNotify;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterListNotify extends RecyclerView.Adapter<AdapterListNotify.TopicViewHoder> {
    private List<ObjNotify> mList;
    private Context context;
    private ItemClickListener OnIListener;


    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterListNotify(List<ObjNotify> listAirport, Context context) {
        this.mList = listAirport;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notify, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        ObjNotify obj = mList.get(position);
        try {
            if (obj.getIS_READ().equals("0")) {
                String styledText = "<font color='#1263BB'><b>" + obj.getCONTENT() + "</b></font>";
                holder.txt_content.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                String title = "<font color='#1263BB'><b>" + obj.getTITLE() + "</b></font>";
                holder.txt_title.setText(Html.fromHtml(title), TextView.BufferType.SPANNABLE);

                //  holder.txt_content.setText(Html.fromHtml(StringUtil.convert_html(obj.getCONTENT())));
            } else {
                String styledText = "<font color='#000000'>" + obj.getCONTENT() + "</font>";
                holder.txt_content.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                String title = "<font color='#000000'><b>" + obj.getTITLE() + "</b></font>";
                holder.txt_title.setText(Html.fromHtml(title), TextView.BufferType.SPANNABLE);
                //holder.txt_content.setText(Html.fromHtml(StringUtil.convert_html(obj.getCONTENT())));
            }
            holder.txt_date_time.setText(obj.getUPDATE_TIME());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_title)
        TextView txt_title;
        @BindView(R.id.txt_content)
        TextView txt_content;
        @BindView(R.id.txt_date_time)
        TextView txt_date_time;

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

    public void updateList(List<ObjNotify> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
