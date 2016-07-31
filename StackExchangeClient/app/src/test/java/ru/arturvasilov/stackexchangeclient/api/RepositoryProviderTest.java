package ru.arturvasilov.stackexchangeclient.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ru.arturvasilov.stackexchangeclient.data.database.LocalRepository;
import ru.arturvasilov.stackexchangeclient.data.keyvalue.KeyValueStorage;
import ru.arturvasilov.stackexchangeclient.testutils.TestKeyValueStorage;
import ru.arturvasilov.stackexchangeclient.testutils.TestLocalRepository;
import ru.arturvasilov.stackexchangeclient.testutils.TestRemoteRepository;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class RepositoryProviderTest {

    @SuppressWarnings("ConstantConditions")
    @Before
    public void setUp() throws Exception {
        RepositoryProvider.setRemoteRepository(null);
        RepositoryProvider.setLocalRepository(null);
        RepositoryProvider.setKeyValueStorage(null);
    }

    @Test
    public void testRemoteNotInited() throws Exception {
        assertNull(RepositoryProvider.provideRemoteRepository());
    }

    @Test
    public void testRemoteSetter() throws Exception {
        RemoteRepository repository = new TestRemoteRepository();
        RepositoryProvider.setRemoteRepository(repository);

        assertTrue(repository == RepositoryProvider.provideRemoteRepository());
    }

    @Test
    public void testLocalNotInited() throws Exception {
        assertNull(RepositoryProvider.provideLocalRepository());
    }

    @Test
    public void testLocalSetter() throws Exception {
        LocalRepository repository = new TestLocalRepository();
        RepositoryProvider.setLocalRepository(repository);

        assertTrue(repository == RepositoryProvider.provideLocalRepository());
    }

    @Test
    public void testKeyValueNotInited() throws Exception {
        assertNull(RepositoryProvider.provideKeyValueStorage());
    }

    @Test
    public void testKeyValueSetter() throws Exception {
        KeyValueStorage keyValueStorage = new TestKeyValueStorage();
        RepositoryProvider.setKeyValueStorage(keyValueStorage);

        assertTrue(keyValueStorage == RepositoryProvider.provideKeyValueStorage());
    }

    @SuppressWarnings("ConstantConditions")
    @After
    public void tearDown() throws Exception {
        RepositoryProvider.setRemoteRepository(null);
        RepositoryProvider.setLocalRepository(null);
        RepositoryProvider.setKeyValueStorage(null);
    }
}
