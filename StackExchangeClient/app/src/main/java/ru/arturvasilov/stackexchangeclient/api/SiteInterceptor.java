package ru.arturvasilov.stackexchangeclient.api;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Artur Vasilov
 */
class SiteInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();
        if (!url.url().toString().contains("/apps")) {
            url = request.url().newBuilder()
                    .addQueryParameter("site", "stackoverflow")
                    .build();
        }
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
