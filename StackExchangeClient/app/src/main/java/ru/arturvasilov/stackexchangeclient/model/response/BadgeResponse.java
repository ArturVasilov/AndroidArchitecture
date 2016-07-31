package ru.arturvasilov.stackexchangeclient.model.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.Badge;

/**
 * @author Artur Vasilov
 */
public class BadgeResponse extends ApiError {

    @SerializedName("items")
    private List<Badge> mBadges;

    @NonNull
    public List<Badge> getBadges() {
        if (mBadges == null) {
            mBadges = new ArrayList<>();
        }
        return mBadges;
    }

    public void setBadges(@NonNull List<Badge> badges) {
        mBadges = badges;
    }
}
