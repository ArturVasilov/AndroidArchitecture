package ru.arturvasilov.stackexchangeclient;

import android.app.Application;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.iid.FirebaseInstanceId;

import io.fabric.sdk.android.Fabric;
import ru.arturvasilov.sqlite.core.SQLite;
import ru.arturvasilov.stackexchangeclient.api.RepositoryProvider;
import ru.arturvasilov.stackexchangeclient.app.analytics.Analytics;
import ru.arturvasilov.stackexchangeclient.app.analytics.EventTags;
import ru.arturvasilov.stackexchangeclient.data.keyvalue.HawkStorage;
import ru.arturvasilov.stackexchangeclient.utils.PicassoUtils;
import ru.arturvasilov.stackexchangeclient.utils.TextUtils;

/**
 * @author Artur Vasilov
 */
public class AppDelegate extends Application {

    private static AppDelegate sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        Fabric.with(this, new Crashlytics());
        Analytics.init(this);
        Analytics.buildEvent().log(EventTags.APP_STARTED);

        SQLite.initialize(this);
        RepositoryProvider.setKeyValueStorage(new HawkStorage(this));

        PicassoUtils.setup(this);

        String token = FirebaseInstanceId.getInstance().getToken();
        if (!TextUtils.isEmpty(token)) {
            Analytics.setFcmRegistrationKey(token);
        }
    }

    @NonNull
    public static AppDelegate getAppContext() {
        return sInstance;
    }
}
