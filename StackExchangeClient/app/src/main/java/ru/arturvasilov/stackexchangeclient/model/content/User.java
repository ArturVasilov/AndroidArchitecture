package ru.arturvasilov.stackexchangeclient.model.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Artur Vasilov
 */
public class User implements Serializable {

    private static final long serialVersionUID = User.class.getName().hashCode();

    @SerializedName("user_id")
    private int mUserId;

    @SerializedName("age")
    private int mAge;

    @SerializedName("display_name")
    private String mName;

    @SerializedName("reputation")
    private int mReputation;

    @SerializedName("link")
    private String mLink;

    @SerializedName("profile_image")
    private String mProfileImage;

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public void setName(@NonNull String name) {
        mName = name;
    }

    public int getReputation() {
        return mReputation;
    }

    public void setReputation(int reputation) {
        mReputation = reputation;
    }

    @NonNull
    public String getLink() {
        return mLink;
    }

    public void setLink(@NonNull String link) {
        mLink = link;
    }

    @NonNull
    public String getProfileImage() {
        return mProfileImage;
    }

    public void setProfileImage(@NonNull String profileImage) {
        mProfileImage = profileImage;
    }
}
