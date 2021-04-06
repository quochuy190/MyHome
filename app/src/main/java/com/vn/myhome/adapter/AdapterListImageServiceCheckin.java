package com.vn.myhome.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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
import com.vn.myhome.callback.OnListenerItemClickObjService;
import com.vn.myhome.models.ListImageCheckinService;

import java.util.List;

public class AdapterListImageServiceCheckin extends RecyclerView.Adapter<AdapterListImageServiceCheckin.CategoryServiceViewHolder> {
    private static final String TAG = "AdapterListImageService";
    public static Context mContext;
    private List<ListImageCheckinService> mLisCateService;

    public static OnListenerItemClickObjService onListenerItemClickObjService;
    private ItemClickListener OnIListener;
    private ItemClickListener OnIListener_Title;

    public AdapterListImageServiceCheckin(Context context, List<ListImageCheckinService> mLisCateService,
                                          OnListenerItemClickObjService onListenerItemClickObjService) {
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

    public ItemClickListener getOnIListener_Title() {
        return OnIListener_Title;
    }

    public void setOnIListener_Title(ItemClickListener onIListener_Title) {
        OnIListener_Title = onIListener_Title;
    }

    @NonNull
    @Override
    public CategoryServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_image_service_checkin, parent, false);
        return new CategoryServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryServiceViewHolder holder, final int position) {
        holder.title.setText(mLisCateService.get(position).getTYPE_NAME());
        Log.d(TAG, "onBindViewHolder: ");
        if (mLisCateService.get(position).getDATA() != null &&
                mLisCateService.get(position).getDATA().size() > 0){
            Log.d(TAG, "setData: getDATA.size"+mLisCateService.get(position).getDATA().size());
            holder.horizontalAdapter.setData(mLisCateService.get(position).getDATA(),
                    mLisCateService.get(position).getIMG_TYPE()); // List of Strings
            holder.horizontalAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mLisCateService.size();
    }


    public static class CategoryServiceViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        private AdapterSubImageService horizontalAdapter;
        private RecyclerView horizontalList;
        private ImageView icon_down;

        @SuppressLint("WrongConstant")
        public CategoryServiceViewHolder(View view) {
            super(view);
            Context context = itemView.getContext();
            title = (TextView) view.findViewById(R.id.txt_title_objservice);
            icon_down = (ImageView) view.findViewById(R.id.icon_down);
            horizontalList = (RecyclerView) itemView.findViewById(R.id.recycle_lis_objservice);
            horizontalList.setLayoutManager(new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL,
                    false));
            horizontalAdapter = new AdapterSubImageService(mContext, onListenerItemClickObjService);
            horizontalList.setAdapter(horizontalAdapter);
        }
    }

    public void update_list(List<ListImageCheckinService> list) {
        Log.d(TAG, "update_list: ");
        mLisCateService.clear();
        mLisCateService.addAll(list);
        notifyDataSetChanged();
    }

}
