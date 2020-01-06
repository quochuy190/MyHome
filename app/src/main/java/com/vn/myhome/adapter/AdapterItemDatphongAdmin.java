package com.vn.myhome.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.activity.home.ActivityRoomDetail;
import com.vn.myhome.callback.OnItemClickListennerTwoBtn;
import com.vn.myhome.callback.setOnItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjBooking;
import com.vn.myhome.models.ObjHomeStay;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterItemDatphongAdmin extends RecyclerView.Adapter<AdapterItemDatphongAdmin.TopicViewHoder> {
    private List<ObjBooking> mList;
    private Context context;
    private OnItemClickListennerTwoBtn OnIListener;
    private setOnItemClickListener click_lable;

    public setOnItemClickListener getClick_lable() {
        return click_lable;
    }

    public void setClick_lable(setOnItemClickListener click_lable) {
        this.click_lable = click_lable;
    }

    public OnItemClickListennerTwoBtn getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(OnItemClickListennerTwoBtn onIListener) {
        OnIListener = onIListener;
    }

    public AdapterItemDatphongAdmin(List<ObjBooking> listAirport, Context context) {
        this.mList = listAirport;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lichdatphong_admin, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        ObjBooking obj = mList.get(position);
        try {
            if (obj != null) {
                if (obj.getROOM_NAME() != null)
                    holder.txt_name_home.setText("" + obj.getROOM_NAME());
                if (obj.getBOOK_NAME() != null) {
                    //  holder.txt_name_booker.setText("Người đặt: " + obj.getBOOK_NAME());
                    String styledText = "Người đặt: <font color='#3300FF'><b><u>" + obj.getBOOK_NAME() + "</u></b></font>";
                    holder.txt_name_booker.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                } else {
                    holder.txt_name_booker.setText("Người đặt:");
                }

                if (obj.getSTART_TIME() != null && obj.getEND_TIME() != null) {
                    String time_book = "Thời gian: <font color='#000000'>"
                            + obj.getSTART_TIME() + " - " + obj.getEND_TIME() + "</font>";
                    holder.txt_time_book.setText(Html.fromHtml(time_book), TextView.BufferType.SPANNABLE);
                    //  holder.txt_time_book.setText(time_book);
                }

                if (obj.getBOOKING_TIME() != null) {
                    String styledText = "Thời gian đặt: <font color='#000000'>" + obj.getBOOKING_TIME() + "</font>";
                    holder.txt_create_time_book.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                   /* holder.txt_create_time_book.setText("Thời gian đặt: "
                            + TimeUtils.convent_date(obj.getBOOKING_TIME(),
                            "yyyy-MM-dd'T'HH:mm:ss.'000Z'",
                            "dd/MM/yyyy HH:mm"));*/
                }

                if (obj.getBOOK_STATUS_NAME() != null) {
                    String styledText = "Trạng thái nhà: <font color='#FF9900'><b>" + obj.getBOOK_STATUS_NAME() + "</b></font>.";
                    holder.txt_status_room.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    //   holder.txt_status_room.setText("Trạng thái phòng: Đã khóa");
                } else {
                    String styledText = "Trạng thái nhà:";
                    holder.txt_status_room.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    //holder.txt_status_room.setText("Trạng thái phòng: Đang trống");
                }
                if (obj.getBILLING_STATUS() != null && obj.getBILLING_STATUS().equals("2")) {
                    String styledText = "Trạng thái thanh toán: <font color='#006699'><b>"
                            + obj.getBILLING_STATUS_NAME() + "</b></font>";
                    holder.txt_status_pay.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    // holder.txt_status_pay.setText("Trạng thái phòng: Đã thanh toán");
                } else {
                    String styledText = "Trạng thái thanh toán: <font color='#FF3300'><b>"
                            + obj.getBILLING_STATUS_NAME() + "</b></font>";
                    holder.txt_status_pay.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                }
                if (obj.getIS_BOOK_SERVICES() != null && obj.getIS_BOOK_SERVICES().equals("0")) {
                    String styledText = "Trạng thái dịch vụ: <font color='#FF3300'><b>Chưa đặt dịch vụ</b></font>";
                    holder.txt_status_service.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    // holder.txt_status_pay.setText("Trạng thái phòng: Đã thanh toán");
                } else {
                    if (obj.getBILLING_STATUS_SERVICE().equals("1")) {
                        String styledText = "Trạng thái dịch vụ: <font color='#009900'><b>Đã thanh toán</b></font>";
                        holder.txt_status_service.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    } else {
                        String styledText = "Trạng thái dịch vụ: <font color='#FF3300'><b>Chưa thanh toán</b></font>";
                        holder.txt_status_service.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    }

                }
                if (obj.getCONTENT() != null) {
                    String styledText = "Nội dung chuyển khoản: <font color='#000000'><b>"
                            + obj.getCONTENT() + "</b></font>";
                    holder.txt_content.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    // holder.txt_status_pay.setText("Trạng thái phòng: Đã thanh toán");
                } else {

                    String styledText = "Chủ nhà tự khóa";
                    holder.txt_content.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                }
                //   holder.txt_status_pay.setText("Trạng thái phòng: Chưa thanh toán");
                holder.btn_open_room.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnIListener.OnItemClickListener_One_Btn(position);
                    }
                });
                holder.btn_booking_clearroom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnIListener.OnItemClickListener_Two_Btn(position);
                    }
                });
                holder.txt_name_booker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        click_lable.OnItemClickListener(position);
                    }
                });
                holder.txt_name_home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ActivityRoomDetail.class);
                        ObjHomeStay objHomeStay = new ObjHomeStay("", obj.getGENLINK());
                        intent.putExtra(Constants.KEY_SEND_ROOM_DETAIL, objHomeStay);
                        context.startActivity(intent);
                    }
                });
                if (obj.getCONTENT() != null) {
                    holder.btn_open_room.setText("Cập nhật thanh toán");
                } else {
                    holder.btn_open_room.setText("Mở khóa phòng");
                }

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
        @BindView(R.id.txt_name_home)
        TextView txt_name_home;
        @BindView(R.id.txt_name_booker)
        TextView txt_name_booker;
        @BindView(R.id.txt_time_book)
        TextView txt_time_book;
        @BindView(R.id.txt_create_time_book)
        TextView txt_create_time_book;
        @BindView(R.id.txt_status_room)
        TextView txt_status_room;
        @BindView(R.id.txt_status_pay)
        TextView txt_status_pay;
        @BindView(R.id.txt_status_service)
        TextView txt_status_service;
        @BindView(R.id.txt_content)
        TextView txt_content;
        @BindView(R.id.btn_booking_clearroom)
        Button btn_booking_clearroom;
        @BindView(R.id.btn_open_room)
        Button btn_open_room;

        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //  OnIListener.onClickItem(getLayoutPosition(), mList.get(getLayoutPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public void updateList(List<ObjBooking> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
