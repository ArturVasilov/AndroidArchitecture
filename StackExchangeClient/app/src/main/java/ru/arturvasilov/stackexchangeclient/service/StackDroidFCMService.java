package ru.arturvasilov.stackexchangeclient.service;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import ru.arturvasilov.stackexchangeclient.AppDelegate;
import ru.arturvasilov.stackexchangeclient.BuildConfig;
import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.activity.AuthActivity;
import ru.arturvasilov.stackexchangeclient.app.analytics.Analytics;
import ru.arturvasilov.stackexchangeclient.app.analytics.EventKeys;
import ru.arturvasilov.stackexchangeclient.app.analytics.EventTags;

/**
 * @author Artur Vasilov
 */
public class StackDroidFCMService extends FirebaseMessagingService {

    private static final String MINIMUM_VERSION_KEY = "minimum_version_code";
    private static final String MAXIMUM_VERSION_KEY = "maximum_version_code";
    private static final String NOTIFICATION_ID_KEY = "notification_id";
    private static final String TITLE = "title";
    private static final String BODY = "body";

    private NotificationManagerCompat mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = NotificationManagerCompat.from(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null) {
            Map<String, String> pushParams = remoteMessage.getData();
            int minVersionCode = Integer.parseInt(pushParams.get(MINIMUM_VERSION_KEY));
            int maxVersionCode = Integer.parseInt(pushParams.get(MAXIMUM_VERSION_KEY));
            String notificationId = pushParams.get(NOTIFICATION_ID_KEY);
            boolean show = BuildConfig.VERSION_CODE >= minVersionCode && BuildConfig.VERSION_CODE <= maxVersionCode;
            Analytics.buildEvent()
                    .putString(EventKeys.NOTIFICATION_ID, notificationId)
                    .putString(EventKeys.NOTIFICATION_SHOWN, Boolean.toString(show))
                    .log(EventTags.PUSH_NOTIFICATION);

            if (show) {
                showNotification(pushParams);
            }
        }
    }

    private void showNotification(@NonNull Map<String, String> remoteMessage) {
        Intent intent = new Intent(AppDelegate.getAppContext(), AuthActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[]{intent}, PendingIntent.FLAG_ONE_SHOT);
        Uri notificationRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String title = remoteMessage.get(TITLE);
        String body = remoteMessage.get(BODY);

        NotificationCompat.BigTextStyle notificationStyle = new NotificationCompat.BigTextStyle()
                .setBigContentTitle(title)
                .bigText(body);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(notificationRingtone)
                .setStyle(notificationStyle)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_icon);
        mNotificationManager.notify(0, builder.build());
    }

}
