package com.vn.myhome.activity.user_manager;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.activity.login.ActivityListCity;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.config.Config;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.models.ResponseApi.ResponInfo;
import com.vn.myhome.presenter.UserPresenter;
import com.vn.myhome.untils.KeyboardUtil;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.TimeUtils;
import com.vn.myhome.upload_media.InterfaceUploadImage;
import com.vn.myhome.upload_media.PresenterUploadImage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 18-November-2019
 * Time: 15:43
 * Version: 1.0
 */
public class Activity_Info_User extends BaseActivity implements InterfaceUser.View, InterfaceUploadImage.View {
    String sUserId = "";
    UserPresenter mPresenter;
    @BindView(R.id.edt_username)
    EditText edt_username;
    @BindView(R.id.img_avata)
    CircleImageView img_avata;
    @BindView(R.id.edt_stype)
    EditText edt_stype;
    @BindView(R.id.edt_fullname)
    EditText edt_fullname;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.edt_birthday)
    EditText edt_birthday;
    @BindView(R.id.edt_city)
    EditText edt_city;
    @BindView(R.id.edt_address)
    EditText edt_address;
    @BindView(R.id.edt_stk_bank)
    EditText edt_stk_bank;
    @BindView(R.id.edt_name_bank)
    EditText edt_name_bank;
    @BindView(R.id.edt_nganhang)
    EditText edt_nganhang;
    @BindView(R.id.edt_chinhanh)
    EditText edt_chinhanh;
    @BindView(R.id.edt_phone)
    EditText edt_phone;
    @BindView(R.id.switch_status_info_dichvu)
    SwitchCompat switch_status;
    @BindView(R.id.txt_status)
    TextView txt_status;
    @BindView(R.id.btn_capnhat)
    TextView btn_capnhat;
    @BindView(R.id.btn_doimatkhau)
    TextView btn_doimatkhau;
    @BindView(R.id.ll_city)
    ConstraintLayout ll_city;
    @BindView(R.id.ll_stype)
    ConstraintLayout ll_stype;
    @BindView(R.id.img_user_type)
    ImageView img_user_type;
    @BindView(R.id.img_get_city)
    ImageView img_get_city;
    @BindView(R.id.img_get_image)
    ImageView img_get_image;
    PresenterUploadImage mPresenterUpload;
    private String sUserType = "", sUsername = "", sPassWord = "", sEmail = "",
            sFulName = "", sMobile = "", sAddress = "", sDOB = "", sProvince = "", sSTK = "",
            sTenTK = "", sTenNN = "", sTenCN = "", sAVATAR = "", sState = "";

    @Override
    public int setContentViewId() {
        return R.layout.activity_infi_dichvu;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new UserPresenter(this);
        mPresenterUpload = new PresenterUploadImage(this);
        initAppbar();
        initData();
        initEvent();
    }

    Calendar myCalendar_start = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener start_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar_start.set(Calendar.YEAR, year);
            myCalendar_start.set(Calendar.MONTH, monthOfYear);
            myCalendar_start.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            update_start_date();
        }

    };
    BottomSheetDialog mBottomSheetDialog;
    TextView txt_chunha;
    TextView txt_dichvu;
    TextView txt_dong;

    private void show_bottom_dialog() {
        mBottomSheetDialog = new BottomSheetDialog(Activity_Info_User.this);
        View sheetView = getLayoutInflater().inflate(R.layout.dialog_usertype, null);
        mBottomSheetDialog.setContentView(sheetView);
        txt_chunha = sheetView.findViewById(R.id.txt_chunha);
        txt_dichvu = sheetView.findViewById(R.id.txt_dichvu);
        txt_dong = sheetView.findViewById(R.id.txt_dong_dialog);
        txt_chunha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserType = "1";
                edt_stype.setText("Chủ nhà");
                mBottomSheetDialog.dismiss();
            }
        });
        txt_dichvu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserType = "2";
                edt_stype.setText("Cộng tác viên");
                mBottomSheetDialog.dismiss();
            }
        });
        txt_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();
    }

    private void update_start_date() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edt_birthday.setText(sdf.format(myCalendar_start.getTime()));
    }

    private void initEvent() {
        img_user_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
                String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                if (objLogin.getUSER_TYPE().equals(Constants.UserType.ADMIN)) {
                    if (sUserId != null) {
                        if (!sUserName.equals(sUserId)) {
                            show_bottom_dialog();
                        }
                    }
                }

            }
        });
        switch_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sState = "1";
                } else {
                    sState = "0";
                }
            }
        });
        edt_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Activity_Info_User.this, R.style.MyDatePickerStyle, start_date,
                        myCalendar_start.get(Calendar.YEAR), myCalendar_start.get(Calendar.MONTH),
                        myCalendar_start.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        img_get_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionsAndOpenFilePicker();
            }
        });
        ll_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_city = new Intent(Activity_Info_User.this, ActivityListCity.class);
                startActivityForResult(intent_city, Constants.RequestCode.GET_CITY);
            }
        });
        img_get_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_city = new Intent(Activity_Info_User.this, ActivityListCity.class);
                startActivityForResult(intent_city, Constants.RequestCode.GET_CITY);
            }
        });
        btn_capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
               /* if (objLogin.getUSER_TYPE().equals(Constants.UserType.ADMIN)) {
                    get_api_reg(sUserId, "");
                } else {
                    sState = "1";
                    get_api_reg(sUserId, "1");
                }*/
                String pass = SharedPrefs.getInstance().get(Constants.KEY_SAVE_PASSWORD, String.class);
                get_api_reg(sUserId, sState, pass);

            }
        });
        btn_doimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogComfirm("Thông báo,",
                        "Bạn có chắc chắn muốn thay đổi mật khẩu không?",
                        true, new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                                String title = getIntent().getStringExtra(Constants.KEY_SEND_INFO_USERID_TITLE);
                                sUserId = getIntent().getStringExtra(Constants.KEY_SEND_INFO_USERID);
                                if (sUserName.equals(sUserId)) {
                                    showDialog_Pass();
                                } else {
                                    showDialog_Pass_user();
                                }

                            }

                            @Override
                            public void onClickNoDialog() {

                            }
                        });
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
        int max = 1;
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_CITY:
                if (resultCode == RESULT_OK) {
                    edt_city.setText(App.mCity.getNAME());
                    sProvince = App.mCity.getMATP();
                }
                break;
        }
        if (requestCode == FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            mediaFiles.clear();
            mediaFiles.addAll(data.<MediaFile>getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES));
            if (mediaFiles != null && mediaFiles.size() > 0) {
                IMAGE_PATH = mediaFiles.get(0).getPath();
                File imgFile = new File(IMAGE_PATH);
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    img_avata.setImageBitmap(myBitmap);
                }
            }

        }
    }

    TextView txt_title;

    private void initAppbar() {
        ImageView img_back = findViewById(R.id.btn_back);
        txt_title = findViewById(R.id.txt_title);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_title.setText("MY HOME");
    }

    private void initData() {
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        if (objLogin.getUSER_TYPE().equals(Constants.UserType.ADMIN)) {
            switch_status.setEnabled(true);
        } else {
            switch_status.setEnabled(false);
        }
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        String title = getIntent().getStringExtra(Constants.KEY_SEND_INFO_USERID_TITLE);
        sUserId = getIntent().getStringExtra(Constants.KEY_SEND_INFO_USERID);
        txt_title.setText(title);
        if (sUserId != null) {
            if (sUserName.equals(sUserId)) {
                btn_capnhat.setVisibility(View.VISIBLE);
                btn_doimatkhau.setVisibility(View.VISIBLE);
            } else {
                if (objLogin.getUSER_TYPE().equals(Constants.UserType.ADMIN)) {
                    btn_capnhat.setVisibility(View.VISIBLE);
                } else {
                    btn_capnhat.setVisibility(View.GONE);
                }
                // btn_doimatkhau.setVisibility(View.GONE);
            }
            showDialogLoading();
            mPresenter.api_get_user_info(sUserName, sUserId);
        }

    }

    @Override
    public void show_error_api(String sService) {

    }

    @Override
    public void show_get_user_info(ResponInfo objRes) {
        hideDialogLoading();
        if (objRes != null && objRes.getINFO() != null) {
            if (objRes.getINFO().size() > 0) {
                ObjLogin objLogin = objRes.getINFO().get(0);
                if (objLogin != null) {
                    // SharedPrefs.getInstance().put(Constants.KEY_SAVE_OBJECT_LOGIN, objLogin);
              /*      if (isUpdate)
                        finish();*/
                    set_info(objLogin);
                }

            }

        }
    }

    private void set_info(ObjLogin objLogin) {
        try {
            if (objLogin.getAVATAR() != null) {
                try {
                    String sUrl = Config.BASE_URL_MEDIA + objLogin.getAVATAR();
                    URL url = new URL(sUrl);
                    URI urlinfo = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
                            url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                    url = urlinfo.toURL();
                    RequestOptions options = new RequestOptions()
                            .placeholder(R.drawable.img_defaul)
                            .error(R.drawable.img_defaul);
                    Glide.with(this).load(url).apply(options).into(img_avata);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

            } else
                Glide.with(this).load(R.drawable.img_defaul).into(img_avata);
            edt_username.setText(objLogin.getUSERID());
            edt_stype.setText(objLogin.getDES_USERTYPE());
            edt_fullname.setText(objLogin.getFULL_NAME());
            edt_email.setText(objLogin.getEMAIL());
            edt_city.setText(objLogin.getPROVINCE_NAME());
            edt_address.setText(objLogin.getADDRESS());
            edt_phone.setText(objLogin.getMOBILE());
            edt_birthday.setText(TimeUtils.convent_date(objLogin.getDOB(), "dd/MM/yyyy HH:mm:ss",
                    "dd/MM/yyyy"));
            set_sate(objLogin.getSTATE());
            sUserType = objLogin.getUSER_TYPE();
            sProvince = objLogin.getID_PROVINCE();
            edt_name_bank.setText(objLogin.getTEN_TK());
            edt_nganhang.setText(objLogin.getTEN_NH());
            edt_stk_bank.setText(objLogin.getSO_TK());
            edt_chinhanh.setText(objLogin.getTEN_CN());
            if (objLogin.getAVATAR() != null)
                sAVATAR = objLogin.getAVATAR();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void set_sate(String state) {
        if (state.equals("0")) {
            sState = "0";
            switch_status.setChecked(false);
            txt_status.setText("Khóa");
            txt_status.setTextColor(getResources().getColor(R.color.black));
        } else if (state.equals("1")) {
            sState = "1";
            switch_status.setChecked(true);
            txt_status.setText("Kích hoạt");
        }
    }

    boolean isUpdate = false;

    @Override
    public void show_update_user_info(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            Toast.makeText(this, "Cập nhật thông tin tài khoản thành công.", Toast.LENGTH_SHORT).show();
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_OBJECT_LOGIN, mObjLogin);
            setResult(RESULT_OK);
            finish();
        } else {
            showDialogNotify("Thông báo", objError.getRESULT());
        }

    }

    @Override
    public void show_get_listuser(ResponInfo objRes) {

    }


    @Override
    public void show_change_pass(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_PASSWORD, sPassNew);
        } else {
            showDialogNotify("Thông báo", objError.getRESULT());
        }
    }

    private void get_api_reg(String sUserId, String sState, String sPass) {
        sPassWord = sPass;
        if (sUserType.length() == 0) {
            showDialogNotify("Thông báo", "Bạn chưa loại tài khoản để đăng ký.");
            return;
        }
      /*  if (edt_username.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào tên đăng nhập.");
            KeyboardUtil.requestKeyboard(edt_username);
            return;
        } else {
            sUsername = edt_username.getText().toString().trim();
        }*/
        if (edt_fullname.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào họ và tên.");
            KeyboardUtil.requestKeyboard(edt_fullname);
            return;
        } else {
            sFulName = edt_fullname.getText().toString().trim();
        }
        if (edt_phone.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào số điện thoại.");
            KeyboardUtil.requestKeyboard(edt_phone);
            return;
        } else {
            sMobile = edt_phone.getText().toString().trim();
        }
        if (edt_email.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào email của bạn.");
            KeyboardUtil.requestKeyboard(edt_email);
            return;
        } else {
            sEmail = edt_email.getText().toString().trim();
        }
        if (edt_birthday.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào email của bạn.");
            KeyboardUtil.requestKeyboard(edt_birthday);
            return;
        } else {
            sDOB = edt_birthday.getText().toString().trim();
        }
        if (sProvince.length() == 0) {
            showDialogNotify("Thông báo", "Bạn chưa chọn tỉnh thành phố.");
            return;
        }

        if (edt_address.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào địa chỉ của bạn.");
            KeyboardUtil.requestKeyboard(edt_address);
            return;
        } else {
            sAddress = edt_address.getText().toString().trim();
        }
        sSTK = edt_stk_bank.getText().toString().trim();
        sTenTK = edt_name_bank.getText().toString().trim();
        sTenNN = edt_nganhang.getText().toString();
        sTenCN = edt_chinhanh.getText().toString();
        if (IMAGE_PATH.length() > 0) {
            showDialogLoading();
            mPresenterUpload.api_upload_image_only(IMAGE_PATH);
        } else {
            get_api();
        }

    }
    ObjLogin mObjLogin;
    private void get_api() {
        showDialogLoading();
        sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mObjLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        if (sUserId != null) {
            if (sUsername.equals(sUserId)) {
                mObjLogin.setMOBILE(sMobile);
                mObjLogin.setEMAIL(sEmail);
                mObjLogin.setFULL_NAME(sFulName);
                mObjLogin.setADDRESS(sAddress);
                mObjLogin.setSO_TK(sSTK);
                mObjLogin.setTEN_TK(sTenTK);
                mObjLogin.setTEN_NH(sTenNN);
                mObjLogin.setTEN_CN(sTenCN);
            }
        }
        mPresenter.api_update_user_info(sUsername, sUserId, sPassWord, sMobile, sEmail, sFulName,
                sDOB, sUserType, sAVATAR, sState, sAddress, sProvince, sSTK, sTenTK,
                sTenNN, sTenCN);
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

    String IMAGE_PATH = "";

    @Override
    public void show_error_api_uploadimage() {
        hideDialogLoading();
        get_api();
    }

    @Override
    public void show_upload_image(String objError) {
        hideDialogLoading();
        if (objError != null && objError.length() > 0) {
            sAVATAR = objError;
            get_api();
        }
    }

    @Override
    public void show_upload_image_multil(String sListImage) {

    }

    Dialog dialog_pass;
    String sPassOld = "";
    String sPassNew = "";
    String sPassComfirm = "";

    public void showDialog_Pass() {
        dialog_pass = new Dialog(this, R.style.Theme_Dialog);
        dialog_pass.setCancelable(true);
        dialog_pass.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_pass.setContentView(R.layout.dialog_change_pass);
        dialog_pass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText edt_pass_old = dialog_pass.findViewById(R.id.edt_pass_old);
        EditText edt_pass_new = dialog_pass.findViewById(R.id.edt_pass_new);
        EditText edt_pass_comfirm = dialog_pass.findViewById(R.id.edt_pass_confirm);
        Button btn_comfirm = dialog_pass.findViewById(R.id.btn_update);

        btn_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_pass_old.getText().toString().length() == 0) {
                    Toast.makeText(Activity_Info_User.this, "Bạn chưa nhập vào mật khẩu cũ", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    sPassOld = edt_pass_old.getText().toString();
                }
                if (edt_pass_new.getText().toString().length() == 0) {
                    Toast.makeText(Activity_Info_User.this, "Bạn chưa nhập vào mật khẩu mới", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    sPassNew = edt_pass_new.getText().toString();
                }
                if (edt_pass_comfirm.getText().toString().length() == 0) {
                    Toast.makeText(Activity_Info_User.this,
                            "Bạn chưa nhập vào xác nhận mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    sPassComfirm = edt_pass_comfirm.getText().toString();
                }
                String sPass = SharedPrefs.getInstance().get(Constants.KEY_SAVE_PASSWORD, String.class);
                String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                if (sPassOld.equals(sPass)) {
                    if (sPassNew.equals(sPassComfirm)) {
                        showDialogLoading();
                        mPresenter.api_change_pass(sUserName, sPassOld, sPassNew);
                        dialog_pass.dismiss();
                    } else {
                        Toast.makeText(Activity_Info_User.this,
                                "Xác nhận mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Activity_Info_User.this,
                            "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog_pass.show();

    }

    public void showDialog_Pass_user() {
        dialog_pass = new Dialog(this, R.style.Theme_Dialog);
        dialog_pass.setCancelable(true);
        dialog_pass.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_pass.setContentView(R.layout.dialog_change_pass);
        dialog_pass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText edt_pass_old = dialog_pass.findViewById(R.id.edt_pass_old);
        EditText edt_pass_new = dialog_pass.findViewById(R.id.edt_pass_new);
        EditText edt_pass_comfirm = dialog_pass.findViewById(R.id.edt_pass_confirm);
        Button btn_comfirm = dialog_pass.findViewById(R.id.btn_update);

        edt_pass_new.setVisibility(View.GONE);
        edt_pass_comfirm.setVisibility(View.GONE);
        edt_pass_old.setHint("Nhập vào mật khẩu muốn thay đổi");
        btn_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_pass_old.getText().toString().length() == 0) {
                    Toast.makeText(Activity_Info_User.this, "Bạn chưa nhập vào mật khẩu ", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    sPassOld = edt_pass_old.getText().toString();
                }
                get_api_reg("", "", sPassOld);
                dialog_pass.dismiss();
            }
        });
        dialog_pass.show();

    }
}
