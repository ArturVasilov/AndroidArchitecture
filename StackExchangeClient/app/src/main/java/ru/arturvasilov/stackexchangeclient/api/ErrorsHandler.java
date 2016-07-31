package ru.arturvasilov.stackexchangeclient.api;

import android.support.annotation.NonNull;

import ru.arturvasilov.stackexchangeclient.model.response.ApiError;
import ru.arturvasilov.stackexchangeclient.model.response.ServerError;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
final class ErrorsHandler {

    private ErrorsHandler() {
    }

    @NonNull
    public static <T extends ApiError> Observable.Transformer<T, T> handleErrors() {
        return observable -> observable
                .flatMap(t -> {
                    if (t.isError()) {
                        return Observable.error(new ServerError(t));
                    }
                    return Observable.just(t);
                });
    }

}
