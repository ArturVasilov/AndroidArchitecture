package ru.arturvasilov.stackexchangeclient.api.service;

import android.support.annotation.NonNull;

import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.arturvasilov.stackexchangeclient.model.response.ApiError;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public interface ApplicationService {

    @NonNull
    @GET("/apps/{accessTokens}/de-authenticate")
    Observable<ApiError> logout(@NonNull @Path("accessTokens") String token);

}
