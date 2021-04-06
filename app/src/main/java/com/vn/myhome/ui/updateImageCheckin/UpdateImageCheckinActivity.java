package com.vn.myhome.ui.updateImageCheckin;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.vn.myhome.R;
import com.vn.myhome.adapter.AdapterImage_Zoom_Viewpage;
import com.vn.myhome.adapter.AdapterListImageServiceCheckin;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.callback.OnListenerItemClickObjService;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ImageCheckinService;
import com.vn.myhome.models.ImageRoomObj;
import com.vn.myhome.models.ListImageCheckinService;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.untils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 26-July-2020
 * Time: 08:36
 * Version: 1.0
 */
public class UpdateImageCheckinActivity extends BaseActivity {
    private static final String TAG = "UpdateImageCheckinActiv";
    @BindView(R.id.rcv_list_image_service)
    RecyclerView rcvListImageService;
    UpdateImageCheckinPresenter mPresenter;
    String sUserName;
    String idBookService;
    List<ListImageCheckinService> mListImage;
    AdapterListImageServiceCheckin adapter;
    RecyclerView.LayoutManager mLayoutManagerProduct;
    public static UpdateImageCheckinActivity mInstance;

    public static UpdateImageCheckinActivity getInstance() {
        return mInstance;
    }

    public int mWidth;
    public int mHeight;
    private String sCurrentType;

    private void getWidthHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        mWidth = width;
        mHeight = height;

    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_update_image_checkin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new UpdateImageCheckinPresenter(this);
        mInstance = this;
        initAppbar();
        getWidthHeight();
        init();
        initData();
    }

    private void initAppbar() {
        ImageView img_back = findViewById(R.id.btn_back);
        TextView txt_title = findViewById(R.id.txt_title);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent());
                finish();
            }
        });
        txt_title.setText("ẢNH DỊCH VỤ");
    }

    private void init() {
        mListImage = new ArrayList<>();
        adapter = new AdapterListImageServiceCheckin(this, mListImage, new OnListenerItemClickObjService() {
            @Override
            public void onClickListener(Object objService) {
                ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
                if (!objLogin.getUSER_TYPE().equals(Constants.UserType.CHUNHA)) {
                    showDialogComfirm("Xác nhận", "Bạn có chắc chắn muốn xóa ảnh này không?",
                            true, new ClickDialog() {
                                @Override
                                public void onClickYesDialog() {
                                    ImageCheckinService objImage = (ImageCheckinService) objService;
                                    mPresenter.deleteImageCheckinService(sUserName, idBookService, objImage.getID());
                                }

                                @Override
                                public void onClickNoDialog() {

                                }
                            });
                } else showAlertDialog("Thông báo", "Bạn không có quyền xóa ảnh");
            }

            @Override
            public void onItemAddImageClick(int position, String type) {
                sCurrentType = type;
                Intent intent = new Intent(UpdateImageCheckinActivity.this, ReviewUploadImageActivity.class);
                intent.putExtra(Constants.KEY_SEND_ID_BOOK_SERVICE, idBookService);
                intent.putExtra(Constants.KEY_SEND_TYPE_UPLOAD_IMAGE_SERVICE, type);
                startActivityForResult(intent, Constants.RequestCode.UPLOAD_IMAGE_SERVICE);
                //checkPermissionsAndOpenFilePicker();
            }

            @Override
            public void doShowImageZom(int position, String type) {
                for (ListImageCheckinService obj : mListImage) {
                    if (obj != null && obj.getIMG_TYPE() != null && obj.getIMG_TYPE().equals(type)) {
                        showDialog_Full_image(position, obj.getDATA());
                    }
                }

            }

        });
        mLayoutManagerProduct = new GridLayoutManager(this, 1);
        rcvListImageService.setHasFixedSize(true);
        rcvListImageService.setLayoutManager(mLayoutManagerProduct);
        rcvListImageService.setItemAnimator(new DefaultItemAnimator());
        rcvListImageService.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        showDialogLoading();
        idBookService = getIntent().getStringExtra(Constants.KEY_SEND_ID_BOOK_SERVICE);
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenter.getAllImageCheckinService(sUserName, idBookService);
    }

    public void showListImageService(List<ListImageCheckinService> mList) {
        hideDialogLoading();
        mListImage = mList;
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        if (objLogin.getUSER_TYPE().equals(Constants.UserType.CHECK_IN)) {
            for (ListImageCheckinService obj : mListImage) {
                obj.getDATA().add(0, new ImageCheckinService("addImage", idBookService, ""));
            }
        }
        adapter.update_list(mListImage);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestCode.UPLOAD_IMAGE_SERVICE
                && resultCode == RESULT_OK && data != null) {
            initData();
        }
    }

    public void showDeleteImage(boolean isSuccess, String sMessage) {
        if (isSuccess) {
            initData();
            Toast.makeText(mInstance, sMessage, Toast.LENGTH_SHORT).show();
        } else {
            showDialogNotify("Lỗi", sMessage);
        }
    }

    Dialog dialog;
    List<ImageRoomObj> mListAnh = new ArrayList<>();

    public void showDialog_Full_image(int posision, List<ImageCheckinService> mListImage) {
        mListAnh.clear();
        if (mListImage != null) {
            for (ImageCheckinService objImage : mListImage) {
                if (objImage != null && objImage.getIMG() != null)
                    mListAnh.add(new ImageRoomObj(objImage.getIMG()));
            }
        }
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        if (objLogin.getUSER_TYPE().equals(Constants.UserType.CHECK_IN)) {
            if (posision>0){
                posision--;
            }
        }
        dialog = new Dialog(UpdateImageCheckinActivity.this, R.style.Theme_Dialog);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_fullscreen_image);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        ViewPager viewPager = dialog.findViewById(R.id.viewpager_image);
        ImageView ic_delete = dialog.findViewById(R.id.ic_delete);
        AdapterImage_Zoom_Viewpage adapter = new AdapterImage_Zoom_Viewpage(this, mListAnh);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(posision);
        ic_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
