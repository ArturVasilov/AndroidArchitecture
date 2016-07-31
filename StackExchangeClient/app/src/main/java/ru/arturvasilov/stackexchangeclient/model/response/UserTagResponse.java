package ru.arturvasilov.stackexchangeclient.model.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.UserTag;

/**
 * @author Artur Vasilov
 */
public class UserTagResponse extends ApiError {

    @SerializedName("items")
    private List<UserTag> mUserTags;

    @NonNull
    public List<UserTag> getUserTags() {
        if (mUserTags == null) {
            mUserTags = new ArrayList<>();
        }
        return mUserTags;
    }

    public void setUserTags(@NonNull List<UserTag> userTags) {
        mUserTags = userTags;
    }
}
