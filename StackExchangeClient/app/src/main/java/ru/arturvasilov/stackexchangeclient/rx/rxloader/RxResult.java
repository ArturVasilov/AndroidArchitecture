package ru.arturvasilov.stackexchangeclient.rx.rxloader;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author Artur Vasilov
 */
class RxResult<T> {

    private final T mResult;

    private final Throwable mError;

    private final boolean mIsComplete;

    private RxResult(@Nullable T result, @Nullable Throwable error, boolean isComplete) {
        mResult = result;
        mError = error;
        mIsComplete = isComplete;
    }

    @NonNull
    public static <T> RxResult<T> onNext(@Nullable T result) {
        return new RxResult<>(result, null, false);
    }

    @NonNull
    public static <T> RxResult<T> onError(@Nullable Throwable error) {
        return new RxResult<>(null, error, false);
    }

    @NonNull
    public static <T> RxResult<T> onComplete() {
        return new RxResult<>(null, null, true);
    }

    @Nullable
    public T getResult() {
        return mResult;
    }

    @Nullable
    public Throwable getError() {
        return mError;
    }

    public boolean isOnComplete() {
        return mIsComplete;
    }

}
