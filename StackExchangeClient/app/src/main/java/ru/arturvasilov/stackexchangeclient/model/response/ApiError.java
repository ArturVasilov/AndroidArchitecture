package ru.arturvasilov.stackexchangeclient.model.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class ApiError {

    @SerializedName("error_id")
    private int mErrorCode;

    @SerializedName("error_message")
    private String mErrorMessage;

    @SerializedName("error_name")
    private String mErrorName;

    public boolean isError() {
        return mErrorCode > 400;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(int errorCode) {
        mErrorCode = errorCode;
    }

    @NonNull
    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setErrorMessage(@NonNull String errorMessage) {
        mErrorMessage = errorMessage;
    }

    @NonNull
    public String getErrorName() {
        return mErrorName;
    }

    public void setErrorName(@NonNull String errorName) {
        mErrorName = errorName;
    }
}
