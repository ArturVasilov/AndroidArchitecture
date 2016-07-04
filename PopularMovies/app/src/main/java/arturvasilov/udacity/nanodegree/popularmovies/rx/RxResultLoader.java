package arturvasilov.udacity.nanodegree.popularmovies.rx;

import android.content.Context;
import android.content.Loader;
import android.support.annotation.NonNull;

import rx.Observable;

/**
 * @author Artur Vasilov
 */
class RxResultLoader<T> extends Loader<RxResult<T>> {

    private final Observable<T> mObservable;

    private boolean mOnNextCalled;

    private T mResult;

    public RxResultLoader(@NonNull Context context, @NonNull Observable<T> observable) {
        super(context);
        mObservable = observable;
    }

    @Override
    public void deliverResult(RxResult<T> data) {
        if (isReset()) {
            return;
        }
        if (data != null) {
            mResult = data.getResult();
        }
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged() || mResult == null) {
            forceLoad();
        }
    }

    @Override
    protected void onForceLoad() {
        mObservable.subscribe(t -> {
            mOnNextCalled = true;
            deliverResult(RxResult.onNext(t));
        }, throwable -> deliverResult(RxResult.<T>onError(throwable)), () -> {
            if (!mOnNextCalled) {
                deliverResult(null);
            }
        });
    }

}
