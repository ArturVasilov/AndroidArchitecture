package ru.arturvasilov.stackexchangeclient.view;

import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public interface ErrorView {

    void showNetworkError();

    void showUnexpectedError();

    void showErrorMessage(@NonNull String message);

}
