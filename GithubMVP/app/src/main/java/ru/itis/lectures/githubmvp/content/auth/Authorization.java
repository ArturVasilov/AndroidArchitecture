package ru.itis.lectures.githubmvp.content.auth;

import com.google.gson.annotations.SerializedName;

public class Authorization {

    @SerializedName("id")
    private int mId;

    @SerializedName("token")
    private String mToken;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }
}
