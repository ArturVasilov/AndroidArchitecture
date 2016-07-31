package ru.arturvasilov.stackexchangeclient.api;

import android.support.annotation.NonNull;

import ru.arturvasilov.stackexchangeclient.data.keyvalue.KeyValueStorage;
import ru.arturvasilov.stackexchangeclient.data.database.LocalRepository;

/**
 * @author Artur Vasilov
 */
public final class RepositoryProvider {

    private static RemoteRepository sRemoteRepository;
    private static LocalRepository sLocalRepository;
    private static KeyValueStorage sKeyValueStorage;

    private RepositoryProvider() {
    }

    @NonNull
    public static RemoteRepository provideRemoteRepository() {
        return sRemoteRepository;
    }

    @NonNull
    public static LocalRepository provideLocalRepository() {
        return sLocalRepository;
    }

    @NonNull
    public static KeyValueStorage provideKeyValueStorage() {
        return sKeyValueStorage;
    }

    public static void setRemoteRepository(@NonNull RemoteRepository remoteRepository) {
        sRemoteRepository = remoteRepository;
    }

    public static void setLocalRepository(@NonNull LocalRepository localRepository) {
        sLocalRepository = localRepository;
    }

    public static void setKeyValueStorage(@NonNull KeyValueStorage keyValueStorage) {
        sKeyValueStorage = keyValueStorage;
    }
}
