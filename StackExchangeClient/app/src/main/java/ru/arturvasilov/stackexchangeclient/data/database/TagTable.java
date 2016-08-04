package ru.arturvasilov.stackexchangeclient.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import ru.arturvasilov.sqlite.core.BaseTable;
import ru.arturvasilov.sqlite.core.Table;
import ru.arturvasilov.sqlite.utils.TableBuilder;

/**
 * @author Artur Vasilov
 */
public class TagTable extends BaseTable<String> {

    public static final Table<String> TABLE = new TagTable();

    public static final String TAG = "tag";

    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        TableBuilder.create(this)
                .stringColumn(TAG)
                .primaryKey(TAG)
                .execute(database);
    }

    @NonNull
    @Override
    public ContentValues toValues(@NonNull String tag) {
        ContentValues values = new ContentValues();
        values.put(TAG, tag);
        return values;
    }

    @NonNull
    @Override
    public String fromCursor(@NonNull Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(TAG));
    }
}
