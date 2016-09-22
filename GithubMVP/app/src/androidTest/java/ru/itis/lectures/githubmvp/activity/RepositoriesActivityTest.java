package ru.itis.lectures.githubmvp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.sqlite.core.SQLite;
import ru.itis.lectures.githubmvp.R;
import ru.itis.lectures.githubmvp.api.ApiFactory;
import ru.itis.lectures.githubmvp.api.DefaultApiProvider;
import ru.itis.lectures.githubmvp.content.Repository;
import ru.itis.lectures.githubmvp.database.tables.RepositoryTable;
import ru.itis.lectures.githubmvp.testutils.IntegrationGithubService;
import ru.itis.lectures.githubmvp.testutils.IntegrationProviderImpl;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;

/**
 * @author Artur Vasilov
 */
@RunWith(AndroidJUnit4.class)
public class RepositoriesActivityTest {

    private static final Matcher<View> RECYCLER_VIEW = withId(R.id.recyclerView);
    private static final Matcher<View> EMPTY_VIEW = withId(R.id.empty);

    @Rule
    public final ActivityTestRule<RepositoriesActivity> mActivityRule = new ActivityTestRule<>(RepositoriesActivity.class);

    @Test
    public void testEmptyView() throws Exception {
        onView(EMPTY_VIEW).check(matches(isDisplayed()));
        onView(RECYCLER_VIEW).check(matches(not(isDisplayed())));
    }
}
