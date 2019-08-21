package com.vn.myhome.activity.home;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skyhope.showmoretextview.ShowMoreTextView;
import com.vn.myhome.R;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.config.Config;
import com.vn.myhome.config.Constants;
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
    @BindView(R.id.ll_view_all_image)
    ConstraintLayout ll_view_all_image;
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

        initData();
        initEvent();
    }

    private void initEvent() {
        ll_view_all_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            txt_room_max.setText(objRoomDetail.getMAX_GUEST());
        if (objRoomDetail.getMAX_ROOM() != null)
            txt_guest_max.setText(objRoomDetail.getMAX_ROOM());

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

    }
}
