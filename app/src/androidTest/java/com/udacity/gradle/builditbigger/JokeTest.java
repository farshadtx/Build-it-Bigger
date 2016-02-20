package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Farshad on 2/19/16.
 *
 */

public class JokeTest extends AndroidTestCase {

    private final CountDownLatch signal = new CountDownLatch(1);

    public void testJokeRetriever() {

        new JokeRetrieverTask(new TestJokeCallback()).execute();

        try {
            boolean success = signal.await(5, TimeUnit.SECONDS);
            if (!success) {
                fail("Test timed out, make sure the server is actually running.");
            }
        } catch (InterruptedException e) {
            fail();
        }
    }

    private class TestJokeCallback implements JokeRetrieverTask.JokeCallback {

        @Override
        public void getJokeCallback(String joke) {
            assertTrue(joke != null && joke.length() > 0);
            signal.countDown();
        }
    }
}
