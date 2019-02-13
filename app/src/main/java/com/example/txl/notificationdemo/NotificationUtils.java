package com.example.txl.notificationdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

/**
 * @author TXL
 * description :
 */
public class NotificationUtils {
    private static final String TAG = NotificationUtils.class.getSimpleName();
    private final NotificationManager mNotificationManager;
    public final String CHANNEL_ID;
    private Context mContext;

    public NotificationUtils(Context context) {
        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        CHANNEL_ID = context.getPackageName();
        mContext = context;
    }

    public NotificationManager getmNotificationManager() {
        return mNotificationManager;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel() {
        if (mNotificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            // The user-visible name of the channel.
            CharSequence name = "MediaSession";
            // The user-visible description of the channel.
            String description = "MediaSession and MediaPlayer";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            // Configure the notification channel.
            mChannel.setDescription(description);
            mChannel.enableLights(false);
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(false);
            mChannel.setVibrationPattern(new long[]{0});
            mChannel.setSound(null, null);
            mNotificationManager.createNotificationChannel(mChannel);
            Log.d(TAG, "createChannel: New channel created");
        } else {
            Log.d(TAG, "createChannel: Existing channel reused");
        }
    }

    public Notification buildNotification(NotificationType type){
        if (isAndroidOOrHigher()) {
            createChannel();
        }
        NotificationCompat.Builder mBuilder = getBuilder(type);
        return mBuilder.build();
    }

    private NotificationCompat.Builder getBuilder(NotificationType type) {
        NotificationCompat.Builder builder;
        switch (type){
            case BasicNotification:
                builder = getBasicBuilder();
                break;
            case LongNotification:
                builder = getLongBuilder();
                break;
            case StartActivity:
                builder = getStartActivityBuilder();
                break;
            default:
                builder = getBasicBuilder();
                break;
        }
        return builder;
    }

    private NotificationCompat.Builder getBasicBuilder() {
        return new NotificationCompat.Builder(mContext, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)//必须要有这个不然会出现异常
                    .setContentTitle("常规NotificationTitle")
                    .setContentText("常规NotificationContent")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    /**
     * 默认情况下，通知的文本内容会被截断以适合一行。如果希望通知更长，可以通过添加带有setStyle（）
     * 的样式模板来启用可扩展通知。 例如，以下代码创建一个更大的文本区域
     * */
    private NotificationCompat.Builder getLongBuilder() {
        return new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)//必须要有这个不然会出现异常
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line sda das dsa dsa das das ds")
                        .setSummaryText("SummaryText")
                        .setBigContentTitle("big title")
                )
                .setContentTitle("Long  NotificationTitle")
                .setContentText("Long  NotificationContent")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    private NotificationCompat.Builder getStartActivityBuilder(){
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        return mBuilder;
    }

    private boolean isAndroidOOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    public enum  NotificationType{
        BasicNotification,
        LongNotification,
        StartActivity
    }
}
