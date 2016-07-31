package ru.arturvasilov.stackexchangeclient.model.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.Notification;

/**
 * @author Artur Vasilov
 */
public class NotificationResponse extends ApiError {

    @SerializedName("items")
    private List<Notification> mNotifications;

    @NonNull
    public List<Notification> getNotifications() {
        if (mNotifications == null) {
            mNotifications = new ArrayList<>();
        }
        return mNotifications;
    }

    public void setNotifications(@NonNull List<Notification> notifications) {
        mNotifications = notifications;
    }
}
