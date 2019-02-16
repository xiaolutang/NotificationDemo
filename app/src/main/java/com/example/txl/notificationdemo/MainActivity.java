package com.example.txl.notificationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    NotificationUtils notificationUtils;
    Button basicNotificationButton;
    Button longNotificationButton;
    Button startActivityNotificationButton;
    Button headUpNotificationButton;
    Button badgeNotificationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        notificationUtils = new NotificationUtils(this);
        initView();
    }

    private void initView() {
        basicNotificationButton = findViewById(R.id.bt_basic_notification);
        basicNotificationButton.setOnClickListener(this);

        longNotificationButton = findViewById(R.id.bt_long_notification);
        longNotificationButton.setOnClickListener(this);

        startActivityNotificationButton = findViewById(R.id.bt_start_activity_notification);
        startActivityNotificationButton.setOnClickListener(this);

        headUpNotificationButton = findViewById(R.id.bt_head_up_notification);
        headUpNotificationButton.setOnClickListener(this);

        badgeNotificationButton = findViewById(R.id.bt_badge_notification);
        badgeNotificationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final Notification[] notification = new Notification[1];
        switch (v.getId()){
            case R.id.bt_basic_notification:
                notification[0] = notificationUtils.buildNotification(NotificationUtils.NotificationType.BasicNotification);
                notificationUtils.getNotificationManager().notify(12345, notification[0]);
                break;
            case R.id.bt_long_notification:
                notification[0] = notificationUtils.buildNotification(NotificationUtils.NotificationType.LongNotification);
                notificationUtils.getNotificationManager().notify(12346, notification[0]);
                break;
            case R.id.bt_start_activity_notification:
                startActivityNotificationButton.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notification[0] = notificationUtils.buildNotification(NotificationUtils.NotificationType.StartActivity);
                        notificationUtils.getNotificationManager().notify(12347, notification[0]);
                    }
                },5000);
                break;
            case R.id.bt_head_up_notification:
                headUpNotificationButton.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Notification notification1 = notificationUtils.buildNotification(NotificationUtils.NotificationType.HeadUp);
                        notificationUtils.getNotificationManager().notify(12348,notification1);
                    }
                },5000);
                break;
            case R.id.bt_badge_notification:
                //华为角标
//                Bundle extra =new Bundle();
//                extra.putString("package", getPackageName());
//                extra.putString("class", "com.example.txl.notificationdemo.MainActivity");
//                extra.putInt("badgenumber", 5);
//                getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, extra);

                badgeNotificationButton.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Notification notification1 = notificationUtils.buildNotification(NotificationUtils.NotificationType.Badge);
                        notificationUtils.getNotificationManager().notify(12349,notification1);
                    }
                },5000);
            break;
        }
    }
}
