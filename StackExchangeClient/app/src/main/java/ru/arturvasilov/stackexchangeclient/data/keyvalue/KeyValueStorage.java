package ru.arturvasilov.stackexchangeclient.data.keyvalue;

import android.support.annotation.NonNull;

import rx.Observable;

/**
 * @author Artur Vasilov
 */
public interface KeyValueStorage {

    String KEY_ACCESS_TOKEN = "key_access_token";
    String KEY_WALKTHROUGH_PASSED = "key_walkthrough_passed";
    String KEY_USER_ID = "key_user_id";

    @NonNull
    Observable<Boolean> saveAccessToken(@NonNull String token);

    @NonNull
    Observable<String> getAccessToken();

    @NonNull
    String obtainAccessToken();

    void saveWalkthroughPassed();

    boolean isWalkthroughPassed();

    void saveUserId(int userId);

    @NonNull
    Observable<Integer> getCurrentUserId();

    void logout();

}
