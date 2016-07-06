package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.rx;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Artur Vasilov
 */
public final class RxUtils {

    public static void setupTestSchedulers() {
        try {
            RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
                @Override
                public Scheduler getIOScheduler() {
                    return Schedulers.immediate();
                }

                @Override
                public Scheduler getNewThreadScheduler() {
                    return Schedulers.immediate();
                }
            });

            RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
                @Override
                public Scheduler getMainThreadScheduler() {
                    return Schedulers.immediate();
                }
            });
        } catch (IllegalStateException ignored) {
        }
    }

    @NonNull
    public static Context rxContext() {
        return mock(Context.class);
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public static LoaderManager rxLoaderManager() {
        LoaderManager lm = mock(LoaderManager.class);
        when(lm.initLoader(anyInt(), any(Bundle.class), any(LoaderManager.LoaderCallbacks.class)))
                .then(new MockedRxLoaderAnswer());
        when(lm.restartLoader(anyInt(), any(Bundle.class), any(LoaderManager.LoaderCallbacks.class)))
                .then(new MockedRxLoaderAnswer());
        return lm;
    }

    private static class MockedRxLoaderAnswer implements Answer<Loader> {

        @Override
        public Loader answer(InvocationOnMock invocation) throws Throwable {
            RxLcImpl callbacks = (RxLcImpl) invocation.getArguments()[2];
            callbacks.subscribeForTesting();
            return null;
        }
    }


}
