package ru.arturvasilov.stackexchangeclient.service;

import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.support.annotation.LayoutRes;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ru.arturvasilov.sqlite.core.SQLite;
import ru.arturvasilov.sqlite.core.Where;
import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.data.database.NotificationTable;
import ru.arturvasilov.stackexchangeclient.data.database.UserTable;
import ru.arturvasilov.stackexchangeclient.model.content.Notification;
import ru.arturvasilov.stackexchangeclient.model.content.User;
import ru.arturvasilov.stackexchangeclient.utils.HtmlCompat;

/**
 * @author Artur Vasilov
 */
public class NotificationsRemoteViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new NotificationsViewFactory();
    }

    private class NotificationsViewFactory implements RemoteViewsService.RemoteViewsFactory {

        private final List<Notification> mNotifications;

        private final Intent mItemClickListenerIntent;

        public NotificationsViewFactory() {
            mNotifications = new ArrayList<>();
            User currentUser = SQLite.get().queryObject(UserTable.TABLE, Where.create());
            mItemClickListenerIntent = new Intent(Intent.ACTION_VIEW);
            if (currentUser != null) {
                mItemClickListenerIntent.setData(Uri.parse(currentUser.getLink()));
            }
        }

        @Override
        public void onCreate() {
            // Do nothing
        }

        @Override
        public void onDataSetChanged() {
            final long identityToken = Binder.clearCallingIdentity();

            mNotifications.clear();
            List<Notification> notifications = SQLite.get().query(NotificationTable.TABLE, Where.create());
            mNotifications.addAll(notifications);

            Binder.restoreCallingIdentity(identityToken);
        }

        @Override
        public void onDestroy() {
            mNotifications.clear();
        }

        @Override
        public int getCount() {
            return mNotifications.size();
        }

        @Override
        public RemoteViews getViewAt(int index) {
            if (index < 0 || index >= mNotifications.size()) {
                return null;
            }

            Notification notification = mNotifications.get(index);
            @LayoutRes int layoutResId = notification.isUnread()
                    ? R.layout.widget_notification_item_unread
                    : R.layout.widget_notification_item;
            RemoteViews remoteViews = new RemoteViews(getPackageName(), layoutResId);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(notification.getCreationDate() * 1000);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM", Locale.getDefault());
            String date = dateFormat.format(calendar.getTime());
            remoteViews.setTextViewText(R.id.notificationDate, date);

            CharSequence body = HtmlCompat.fromHtml(notification.getBody());
            remoteViews.setTextViewText(R.id.notificationBody, body);

            remoteViews.setOnClickFillInIntent(R.id.notificationItemLayout, mItemClickListenerIntent);

            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return new RemoteViews(getPackageName(), R.layout.widget_notification_item);
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
