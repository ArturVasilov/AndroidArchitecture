package ru.itis.lectures.githubmvp.testutils;

import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.TimeUnit;

/**
 * @author Artur Vasilov
 */
public class TestUtils {

    @NonNull
    public static IdlingResource waitForDialog(@NonNull FragmentManager fragmentManager, @NonNull String tag) {
        IdlingPolicies.setMasterPolicyTimeout(3, TimeUnit.SECONDS);
        IdlingPolicies.setIdlingResourceTimeout(3, TimeUnit.SECONDS);

        IdlingResource idlingResource = new DialogFragmentIdlingResource(fragmentManager, tag);
        Espresso.registerIdlingResources(idlingResource);

        return idlingResource;
    }

    public static class DialogFragmentIdlingResource implements IdlingResource {

        private final FragmentManager mFragmentManager;
        private final String mTag;
        private ResourceCallback mResourceCallback;

        public DialogFragmentIdlingResource(@NonNull FragmentManager manager, @NonNull String tag) {
            mFragmentManager = manager;
            mTag = tag;
        }

        @Override
        public boolean isIdleNow() {
            boolean idle = (mFragmentManager.findFragmentByTag(mTag) == null);
            if (idle) {
                mResourceCallback.onTransitionToIdle();
            }
            return idle;
        }

        @Override
        public String getName() {
            return "IdlingResources for dialog:" + mTag;
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback callback) {
            mResourceCallback = callback;
        }
    }
}
