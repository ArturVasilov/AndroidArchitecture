package ru.arturvasilov.stackexchangeclient.testutils.service;

import android.support.annotation.NonNull;

import retrofit2.http.Path;
import ru.arturvasilov.stackexchangeclient.api.service.ApplicationService;
import ru.arturvasilov.stackexchangeclient.model.response.ApiError;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class ApplicationServiceMock implements ApplicationService {

    @NonNull
    @Override
    public Observable<ApiError> logout(@NonNull @Path("accessTokens") String token) {
        return Observable.empty();
    }
}
