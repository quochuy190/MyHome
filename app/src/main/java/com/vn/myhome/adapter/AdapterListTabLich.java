package com.vn.myhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.models.ObjCalendar;

import java.util.List;

public class AdapterListTabLich extends
        RecyclerView.Adapter<AdapterListTabLich.CalendarCustomView> {
    public static Context mContext;
    private List<ObjCalendar> mListCalendar;
    boolean isShowall = false;
    public static ItemClickListener onListenerItemClickObjService;
    private ItemClickListener OnIListener;

    public AdapterListTabLich(Context context, List<ObjCalendar> mLisCateService,
                              ItemClickListener onListenerItemClickObjService, boolean isShowall) {
        this.onListenerItemClickObjService = onListenerItemClickObjService;
        mContext = context;
        this.mListCalendar = mLisCateService;
        this.isShowall = isShowall;
    }

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }
    @NonNull
    @Override
    public CalendarCustomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_calender_custom, parent, false);
        return new CalendarCustomView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarCustomView holder, int position) {
        ObjCalendar objCalendar = mListCalendar.get(position);
        if (objCalendar.getsMonth() != null && objCalendar.getsYear() != null)
            holder.title.setText("Tháng " + objCalendar.getsMonth() + ", " + objCalendar.getsYear());
        holder.horizontalAdapter.setData(mListCalendar.get(position).getmLisday());

        // List of Strings
    }

    @Override
    public int getItemCount() {
        return mListCalendar.size();
    }


    public static class CalendarCustomView extends RecyclerView.ViewHolder {
        public final TextView title;
        TextView txt_title_view_all;
        private AdapterItemTabLich horizontalAdapter;
        private RecyclerView horizontalList;
        private ImageView icon_down;

        public CalendarCustomView(View view) {
            super(view);
            Context context = itemView.getContext();
            title = (TextView) view.findViewById(R.id.txt_title_objservice);
            horizontalList = (RecyclerView) itemView.findViewById(R.id.recycle_lis_objservice);
            horizontalList.setLayoutManager(new GridLayoutManager(context, 7));
            horizontalList.setNestedScrollingEnabled(false);
            horizontalAdapter = new AdapterItemTabLich(mContext, onListenerItemClickObjService);
            horizontalList.setAdapter(horizontalAdapter);
        }
    }

    public void update_list(List<ObjCalendar> list) {
        mListCalendar.clear();
        mListCalendar.addAll(list);
        notifyDataSetChanged();
    }

}
