package ru.arturvasilov.stackexchangeclient.rx;

import rx.functions.Action1;

/**
 * @author Artur Vasilov
 */
public class StubAction<T> implements Action1<T> {

    @Override
    public void call(T t) {
        // Do nothing
    }
}
