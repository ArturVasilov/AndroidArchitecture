package ru.itis.lectures.githubmvp.matchers;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * @author Artur Vasilov
 */
public class InputLayoutErrorShowMatcher extends TypeSafeMatcher<View> {

    @Override
    protected boolean matchesSafely(View item) {
        if (!(item instanceof TextInputLayout)) {
            return false;
        }

        TextInputLayout layout = (TextInputLayout) item;
        return TextUtils.isEmpty(layout.getError());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with no error");
    }

    @NonNull
    public static InputLayoutErrorShowMatcher isInputErrorNotDisplayed() {
        return new InputLayoutErrorShowMatcher();
    }
}
