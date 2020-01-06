package com.vn.myhome.activity.notifycation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.jaiselrahman.filepicker.utils.FilePickerProvider;
import com.vn.myhome.R;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.callback.ClickDialog;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.ObjErrorApi;
import com.vn.myhome.models.ObjNotify;
import com.vn.myhome.models.ResponseApi.ResponGetListNotify;
import com.vn.myhome.presenter.InterfaceNotify;
import com.vn.myhome.presenter.NotifyPresenter;
import com.vn.myhome.untils.SharedPrefs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 15-July-2019
 * Time: 09:18
 * Version: 1.0
 */
public class Activity_Notify_Detail extends BaseActivity implements InterfaceNotify.View {
    @BindView(R.id.img_home)
    ImageView img_home;
    @BindView(R.id.txt_time)
    TextView txt_time;
    @BindView(R.id.txt_title_notify)
    TextView txt_title;
    @BindView(R.id.webview_content)
    TextView webview_content;
    public static final int PERMISSIONS_REQUEST_CODE = 0;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private final static int FILE_REQUEST_CODE = 1;
    private ArrayList<MediaFile> mediaFiles = new ArrayList<>();
    NotifyPresenter mPresenter;
    ObjNotify objNotify;

    //  String path = Environment.getExternalStorageDirectory().toString();
    @Override
    public int setContentViewId() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new NotifyPresenter(this);
        initAppbar();
        init();
        initData();
        initEvent();
    }

    String sUserId;

    private void initData() {
        sUserId = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        objNotify = (ObjNotify) getIntent().getSerializableExtra(Constants.KEY_SEND_NOTIFY_DETAIL);
        if (objNotify != null) {
            mPresenter.api_update_list_notifi(sUserId, objNotify.getID());
            set_info();
        }
    }

    private void set_info() {
        txt_title.setText(objNotify.getTITLE());
        txt_time.setText(objNotify.getSENT_TIME());
        if (objNotify.getCONTENT() != null) {
            //  initWebview(webview_content, objNotify.getCONTENT());
            webview_content.setText(objNotify.getCONTENT());
        }
    }

    private void initEvent() {

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogComfirm("Thông báo",
                        "Bạn có chắc chắn muốn xoá tin nhắn này không.",
                        true, new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {

                            }

                            @Override
                            public void onClickNoDialog() {

                            }
                        });

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void send_api() {

    }

    private void init() {
    }

    String IMAGE_PATH = "";
    String sUrlImage = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null) {

        }
    }

    private void showError() {
        Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT).show();
    }

    public void shareLogFile() {
        File logFile = new File(getExternalCacheDir(), "logcat.txt");
        try {
            if (logFile.exists())
                logFile.delete();
            logFile.createNewFile();
            Runtime.getRuntime().exec("logcat -f " + logFile.getAbsolutePath() + " -t 100 *:W Glide:S " + FilePickerActivity.TAG);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (logFile.exists()) {
            Intent intentShareFile = new Intent(Intent.ACTION_SEND);
            intentShareFile.setType("text/plain");
            intentShareFile.putExtra(Intent.EXTRA_STREAM,
                    FilePickerProvider.getUriForFile(this, logFile));
            intentShareFile.putExtra(Intent.EXTRA_SUBJECT, "FilePicker Log File");
            intentShareFile.putExtra(Intent.EXTRA_TEXT, "FilePicker Log File");
            startActivity(Intent.createChooser(intentShareFile, "Share"));
        }
    }

    private void initAppbar() {
        ImageView img_back = findViewById(R.id.btn_back);
        TextView txt_title = findViewById(R.id.txt_title);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_title.setText("NỘI DUNG");
        img_home.setVisibility(View.VISIBLE);
        img_home.setImageResource(R.drawable.ic_delete);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public static void initWebview(WebView webview_debai, String link_web) {
        webview_debai.getSettings().setJavaScriptEnabled(true);
        webview_debai.getSettings();
        webview_debai.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webview_debai.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setDefaultFontSize(18);
        webSettings.setTextZoom((int) (webSettings.getTextZoom() * 1));
        /* <html><body  align='center'>You scored <b>192</b> points.</body></html>*/
        String pish = "<html><body>";
        String pas = "</body></html>";

        webview_debai.loadDataWithBaseURL("", pish + link_web + pas,
                "text/html", "UTF-8", "");
    }

    @Override
    public void show_error_api(String sService) {

    }

    @Override
    public void show_list_notifi(ResponGetListNotify objError) {

    }

    @Override
    public void show_update_list_notifi(ObjErrorApi objError) {

    }
}
