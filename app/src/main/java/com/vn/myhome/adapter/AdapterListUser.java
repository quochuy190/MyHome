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
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.untils.StringUtil;
import com.vn.myhome.untils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterListUser extends RecyclerView.Adapter<AdapterListUser.TopicViewHoder> {
    private List<ObjLogin> mList;
    private Context context;
    private ItemClickListener OnIListener;


    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterListUser(List<ObjLogin> listAirport, Context context) {
        this.mList = listAirport;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        try {

            ObjLogin obj = mList.get(position);
            if (obj != null && obj.getFULL_NAME().length() > 0)
                holder.txt_name.setText(Html.fromHtml(StringUtil.convert_html(obj.getFULL_NAME())));
            holder.txt_username_user.setText(Html.fromHtml
                    ("User name: <font color='#000000'>" + obj.getUSERID() + "</font>"));
            holder.txt_phone.setText(Html.fromHtml("Điện thoại: <font color='#000000'>" + obj.getMOBILE() + "</font>"));
            holder.txt_ngaysinh.setText(Html.fromHtml
                    ("Ngày sinh: <font color='#000000'>" + TimeUtils.convent_date(obj.getDOB(),
                            "yyyy-MM-dd'T'HH:mm:ss.'000Z'" ,"dd/MM/yyyy") + "</font>"));

            holder.txt_diachi.setText(Html.fromHtml("Địa chỉ: <font color='#000000'>" + obj.getADDRESS() + "</font>"));
            holder.txt_email.setText(Html.fromHtml("Email: <font color='#000000'>" + obj.getEMAIL() + "</font>"));
            switch (obj.getUSER_TYPE()) {
                case Constants.UserType.ADMIN:
                    holder.txt_type_user.setText(Html.fromHtml
                            ("User type: <font color='#000000'>Admin</font>"));
                    break;
                case Constants.UserType.CHUNHA:
                    holder.txt_type_user.setText(Html.fromHtml
                            ("User type: <font color='#000000'>Chủ nhà</font>"));
                    break;
                case Constants.UserType.CTV:
                    holder.txt_type_user.setText(Html.fromHtml
                            ("User type: <font color='#000000'>Cộng tác viên</font>"));
                    break;
                case Constants.UserType.DICHVU:
                    holder.txt_type_user.setText(Html.fromHtml
                            ("User type: <font color='#000000'>Dịch vụ</font>"));
                    break;
                case Constants.UserType.GOLD:
                    holder.txt_type_user.setText(Html.fromHtml
                            ("User type: <font color='#000000'>Supper Gold</font>"));
                    break;
                case Constants.UserType.CHECK_IN:
                    holder.txt_type_user.setText(Html.fromHtml
                            ("User type: <font color='#000000'>Check - in</font>"));
                    break;
            }
            if (obj.getSTATE().equals("1")) {
                holder.txt_status.setBackgroundColor(context.getResources().getColor(R.color.NeoColorXanhHoangGia));
                holder.txt_status.setText("Đã kích hoạt");
            } else if (obj.getSTATE().equals("0")) {
                holder.txt_status.setBackgroundColor(context.getResources().getColor(R.color.progress_bar_line));
                holder.txt_status.setText("Đang khóa");
            }
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
        @BindView(R.id.txt_name_user)
        TextView txt_name;
        @BindView(R.id.txt_username_user)
        TextView txt_username_user;
        @BindView(R.id.txt_phone)
        TextView txt_phone;
        @BindView(R.id.txt_birthday)
        TextView txt_ngaysinh;
        @BindView(R.id.txt_email)
        TextView txt_email;
        @BindView(R.id.txt_type_user)
        TextView txt_type_user;
        @BindView(R.id.txt_diachi)
        TextView txt_diachi;
        @BindView(R.id.txt_status)
        TextView txt_status;
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

    public void updateList(List<ObjLogin> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
