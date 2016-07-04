package ru.itis.lectures.githubmvp.api;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ru.itis.lectures.githubmvp.utils.TextUtils;

/**
 * @author Artur Vasilov
 */
public final class TokenInterceptor implements Interceptor {

    private final String mToken;

    private TokenInterceptor(@NonNull String token) {
        mToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (TextUtils.isEmpty(mToken)) {
            return chain.proceed(chain.request());
        }
        Request request = chain.request().newBuilder()
                .addHeader("Authorization", String.format("%s %s", "token", mToken))
                .build();
        return chain.proceed(request);
    }

    @NonNull
    public static Interceptor getInstance(@NonNull String token) {
        return new TokenInterceptor(token);
    }
}
