package com.vn.myhome.ui.updateImageCheckin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.vn.myhome.R;
import com.vn.myhome.adapter.AdapterReviewImageService;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.setOnItemClickListener;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjImageHome;
import com.vn.myhome.untils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 26-July-2020
 * Time: 15:42
 * Version: 1.0
 */
public class ReviewUploadImageActivity extends BaseActivity {
    private static final String TAG = "ReviewUploadImageActivi";
    List<ObjImageHome> mListImage;
    AdapterReviewImageService adapter;
    @BindView(R.id.rcv_review_image)
    RecyclerView rcvListImageService;
    @BindView(R.id.btn_upload_image)
    Button btnUploadImage;
    RecyclerView.LayoutManager mLayoutManager;
    String sUserName;
    String idBookService;
    String sType;
    ReviewUploadImagePresenter mPresenter;

    @Override
    public int setContentViewId() {
        return R.layout.activity_upload_image_service;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ReviewUploadImagePresenter(this);
        initAppbar();
        initData();
        init();
        initEvent();
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
        txt_title.setText("TẢI ẢNH LÊN");
    }
    private void initEvent() {
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                String sPath = "";
                if (mListImage.size() > 1) {
                    for (ObjImageHome obj : mListImage) {
                        if (obj.getsPath() != null && obj.getsPath().length() > 0) {
                            sPath = sPath + obj.getsPath() + ",";
                        }
                    }
                    sPath = sPath.substring(0, sPath.length() - 1);
                    showDialogLoading();
                    mPresenter.api_upload_image_multil(sUserName, idBookService, mListImage, sType);
                } else {
                    showDialogNotify("Thông báo", "Bạn chưa chọn ảnh nào.");
                }

            }
        });
    }


    private void initData() {
        idBookService = getIntent().getStringExtra(Constants.KEY_SEND_ID_BOOK_SERVICE);
        sType = getIntent().getStringExtra(Constants.KEY_SEND_TYPE_UPLOAD_IMAGE_SERVICE);
        Log.d(TAG, "initData stype: " + sType);
    }

    private void init() {
        mListImage = new ArrayList<>();
        mListImage.add(new ObjImageHome());
        adapter = new AdapterReviewImageService(mListImage, this);
        mLayoutManager = new GridLayoutManager(this, 3);
        rcvListImageService.setHasFixedSize(true);
        rcvListImageService.setLayoutManager(mLayoutManager);
        rcvListImageService.setItemAnimator(new DefaultItemAnimator());
        rcvListImageService.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                checkPermissionsAndOpenFilePicker();
            }

            @Override
            public void OnLongItemClickListener(int position) {
                mListImage.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void checkPermissionsAndOpenFilePicker() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            get_file_picker();
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    private ArrayList<MediaFile> mediaFiles = new ArrayList<>();
    public static final int PERMISSIONS_REQUEST_CODE = 0;
    private final static int FILE_REQUEST_CODE = 1;

    private void get_file_picker() {
        int max = 10;
        mediaFiles.clear();
        Intent intent = new Intent(this, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                .setCheckPermission(true)
                .setSelectedMediaFiles(mediaFiles)
                .setShowFiles(false)
                .setShowImages(true)
                .setShowVideos(false)
                .setShowAudios(false)
                .setMaxSelection(max)
                .enableImageCapture(true)
                // .setRootPath(Environment.getExternalStorageDirectory().getPath())
                .build());
        startActivityForResult(intent, FILE_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    get_file_picker();
                } else {
                    showError();
                }
            }
        }
    }

    private void showError() {
        Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            mediaFiles.clear();
            mediaFiles.addAll(data.<MediaFile>getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES));
            if (mediaFiles != null && mediaFiles.size() > 0) {
                for (MediaFile media : mediaFiles) {
                    Log.d(TAG, "onActivityResult: " + media.getPath());
                    ObjImageHome obj = new ObjImageHome(media.getPath());
                    mListImage.add(1, obj);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
    public void showErrorApiUploadimage(){
        hideDialogLoading();
        showAlertDialog("Lỗi", "Đăng ảnh không thành công");
    }
    public void showUploadImageSuccess(){
        hideDialogLoading();
        Toast.makeText(this, "Đăng ảnh thành công", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, new Intent());
        finish();
    }
}
