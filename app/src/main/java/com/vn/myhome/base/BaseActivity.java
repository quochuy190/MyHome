package com.vn.myhome.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.vn.myhome.R;
import com.vn.myhome.callback.ClickDialog;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected AlertDialog.Builder builder;
    boolean layout = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        onPostSetContentView(savedInstanceState);
        setContentView(setContentViewId());
        ButterKnife.bind(this);
    }

    public void setLayout(boolean layout) {
        this.layout = layout;
    }

    protected void onPostSetContentView(Bundle savedInstanceState) {

    }

    public abstract int setContentViewId();

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    public void showAlertDialog(String title, String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this,
                    android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        } else
            builder = new AlertDialog.Builder(this);

        builder.setTitle(title)
                .setCancelable(false)
                .setMessage(content)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();

    }

    public void showAlertErrorNetwork() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        } else
            builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.error_network))
                .setCancelable(false)
                .setMessage(getString(R.string.error_network_message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();
    }

    protected ProgressDialog dialog;
    private Handler StopDialogLoadingHandler = new Handler();

    public void hideDialogLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void hideDialogLoading_delay() {
        StopDialogLoadingHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 1000);

    }

    public void showDialogLoading() {
        StopDialogLoadingHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 20000);
        if (!isFinishing()) {
            dialog = new ProgressDialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
            // dialog = new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(getString(R.string.txt_loading_dialog));
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
        }
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void showDialogLoadingtime(int time) {
        StopDialogLoadingHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, time);
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
        }
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void showDialogLoading(String message) {
        StopDialogLoadingHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 15000);
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(message);
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
        }
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public boolean isNetwork() {
        boolean is3g, isWifi;
        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        //For 3G check
        is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        //For WiFi Check
        isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();
        if (!is3g && !isWifi) {
            showAlertErrorNetwork();
            return false;
        } else return true;
    }


    public void showDialogComfirm(String title, String message, boolean is_show_cancel,
                                  final ClickDialog clickDialog) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        } else
            builder = new AlertDialog.Builder(this);
        if (is_show_cancel) {
            builder.setTitle(title)
                    .setCancelable(false)
                    .setMessage(Html.fromHtml(message))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            clickDialog.onClickYesDialog();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            clickDialog.onClickNoDialog();
                        }
                    })
                    .show();
        } else {
            builder.setTitle(title)
                    .setCancelable(false)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            clickDialog.onClickYesDialog();
                        }
                    })
                    /*  .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {

                          }
                      })*/
                    .show();
        }

    }

    public void showDialogNotify(String title, String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        } else
            builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    Dialog dialog_call;

    public void showDialog_CallPhone(ClickDialog clickDialog) {
        dialog_call = new Dialog(this, R.style.Theme_Dialog);
        // dialog.setCancelable(false);
        dialog_call.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_call.setContentView(R.layout.dialog_confirm_call);
        dialog_call.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView txt_name = dialog_call.findViewById(R.id.txt_title);
        TextView txt_exit = dialog_call.findViewById(R.id.txt_exit);
        TextView txt_call = dialog_call.findViewById(R.id.txt_call);
        String sName = "Tổng đài chăm sóc khách hàng <b><font color='#FF0000'>1900 1909</font></b>";
        txt_name.setText(Html.fromHtml(sName), TextView.BufferType.SPANNABLE);
        txt_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_call.dismiss();
                clickDialog.onClickYesDialog();
            }
        });
        txt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_call.dismiss();
            }
        });

        dialog_call.show();

    }


}