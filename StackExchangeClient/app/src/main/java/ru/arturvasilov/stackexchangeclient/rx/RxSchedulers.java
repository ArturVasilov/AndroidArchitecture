package ru.arturvasilov.stackexchangeclient.rx;

import android.support.annotation.NonNull;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Artur Vasilov
 */
public final class RxSchedulers {

    private RxSchedulers() {
    }

    @NonNull
    public static Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    public static Scheduler main() {
        return AndroidSchedulers.mainThread();
    }

    @NonNull
    public static <T> Observable.Transformer<T, T> async() {
        return observable -> observable
                .subscribeOn(io())
                .observeOn(main());
    }

}
