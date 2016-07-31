package ru.arturvasilov.stackexchangeclient.utils;

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
    public static <T extends View> T findById(@NonNull Activity activity, @IdRes int viewId) {
        //noinspection unchecked
        return (T) activity.findViewById(viewId);
    }

    @NonNull
    public static <T extends View> T findById(@NonNull View view, @IdRes int viewId) {
        //noinspection unchecked
        return (T) view.findViewById(viewId);
    }

}
