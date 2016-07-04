package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.rx.utils;

import android.database.Cursor;
import android.support.annotation.NonNull;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.sqlite.DatabaseUtils;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class CursorObservable extends Observable<Cursor> {

    private CursorObservable(@NonNull Cursor cursor) {
        super(subscriber -> {
            subscriber.onNext(cursor);
            subscriber.onCompleted();
            DatabaseUtils.safeCloseCursor(cursor);
        });
    }

    @NonNull
    public static Observable<Cursor> create(@NonNull Cursor cursor) {
        return Observable.defer(() -> new CursorObservable(cursor));
    }
}
