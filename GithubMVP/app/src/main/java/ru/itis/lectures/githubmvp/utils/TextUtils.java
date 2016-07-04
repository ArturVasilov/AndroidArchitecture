package ru.itis.lectures.githubmvp.utils;

import android.support.annotation.Nullable;

/**
 * @author Artur Vasilov
 */
public final class TextUtils {

    private TextUtils() {
    }

    public static boolean isEmpty(@Nullable CharSequence text) {
        return text == null || text.length() == 0;
    }

}
