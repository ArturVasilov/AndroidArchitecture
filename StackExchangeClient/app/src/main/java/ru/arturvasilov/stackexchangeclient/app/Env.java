package ru.arturvasilov.stackexchangeclient.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import ru.arturvasilov.stackexchangeclient.AppDelegate;
import ru.arturvasilov.stackexchangeclient.activity.AuthActivity;
import ru.arturvasilov.stackexchangeclient.api.RepositoryProvider;

/**
 * @author Artur Vasilov
 */
public final class Env {

    private Env() {
    }

    public static void browseUrl(@NonNull Context context, @NonNull String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    public static void logout() {
        String accessToken = RepositoryProvider.provideKeyValueStorage().obtainAccessToken();
        RepositoryProvider.provideRemoteRepository().logout(accessToken);
        RepositoryProvider.provideLocalRepository().logout();
        RepositoryProvider.provideKeyValueStorage().logout();

        Context context = AppDelegate.getAppContext();
        Intent intent = new Intent(context, AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

}
