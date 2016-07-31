package ru.arturvasilov.stackexchangeclient.view;

import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public interface AuthView {

    void showAuth(@NonNull String url);

    void showWalkthrough();

    void showMainScreen();

    void close();

}
