package ru.arturvasilov.stackexchangeclient.app.analytics;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ru.arturvasilov.stackexchangeclient.BuildConfig;
import ru.arturvasilov.stackexchangeclient.model.content.User;

/**
 * @author Artur Vasilov
 */
public class Analytics {

    private static final String APP_VERSION_KEY = "app_version";
    private static final String ANDROID_API_KEY = "android_api";
    private static final String ANDROID_DEVICE_NAME_KEY = "android_device_name";
    private static final String ANDROID_DEVICE_MODEL_KEY = "android_device_model";
    private static final String USER_ID_KEY = "user_id";
    private static final String USER_NAME_KEY = "user_key";
    private static final String FCM_REGISTRATION_KEY = "fcm_registration_key";

    private static FirebaseAnalytics sAnalytics;

    public static void init(@NonNull Context context) {
        sAnalytics = FirebaseAnalytics.getInstance(context);
        sAnalytics.setUserProperty(APP_VERSION_KEY, BuildConfig.VERSION_NAME);
        sAnalytics.setUserProperty(ANDROID_API_KEY, String.valueOf(Build.VERSION.SDK_INT));
        sAnalytics.setUserProperty(ANDROID_DEVICE_NAME_KEY, Build.MODEL);
        sAnalytics.setUserProperty(ANDROID_DEVICE_MODEL_KEY, Build.PRODUCT);
    }

    public static void setCurrentUser(@NonNull User currentUser) {
        sAnalytics.setUserProperty(USER_ID_KEY, String.valueOf(currentUser.getUserId()));
        sAnalytics.setUserProperty(USER_NAME_KEY, currentUser.getName());
    }

    public static void setFcmRegistrationKey(@NonNull String token) {
        sAnalytics.setUserProperty(FCM_REGISTRATION_KEY, token);
    }

    @NonNull
    public static EventBuilder buildEvent() {
        return new EventBuilder();
    }

    public static class EventBuilder {

        private final Bundle mBundle;

        private EventBuilder() {
            mBundle = new Bundle();
        }

        @NonNull
        public EventBuilder putString(@NonNull @EventKey String key, @NonNull String value) {
            mBundle.putString(key, value);
            return this;
        }

        public void log(@NonNull @EventTag String eventTag) {
            sAnalytics.logEvent(eventTag, mBundle);
        }

    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({EventTags.APP_STARTED, EventTags.SCREEN_AUTH, EventTags.AUTH_BUTTON_CLICKED, EventTags.SUCCESS_AUTH,
            EventTags.SCREEN_WALKTHROUGH, EventTags.WALKTHROUGH_BUTTON_CLICK, EventTags.WALKTHROUGH_SWIPE,
            EventTags.WALKTHROUGH_SELECTED_BENEFIT, EventTags.WALKTHROUGH_SPLASH, EventTags.WALKTHROUGH_ERROR,
            EventTags.SCREEN_MAIN, EventTags.MAIN_TABS, EventTags.MAIN_DRAWER_ITEM_SELECTED,
            EventTags.SCREEN_PROFILE, EventTags.PROFILE_BADGE_CLICKED,
            EventTags.SCREEN_ANSWERS, EventTags.ANSWER_CLICKED,
            EventTags.SCREEN_TAGS, EventTags.TAGS_ADD_FAVOURITE, EventTags.TAGS_REMOVE_FAVOURITE,
            EventTags.SCREEN_QUESTION,
            EventTags.PUSH_NOTIFICATION})
    private @interface EventTag {
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({EventKeys.WALKTHROUGH_BENEFIT_POSITION, EventKeys.MAIN_TAGS, EventKeys.MAIN_DRAWER_ITEM,
            EventKeys.PROFILE_USER, EventKeys.PROFILE_BADGE, EventKeys.ANSWER_USER, EventKeys.ANSWER_CLICK,
            EventKeys.TAG, EventKeys.QUESTION_ID, EventKeys.NOTIFICATION_ID, EventKeys.NOTIFICATION_SHOWN})
    private @interface EventKey {
    }

}
