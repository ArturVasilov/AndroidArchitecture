package ru.arturvasilov.stackexchangeclient.model.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.User;

/**
 * @author Artur Vasilov
 */
public class UserResponse extends ApiError {

    @SerializedName("items")
    private List<User> mUsers;

    @NonNull
    public List<User> getUsers() {
        if (mUsers == null) {
            mUsers = new ArrayList<>();
        }
        return mUsers;
    }

    public void setUsers(@NonNull List<User> users) {
        mUsers = users;
    }
}
