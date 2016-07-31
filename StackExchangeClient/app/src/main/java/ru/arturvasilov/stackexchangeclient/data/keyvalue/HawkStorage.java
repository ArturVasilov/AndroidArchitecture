package ru.arturvasilov.stackexchangeclient.data.keyvalue;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

import ru.arturvasilov.stackexchangeclient.BuildConfig;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class HawkStorage implements KeyValueStorage {

    public HawkStorage(@NonNull Context context) {
        Hawk.init(context)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSharedPrefStorage(context))
                .setLogLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE)
                .build();
    }

    @NonNull
    @Override
    public Observable<Boolean> saveAccessToken(@NonNull String token) {
        return Hawk.putObservable(KEY_ACCESS_TOKEN, token);
    }

    @NonNull
    @Override
    public Observable<String> getAccessToken() {
        return Hawk.getObservable(KEY_ACCESS_TOKEN);
    }

    @NonNull
    @Override
    public String obtainAccessToken() {
        return Hawk.get(KEY_ACCESS_TOKEN);
    }

    @Override
    public void saveWalkthroughPassed() {
        Hawk.put(KEY_WALKTHROUGH_PASSED, true);
    }

    @Override
    public boolean isWalkthroughPassed() {
        return Hawk.get(KEY_WALKTHROUGH_PASSED, false);
    }

    @Override
    public void saveUserId(int userId) {
        Hawk.put(KEY_USER_ID, userId);
    }

    @NonNull
    @Override
    public Observable<Integer> getCurrentUserId() {
        return Hawk.getObservable(KEY_USER_ID, -1);
    }

    @Override
    public void logout() {
        Hawk.clear();
    }
}
