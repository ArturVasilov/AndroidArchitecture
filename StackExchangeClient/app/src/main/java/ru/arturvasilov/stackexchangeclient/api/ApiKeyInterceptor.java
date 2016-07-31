package ru.arturvasilov.stackexchangeclient.api;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ru.arturvasilov.stackexchangeclient.BuildConfig;

/**
 * @author Artur Vasilov
 */
class ApiKeyInterceptor implements Interceptor {

    private final String mAccessToken;

    public ApiKeyInterceptor() {
        mAccessToken = RepositoryProvider.provideKeyValueStorage().obtainAccessToken();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("key", BuildConfig.KEY_SECRET)
                .addQueryParameter("access_token", mAccessToken)
                .build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
