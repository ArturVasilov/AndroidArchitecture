package ru.arturvasilov.stackexchangeclient.view;

import android.support.annotation.StringRes;

/**
 * @author Artur Vasilov
 */
public interface WalkthroughView {

    void showBenefit(int index);

    void setActionButtonText(@StringRes int textResId);

    void showLoadingSplash();

    void showError();

    void finishWalkthrough();

}
