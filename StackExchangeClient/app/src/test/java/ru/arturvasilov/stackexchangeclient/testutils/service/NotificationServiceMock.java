package ru.arturvasilov.stackexchangeclient.testutils.service;

import android.support.annotation.NonNull;

import ru.arturvasilov.stackexchangeclient.api.service.NotificationService;
import ru.arturvasilov.stackexchangeclient.model.response.NotificationResponse;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class NotificationServiceMock implements NotificationService {

    @NonNull
    @Override
    public Observable<NotificationResponse> notifications() {
        return Observable.empty();
    }
}
