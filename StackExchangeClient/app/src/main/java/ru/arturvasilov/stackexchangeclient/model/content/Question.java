package ru.arturvasilov.stackexchangeclient.model.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ru.arturvasilov.stackexchangeclient.api.ApiConstants;
import ru.arturvasilov.stackexchangeclient.utils.TextUtils;

/**
 * @author Artur Vasilov
 */
public class Question implements Serializable {

    private static final long serialVersionUID = Question.class.getName().hashCode();

    @SerializedName("question_id")
    private int mQuestionId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("body")
    private String mBody;

    @SerializedName("link")
    private String mLink;

    @SerializedName("owner")
    private User mOwner;

    @SerializedName("is_answered")
    private boolean mIsAnswered;

    @SerializedName("view_count")
    private int mViewCount;

    @SerializedName("answer_count")
    private int mAnswerCount;

    @SerializedName("creation_date")
    private long mCreationDate;

    private String mTag;

    public int getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(int questionId) {
        mQuestionId = questionId;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@NonNull String title) {
        mTitle = title;
    }

    @NonNull
    public String getBody() {
        return mBody;
    }

    public void setBody(@NonNull String body) {
        mBody = body;
    }

    @NonNull
    public String getLink() {
        return mLink;
    }

    public void setLink(@NonNull String link) {
        mLink = link;
    }

    @NonNull
    public User getOwner() {
        return mOwner;
    }

    public void setOwner(@NonNull User owner) {
        mOwner = owner;
    }

    public boolean isAnswered() {
        return mIsAnswered;
    }

    public void setAnswered(boolean answered) {
        mIsAnswered = answered;
    }

    public int getViewCount() {
        return mViewCount;
    }

    public void setViewCount(int viewCount) {
        mViewCount = viewCount;
    }

    public int getAnswerCount() {
        return mAnswerCount;
    }

    public void setAnswerCount(int answerCount) {
        mAnswerCount = answerCount;
    }

    public long getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(long creationDate) {
        mCreationDate = creationDate;
    }

    @NonNull
    public String getTag() {
        if (TextUtils.isEmpty(mTag)) {
            mTag = ApiConstants.TAG_ALL;
        }
        return mTag;
    }

    public void setTag(@NonNull String tag) {
        mTag = tag;
    }
}
