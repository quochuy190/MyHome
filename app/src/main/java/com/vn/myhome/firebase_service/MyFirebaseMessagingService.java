package com.vn.myhome.firebase_service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.collection.ArrayMap;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vn.myhome.MainActivity;
import com.vn.myhome.R;
import com.vn.myhome.activity.login.LoginActivity;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjLogin;
import com.vn.myhome.untils.SharedPrefs;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;


/**
 * Created by QQ on 8/29/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingServ";
    private static int ONGOING_NOTIFICATION_ID = 76;
    String GROUP_KEY_WORK_EMAIL = "GD_SHOP";
    ObjLogin objLogin;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e(TAG, "onNewToken: " + s);
        if (s != null && s.length() > 0) {
            SharedPrefs.getInstance().put(Constants.KEY_TOKEN, s);
        }
    }

    /* "notification": {
            "content": "olala test huy",
                    "id": "1",
                    "id_user": "2",
                    "state": "",
                    "type": "3",
                    "tab": "1",
                    "sub_tab": "1",
                    "title": "Đơn book dọn dẹp 22/11 chờ thanh toán ấdadadd",
        },*/
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        boolean isLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_IS_LOGIN, Boolean.class);

        if (remoteMessage.getNotification() != null) {
            // hienThiThongBao(remoteMessage.getFrom(), remoteMessage.getMessageType());
            Map<String, String> mMap = new ArrayMap<>();
            mMap = remoteMessage.getData();
            if (isLogin)
                displayCustomNotificationForOrders(mMap);
        } else {
            Map<String, String> mMap = new ArrayMap<>();
            mMap = remoteMessage.getData();
            String sGroup = mMap.get("groups");
            String sGroupDes = mMap.get("groups_des");
            displayCustomNotificationForOrders(mMap);
        }
    }

    private NotificationChannel mChannel;
    private NotificationManager notifManager;

    private void displayCustomNotificationForOrders(Map<String, String> mMap) {
        ONGOING_NOTIFICATION_ID++;
        boolean isLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_IS_LOGIN, Boolean.class);
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJECT_LOGIN, ObjLogin.class);
        String content = mMap.get("content");
        String id = mMap.get("id");
        String id_user = mMap.get("id_user");
        String type = mMap.get("type");
        String state = mMap.get("state");
        String tab = mMap.get("tab");
        String title = mMap.get("title");
        // String title = getResources().getString(R.string.app_name);
        String description = mMap.get("content");
        if (objLogin != null && objLogin.getID() != null) {
            if (objLogin.getID().equals(id_user)) {
                if (notifManager == null) {
                    notifManager = (NotificationManager) getSystemService
                            (Context.NOTIFICATION_SERVICE);
                }
                EventBus.getDefault().postSticky(new MessageEvent(Constants.EventBus.KEY_SEND_NOTIFY, 1, 0));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationCompat.Builder builder;
                    Intent intent;
                    if (isLogin) {
                        intent = new Intent(this, MainActivity.class);
                    } else {
                        intent = new Intent(this, LoginActivity.class);
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pendingIntent;
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    if (mChannel == null) {
                        mChannel = new NotificationChannel
                                ("0", title, importance);
                        mChannel.setDescription(description);
                        mChannel.enableVibration(true);
                        notifManager.createNotificationChannel(mChannel);
                    }
                    builder = new NotificationCompat.Builder(this, "0");
           /* Uri soundUri = Uri.parse("android.resource://"
                    + getApplicationContext().getPackageName() + "/" + R.raw.alert);*/
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra(Constants.KEY_SEND_NOTIFYCATION, tab);
                    pendingIntent = PendingIntent.getActivity(this, 1251, intent,
                            PendingIntent.FLAG_ONE_SHOT);
                    builder.setContentTitle(title)
                            .setSmallIcon(R.mipmap.ic_launcher_round) // required
                            .setContentText(description)  // required
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setAutoCancel(true)
                            //.setSound(soundUri)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(description))
                            .setContentIntent(pendingIntent)
                            .setGroup(GROUP_KEY_WORK_EMAIL)
                            //set this notification as the summary for the group
                            .setGroupSummary(true)
                    ;
                    Notification notification = builder.build();
                    notifManager.notify(ONGOING_NOTIFICATION_ID, notification);
                } else {
                    Intent intent;
                    if (isLogin) {
                        intent = new Intent(this, MainActivity.class);
                    } else {
                        intent = new Intent(this, LoginActivity.class);
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra(tab, Constants.KEY_SEND_NOTIFYCATION);
                    PendingIntent pendingIntent = null;
                    pendingIntent = PendingIntent.getActivity(this, 1251,
                            intent, PendingIntent.FLAG_ONE_SHOT);
                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                            .setContentTitle(title)
                            .setContentText(description)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(description))
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            /* .setSound(Uri.parse("android.resource://"
                                     + getApplicationContext().getPackageName() + "/"
                                     + R.raw.alert))*/
                            .setContentIntent(pendingIntent)
                            .setGroup(GROUP_KEY_WORK_EMAIL)
                            //set this notification as the summary for the group
                            .setGroupSummary(true)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .setBigContentTitle(title).bigText(description));

                    NotificationManager notificationManager = (NotificationManager)
                            getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(ONGOING_NOTIFICATION_ID, notificationBuilder.build());
                }
            }
        }

    }
}
