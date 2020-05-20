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
import com.vn.myhome.activity.user_manager.Activity_Info_User;
import com.vn.myhome.callback.OnItemClickListennerTwoBtn;
import com.vn.myhome.callback.setOnItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjBookingService;
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

public class AdapterItemLichdonphongAdmin extends RecyclerView.Adapter<AdapterItemLichdonphongAdmin.TopicViewHoder> {
    private List<ObjBookingService> mList;
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

    public AdapterItemLichdonphongAdmin(List<ObjBookingService> listAirport, Context context) {
        this.mList = listAirport;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lichdonphong_admin, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        ObjBookingService obj = mList.get(position);
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
                if (obj.getNOTES() != null)
                    holder.txt_note.setText("Ghi chú:\n" + obj.getNOTES());
                if (obj.getFULL_NAME() != null) {
                    //  holder.txt_name_booker.setText("Người đặt: " + obj.getBOOK_NAME());
                    String styledText = "Người đặt: <font color='#3300FF'><b><u>" + obj.getFULL_NAME() + "</u></b></font>";
                    holder.txt_name_booker.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                } else {
                    holder.txt_name_booker.setText("Người đặt:");
                }

                if (obj.getSTART_TIME() != null) {
                    String time_book = "Thời gian: <font color='#000000'>"
                            + TimeUtils.convent_date(obj.getSTART_TIME(),
                            "yyyy-MM-dd'T'HH:mm:ss.'000Z'",
                            "dd/MM/yyyy HH:mm") + "</font>";
                    holder.txt_time_checkin.setText(Html.fromHtml(time_book), TextView.BufferType.SPANNABLE);
                    //  holder.txt_time_book.setText(time_book);
                }
                if (obj.getEND_TIME() != null) {
                    String time_book = "Thời gian: <font color='#000000'>"
                            + TimeUtils.convent_date(obj.getEND_TIME(),
                            "yyyy-MM-dd'T'HH:mm:ss.'000Z'",
                            "dd/MM/yyyy HH:mm") + "</font>";
                    holder.txt_time_checkout.setText(Html.fromHtml(time_book), TextView.BufferType.SPANNABLE);
                    //  holder.txt_time_book.setText(time_book);
                }
                if (obj.getNAME_EDIT() != null) {
                    String styledText = "Người cập nhật cuối: <font color='#000000'>" + obj.getNAME_EDIT() + "</font>";
                    holder.txt_update_end_nguoi.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                   /* holder.txt_create_time_book.setText("Thời gian đặt: "
                            + TimeUtils.convent_date(obj.getBOOKING_TIME(),
                            "yyyy-MM-dd'T'HH:mm:ss.'000Z'",
                            "dd/MM/yyyy HH:mm"));*/
                } else {
                    holder.txt_update_end_nguoi.setText("Người cập nhật cuối: ");
                }

                if (obj.getUPDATE_TIME() != null) {
                    String styledText = "Ngày cập nhật cuối: <font color='#000000'>"
                            + obj.getUPDATE_TIME() + "</font>.";
                    holder.txt_time_update_end.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    //   holder.txt_status_room.setText("Trạng thái phòng: Đã khóa");
                } else {
                    String styledText = "Ngày cập nhật cuối:";
                    holder.txt_time_update_end.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    //holder.txt_status_room.setText("Trạng thái phòng: Đang trống");
                }
                if (obj.getSTATE_NAME() != null) {
                    String styledText = "Trạng thái nhà: <font color='#FF9900'><b>"
                            + obj.getSTATE_NAME() + "</b></font>.";
                    holder.txt_status_home.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    //   holder.txt_status_room.setText("Trạng thái phòng: Đã khóa");
                } else {
                    String styledText = "Trạng thái nhà:";
                    holder.txt_status_home.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    //holder.txt_status_room.setText("Trạng thái phòng: Đang trống");
                }
                if (obj.getSTATE().equals("4")){
                    holder.btn_trangthainha.setVisibility(View.GONE);
                }else
                    holder.btn_trangthainha.setVisibility(View.VISIBLE);
                if (obj.getBILLING_STATUS() != null && obj.getBILLING_STATUS().equals("1")) {
                    holder.btn_update_pay.setVisibility(View.GONE);
                    String styledText = "Trạng thái thanh toán: <font color='#006666'><b>Đã thanh toán</b></font>.";
                    holder.txt_status_pay.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    //   holder.txt_status_room.setText("Trạng thái phòng: Đã khóa");
                } else {
                    holder.btn_update_pay.setVisibility(View.VISIBLE);
                    ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
                    if (objLogin.getUSER_TYPE().equals(Constants.UserType.DICHVU)) {
                        holder.btn_update_pay.setVisibility(View.GONE);
                    } else {
                        holder.btn_update_pay.setVisibility(View.VISIBLE);
                    }
                    if (obj.getKIND_OF_PAID().equals("1")){
                        String styledText = "Trạng thái thanh toán: <font color='#CC6633'><b>Thanh toán trả sau</b></font>.";
                        holder.txt_status_pay.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    }else {
                        String styledText = "Trạng thái thanh toán: <font color='#FF3300'><b>Chưa thanh toán</b></font>.";
                        holder.txt_status_pay.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    }

                    //holder.txt_status_room.setText("Trạng thái phòng: Đang trống");
                }
                if (obj.getCONTENT() != null) {
                    String styledText = "Nội dung chuyển khoản: <font color='#000000'><b>"
                            + obj.getCONTENT() + "</b></font>.";
                    holder.txt_content.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                }
                holder.btn_trangthainha.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OnIListener.OnItemClickListener_One_Btn(position);
                    }
                });
                holder.btn_update_pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OnIListener.OnItemClickListener_Two_Btn(position);
                    }
                });
                holder.txt_name_booker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  click_lable.OnLongItemClickListener(position);
                        ObjBookingService obj = mList.get(position);
                        Intent intent = new Intent(context, Activity_Info_User.class);
                        intent.putExtra(Constants.KEY_SEND_INFO_USERID, obj.getUSERID());
                        intent.putExtra(Constants.KEY_SEND_INFO_USERID_TITLE, "Thông tin người đặt");
                        //startActivity(intent);
                        context.startActivity(intent);
                    }
                });
              /*  holder.txt_name_home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        click_lable.OnItemClickListener(position);
                    }
                });*/
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
        @BindView(R.id.txt_note)
        TextView txt_note;
        @BindView(R.id.txt_name_booker)
        TextView txt_name_booker;
        @BindView(R.id.txt_time_checkin)
        TextView txt_time_checkin;
        @BindView(R.id.txt_time_checkout)
        TextView txt_time_checkout;
        @BindView(R.id.txt_update_end_nguoi)
        TextView txt_update_end_nguoi;
        @BindView(R.id.txt_time_update_end)
        TextView txt_time_update_end;
        @BindView(R.id.txt_status_home)
        TextView txt_status_home;
        @BindView(R.id.txt_status_pay)
        TextView txt_status_pay;
        @BindView(R.id.txt_content)
        TextView txt_content;
        @BindView(R.id.btn_trangthainha)
        Button btn_trangthainha;
        @BindView(R.id.btn_update_pay)
        Button btn_update_pay;

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

    public void updateList(List<ObjBookingService> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
