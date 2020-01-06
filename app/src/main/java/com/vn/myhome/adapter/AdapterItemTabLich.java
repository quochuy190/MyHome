package com.vn.myhome.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.callback.setOnItemClickListener;
import com.vn.myhome.models.ObjBooking;
import com.vn.myhome.models.ObjDayCustom;
import com.vn.myhome.untils.TimeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 12-October-2019
 * Time: 09:58
 * Version: 1.0
 */
public class AdapterItemTabLich extends RecyclerView.Adapter<AdapterItemTabLich.CustomDayViewHolder> {
    private List<ObjDayCustom> mList;
    private Context context;
    private setOnItemClickListener OnIListener;
    private ItemClickListener onListenerItemClickObjService;

    public setOnItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(setOnItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterItemTabLich(Context context, ItemClickListener onListenerItemClickObjService) {
        this.context = context;
        this.onListenerItemClickObjService = onListenerItemClickObjService;
    }

    @NonNull
    @Override
    public CustomDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_date_tablich, parent, false);
        return new CustomDayViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CustomDayViewHolder holder, int position) {
        String sDateStartNext = "";
        ObjDayCustom objService = mList.get(position);
        if (objService != null) {
            if (objService.getsDay() != null) {
                Date currentTime = Calendar.getInstance().getTime();
                currentTime.setHours(0);
                currentTime.setMinutes(0);
                currentTime.setSeconds(0);
                SimpleDateFormat format = new SimpleDateFormat("EEEE dd-MMM-yyyy");
                String pattern = "EEEE dd-MMM-yyyy";
                DateFormat df = new SimpleDateFormat(pattern);
                Date today = Calendar.getInstance().getTime();
                String todayAsString = df.format(today);
                holder.txt_day.setText("" + objService.getiDayofMonth());
                //    holder.txt_price_day.setVisibility(View.VISIBLE);
                holder.img_line_cheo.setVisibility(View.GONE);
                holder.txt_day.setTypeface(null, Typeface.BOLD);
                holder.txt_day.setTextColor(context.getResources().getColor(R.color.Black));
                if (objService.getsStartClick() != null) {
                    if (TimeUtils.CompareDates(objService.getsDay(), objService.getsStartClick()).equals("3")) {
                        holder.item_day.setBackgroundColor(context.getResources().getColor(R.color.White));
                        holder.img_line_cheo.setVisibility(View.VISIBLE);
                        holder.txt_day.setTextColor(context.getResources().getColor(R.color.Silver));
                        //holder.txt_day.setTypeface(holder.txt_day.getTypeface(), Typeface.ITALIC);
                        holder.txt_day.setTypeface(null, Typeface.NORMAL);
                        objService.setBooked(true);
                    }
                    for (ObjBooking obj : objService.getmListBooking()) {
                        if (objService.getsStartClick() != null
                                && objService.getsStartClick().length() > 0) {
                            if (TimeUtils.CompareDates_Two(objService.getsStartClick(),
                                    obj.getSTART_TIME()).equals("3")) {
                                if (sDateStartNext.length() > 0) {
                                    if (!TimeUtils.CompareDates_3(sDateStartNext, obj.getSTART_TIME()).equals("3")) {
                                        sDateStartNext = obj.getSTART_TIME();
                                    }
                                } else {
                                    sDateStartNext = obj.getSTART_TIME();
                                }
                            }
                        }
                    }
                    if (sDateStartNext.length() > 0) {
                        if (TimeUtils.CompareDates_Two(objService.getsDay(), sDateStartNext).equals("2")) {
                            holder.item_day.setBackgroundColor(context.getResources().getColor(R.color.White));
                            holder.img_line_cheo.setVisibility(View.VISIBLE);
                            holder.txt_day.setTextColor(context.getResources().getColor(R.color.Silver));
                            //holder.txt_day.setTypeface(holder.txt_day.getTypeface(), Typeface.ITALIC);
                            holder.txt_day.setTypeface(null, Typeface.NORMAL);
                            objService.setBooked(true);
                        }
                        if (TimeUtils.CompareDates_Two(objService.getsDay(), sDateStartNext).equals("1")) {
                            objService.setBooked(false);
                        }
                    }
                    if (TimeUtils.CompareDates(objService.getsDay(), objService.getsStartClick()).equals("1")) {
                        holder.item_day.setBackground(context.getResources().getDrawable(R.drawable.spr_date_start_click));
                       /* holder.view_start_click.setVisibility(View.VISIBLE);
                        holder.view_end_click.setVisibility(View.GONE);
                        holder.view_all_click.setVisibility(View.GONE);*/
                    }
                    if (objService.getsEndClick() != null) {
                        if (TimeUtils.CompareDates(objService.getsDay(), objService.getsEndClick()).equals("2")) {
                            holder.item_day.setBackgroundColor(context.getResources().getColor(R.color.White));
         /*                   holder.view_start_click.setVisibility(View.GONE);
                            holder.view_end_click.setVisibility(View.GONE);
                            holder.view_all_click.setVisibility(View.GONE);*/
                            holder.img_line_cheo.setVisibility(View.VISIBLE);
                            holder.txt_day.setTextColor(context.getResources().getColor(R.color.Silver));
                            //holder.txt_day.setTypeface(holder.txt_day.getTypeface(), Typeface.ITALIC);
                            holder.txt_day.setTypeface(null, Typeface.NORMAL);
                            objService.setBooked(true);
                        }
                        if (TimeUtils.CompareDates(objService.getsDay(), objService.getsEndClick()).equals("1")) {
                            holder.item_day.setBackground(context.getResources().getDrawable(R.drawable.spr_date_end_click));
                           /* holder.view_start_click.setVisibility(View.GONE);
                            holder.view_end_click.setVisibility(View.VISIBLE);
                            holder.view_all_click.setVisibility(View.GONE);*/
                        }
                        if (TimeUtils.CompareDates_Three_Tab_Lich(objService.getsStartClick(), objService.getsEndClick(),
                                objService.getsDay())) {
                            holder.item_day.setBackgroundColor(context.getResources().getColor(R.color.LightGrey));
                            objService.setBooked(true);
                         /*   holder.view_start_click.setVisibility(View.GONE);
                            holder.view_end_click.setVisibility(View.GONE);
                            holder.view_all_click.setVisibility(View.VISIBLE);*/
                        }
                    }
                }
                if (TimeUtils.CompareDates(todayAsString, objService.getsDay()).equals("2")) {
                    objService.setBooked(true);
                    holder.item_day.setBackgroundColor(context.getResources().getColor(R.color.White));
                  /*  holder.view_start_click.setVisibility(View.GONE);
                    holder.view_end_click.setVisibility(View.GONE);
                    holder.view_all_click.setVisibility(View.GONE);*/
                    holder.img_line_cheo.setVisibility(View.VISIBLE);
                    holder.txt_day.setTextColor(context.getResources().getColor(R.color.Silver));
                    //holder.txt_day.setTypeface(holder.txt_day.getTypeface(), Typeface.ITALIC);
                    //    holder.txt_day.setTypeface(null, Typeface.NORMAL);
                }
                if (TimeUtils.CompareDates(todayAsString, objService.getsDay()).equals("1")) {
                    holder.txt_day.setBackgroundResource(R.drawable.spr_date_currentk);
                    holder.txt_day.setTextColor(context.getResources().getColor(R.color.Black));
                }
                if (objService.isBooked() && objService.getBILLING_STATUS() != null) {
                    if (objService.getBILLING_STATUS().equals("2")) {
                        holder.item_day.setBackgroundColor(context.getResources().getColor(R.color.app_main_light));
                    } else
                        holder.item_day.setBackgroundColor(context.getResources().getColor(R.color.LightGrey));
               /*     holder.view_start_click.setVisibility(View.GONE);
                    holder.view_end_click.setVisibility(View.GONE);
                    holder.view_all_click.setVisibility(View.VISIBLE);*/
                    //   holder.item_day.setBackgroundColor(R.color.LightGrey);
                }
                if (objService.getsStartBooking() != null && objService.getsStartBooking().length() > 0) {
                    if (TimeUtils.CompareDates(objService.getsDay(), objService.getsEndClick()).equals("1")) {
                        if (objService.getBILLING_STATUS() != null && objService.getBILLING_STATUS().equals("2")) {
                            holder.item_day.setBackground(context.getResources().
                                    getDrawable(R.drawable.spr_date_start_lock_appmain_and_date_clickend));
                        } else
                            holder.item_day.setBackground(context.getResources().getDrawable(R.drawable.spr_date_start_click));
                        //   holder.item_day.setBackgroundColor(context.getResources().getColor(R.color.LightGrey));
            /*            holder.view_start_click.setVisibility(View.GONE);
                        holder.view_end_click.setVisibility(View.GONE);
                        holder.view_all_click.setVisibility(View.VISIBLE);*/
                    } else {
                        if (objService.getBILLING_STATUS() != null && objService.getBILLING_STATUS().equals("2")) {
                            holder.item_day.setBackground(context.getResources().
                                    getDrawable(R.drawable.spr_date_start_lock_appmain_color));
                        } else
                            holder.item_day.setBackground(context.getResources().
                                    getDrawable(R.drawable.spr_date_start_click));
                      /*  holder.view_start_click.setVisibility(View.VISIBLE);
                        holder.view_end_click.setVisibility(View.GONE);
                        holder.view_all_click.setVisibility(View.GONE);*/

                    }
                }

                if (objService.getsEndBooking() != null && objService.getsEndBooking().length() > 0) {
                    if (TimeUtils.CompareDates(objService.getsDay(), objService.getsStartClick()).equals("1")) {
                        if (objService.getBILLING_STATUS() != null && objService.getBILLING_STATUS().equals("2")) {
                            holder.item_day.setBackground(context.getResources().
                                    getDrawable(R.drawable.spr_date_end_lock_appmain_and_date_startclick));
                        } else
                            holder.item_day.setBackgroundColor(context.getResources().getColor(R.color.LightGrey));
                     /*   holder.view_start_click.setVisibility(View.GONE);
                        holder.view_end_click.setVisibility(View.GONE);
                        holder.view_all_click.setVisibility(View.VISIBLE);*/
                    } else {
                        if (objService.getBILLING_STATUS() != null && objService.getBILLING_STATUS().equals("2")) {
                            holder.item_day.setBackground(context.getResources().getDrawable(R.drawable.spr_date_end_lock_appmain_color));
                        } else
                            holder.item_day.setBackground(context.getResources().getDrawable(R.drawable.spr_date_end_click));
                      /*  holder.view_start_click.setVisibility(View.GONE);
                        holder.view_end_click.setVisibility(View.VISIBLE);
                        holder.view_all_click.setVisibility(View.GONE);*/

                    }
                    if (objService.getsStartBooking() != null && objService.getsStartBooking().
                            equals(objService.getsEndBooking())) {
                        if (objService.getBILLING_STATUS() != null && objService.getBILLING_STATUS().equals("2")) {
                            holder.item_day.setBackgroundColor(context.getResources().getColor(R.color.app_main_light));
                        } else
                            holder.item_day.setBackgroundColor(context.getResources().getColor(R.color.LightGrey));
                      /*  holder.view_start_click.setVisibility(View.GONE);
                        holder.view_end_click.setVisibility(View.GONE);
                        holder.view_all_click.setVisibility(View.VISIBLE);*/
                    }
                }

            }
        } else {
            holder.txt_day.setText("");
            holder.txt_price_day.setVisibility(View.GONE);
            holder.img_line_cheo.setVisibility(View.GONE);
            holder.item_day.setBackground(context.getResources().getDrawable(R.color.White));
            holder.txt_day.setBackgroundResource(R.color.White);
         /*   holder.view_start_click.setVisibility(View.GONE);
            holder.view_end_click.setVisibility(View.GONE);
            holder.view_all_click.setVisibility(View.GONE);*/
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CustomDayViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_day)
        TextView txt_day;
        @BindView(R.id.txt_price_day)
        TextView txt_price_day;
        @BindView(R.id.item_day)
        ConstraintLayout item_day;
        @BindView(R.id.img_line_cheo)
        ImageView img_line_cheo;

        public CustomDayViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListenerItemClickObjService.onClickItem(getLayoutPosition(), mList.get(getLayoutPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            //  OnIListener.OnLongItemClickListener(getLayoutPosition());
            return false;
        }
    }

    public void setData(List<ObjDayCustom> data) {
        mList = new ArrayList<>();
        if (mList != data) {
            mList = data;
            notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }
}
