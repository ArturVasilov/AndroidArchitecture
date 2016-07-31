package ru.arturvasilov.stackexchangeclient.sqlite.query;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.sqlite.core.SQLiteUtils;
import ru.arturvasilov.stackexchangeclient.sqlite.rx.CursorListMapper;
import ru.arturvasilov.stackexchangeclient.sqlite.rx.CursorObservable;
import ru.arturvasilov.stackexchangeclient.sqlite.table.Table;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author Artur Vasilov
 */
public class QueryListImpl<T> implements QueryList<T> {

    private final Context mContext;
    private final Table<T> mTable;

    private String mQuery;
    private String[] mQueryArgs;

    public QueryListImpl(Context context, @NonNull Table<T> table) {
        mContext = context;
        mTable = table;
    }

    @NonNull
    @Override
    public QueryList<T> where(@Nullable String query) {
        mQuery = query;
        return this;
    }

    @NonNull
    @Override
    public QueryList<T> whereArgs(@Nullable String[] args) {
        mQueryArgs = args;
        return this;
    }

    @NonNull
    @Override
    public List<T> execute() {
        List<T> list = new ArrayList<>();

        Cursor cursor = mContext.getContentResolver().query(mTable.getUri(), null, mQuery, mQueryArgs, null);
        try {
            if (SQLiteUtils.isEmptyCursor(cursor)) {
                return list;
            }
            do {
                T t = mTable.fromCursor(cursor);
                list.add(t);
            } while (cursor.moveToNext());
            return list;
        } finally {
            SQLiteUtils.safeCloseCursor(cursor);
        }
    }

    @NonNull
    @Override
    public Observable<List<T>> asObservable() {
        //noinspection unchecked
        return CursorObservable.create(mContext, mTable.getUri(), null, mQuery, mQueryArgs, null)
                .map(new CursorListMapper(new Func1<Cursor, T>() {
                    @Override
                    public T call(Cursor cursor) {
                        return mTable.fromCursor(cursor);
                    }
                }));
    }
}
