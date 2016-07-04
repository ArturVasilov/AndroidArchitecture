package ru.itis.lectures.githubmvp.rx;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import rx.Observable;
import rx.Observer;

/**
 * @author Artur Vasilov
 */
class RxLcImpl<T> implements LoaderManager.LoaderCallbacks<RxResult<T>> {

    private final Context mContext;

    private final Observable<T> mObservable;

    private final Observer<T> mObserver;

    public RxLcImpl(@NonNull Context context, @NonNull Observable<T> observable, @NonNull Observer<T> observer) {
        mContext = context;
        mObservable = observable;
        mObserver = observer;
    }

    @Override
    public Loader<RxResult<T>> onCreateLoader(int id, Bundle args) {
        return new RxResultLoader<>(mContext, mObservable);
    }

    @Override
    public void onLoadFinished(Loader<RxResult<T>> loader, RxResult<T> data) {
        if (data != null) {
            if (data.getError() != null) {
                mObserver.onError(data.getError());
            } else {
                mObserver.onNext(data.getResult());
            }
        }
        mObserver.onCompleted();

    }

    @Override
    public void onLoaderReset(Loader<RxResult<T>> loader) {
        // Do nothing
    }

    /**
     * Only for test purposes
     *
     * DON'T USE IT!
     */
    @VisibleForTesting
    void subscribeForTesting() {
        mObservable.subscribe(mObserver);
    }
}
