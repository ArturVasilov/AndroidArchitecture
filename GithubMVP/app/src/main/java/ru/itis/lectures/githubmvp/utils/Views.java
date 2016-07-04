package ru.itis.lectures.githubmvp.utils;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * @author Artur Vasilov
 */
public final class Views {

    private Views() {
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public static <T extends View> T findById(@NonNull View view, @IdRes int id) {
        return (T) view.findViewById(id);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public static <T extends View> T findById(@NonNull Activity activity, @IdRes int id) {
        return (T) activity.findViewById(id);
    }

}
