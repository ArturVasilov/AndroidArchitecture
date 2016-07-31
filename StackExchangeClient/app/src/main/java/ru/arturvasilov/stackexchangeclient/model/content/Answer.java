package ru.arturvasilov.stackexchangeclient.model.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class Answer {

    @SerializedName("owner")
    private User mOwner;

    @SerializedName("body")
    private String mBody;

    @SerializedName("is_accepted")
    private boolean mIsAccepted;

    @SerializedName("question_id")
    private int mQuestionId;

    @SerializedName("answer_id")
    private int mAnswerId;

    @NonNull
    public User getOwner() {
        return mOwner;
    }

    public void setOwner(@NonNull User owner) {
        mOwner = owner;
    }

    @NonNull
    public String getBody() {
        return mBody;
    }

    public void setBody(@NonNull String body) {
        mBody = body;
    }

    public boolean isAccepted() {
        return mIsAccepted;
    }

    public void setAccepted(boolean accepted) {
        mIsAccepted = accepted;
    }

    public int getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(int questionId) {
        mQuestionId = questionId;
    }

    public int getAnswerId() {
        return mAnswerId;
    }

    public void setAnswerId(int answerId) {
        mAnswerId = answerId;
    }
}
