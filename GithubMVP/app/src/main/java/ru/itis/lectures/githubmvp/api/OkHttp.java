package ru.itis.lectures.githubmvp.api;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ru.itis.lectures.githubmvp.BuildConfig;

/**
 * @author Artur Vasilov
 */
final class OkHttp {

    private static final int CONNECT_TIMEOUT = 15;
    private static final int READ_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 30;

    private static OkHttpClient sClient = null;

    private OkHttp() {
    }

    @NonNull
    public static OkHttpClient client(@NonNull String token) {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (OkHttp.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = newClient(token);
                }
            }
        }
        return client;
    }

    @NonNull
    public static OkHttpClient newClient(@NonNull String token) {
        sClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(getTokenInterceptor(token))
                .build();
        return sClient;
    }

    @NonNull
    public static Interceptor getLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG
                ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
    }

    @NonNull
    public static Interceptor getTokenInterceptor(@NonNull String token) {
        return TokenInterceptor.getInstance(token);
    }
}
