package ru.arturvasilov.stackexchangeclient.api.service;

import android.support.annotation.NonNull;

import retrofit2.http.GET;
import ru.arturvasilov.stackexchangeclient.model.response.NotificationResponse;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public interface NotificationService {

    @NonNull
    @GET("/me/notifications?pagesize=50&filter=!9X8frFZkZ")
    Observable<NotificationResponse> notifications();

}
