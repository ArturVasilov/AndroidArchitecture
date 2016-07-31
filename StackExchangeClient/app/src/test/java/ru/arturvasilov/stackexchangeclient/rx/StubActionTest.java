package ru.arturvasilov.stackexchangeclient.rx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import rx.functions.Action1;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class StubActionTest {

    @Test
    public void testCreated() throws Exception {
        Action1<Integer> action1 = new StubAction<>();
        assertNotNull(action1);
    }

    @Test
    public void testCallDoesNothing() throws Exception {
        Action1<Runnable> action1 = new StubAction<>();

        Runnable runnable = Mockito.mock(Runnable.class);
        Mockito.doNothing().when(runnable).run();
        action1.call(runnable);

        Mockito.verify(runnable, times(0)).run();
    }
}
