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
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterTabChitietDatphong extends RecyclerView.Adapter<AdapterTabChitietDatphong.TopicViewHoder> {
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

    public AdapterTabChitietDatphong(List<ObjBooking> listAirport, Context context) {
        this.mList = listAirport;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tab_chitiet_datphong, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        ObjBooking obj = mList.get(position);
        try {
            if (obj != null) {
                holder.txt_name_home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ActivityRoomDetail.class);
                        ObjHomeStay objHomeStay = new ObjHomeStay("", obj.getGENLINK());
                        intent.putExtra(Constants.KEY_SEND_ROOM_DETAIL, objHomeStay);
                        context.startActivity(intent);
                    }
                });
                if (obj.getROOM_NAME() != null)
                    holder.txt_name_home.setText("" + obj.getROOM_NAME());
                if (obj.getBOOK_NAME() != null) {
                    //  holder.txt_name_booker.setText("Người đặt: " + obj.getBOOK_NAME());
                    String styledText = "Người đặt: <font color='#3300FF'><b><u>" + obj.getBOOK_NAME() + "</u></b></font>";
                    holder.txt_name_booker.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                }

                if (obj.getSTART_TIME() != null && obj.getEND_TIME() != null)
                    holder.txt_time_book.setText("Thời gian: " + obj.getSTART_TIME() + " - " + obj.getEND_TIME());
                if (obj.getBOOKING_TIME() != null)
                    holder.txt_create_time_book.setText("Thời gian đặt: "
                            + TimeUtils.convent_date(obj.getBOOKING_TIME(),
                            "yyyy-MM-dd'T'HH:mm:ss.'000Z'",
                            "dd/MM/yyyy HH:mm"));
         /*       if (obj.getBOOK_STATUS() != null && obj.getBOOK_STATUS().equals("2")) {
                    String styledText = "Trạng thái nhà: <font color='#FF3300'><b>Đã khóa</b></font>.";
                    holder.txt_status_room.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    //   holder.txt_status_room.setText("Trạng thái phòng: Đã khóa");
                } else {
                    String styledText = "Trạng thái nhà: <font color='#0066FF'><b>Đang trống</b></font>.";
                    holder.txt_status_room.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    //holder.txt_status_room.setText("Trạng thái phòng: Đang trống");
                }*/


                //   holder.txt_status_pay.setText("Trạng thái phòng: Chưa thanh toán");
                if (obj.getIS_BOOK_SERVICES() != null && obj.getIS_BOOK_SERVICES().equals("1")) {
                    if (obj.getBILLING_STATUS_SERVICE().equals("1")) {
                        holder.btn_booking_clearroom.setBackgroundResource(R.drawable.spr_btn_book_clearrom);
                        holder.btn_booking_clearroom.setText("Đã thanh toán");
                        holder.btn_booking_clearroom.setEnabled(false);
                    } else {
                        holder.btn_booking_clearroom.setBackgroundResource(R.drawable.spr_btn_orange);
                        holder.btn_booking_clearroom.setText("Chờ thanh toán");
                    }

                } else {
                    holder.btn_booking_clearroom.setText("Đặt dọn phòng");
                    holder.btn_booking_clearroom.setBackgroundResource(R.drawable.spr_btn_red);
                }
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
                ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
                if (objLogin != null && objLogin.getUSER_TYPE().
                        equals(Constants.UserType.CHUNHA)) {
                    if (obj.getIS_BOOK_SERVICES() != null && obj.getIS_BOOK_SERVICES().equals("1")) {
                        if (obj.getBILLING_STATUS_SERVICE().equals("1")) {
                            holder.btn_booking_clearroom.setBackgroundResource(R.drawable.spr_btn_book_clearrom);
                            holder.btn_booking_clearroom.setText("Đã thanh toán");
                            holder.btn_booking_clearroom.setEnabled(false);
                            String txt_book_status = "Trạng thái dv: <font color='#00CC00'><b>" +
                                    "Đã đặt phòng - Đã thanh toán</b></font>";
                            holder.txt_status_room.setText(Html.fromHtml(txt_book_status), TextView.BufferType.SPANNABLE);
                            holder.btn_booking_clearroom.setVisibility(View.GONE);
                        } else {
                            holder.btn_booking_clearroom.setBackgroundResource(R.drawable.spr_btn_orange);
                            holder.btn_booking_clearroom.setText("Thanh toán dv");
                            String txt_book_status = "";
                            if (obj.getKIND_OF_PAID().equals("1")) {
                                txt_book_status = "Trạng thái dv: <font color='#CC6633'><b>" +
                                        "Đã đặt - Thanh toán sau</b></font>";
                            } else {
                                txt_book_status = "Trạng thái dv: <font color='#CC6633'><b>" +
                                        "Đã đặt - Chờ thanh toán</b></font>";
                            }
                            holder.txt_status_room.setText(Html.fromHtml(txt_book_status), TextView.BufferType.SPANNABLE);
                        }

                    } else {
                        String txt_book_status = "Trạng thái dv: <font color='#FF3300'><b>" +
                                "Chưa đặt dv</b></font>";
                        holder.txt_status_room.setText(Html.fromHtml(txt_book_status), TextView.BufferType.SPANNABLE);
                        holder.btn_booking_clearroom.setText("Đặt dọn phòng");
                        holder.btn_booking_clearroom.setBackgroundResource(R.drawable.spr_btn_red);
                    }
                    //holder.txt_status_room.setText(room_status);

                    holder.txt_status_pay.setVisibility(View.GONE);
                    holder.btn_open_room.setVisibility(View.VISIBLE);
                    holder.btn_booking_clearroom.setVisibility(View.VISIBLE);
                    if (obj.getCONTENT() != null) {
                        holder.btn_open_room.setVisibility(View.GONE);
                    } else {
                        holder.btn_open_room.setVisibility(View.VISIBLE);
                    }
                }
                if (objLogin.getUSER_TYPE().
                        equals(Constants.UserType.CTV) || objLogin.getUSER_TYPE().
                        equals(Constants.UserType.DICHVU)) {
                    holder.btn_open_room.setVisibility(View.GONE);
                    holder.btn_booking_clearroom.setVisibility(View.VISIBLE);
                    holder.btn_booking_clearroom.setText("Thanh toán");
                    holder.txt_status_pay.setVisibility(View.VISIBLE);
                    String txt_book_status = "Trạng thái nhà: <font color='#FF9900'><b>"
                            + obj.getBOOK_STATUS_NAME() + "</b></font>";
                    //holder.txt_status_room.setText(room_status);
                    holder.txt_status_room.setText(Html.fromHtml(txt_book_status), TextView.BufferType.SPANNABLE);
                    if (obj.getBILLING_STATUS() != null && obj.getBILLING_STATUS().equals("2")) {
                        holder.btn_booking_clearroom.setVisibility(View.GONE);
                        String styledText = "Trạng thái thanh toán: <font color='#00CC00'><b>"
                                + obj.getBILLING_STATUS_NAME() + "</b></font>";
                        holder.txt_status_pay.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                        // holder.txt_status_pay.setText("Trạng thái phòng: Đã thanh toán");
                    } else {
                        holder.btn_booking_clearroom.setBackgroundResource(R.drawable.spr_btn_red);
                        String styledText = "Trạng thái thanh toán: <font color='#FF3300'><b>"
                                + obj.getBILLING_STATUS_NAME() + "</b></font>";
                        holder.txt_status_pay.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    }
                    // holder.txt_status_pay.setText(obj.getBILLING_STATUS_NAME());
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
