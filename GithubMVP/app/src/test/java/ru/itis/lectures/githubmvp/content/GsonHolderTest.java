package ru.itis.lectures.githubmvp.content;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class GsonHolderTest {

    @Test
    public void testHolderInitialized() throws Exception {
        Gson gson = GsonHolder.getGson();
        assertNotNull(gson);
    }

    @Test
    public void testMultiThreading() throws Exception {
        Thread thread1 = new Thread(getTestRunnable());
        Thread thread2 = new Thread(getTestRunnable());
        Thread thread3 = new Thread(getTestRunnable());
        Thread thread4 = new Thread(getTestRunnable());

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

    @NonNull
    public Runnable getTestRunnable() {
        return () -> assertNotNull(GsonHolder.getGson());
    }
}
