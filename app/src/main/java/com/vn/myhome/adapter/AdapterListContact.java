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
import com.vn.myhome.models.ContactObj;
import com.vn.myhome.untils.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterListContact extends RecyclerView.Adapter<AdapterListContact.TopicViewHoder> {
    private List<ContactObj> mList;
    private Context context;
    private ItemClickListener OnIListener;


    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterListContact(List<ContactObj> listAirport, Context context) {
        this.mList = listAirport;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contacts, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        try {
            ContactObj obj = mList.get(position);
            if (obj != null && obj.getNAME().length() > 0)
                holder.txt_name.setText(Html.fromHtml(StringUtil.convert_html(obj.getNAME())));
            holder.txt_cmt.setText(Html.fromHtml("Số CMT: <font color='#000000'>"+obj.getCMT()+"</font>"));
            holder.txt_phone.setText(Html.fromHtml("Điện thoại: <font color='#000000'>"+obj.getMOBILE()+"</font>"));
            holder.txt_ngaysinh.setText(Html.fromHtml("Ngày sinh: <font color='#000000'>"+obj.getDOB()+"</font>"));
            holder.txt_tinh.setText(Html.fromHtml("Tỉnh tp: <font color='#000000'>"+obj.getPROVINCE_NAME()+"</font>"));
            holder.txt_diachi.setText(Html.fromHtml("Địa chỉ: <font color='#000000'>"+obj.getADDRESS()+"</font>"));

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
        @BindView(R.id.txt_name_contact)
        TextView txt_name;
        @BindView(R.id.txt_cmt)
        TextView txt_cmt;
        @BindView(R.id.txt_phone)
        TextView txt_phone;
        @BindView(R.id.txt_ngaysinh)
        TextView txt_ngaysinh;
        @BindView(R.id.txt_email)
        TextView txt_email;
        @BindView(R.id.txt_tinh)
        TextView txt_tinh;
        @BindView(R.id.txt_diachi)
        TextView txt_diachi;
        @BindView(R.id.img_avata)
        CircleImageView img_avata;

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

    public void updateList(List<ContactObj> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
