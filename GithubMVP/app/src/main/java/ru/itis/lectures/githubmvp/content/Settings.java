package ru.itis.lectures.githubmvp.content;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public final class Settings {

    private static final String PREFS_NAME = "github_preferences";

    private static final String TOKEN_KEY = "token_key";
    private static final String USER_NAME_KEY = "user_name_key";
    private static final String WALKTHROUGH_KEY = "walkthrough_key";

    private Settings() {
    }

    public static void putToken(Context context, @NonNull String token) {
        prefs(context)
                .edit()
                .putString(TOKEN_KEY, token)
                .apply();
    }

    @NonNull
    public static String getToken(Context context) {
        return prefs(context).getString(TOKEN_KEY, "");
    }

    public static void putUserName(Context context, @NonNull String name) {
        prefs(context)
                .edit()
                .putString(USER_NAME_KEY, name)
                .apply();
    }

    @NonNull
    public static String getUserName(Context context) {
        return prefs(context).getString(USER_NAME_KEY, "");
    }

    public static void saveWalkthroughPassed(Context context) {
        prefs(context)
                .edit()
                .putBoolean(WALKTHROUGH_KEY, true)
                .apply();
    }

    public static boolean isWalkthroughPassed(Context context) {
        return prefs(context).getBoolean(WALKTHROUGH_KEY, false);
    }

    public static void clear(Context context) {
        prefs(context).edit().clear().apply();
    }

    @NonNull
    private static SharedPreferences prefs(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}
