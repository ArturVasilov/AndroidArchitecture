package ru.arturvasilov.stackexchangeclient.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import ru.arturvasilov.sqlite.core.BaseTable;
import ru.arturvasilov.sqlite.core.Table;
import ru.arturvasilov.sqlite.utils.TableBuilder;
import ru.arturvasilov.stackexchangeclient.model.content.Notification;

/**
 * @author Artur Vasilov
 */
public class NotificationTable extends BaseTable<Notification> {

    public static final Table<Notification> TABLE = new NotificationTable();

    public static final String IS_UNREAD = "is_unread";
    public static final String CREATION_DATE = "creation_date";
    public static final String BODY = "body";

    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        TableBuilder.create(this)
                .intColumn(IS_UNREAD)
                .stringColumn(CREATION_DATE)
                .stringColumn(BODY)
                .execute(database);
    }

    @NonNull
    @Override
    public ContentValues toValues(@NonNull Notification notification) {
        ContentValues values = new ContentValues();
        values.put(IS_UNREAD, notification.isUnread() ? 1 : 0);
        values.put(CREATION_DATE, String.valueOf(notification.getCreationDate()));
        values.put(BODY, notification.getBody());
        return values;
    }

    @NonNull
    @Override
    public Notification fromCursor(@NonNull Cursor cursor) {
        Notification notification = new Notification();
        notification.setUnread(cursor.getInt(cursor.getColumnIndex(IS_UNREAD)) > 0);
        notification.setCreationDate(Long.parseLong(cursor.getString(cursor.getColumnIndex(CREATION_DATE))));
        notification.setBody(cursor.getString(cursor.getColumnIndex(BODY)));
        return notification;
    }
}
