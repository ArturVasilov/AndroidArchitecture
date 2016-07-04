package ru.itis.lectures.githubmvp.matchers;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * @author Artur Vasilov
 */
public class InputLayoutErrorMatcher extends TypeSafeMatcher<View> {

    @StringRes
    private final int mErrorTextId;

    private CharSequence mErrorText;

    private InputLayoutErrorMatcher(@StringRes int errorTextId) {
        mErrorTextId = errorTextId;
    }

    private InputLayoutErrorMatcher(@NonNull String errorText) {
        this(-1);
        mErrorText = errorText;
    }

    @Override
    protected boolean matchesSafely(View item) {
        if (!(item instanceof TextInputLayout)) {
            return false;
        }

        if (mErrorTextId >= 0) {
            mErrorText = item.getResources().getString(mErrorTextId);
        }

        TextInputLayout layout = (TextInputLayout) item;
        return TextUtils.equals(mErrorText, layout.getError());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with error: " + mErrorText);
    }

    @NonNull
    public static InputLayoutErrorMatcher withInputError(@StringRes int errorTextId) {
        return new InputLayoutErrorMatcher(errorTextId);
    }

    @NonNull
    public static InputLayoutErrorMatcher withInputError(@NonNull String errorText) {
        return new InputLayoutErrorMatcher(errorText);
    }
}
