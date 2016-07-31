package ru.arturvasilov.stackexchangeclient.sqlite.query;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.concurrent.Callable;

import ru.arturvasilov.stackexchangeclient.sqlite.core.SQLiteUtils;
import ru.arturvasilov.stackexchangeclient.sqlite.table.Table;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author Artur Vasilov
 */
public class QueryObjectImpl<T> implements QueryObject<T> {

    private final Context mContext;
    private final Table<T> mTable;

    private String mQuery;
    private String[] mQueryArgs;

    public QueryObjectImpl(Context context, @NonNull Table<T> table) {
        mContext = context;
        mTable = table;
    }

    @NonNull
    @Override
    public QueryObject<T> where(@Nullable String query) {
        mQuery = query;
        return this;
    }

    @NonNull
    @Override
    public QueryObject<T> whereArgs(@Nullable String[] args) {
        mQueryArgs = args;
        return this;
    }

    @Nullable
    @Override
    public T execute() {
        Cursor cursor = mContext.getContentResolver()
                .query(mTable.getUri(), null, mQuery, mQueryArgs, null);
        try {
            if (SQLiteUtils.isEmptyCursor(cursor)) {
                return null;
            }
            return mTable.fromCursor(cursor);
        } finally {
            SQLiteUtils.safeCloseCursor(cursor);
        }
    }

    @NonNull
    @Override
    public Observable<T> asObservable() {
        return Observable.fromCallable(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return execute();
            }
        })
                .filter(new Func1<T, Boolean>() {
                    @Override
                    public Boolean call(T t) {
                        return t != null;
                    }
                });
    }
}
