package ru.arturvasilov.stackexchangeclient.view;

import android.support.annotation.NonNull;

import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.Badge;
import ru.arturvasilov.stackexchangeclient.model.content.UserTag;

/**
 * @author Artur Vasilov
 */
public interface ProfileView {

    void showUserName(@NonNull String name);

    void showUserImage(@NonNull String url);

    void showProfileLink(@NonNull String profileLink);

    void showReputation(@NonNull String reputation);

    void showBadges(@NonNull List<Badge> badges);

    void showTopTags(@NonNull List<UserTag> tags);

}
