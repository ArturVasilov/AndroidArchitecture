package ru.itis.lectures.githubmvp.activity;

import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;

import com.google.gson.JsonObject;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Header;
import ru.itis.lectures.githubmvp.R;
import ru.itis.lectures.githubmvp.api.ApiFactory;
import ru.itis.lectures.githubmvp.api.DefaultApiProvider;
import ru.itis.lectures.githubmvp.content.Repository;
import ru.itis.lectures.githubmvp.content.Settings;
import ru.itis.lectures.githubmvp.content.auth.Authorization;
import ru.itis.lectures.githubmvp.fragment.dialog.LoadingDialog;
import ru.itis.lectures.githubmvp.testutils.IntegrationGithubService;
import ru.itis.lectures.githubmvp.testutils.IntegrationProviderImpl;
import ru.itis.lectures.githubmvp.testutils.TestUtils;
import rx.Observable;
import rx.Subscriber;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static ru.itis.lectures.githubmvp.matchers.InputLayoutErrorMatcher.withInputError;

/**
 * @author Artur Vasilov
 */
@RunWith(AndroidJUnit4.class)
public class LogInActivityTest {

    private static final String SUCCESS_LOGIN = "Aaaaaaaaa";

    private static final Matcher<View> LOGIN_INPUT_LAYOUT = withId(R.id.loginInputLayout);
    private static final Matcher<View> LOGIN_EDIT = withId(R.id.loginEdit);
    private static final Matcher<View> PASSWORD_INPUT_LAYOUT = withId(R.id.passwordInputLayout);
    private static final Matcher<View> PASSWORD_EDIT = withId(R.id.passwordEdit);
    private static final Matcher<View> LOG_IN_BUTTON = withId(R.id.logInButton);

    @Rule
    public final ActivityTestRule<LogInActivity> mActivityRule =
            new ActivityTestRule<>(LogInActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
        ApiFactory.setProvider(new IntegrationProviderImpl(
                new TestServerImpl()));
    }

    @Test
    public void testUiElements() throws Exception {
        onView(LOGIN_INPUT_LAYOUT).check(matches(
                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        onView(LOGIN_EDIT).check(matches(
                allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                        isFocusable(), isClickable())));

        onView(PASSWORD_INPUT_LAYOUT).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        onView(PASSWORD_EDIT).check(matches(
                allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                        withInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD),
                        isFocusable(), isClickable())));

        onView(LOG_IN_BUTTON).check(matches(
                allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                        withText(R.string.log_in), isClickable(), isFocusable())));
    }

    @Test
    public void testActions() throws Exception {
        onView(LOGIN_EDIT).perform(typeText("TestLogin"));
        closeSoftKeyboard();
        onView(PASSWORD_EDIT).perform(typeText("TestPassword"));
        closeSoftKeyboard();

        onView(LOGIN_EDIT).check(matches(withText("TestLogin")));
        onView(PASSWORD_EDIT).check(matches(withText("TestPassword")));
    }

    @Test
    public void testLogInFail() throws Exception {
        errorInputAndClick();

        checkActionForDialog(() ->
                onView(LOGIN_INPUT_LAYOUT)
                        .check(matches(withInputError(R.string.error))));
    }

    @Test
    public void testLogInCorrect() throws Exception {
        correctInputAndClick();
        Intents.intended(hasComponent(
                RepositoriesActivity.class.getName()));
    }

    @Test
    public void testErrorThenCorrect() throws Exception {
        errorInputAndClick();

        onView(LOGIN_INPUT_LAYOUT).check(matches(withInputError(R.string.error)));

        clearInputs();

        correctInputAndClick();
        Intents.intended(hasComponent(RepositoriesActivity.class.getName()));
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
        Settings.clear(mActivityRule.getActivity());
        ApiFactory.setProvider(new DefaultApiProvider(""));
    }

    private void errorInputAndClick() {
        onView(LOGIN_EDIT).perform(typeText("acacaa"));
        closeSoftKeyboard();
        onView(PASSWORD_EDIT).perform(typeText("dcacdca"));
        closeSoftKeyboard();
        onView(LOG_IN_BUTTON).perform(click());
    }

    private void correctInputAndClick() {
        onView(LOGIN_EDIT).perform(typeText(SUCCESS_LOGIN));
        closeSoftKeyboard();
        onView(PASSWORD_EDIT).perform(typeText("dcacdca"));
        closeSoftKeyboard();
        onView(LOG_IN_BUTTON).perform(click());
    }

    private void clearInputs() {
        onView(LOGIN_EDIT).perform(clearText());
        closeSoftKeyboard();
        onView(PASSWORD_EDIT).perform(clearText());
        closeSoftKeyboard();
    }

    private void checkActionForDialog(@NonNull Runnable runnable) {
        IdlingResource resource = TestUtils.waitForDialog(
                mActivityRule.getActivity().getFragmentManager(),
                LoadingDialog.class.getSimpleName());
        runnable.run();
        Espresso.unregisterIdlingResources(resource);
    }

    private class TestServerImpl extends IntegrationGithubService {

        @Override
        public Observable<Authorization> authorize(@Header("Authorization") final String authorization, @Body JsonObject params) {
            return Observable.create(new Observable.OnSubscribe<Authorization>() {
                @Override
                public void call(Subscriber<? super Authorization> subscriber) {
                    processSubscriber(authorization, subscriber);
                }
            });
        }

        @Override
        public Observable<List<Repository>> repositories() {
            return Observable.empty();
        }
    }

    private void processSubscriber(@NonNull String authorization, Subscriber<? super Authorization> subscriber) {
        String base64 = authorization.split(" ")[1];
        String decode = new String(Base64.decode(base64.getBytes(), Base64.DEFAULT));
        String login = decode.split(":")[0];

        if (TextUtils.equals(login, SUCCESS_LOGIN)) {
            Authorization auth = new Authorization();
            auth.setId(111);
            auth.setToken("Aaaaaaa");
            subscriber.onNext(auth);
            subscriber.onCompleted();
        } else {
            subscriber.onError(new RuntimeException());
        }
    }

}
