package com.vn.myhome.fragment.myhome;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.activity.login.ActivityListCity;
import com.vn.myhome.activity.login.ActivityListTowers;
import com.vn.myhome.activity.myhome.ActivityNewRoom;
import com.vn.myhome.adapter.AdapterImagePath;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.callback.OnItemClickListennerTwoBtn;
import com.vn.myhome.config.Config;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ObjImageHome;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.models.ResponseApi.ObjCity;
import com.vn.myhome.models.ResponseApi.ResponListImageHome;
import com.vn.myhome.presenter.InterfaceMyHome;
import com.vn.myhome.presenter.MyHomePresenter;
import com.vn.myhome.untils.KeyboardUtil;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.StringUtil;
import com.vn.myhome.upload_media.InterfaceUploadImage;
import com.vn.myhome.upload_media.PresenterUploadImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 22-April-2019
 * Time: 10:30
 * Version: 1.0
 */
public class FragmentInfoNewRoom extends BaseFragment implements View.OnClickListener,
        InterfaceMyHome.View, InterfaceUploadImage.View {
    private static final String TAG = "FragmentInfoNewRoom";
    public static FragmentInfoNewRoom fragment;
    @BindView(R.id.rcv_list_image)
    RecyclerView rcv_list_image;
    @BindView(R.id.ic_get_image_cover)
    CircleImageView ic_get_image_cover;
    @BindView(R.id.img_cover)
    ImageView img_cover;
    @BindView(R.id.ll_city)
    ConstraintLayout ll_city;
    @BindView(R.id.ll_tower)
    ConstraintLayout ll_tower;
    @BindView(R.id.edt_city_myhome)
    EditText edt_city;
    @BindView(R.id.edt_tower_myhome)
    EditText edt_tower;
    @BindView(R.id.btn_add_room)
    Button btn_add_room;
    @BindView(R.id.edt_name_myhome)
    EditText edt_name_myhome;
    @BindView(R.id.edt_address_myhome)
    EditText edt_address;
    @BindView(R.id.edt_sort_description)
    EditText edt_sort_description;
    @BindView(R.id.edt_full_des)
    EditText edt_full_des;
    @BindView(R.id.edt_number_room)
    EditText edt_number_room;
    @BindView(R.id.edt_number_bed)
    EditText edt_number_bed;
    LinearLayoutManager layoutManager;
    List<ObjImageHome> mListImage;
    AdapterImagePath adapterImage;
    String sOption = "";
    ObjCity objCity;
    MyHomePresenter mPresenter;
    PresenterUploadImage mPresenterUpload;

    public static FragmentInfoNewRoom getInstance() {
        if (fragment == null) {
            synchronized (FragmentInfoNewRoom.class) {
                if (fragment == null)
                    fragment = new FragmentInfoNewRoom();
            }
        }
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.KEY_UPDATE_MYHOME)) {
            get_api();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_room, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new MyHomePresenter(this);
        mPresenterUpload = new PresenterUploadImage(this);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mListImageHome = new ArrayList<>();
        initLisImage();
        initData();
        KeyboardUtil.hideSoftKeyboard(getActivity());
        initEvent();
        return view;
    }

    String sUserName;

    private void initData() {
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        if (ActivityNewRoom.isUpdate) {
            btn_add_room.setText("CẬP NHẬT");
        } else
            btn_add_room.setText("TẠO PHÒNG");
        if (ActivityNewRoom.objMyhome != null) {
            setDataMyHome(ActivityNewRoom.objMyhome);
            mPresenter.api_get_album_image(sUserName, sGetlink);
        } else {
            sGetlink = ActivityNewRoom.sGetlink;
            mListImage.add(null);
        }
    }

    private void setDataMyHome(ObjHomeStay objMyhome) {
        try {
            if (objMyhome.getID_PROVINCE() != null
                    && objMyhome.getPROVINCE_NAME() != null) {
                objCity = new ObjCity(objMyhome.getID_PROVINCE(), objMyhome.getPROVINCE_NAME());
                edt_city.setText(objMyhome.getPROVINCE_NAME());
            }
            if (objMyhome.getLOCATION_ID() != null) {
                sLocationId = objMyhome.getLOCATION_ID();
                switch (objMyhome.getLOCATION_ID()) {
                    case "1":
                        edt_tower.setText("Sunrise");
                        break;
                    case "2":
                        edt_tower.setText("Greenbay Premium");
                        break;
                    case "3":
                        edt_tower.setText("Greenbay Garden");
                        break;
                }

            }
            if (objMyhome.getNAME() != null) {
                edt_name_myhome.setText(objMyhome.getNAME());
            }
            if (objMyhome.getADDRESS() != null)
                edt_address.setText(objMyhome.getADDRESS());
            if (objMyhome.getDESCRIPTION() != null)
                edt_sort_description.setText(objMyhome.getDESCRIPTION());
            if (objMyhome.getINFOMATION() != null)
                edt_full_des.setText(Html.fromHtml(objMyhome.getINFOMATION()));
            if (objMyhome.getPRICE() != null) {
                sPrice = objMyhome.getPRICE();
            }
            if (objMyhome.getPRICE_EXTRA() != null) {
                sPriceExtra = objMyhome.getPRICE_EXTRA();
            }
            if (objMyhome.getPRICE_SPECIAL() != null) {
                sPriceSpecial = objMyhome.getPRICE_SPECIAL();
            }
            if (objMyhome.getGENLINK() != null)
                sGetlink = objMyhome.getGENLINK();
            if (objMyhome.getMAX_GUEST() != null) {
                sMaxGuest = objMyhome.getMAX_GUEST();
            }
            if (objMyhome.getMAX_BED() != null) {
                edt_number_bed.setText(objMyhome.getMAX_BED());
                sMaxBed = objMyhome.getMAX_BED();
            }
            if (objMyhome.getMAX_ROOM() != null) {
                edt_number_room.setText(objMyhome.getMAX_ROOM());
                sMaxRoom = objMyhome.getMAX_ROOM();
            }
            if (objMyhome.getCLEAN_ROOM() != null)
                sClean_Room = objMyhome.getCLEAN_ROOM();
            if (objMyhome.getIMG() != null) {
                try {
                    String sUrl = Config.BASE_URL_MEDIA + objMyhome.getIMG();
                    URL url = new URL(sUrl);
                    URI urlinfo = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
                            url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                    url = urlinfo.toURL();
                    RequestOptions options = new RequestOptions()
                            .placeholder(R.drawable.img_defaul)
                            .error(R.drawable.img_defaul);
                    Glide.with(getActivity()).load(url).apply(options).into(img_cover);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

            } else
                Glide.with(getActivity()).load(R.drawable.img_defaul).into(img_cover);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<ObjImageHome> mListImageHome;

    private void initEvent() {
        ic_get_image_cover.setOnClickListener(this);
        edt_tower.setOnClickListener(this);
        ll_city.setOnClickListener(this);
        ll_tower.setOnClickListener(this);
        edt_city.setOnClickListener(this);
        btn_add_room.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_get_image_cover:
                sOption = "1";
                checkPermissionsAndOpenFilePicker();
                break;
            case R.id.ll_city:
                App.mCity = null;
                Intent intent_city = new Intent(getContext(), ActivityListCity.class);
                startActivityForResult(intent_city, Constants.RequestCode.GET_CITY);
                break;
            case R.id.ll_tower:
                if (objCity != null && objCity.getMATP() != null) {
                    App.mTower = null;
                    Intent intent_tower = new Intent(getContext(), ActivityListTowers.class);
                    intent_tower.putExtra(Constants.KEY_SEND_ID_PROVINCE, objCity.getMATP());
                    startActivityForResult(intent_tower, Constants.RequestCode.GET_TOWER);
                } else
                    showAlertDialog(getString(R.string.txt_dialog_title), getString(R.string.txt_dialog_content_validate_city));

                break;
            case R.id.edt_tower_myhome:
                if (objCity != null && objCity.getMATP() != null) {
                    App.mTower = null;
                    Intent intent_tower = new Intent(getContext(), ActivityListTowers.class);
                    intent_tower.putExtra(Constants.KEY_SEND_ID_PROVINCE, objCity.getMATP());
                    startActivityForResult(intent_tower, Constants.RequestCode.GET_TOWER);
                } else
                    showAlertDialog(getString(R.string.txt_dialog_title), getString(R.string.txt_dialog_content_validate_city));
                break;
            case R.id.edt_city_myhome:
                App.mCity = null;
                Intent intent_city1 = new Intent(getContext(), ActivityListCity.class);
                startActivityForResult(intent_city1, Constants.RequestCode.GET_CITY);
                break;
            case R.id.btn_add_room:
                get_api_edit_myhome();
                EventBus.getDefault().post(new MessageEvent(Constants.EventBus.KEY_UPDATE_MYHOME, 1, 0));
                break;
        }
    }

    private void get_api_edit_myhome() {
        if (edt_name_myhome.getText().toString().length() == 0) {
            showAlertDialog("Thông báo", "Mời bạn nhập vào tên nhà của bạn.");
            KeyboardUtil.requestKeyboard(edt_name_myhome);
            return;
        }
        if (edt_address.getText().toString().length() == 0) {
            showAlertDialog("Thông báo", "Mời bạn nhập vào địa chỉ");
            KeyboardUtil.requestKeyboard(edt_address);
            return;
        }
        if (edt_sort_description.getText().toString().length() == 0) {
            showAlertDialog("Thông báo", "Mời bạn nhập vào mô tả nhà.");
            KeyboardUtil.requestKeyboard(edt_sort_description);
            return;
        }
        if (edt_full_des.getText().toString().length() == 0) {
            showAlertDialog("Thông báo", "Mời bạn nhập vào mô tả đầy đủ nhà của bạn");
            KeyboardUtil.requestKeyboard(edt_full_des);
            return;
        }
        if (edt_number_room.getText().toString().length() == 0) {
            showAlertDialog("Thông báo", "Mời bạn nhập vào số phòng.");
            KeyboardUtil.requestKeyboard(edt_number_room);
            return;
        }
        if (edt_number_bed.getText().toString().length() == 0) {
            showAlertDialog("Thông báo", "Mời bạn nhập vào số giường.");
            KeyboardUtil.requestKeyboard(edt_number_bed);
            return;
        }
        if (objCity != null && objCity.getMATP() != null) {
            sProvinceId = objCity.getMATP();
        } else {
            showAlertDialog("Thông báo", "Mời bạn nhập vào thành phố.");
            return;
        }
        sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenterUpload.api_upload_image_multil(sUser, sGetlink, mListImage);
        if (IMAGE_PATH.length() > 0) {
            showDialogLoading();
            isCount = 0;
            mPresenterUpload.api_upload_image_only(IMAGE_PATH);
        } else {
            showDialogLoading();
            get_api();
        }
    }

    public static String sUser;
    boolean isUploadAvata = false;
    int isCount = 0;
    String sUsername = "", sName = "", sAddress = "", sPrice = "", sPriceSpecial = "", sPriceExtra = "", sMaxGuest = "",
            sMaxRoom = "", sMaxBed = "", sClean_Room = "", sDesCription = "", sInformation = "", sPolicy_cancle = "",
            sGetlink = "", sProvinceId = "", sLocationId = "", sCover = "", sMaxGuest_Exst = "";

    private void get_api() {
      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000);*/
        sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        sName = edt_name_myhome.getText().toString();
        sAddress = edt_address.getText().toString();
        sDesCription = edt_sort_description.getText().toString();
        sInformation = edt_full_des.getText().toString();
        sMaxRoom = edt_number_room.getText().toString();
        sMaxBed = edt_number_bed.getText().toString();
        if (objCity != null && objCity.getMATP() != null) {
            sProvinceId = objCity.getMATP();
        }
        mPresenter.api_edit_room(sUsername, sName, sAddress, sPrice, sPriceSpecial, sPriceExtra, sMaxGuest,
                sMaxRoom, sMaxBed, sClean_Room, sDesCription, sInformation, sPolicy_cancle, sGetlink,
                sProvinceId, sLocationId, sCover, sMaxGuest_Exst);
    }

    private void initLisImage() {
        mListImage = new ArrayList<>();
        adapterImage = new AdapterImagePath(mListImage, getContext());
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), HORIZONTAL, false);
        rcv_list_image.setNestedScrollingEnabled(false);
        rcv_list_image.setHasFixedSize(true);
        rcv_list_image.setLayoutManager(layoutManager);
        rcv_list_image.setItemAnimator(new DefaultItemAnimator());
        rcv_list_image.setAdapter(adapterImage);
        adapterImage.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                ObjImageHome obj = (ObjImageHome) item;
                if (obj == null) {
                    sOption = "2";
                    checkPermissionsAndOpenFilePicker();
                }
            }
        });
        adapterImage.setClick_delete(new OnItemClickListennerTwoBtn() {
            @Override
            public void OnItemClickListener_One_Btn(int position) {
                showDialogComfirm("Thông báo",
                        "Bạn có chắc chắn muốn xóa ảnh này không?", true,
                        new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                mListImage.remove(position);
                                adapterImage.notifyDataSetChanged();
                            }

                            @Override
                            public void onClickNoDialog() {

                            }
                        });

            }

            @Override
            public void OnItemClickListener_Two_Btn(int position) {

            }
        });
        adapterImage.notifyDataSetChanged();
    }

    private void checkPermissionsAndOpenFilePicker() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            get_file_picker();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    private ArrayList<MediaFile> mediaFiles = new ArrayList<>();
    public static final int PERMISSIONS_REQUEST_CODE = 0;
    private final static int FILE_REQUEST_CODE = 1;

    private void get_file_picker() {
        int max = 1;
        if (sOption.equals("1")) {
            max = 1;
        } else if (sOption.equals("2")) {
            max = 10;
        }
        mediaFiles.clear();
        Intent intent = new Intent(getContext(), FilePickerActivity.class);
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
        Toast.makeText(getActivity(), "Allow external storage reading", Toast.LENGTH_SHORT).show();
    }

    String IMAGE_PATH = "";
    String IMAGE_PATH_MULTIL = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            mediaFiles.clear();
            mediaFiles.addAll(data.<MediaFile>getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES));
            if (sOption.equals("2")) {
                if (mediaFiles != null && mediaFiles.size() > 0) {
                    for (MediaFile media : mediaFiles) {
                        ObjImageHome obj = new ObjImageHome(media.getPath());
                        mListImage.add(1, obj);
                    }
                } else {
                }
                adapterImage.notifyDataSetChanged();
            } else if (sOption.equals("1")) {
                if (mediaFiles != null && mediaFiles.size() > 0) {
                    IMAGE_PATH = mediaFiles.get(0).getPath();
                    File imgFile = new File(IMAGE_PATH);
                    if (imgFile.exists()) {
                        //   Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        Bitmap myBitmap =
                                null;
                        try {
                            myBitmap = StringUtil.decodeSampledBitmapFromUri(getContext(),
                                    Uri.fromFile(new File(imgFile.getAbsolutePath())), 500, 500);
                            img_cover.setImageBitmap(myBitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        switch (requestCode) {
            case Constants.RequestCode.GET_CITY:
                if (resultCode == RESULT_OK) {
                    if (App.mCity != null) {
                        objCity = App.mCity;
                        edt_city.setText(App.mCity.getNAME());
                    } else {
                        edt_city.setText("");
                        edt_city.setHint(getContext().getString(R.string.hide_register_city));
                    }
                    sLocationId = "";
                    edt_tower.setText("");
                    edt_tower.setHint(getContext().getString(R.string.hide_tower));
                    App.mTower = null;
                }
            case Constants.RequestCode.GET_TOWER:
                if (resultCode == RESULT_OK) {
                    if (App.mTower != null) {
                        edt_tower.setText(App.mTower.getsName());
                        sLocationId = App.mTower.getsId();
                    } else {
                        edt_tower.setText("");
                        edt_tower.setHint(getContext().getString(R.string.hide_tower));
                    }
                }
                break;
        }
    }


    @Override
    public void show_error_api(String sService) {

    }

    @Override
    public void show_get_mylist_room(GetRoomResponse objRes) {

    }

    @Override
    public void show_room_detail(ObjHomeStay objRes) {

    }

    @Override
    public void show_new_room(ObjErrorApi objError) {

    }

    @Override
    public void show_edit_room(ObjErrorApi objError) {
        hideDialogLoading();
        if (objError != null && objError.getERROR().equals("0000")) {
            //Toast.makeText(getActivity(), "Cập nhật nhà thành công.", Toast.LENGTH_SHORT).show();
        } else showAlertDialog("Lỗi", objError.getRESULT());
    }

    @Override
    public void show_delete_room(ObjErrorApi objError) {

    }

    @Override
    public void show_update_state_room(ObjErrorApi objError) {

    }

    @Override
    public void show_get_album_image(ResponListImageHome objRes) {
        hideDialogLoading();
        mListImage.clear();
        mListImage.add(null);
        if (objRes != null && objRes.getERROR().equals("0000")) {
            if (objRes.getINFO() != null)
                mListImage.addAll(objRes.getINFO());
        }
        /*if (mListImageHome.size() > 0) {
            for (ObjImageHome objImageHome : mListImageHome)
                mListImage.add(objImageHome.getIMG());
        }*/
        adapterImage.notifyDataSetChanged();
    }

    @Override
    public void show_error_api_uploadimage() {
        get_api();
    }

    @Override
    public void show_upload_image(String objError) {
        if (objError != null && objError.length() > 0) {
            sCover = objError;
        }
        get_api();
    }

    @Override
    public void show_upload_image_multil(String sListImage) {
        hideDialogLoading();

    }
}
