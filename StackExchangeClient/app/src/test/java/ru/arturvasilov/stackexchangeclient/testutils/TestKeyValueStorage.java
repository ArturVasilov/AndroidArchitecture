package ru.arturvasilov.stackexchangeclient.testutils;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import ru.arturvasilov.stackexchangeclient.data.keyvalue.KeyValueStorage;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class TestKeyValueStorage implements KeyValueStorage {

    private final Map<String, Object> mMap = new HashMap<>();

    @NonNull
    @Override
    public Observable<Boolean> saveAccessToken(@NonNull String token) {
        mMap.put(KEY_ACCESS_TOKEN, token);
        return Observable.just(true);
    }

    @NonNull
    @Override
    public Observable<String> getAccessToken() {
        return Observable.just((String) mMap.get(KEY_ACCESS_TOKEN));
    }

    @NonNull
    @Override
    public String obtainAccessToken() {
        return (String) mMap.get(KEY_ACCESS_TOKEN);
    }

    @Override
    public void saveWalkthroughPassed() {
        mMap.put(KEY_WALKTHROUGH_PASSED, true);
    }

    @Override
    public boolean isWalkthroughPassed() {
        return mMap.containsKey(KEY_WALKTHROUGH_PASSED);
    }

    @Override
    public void saveUserId(int userId) {
        mMap.put(KEY_USER_ID, userId);
    }

    @NonNull
    @Override
    public Observable<Integer> getCurrentUserId() {
        return Observable.just((Integer) mMap.get(KEY_USER_ID));
    }

    @Override
    public void logout() {
        mMap.clear();
    }
}
