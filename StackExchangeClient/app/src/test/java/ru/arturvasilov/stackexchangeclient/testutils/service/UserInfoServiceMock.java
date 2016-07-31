package ru.arturvasilov.stackexchangeclient.testutils.service;

import android.support.annotation.NonNull;

import retrofit2.http.Path;
import ru.arturvasilov.stackexchangeclient.api.service.UserInfoService;
import ru.arturvasilov.stackexchangeclient.model.response.BadgeResponse;
import ru.arturvasilov.stackexchangeclient.model.response.UserResponse;
import ru.arturvasilov.stackexchangeclient.model.response.UserTagResponse;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class UserInfoServiceMock implements UserInfoService {

    @NonNull
    @Override
    public Observable<UserResponse> getCurrentUser() {
        return Observable.empty();
    }

    @NonNull
    @Override
    public Observable<BadgeResponse> badges(@Path("ids") int userId) {
        return Observable.empty();
    }

    @NonNull
    @Override
    public Observable<UserTagResponse> topTags(@Path("id") int userId) {
        return Observable.empty();
    }
}
