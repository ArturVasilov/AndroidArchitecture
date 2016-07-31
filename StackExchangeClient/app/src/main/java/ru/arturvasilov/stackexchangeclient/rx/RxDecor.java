package ru.arturvasilov.stackexchangeclient.rx;

import android.support.annotation.NonNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import ru.arturvasilov.stackexchangeclient.view.ErrorView;
import ru.arturvasilov.stackexchangeclient.view.LoadingView;
import rx.Observable;
import rx.functions.Action1;

/**
 * @author Artur Vasilov
 */
public final class RxDecor {

    private static final List<Class<?>> NETWORK_EXCEPTIONS = Arrays.asList(
            UnknownHostException.class,
            SocketTimeoutException.class
    );

    private RxDecor() {
    }

    @NonNull
    public static <T> Observable.Transformer<T, T> loading(@NonNull LoadingView view) {
        return observable -> observable
                .doOnSubscribe(view::showLoadingIndicator)
                .doOnTerminate(view::hideLoadingIndicator);
    }

    @NonNull
    public static Action1<Throwable> error(@NonNull ErrorView view) {
        return e -> {
            if (e instanceof HttpException) {
                String message = ((HttpException) e).message();
                view.showErrorMessage(message);
            } else if (NETWORK_EXCEPTIONS.contains(e.getClass())) {
                view.showNetworkError();
            } else {
                view.showUnexpectedError();
            }
        };
    }

}
