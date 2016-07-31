package ru.arturvasilov.stackexchangeclient.sqlite;

import android.content.Context;
import android.support.annotation.NonNull;

import ru.arturvasilov.stackexchangeclient.sqlite.action.DeleteAction;
import ru.arturvasilov.stackexchangeclient.sqlite.action.DeleteActionImpl;
import ru.arturvasilov.stackexchangeclient.sqlite.action.InsertAction;
import ru.arturvasilov.stackexchangeclient.sqlite.action.InsertActionImpl;
import ru.arturvasilov.stackexchangeclient.sqlite.action.UpdateAction;
import ru.arturvasilov.stackexchangeclient.sqlite.action.UpdateActionImpl;
import ru.arturvasilov.stackexchangeclient.sqlite.query.Query;
import ru.arturvasilov.stackexchangeclient.sqlite.query.QueryImpl;
import ru.arturvasilov.stackexchangeclient.sqlite.table.Table;

/**
 * @author Artur Vasilov
 */
public class SQLite {

    private final Context mContext;

    private static SQLite sSQLite;

    private SQLite(Context context) {
        mContext = context;
    }

    @NonNull
    public static SQLite initialize(Context context) {
        SQLite sqLite = sSQLite;
        if (sqLite == null) {
            synchronized (SQLite.class) {
                sqLite = sSQLite;
                if (sqLite == null) {
                    sqLite = sSQLite = new SQLite(context);
                }
            }
        }
        return sqLite;
    }

    @NonNull
    public static SQLite get() {
        if (sSQLite == null) {
            throw new IllegalStateException("You should call initialize(Context) first, to initialize the database");
        }
        return sSQLite;
    }

    @NonNull
    public <T> Query<T> query(@NonNull Table<T> table) {
        return new QueryImpl<>(mContext, table);
    }

    @NonNull
    public <T> InsertAction<T> insert(@NonNull Table<T> table) {
        return new InsertActionImpl<>(table, mContext);
    }

    @NonNull
    public <T> DeleteAction<T> delete(@NonNull Table<T> table) {
        return new DeleteActionImpl<>(mContext, table);
    }

    @NonNull
    public <T> UpdateAction<T> update(@NonNull Table<T> table) {
        return new UpdateActionImpl<>(table, mContext);
    }
}
