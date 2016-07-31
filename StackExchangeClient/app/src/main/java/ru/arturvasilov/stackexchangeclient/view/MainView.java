package ru.arturvasilov.stackexchangeclient.view;

import android.support.annotation.NonNull;

import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.User;

/**
 * @author Artur Vasilov
 */
public interface MainView {

    void showUserImage(@NonNull String imageUrl);

    void showUserName(@NonNull String name);

    void clearTabs();

    void addTab(@NonNull String tabTitle);

    void showTags(@NonNull List<String> tags);

    void openProfile(@NonNull User currentUser);

    void openAnswers(@NonNull User currentUser);

    void hideTabLayout();
}
