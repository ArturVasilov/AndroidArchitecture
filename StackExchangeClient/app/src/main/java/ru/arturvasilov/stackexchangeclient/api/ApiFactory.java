package ru.arturvasilov.stackexchangeclient.api;

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.arturvasilov.stackexchangeclient.BuildConfig;
import ru.arturvasilov.stackexchangeclient.api.service.AnswerService;
import ru.arturvasilov.stackexchangeclient.api.service.ApplicationService;
import ru.arturvasilov.stackexchangeclient.api.service.NotificationService;
import ru.arturvasilov.stackexchangeclient.api.service.QuestionService;
import ru.arturvasilov.stackexchangeclient.api.service.TagsService;
import ru.arturvasilov.stackexchangeclient.api.service.UserInfoService;

/**
 * @author Artur Vasilov
 */
public final class ApiFactory {

    private static OkHttpClient sClient;

    private static UserInfoService sUserInfoService;
    private static QuestionService sQuestionService;
    private static AnswerService sAnswerService;
    private static TagsService sTagsService;
    private static NotificationService sNotificationService;
    private static ApplicationService sApplicationService;

    private ApiFactory() {
    }

    @NonNull
    public static UserInfoService getUserInfoService() {
        UserInfoService service = sUserInfoService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sUserInfoService;
                if (service == null) {
                    service = sUserInfoService = buildRetrofit().create(UserInfoService.class);
                }
            }
        }
        return service;
    }

    @NonNull
    public static QuestionService getQuestionService() {
        QuestionService service = sQuestionService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sQuestionService;
                if (service == null) {
                    service = sQuestionService = buildRetrofit().create(QuestionService.class);
                }
            }
        }
        return service;
    }

    @NonNull
    public static AnswerService getAnswerService() {
        AnswerService service = sAnswerService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sAnswerService;
                if (service == null) {
                    service = sAnswerService = buildRetrofit().create(AnswerService.class);
                }
            }
        }
        return service;
    }

    @NonNull
    public static TagsService getTagsService() {
        TagsService service = sTagsService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sTagsService;
                if (service == null) {
                    service = sTagsService = buildRetrofit().create(TagsService.class);
                }
            }
        }
        return service;
    }

    @NonNull
    public static NotificationService getNotificationService() {
        NotificationService service = sNotificationService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sNotificationService;
                if (service == null) {
                    service = sNotificationService = buildRetrofit().create(NotificationService.class);
                }
            }
        }
        return service;
    }

    @NonNull
    public static ApplicationService getApplicationService() {
        ApplicationService service = sApplicationService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sApplicationService;
                if (service == null) {
                    service = sApplicationService = buildRetrofit().create(ApplicationService.class);
                }
            }
        }
        return service;
    }

    @NonNull
    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ApiFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new ApiKeyInterceptor())
                .addInterceptor(new SiteInterceptor())
                .addInterceptor(new HttpLoggingInterceptor())
                .build();
    }
}
