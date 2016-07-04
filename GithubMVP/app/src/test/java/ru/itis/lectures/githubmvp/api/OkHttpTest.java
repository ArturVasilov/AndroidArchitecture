package ru.itis.lectures.githubmvp.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class OkHttpTest {

    @Test
    public void testLoggingInterceptor() throws Exception {
        Interceptor interceptor = OkHttp.getLoggingInterceptor();
        assertNotNull(interceptor);
    }

    @Test
    public void testTokenInterceptor() throws Exception {
        Interceptor interceptor = OkHttp.getTokenInterceptor("token");
        assertNotNull(interceptor);
    }

    @Test
    public void testInterceptorsCount() throws Exception {
        OkHttpClient client = OkHttp.client("aaa");
        assertEquals(2, client.interceptors().size());
    }

    @Test
    public void testClient() throws Exception {
        OkHttpClient client = OkHttp.client("aaa");
        assertNotNull(client);
    }

    @Test
    public void testNewClient() throws Exception {
        OkHttpClient client = OkHttp.newClient("aaa");
        assertNotNull(client);
    }

    @Test
    public void testClientRemainTheSame() throws Exception {
        OkHttpClient client1 = OkHttp.newClient("token");
        OkHttpClient client2 = OkHttp.client("token");
        assertTrue(client1 == client2);
    }

    @Test
    public void testClientChanges() throws Exception {
        OkHttpClient client1 = OkHttp.newClient("token");
        OkHttpClient client2 = OkHttp.newClient("token");
        assertFalse(client1 == client2);
    }
}
