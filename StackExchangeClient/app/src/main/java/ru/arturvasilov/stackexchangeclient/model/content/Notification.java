package ru.arturvasilov.stackexchangeclient.model.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class Notification {

    @SerializedName("is_unread")
    private boolean mIsUnread;

    @SerializedName("creation_date")
    private long mCreationDate;

    @SerializedName("body")
    private String mBody;

    public boolean isUnread() {
        return mIsUnread;
    }

    public void setUnread(boolean unread) {
        mIsUnread = unread;
    }

    public long getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(long creationDate) {
        mCreationDate = creationDate;
    }

    @NonNull
    public String getBody() {
        return mBody;
    }

    public void setBody(@NonNull String body) {
        mBody = body;
    }
}
