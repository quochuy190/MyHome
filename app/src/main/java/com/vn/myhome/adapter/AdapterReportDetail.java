package com.vn.myhome.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.myhome.R;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.models.TitleReportDetail;

import java.util.List;

public class AdapterReportDetail extends
        RecyclerView.Adapter<AdapterReportDetail.CategoryServiceViewHolder> {
    public Context mContext;
    private List<TitleReportDetail> mLisCateService;
    public static ItemClickListener onListenerItemClickObjService;
    private ItemClickListener OnIListener;

    public AdapterReportDetail(Context context, List<TitleReportDetail> mLisCateService,
                               ItemClickListener onListenerItemClickObjService) {
        this.onListenerItemClickObjService = onListenerItemClickObjService;
        mContext = context;
        this.mLisCateService = mLisCateService;
    }

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    @NonNull
    @Override
    public CategoryServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_title_report_detail,
                parent, false);
        return new CategoryServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryServiceViewHolder holder, final int position) {
        TitleReportDetail objDetail = mLisCateService.get(position);
        if (objDetail.getsNameHome() != null)
            holder.title.setText(objDetail.getsNameHome());
        holder.horizontalAdapter.setData(mLisCateService.get(position).getmList(), mContext);

    }

    @Override
    public int getItemCount() {
        return mLisCateService.size();
    }


    public static class CategoryServiceViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        private AdapterItemReportDetail horizontalAdapter;
        private RecyclerView horizontalList;

        @SuppressLint("WrongConstant")
        public CategoryServiceViewHolder(View view) {
            super(view);
            Context context = itemView.getContext();
            title = (TextView) view.findViewById(R.id.txt_name_home);
            horizontalList = (RecyclerView) itemView.findViewById(R.id.rcv_list_report_detail);
            horizontalList.setLayoutManager(new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL,
                    false));
            horizontalAdapter = new AdapterItemReportDetail(onListenerItemClickObjService);
            horizontalList.setAdapter(horizontalAdapter);
        }
    }

    public void update_list(List<TitleReportDetail> list) {
        mLisCateService.clear();
        mLisCateService.addAll(list);
        notifyDataSetChanged();
    }

}
