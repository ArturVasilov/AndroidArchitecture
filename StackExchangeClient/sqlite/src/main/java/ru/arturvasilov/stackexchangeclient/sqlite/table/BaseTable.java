package ru.arturvasilov.stackexchangeclient.sqlite.table;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import ru.arturvasilov.stackexchangeclient.sqlite.core.SQLiteContentProvider;

/**
 * @author Artur Vasilov
 */
public abstract class BaseTable<T> implements Table<T> {

    @NonNull
    @Override
    public final Uri getUri() {
        return SQLiteContentProvider.getBaseUri().buildUpon().appendPath(getTableName()).build();
    }

    @NonNull
    @Override
    public String getTableName() {
        return getClass().getSimpleName();
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase database, int oldVersion, int newVersion) {
        if (newVersion <= getLastUpgradeVersion() && newVersion > oldVersion) {
            database.execSQL("DROP TABLE IF EXISTS " + getTableName());
            onCreate(database);
        }
    }
}
