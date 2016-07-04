package ru.itis.lectures.githubmvp;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import ru.arturvasilov.sqlite.SQLite;
import ru.itis.lectures.githubmvp.api.ApiFactory;
import ru.itis.lectures.githubmvp.api.ApiProvider;
import ru.itis.lectures.githubmvp.api.DefaultApiProvider;
import ru.itis.lectures.githubmvp.content.Settings;

/**
 * @author Artur Vasilov
 */
public class AppDelegate extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        SQLite.initialize(this);
        sContext = this;

        String token = Settings.getToken(this);
        ApiProvider provider = new DefaultApiProvider(token);
        ApiFactory.setProvider(provider);
    }

    @NonNull
    public static Context getContext() {
        return sContext;
    }
}
