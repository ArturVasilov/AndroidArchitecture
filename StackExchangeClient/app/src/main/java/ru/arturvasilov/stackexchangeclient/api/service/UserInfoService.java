package ru.arturvasilov.stackexchangeclient.api.service;

import android.support.annotation.NonNull;

import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.arturvasilov.stackexchangeclient.model.response.BadgeResponse;
import ru.arturvasilov.stackexchangeclient.model.response.UserResponse;
import ru.arturvasilov.stackexchangeclient.model.response.UserTagResponse;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public interface UserInfoService {

    @NonNull
    @GET("/me")
    Observable<UserResponse> getCurrentUser();

    @NonNull
    @GET("/users/{ids}/badges?pagesize=10&order=desc&sort=rank")
    Observable<BadgeResponse> badges(@Path("ids") int userId);

    @NonNull
    @GET("/users/{id}/top-tags?pagesize=10")
    Observable<UserTagResponse> topTags(@Path("id") int userId);

}
