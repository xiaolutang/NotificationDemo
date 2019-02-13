package com.example.txl.notificationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    NotificationUtils notificationUtils;
    Button basicNotificationButton;
    Button longNotificationButton;
    Button startActivityNotificationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public void onClick(View v) {
        Notification notification;
        switch (v.getId()){
            case R.id.bt_basic_notification:
                notification = notificationUtils.buildNotification(NotificationUtils.NotificationType.BasicNotification);
                notificationUtils.getmNotificationManager().notify(12345,notification);
                break;
            case R.id.bt_long_notification:
                notification = notificationUtils.buildNotification(NotificationUtils.NotificationType.LongNotification);
                notificationUtils.getmNotificationManager().notify(12346,notification);
                break;
            case R.id.bt_start_activity_notification:
                notification = notificationUtils.buildNotification(NotificationUtils.NotificationType.StartActivity);
                notificationUtils.getmNotificationManager().notify(12346,notification);
                break;
        }
    }
}
