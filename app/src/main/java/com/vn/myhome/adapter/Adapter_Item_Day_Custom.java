package com.vn.myhome.adapter;

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
import com.vn.myhome.untils.StringUtil;
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
public class Adapter_Item_Day_Custom extends RecyclerView.Adapter<Adapter_Item_Day_Custom.CustomDayViewHolder> {
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

    public Adapter_Item_Day_Custom(Context context, ItemClickListener onListenerItemClickObjService) {
        this.context = context;
        this.onListenerItemClickObjService = onListenerItemClickObjService;
    }

    @NonNull
    @Override
    public CustomDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day_custom_calender, parent, false);
        return new CustomDayViewHolder(view);
    }

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

                if (TimeUtils.CompareDates(todayAsString, objService.getsDay()).equals("3")
                        || TimeUtils.CompareDates(todayAsString, objService.getsDay()).equals("1")) {
                    holder.img_line_cheo.setVisibility(View.GONE);
                    holder.item_day.setBackgroundColor(context.getResources().getColor(R.color.White));
                    holder.txt_day.setTextColor(context.getResources().getColor(R.color.Black));
                    holder.txt_price_day.setVisibility(View.VISIBLE);
                    objService.setBooked(false);
                    if (objService.getmListBooking() != null && objService.getmListBooking().size() > 0) {
                        for (ObjBooking obj : objService.getmListBooking()) {
                            if (objService.getsStartBooking() != null
                                    && objService.getsStartBooking().length() > 0) {
                                if (TimeUtils.CompareDates_Two(objService.getsStartBooking(),
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
                            switch (TimeUtils.CompareDates_Two(objService.getsDay(), obj.getSTART_TIME())) {
                                case "2":
                                    if (TimeUtils.CompareDates_Two(objService.getsDay(), obj.getEND_TIME()).equals("3")) {
                                        //  holder.item_day.setBackground(context.getResources().getDrawable(R.color.LightGrey));
                                        holder.img_line_cheo.setVisibility(View.VISIBLE);
                                        holder.txt_day.setTextColor(context.getResources().getColor(R.color.Silver));
                                        holder.txt_day.setTypeface(null, Typeface.NORMAL);
                                        holder.txt_price_day.setTextColor(context.getResources().getColor(R.color.Silver));
                                        objService.setBooked(true);
                                    }
                                    break;
                                case "1":
                                    holder.img_line_cheo.setVisibility(View.VISIBLE);
                                    holder.txt_day.setTextColor(context.getResources().getColor(R.color.Silver));
                                    holder.txt_day.setTypeface(null, Typeface.NORMAL);
                                    holder.txt_price_day.setTextColor(context.getResources().getColor(R.color.Silver));
                                    //  holder.item_day.setBackground(context.getResources().getDrawable(R.drawable.vector_triangle));
                                    objService.setBooked(true);
                                    break;
                            }
                        }
                    }
                    if (objService.getsStartBooking() != null && objService.getsStartBooking().length() > 0) {
                        if (objService.getsEndBooking() != null && objService.getsEndBooking().length() > 0) {
                            if (TimeUtils.CompareDates(objService.getsDay(), objService.getsStartBooking()).equals("2")) {
                                if (TimeUtils.CompareDates(objService.getsDay(), objService.getsEndBooking()).equals("3")) {
                                    holder.view_click.setVisibility(View.VISIBLE);
                                }
                                //  holder.item_day.setBackgroundColor(context.getResources().getColor(R.color.app_main));

                            }
                            if (TimeUtils.CompareDates(objService.getsDay(), objService.getsEndBooking()).equals("1")) {
                                //   holder.item_day.setBackgroundColor(context.getResources().getColor(R.color.app_main));
                                holder.view_click.setVisibility(View.VISIBLE);
                            }

                        } else {
                            if (TimeUtils.CompareDates(objService.getsDay(), objService.getsStartBooking()).equals("3")) {
                                holder.img_line_cheo.setVisibility(View.VISIBLE);
                                holder.txt_day.setTextColor(context.getResources().getColor(R.color.Silver));
                                holder.txt_day.setTypeface(null, Typeface.NORMAL);
                                holder.txt_price_day.setTextColor(context.getResources().getColor(R.color.Silver));
                                // holder.item_day.setBackground(context.getResources().getDrawable(R.color.Yellow));
                                objService.setBooked(true);
                                holder.item_day.setBackgroundColor(context.getResources().getColor(R.color.White));
                            }
                            if (sDateStartNext.length() > 0)
                                if (TimeUtils.CompareDates_Two(objService.getsDay(), sDateStartNext).equals("2")) {
                                    holder.img_line_cheo.setVisibility(View.VISIBLE);
                                    holder.txt_day.setTextColor(context.getResources().getColor(R.color.Silver));
                                    holder.txt_day.setTypeface(null, Typeface.NORMAL);
                                    holder.txt_price_day.setTextColor(context.getResources().getColor(R.color.Silver));
                                    // holder.item_day.setBackground(context.getResources().getDrawable(R.color.Yellow));
                                    objService.setBooked(true);
                                }
                        }
                        if (sDateStartNext.length() > 0)
                            if (TimeUtils.CompareDates_Two(objService.getsDay(), sDateStartNext).equals("1")) {
                                holder.txt_day.setTextColor(context.getResources().getColor(R.color.Black));
                                holder.txt_day.setTypeface(null, Typeface.BOLD);
                                holder.img_line_cheo.setVisibility(View.GONE);
                                // holder.txt_price_day.setTextColor(context.getResources().getColor(R.color.Silver));
                                // holder.item_day.setBackground(context.getResources().getDrawable(R.color.Yellow));
                                objService.setBooked(false);
                            }
                        if (TimeUtils.CompareDates(objService.getsDay(), objService.getsStartBooking()).equals("1")) {
                            //  holder.item_day.setBackgroundColor(context.getResources().getColor(R.color.app_main));
                            holder.view_click.setVisibility(View.VISIBLE);
                        }
                    }
                }
                if (TimeUtils.CompareDates(todayAsString, objService.getsDay()).equals("2")) {
                    holder.item_day.setBackgroundColor(context.getResources().getColor(R.color.White));
                    holder.img_line_cheo.setVisibility(View.VISIBLE);
                    holder.txt_day.setTextColor(context.getResources().getColor(R.color.Silver));
                    holder.txt_price_day.setVisibility(View.GONE);
                    //holder.txt_day.setTypeface(holder.txt_day.getTypeface(), Typeface.ITALIC);
                    holder.txt_day.setTypeface(null, Typeface.NORMAL);
                    objService.setBooked(true);
                }
                if (TimeUtils.CompareDates(todayAsString, objService.getsDay()).equals("1")) {
            /*        holder.txt_current_day.setBackground(context.getResources().
                            getDrawable(R.drawable.spr_date_currentk));*/
                    holder.view_curren_day.setVisibility(View.VISIBLE);
                    //  holder.item_day.setBackground(context.getResources().getDrawable(R.drawable.spr_date_currentk));
                    holder.txt_day.setTextColor(context.getResources().getColor(R.color.Black));
                    holder.txt_price_day.setVisibility(View.VISIBLE);
                }
             /*   switch (TimeUtils.CompareDates(todayAsString, objService.getsDay())) {
                    case "1":

                        break;
                    case "3":

                        break;
                    case "2":

                        //holder.txt_day.setTypeface(Typeface.NORMAL);
                        break;
                }*/
                if (objService.isWeekend()) {
                    holder.txt_price_day.setText(StringUtil.formatNumber(objService.getsPriceWeekend()
                            .substring(0, objService.getsPriceWeekend().length() - 3)) + "K");
                    holder.txt_price_day.setTextColor(context.getResources().getColor(R.color.Brown));
                } else {
                    holder.txt_price_day.setText(StringUtil.formatNumber(objService.getsPrice()
                            .substring(0, objService.getsPrice().length() - 3)) + "K");
                    holder.txt_price_day.setTextColor(context.getResources().getColor(R.color.Black));
                }

                holder.txt_day.setText("" + objService.getiDayofMonth());
                //    holder.txt_price_day.setVisibility(View.VISIBLE);


            }
        } else {
            holder.view_curren_day.setVisibility(View.GONE);
            holder.view_click.setVisibility(View.GONE);
            holder.txt_day.setText("");
            holder.txt_price_day.setVisibility(View.GONE);
            holder.img_line_cheo.setVisibility(View.GONE);
            holder.item_day.setBackground(context.getResources().getDrawable(R.color.White));
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
        @BindView(R.id.txt_current_day)
        TextView txt_current_day;
        @BindView(R.id.view_curren_day)
        View view_curren_day;
        @BindView(R.id.view_click)
        View view_click;


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
