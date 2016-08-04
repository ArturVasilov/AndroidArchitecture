package ru.arturvasilov.stackexchangeclient.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import java.util.List;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.activity.AuthActivity;
import ru.arturvasilov.stackexchangeclient.api.RepositoryProvider;
import ru.arturvasilov.stackexchangeclient.model.content.Notification;
import ru.arturvasilov.stackexchangeclient.rx.StubAction;
import rx.functions.Action1;

/**
 * @author Artur Vasilov
 */
public class UpdateWidgetService extends IntentService {

    @SuppressWarnings("unused")
    public UpdateWidgetService() {
        super(UpdateWidgetService.class.getName());
    }

    @SuppressWarnings("unused")
    public UpdateWidgetService(String name) {
        super(name);
    }

    public static void start(@NonNull Context context, @NonNull int[] appWidgetIds) {
        Intent intent = new Intent(context, UpdateWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        RepositoryProvider.provideRemoteRepository()
                .notifications()
                .subscribe(handleNotifications(appWidgetIds), new StubAction<>());
    }

    @NonNull
    private Action1<List<Notification>> handleNotifications(int[] appWidgetIds) {
        return notifications -> {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            for (int widgetId : appWidgetIds) {
                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_layout);

                Intent appIntent = new Intent(this, AuthActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, appIntent, 0);
                remoteViews.setOnClickPendingIntent(R.id.widget_title, pendingIntent);

                PendingIntent itemPendingIntent = PendingIntent.getActivity(this, 0, new Intent(Intent.ACTION_VIEW), 0);
                remoteViews.setPendingIntentTemplate(R.id.notificationsListWidget, itemPendingIntent);
                Intent remoteViewIntent = new Intent(this, NotificationsRemoteViewService.class);
                remoteViews.setRemoteAdapter(R.id.notificationsListWidget, remoteViewIntent);
                remoteViews.setEmptyView(R.id.notificationsListWidget, R.id.empty);

                appWidgetManager.updateAppWidget(widgetId, remoteViews);
            }
        };
    }
}
