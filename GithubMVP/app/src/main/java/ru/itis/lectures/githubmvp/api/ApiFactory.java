package ru.itis.lectures.githubmvp.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.itis.lectures.githubmvp.AppDelegate;
import ru.itis.lectures.githubmvp.content.Settings;

/**
 * @author Artur Vasilov
 */
public final class ApiFactory {

    private static ApiProvider sProvider;

    private ApiFactory() {
    }

    public static void setProvider(@Nullable ApiProvider provider) {
        sProvider = provider;
    }

    public static void resetProvider() {
        sProvider = null;
    }

    @NonNull
    public static ApiProvider getProvider() {
        ApiProvider provider = sProvider;
        if (provider == null) {
            synchronized (ApiFactory.class) {
                provider = sProvider;
                if (provider == null) {
                    provider = sProvider = new DefaultApiProvider(Settings.getToken(AppDelegate.getContext()));
                }
            }
        }
        return provider;
    }

}
