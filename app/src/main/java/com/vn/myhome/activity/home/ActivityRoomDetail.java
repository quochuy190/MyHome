package com.vn.myhome.activity.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skyhope.showmoretextview.ShowMoreTextView;
import com.vn.myhome.R;
import com.vn.myhome.activity.book_room.Activity_calendar_booking;
import com.vn.myhome.adapter.AdapterImageUpFace;
import com.vn.myhome.adapter.AdapterImage_Zoom_Viewpage;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ItemClickListener;
import com.vn.myhome.config.Config;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ImageRoomObj;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ResponseApi.GetAlbumImageHomeResponse;
import com.vn.myhome.models.ResponseApi.GetImageCoverResponse;
import com.vn.myhome.models.ResponseApi.GetRoomResponse;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.StringUtil;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 13-August-2019
 * Time: 01:29
 * Version: 1.0
 */
public class ActivityRoomDetail extends BaseActivity implements InterfaceRoom.View {
    @BindView(R.id.txt_name_roomdetail)
    TextView txt_name_homestay;
    @BindView(R.id.img_room_avata)
    ImageView img_room_cover;
    @BindView(R.id.txt_address_roomdetail)
    TextView txt_address;
    @BindView(R.id.txt_name_host)
    TextView txt_name_host;
    @BindView(R.id.txt_email_host)
    TextView txt_email_host;
    @BindView(R.id.txt_phome_host)
    TextView txt_phome_host;
    @BindView(R.id.txt_room_max)
    TextView txt_room_max;
    @BindView(R.id.txt_bed_max)
    TextView txt_bed_max;
    @BindView(R.id.txt_guest_max)
    TextView txt_guest_max;
    @BindView(R.id.txt_price_thu)
    TextView txt_price_thu;

    @BindView(R.id.txt_price_weeked)
    TextView txt_price_weeked;
    @BindView(R.id.txt_price_dondep)
    TextView txt_price_dondep;
    @BindView(R.id.txt_price_add_guest)
    TextView txt_price_add_guest;
    @BindView(R.id.txt_price_total)
    TextView txt_price_total;
    @BindView(R.id.txt_description)
    ShowMoreTextView txt_description;
    @BindView(R.id.txt_infomation)
    TextView txt_infomation;
    @BindView(R.id.btn_booking)
    Button btn_booking;
    @BindView(R.id.ll_view_all_image)
    ConstraintLayout ll_view_all_image;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.txt_view_all_image)
    TextView txt_view_all_image;
    @BindView(R.id.txt_max_guest_exit)
    TextView txt_max_guest_exit;
    private ObjHomeStay objRoom;
    private RoomPresenter mPresenter;
    String sUsername;

    @Override
    public int setContentViewId() {
        return R.layout.activity_room_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new RoomPresenter(this);
        initListImage();
        initData();
        initEvent();
    }

    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_view_all_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);

                Intent intent = new Intent(ActivityRoomDetail.this, Activity_calendar_booking.class);
                intent.putExtra(Constants.KEY_SEND_ROOM_BOOKING, objRoom);
                startActivity(intent);*/
                showDialogLoading();
                mPresenter.api_check_lock(sUsername, objRoom.getGENLINK());

            }
        });
    }

    private void initData() {
        sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        objRoom = (ObjHomeStay) getIntent().getSerializableExtra(Constants.KEY_SEND_ROOM_DETAIL);
        if (objRoom != null) {
            if (objRoom.getGENLINK() != null) {
                showDialogLoading();
                mPresenter.api_get_room_detail(sUsername, objRoom.getGENLINK());
                mPresenter.api_get_album_image(sUsername, objRoom.getGENLINK());
            }

        }
    }

    private void initTexView() {
        // For using character length
        // textView.setShowingChar(numberOfCharacter);
        //number of line you want to short
        txt_description.setShowingLine(4);
        txt_description.addShowMoreText("Xem thêm");
        txt_description.addShowLessText("Thu gọn");
        txt_description.setShowMoreColor(Color.BLUE); // or other color
        txt_description.setShowLessTextColor(Color.BLUE); // or other color
     /*   txt_infomation.setShowingLine(2);
        txt_infomation.addShowMoreText("Xem thêm");
        txt_infomation.addShowLessText("Thu gọn");
        txt_infomation.setShowMoreColor(Color.BLUE); // or other color
        txt_infomation.setShowLessTextColor(Color.BLUE);*/
    }

    @Override
    public void show_error_api(String sService) {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_get_listroom_idx(GetRoomResponse objRes) {

    }

    @Override
    public void show_get_cover_idx(GetImageCoverResponse obj) {

    }

    @Override
    public void show_search_home(GetRoomResponse objRes) {

    }

    @Override
    public void show_get_room_detail(ObjHomeStay objRoom) {
        hideDialogLoading_delay();
        if (objRoom != null && objRoom.getERROR().equals("0000")) {
            set_info_room(objRoom);
        } else
            showDialogNotify("Thông báo", objRoom.getRESULT());
    }

    private void set_info_room(ObjHomeStay objRoomDetail) {
        objRoom = objRoomDetail;
        if (objRoomDetail.getNAME() != null)
            txt_name_homestay.setText(objRoomDetail.getNAME());
        if (objRoomDetail.getADDRESS() != null)
            txt_address.setText(objRoomDetail.getADDRESS());
        if (objRoomDetail.getEMAIL() != null)
            txt_email_host.setText(objRoomDetail.getEMAIL());
        if (objRoomDetail.getMOBILE() != null)
            txt_phome_host.setText(objRoomDetail.getMOBILE());
        if (objRoomDetail.getHOST_NAME() != null)
            txt_name_host.setText(objRoomDetail.getHOST_NAME());
        if (objRoomDetail.getMAX_BED() != null)
            txt_bed_max.setText(objRoomDetail.getMAX_BED());
        if (objRoomDetail.getMAX_GUEST() != null)
            txt_guest_max.setText(objRoomDetail.getMAX_GUEST());
        if (objRoomDetail.getMAX_ROOM() != null)
            txt_room_max.setText(objRoomDetail.getMAX_ROOM());
        if (objRoomDetail.getMAX_GUEST_EXIST() != null)
            txt_max_guest_exit.setText(objRoomDetail.getMAX_GUEST_EXIST());

        if (objRoomDetail.getPRICE() != null) {
            txt_price_thu.setText(StringUtil.conventMonney_Long(objRoomDetail.getPRICE()));
            txt_price_total.setText(StringUtil.conventMonney_Long(objRoomDetail.getPRICE()));
        }

        if (objRoomDetail.getPRICE_SPECIAL() != null)
            txt_price_weeked.setText(StringUtil.conventMonney_Long(objRoomDetail.getPRICE_SPECIAL()));
        if (objRoomDetail.getCLEAN_ROOM() != null)
            txt_price_dondep.setText(StringUtil.conventMonney_Long(objRoomDetail.getCLEAN_ROOM()));
        if (objRoomDetail.getPRICE_EXTRA() != null)
            txt_price_add_guest.setText(StringUtil.conventMonney_Long(objRoomDetail.getPRICE_EXTRA()));
        if (objRoomDetail.getDESCRIPTION() != null)
            txt_description.setText(objRoomDetail.getDESCRIPTION());
        if (objRoomDetail.getINFOMATION() != null)
            txt_infomation.setText(Html.fromHtml(objRoomDetail.getINFOMATION()));
        initTexView();
        if (objRoomDetail.getIMG() != null) {
            try {
                String sUrl = Config.BASE_URL_MEDIA + objRoomDetail.getIMG();
                URL url = new URL(sUrl);
                URI urlinfo = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
                        url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                url = urlinfo.toURL();
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.img_defaul)
                        .error(R.drawable.img_defaul);
                Glide.with(this).load(url).apply(options).into(img_room_cover);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        } else
            Glide.with(this).load(R.drawable.img_defaul).into(img_room_cover);

    }

    @Override
    public void show_get_album_image(GetAlbumImageHomeResponse objRes) {
        hideDialogLoading();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            mListAnh.clear();
            mListAnh.addAll(objRes.getINFO());
            adapterImageUpFace.notifyDataSetChanged();
            txt_view_all_image.setText("Hình ảnh chi tiết(" + mListAnh.size() + ")");
        }
    }

    @Override
    public void show_check_lock(ObjErrorApi objRes) {
        hideDialogLoading();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            if (objRes.getSTATUS().equals("0")) {
                Intent intent = new Intent(ActivityRoomDetail.this, Activity_calendar_booking.class);
                intent.putExtra(Constants.KEY_SEND_ROOM_BOOKING, objRoom);
                startActivity(intent);
            } else
                showDialogNotify("Thông báo", "Bạn không có quyền đặt phòng");
        } else {
            showDialogNotify("Thông báo", objRes.getRESULT());
        }
    }

    @BindView(R.id.rcv_image_face)
    RecyclerView rcv_image_face;
    // private List<String> mList;
    AdapterImageUpFace adapterImageUpFace;
    RecyclerView.LayoutManager layoutManager;

    private void initListImage() {
        adapterImageUpFace = new AdapterImageUpFace(mListAnh, this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcv_image_face.setNestedScrollingEnabled(false);
        rcv_image_face.setHasFixedSize(true);
        rcv_image_face.setLayoutManager(layoutManager);
        rcv_image_face.setItemAnimator(new DefaultItemAnimator());
        rcv_image_face.setAdapter(adapterImageUpFace);
        adapterImageUpFace.notifyDataSetChanged();
        adapterImageUpFace.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                showDialog_Full_image(position);
            }
        });

    }

    Dialog dialog;
    List<ImageRoomObj> mListAnh = new ArrayList<>();

    public void showDialog_Full_image(int posision) {
        dialog = new Dialog(ActivityRoomDetail.this, R.style.Theme_Dialog);
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
