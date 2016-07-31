package ru.arturvasilov.stackexchangeclient.rx.rxloader;

import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import rx.Observable;
import rx.Observer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class RxLcImplTest {

    private Observer<Integer> mObserver;

    private RxLcImpl<Integer> mRxLc;

    @Before
    public void setUp() throws Exception {
        //noinspection unchecked
        mObserver = mock(Observer.class);
        doNothing().when(mObserver).onNext(anyInt());
        doNothing().when(mObserver).onError(any(Throwable.class));
        doNothing().when(mObserver).onCompleted();

        Context context = mock(Context.class);
        mRxLc = new RxLcImpl<>(context, Observable.just(5), mObserver);
    }

    @Test
    public void testOnCreateLoader() throws Exception {
        Loader<RxResult<Integer>> loader = mRxLc.onCreateLoader(0, mock(Bundle.class));
        Assert.assertNotNull(loader);
    }

    @Test
    public void testNullData() throws Exception {
        Loader<RxResult<Integer>> loader = mRxLc.onCreateLoader(0, mock(Bundle.class));
        mRxLc.onLoadFinished(loader, null);
        verify(mObserver, times(0)).onError(any(Throwable.class));
        verify(mObserver, times(0)).onNext(anyInt());
        verify(mObserver, times(1)).onCompleted();
    }

    @Test
    public void testError() throws Exception {
        Throwable error = new Exception();
        Loader<RxResult<Integer>> loader = mRxLc.onCreateLoader(0, mock(Bundle.class));
        mRxLc.onLoadFinished(loader, RxResult.onError(error));
        verify(mObserver, times(1)).onError(error);
        verify(mObserver, times(0)).onNext(anyInt());
        verify(mObserver, times(1)).onCompleted();
    }

    @Test
    public void testSuccess() throws Exception {
        Loader<RxResult<Integer>> loader = mRxLc.onCreateLoader(0, mock(Bundle.class));
        mRxLc.onLoadFinished(loader, RxResult.onNext(5));
        verify(mObserver, times(0)).onError(any(Throwable.class));
        verify(mObserver, times(1)).onNext(5);
        verify(mObserver, times(1)).onCompleted();
    }

    @Test
    public void testOnLoaderReset() throws Exception {
        mRxLc.onLoaderReset(mRxLc.onCreateLoader(0, mock(Bundle.class)));
        verify(mObserver, times(0)).onError(any(Throwable.class));
        verify(mObserver, times(0)).onNext(anyInt());
        verify(mObserver, times(0)).onCompleted();
    }
}
