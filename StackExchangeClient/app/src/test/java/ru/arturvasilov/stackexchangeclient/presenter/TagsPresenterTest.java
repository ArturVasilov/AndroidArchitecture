package ru.arturvasilov.stackexchangeclient.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.api.RepositoryProvider;
import ru.arturvasilov.stackexchangeclient.model.content.Tag;
import ru.arturvasilov.stackexchangeclient.testutils.MockUtils;
import ru.arturvasilov.stackexchangeclient.testutils.TestLocalRepository;
import ru.arturvasilov.stackexchangeclient.testutils.TestRemoteRepository;
import ru.arturvasilov.stackexchangeclient.view.LoadingView;
import ru.arturvasilov.stackexchangeclient.view.TagsView;
import rx.Observable;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class TagsPresenterTest {

    private static final String FOUND_TAGS_KEY = "found_tags";
    private static final String FIRST_VISIBLE_ITEM_KEY = "first_visible_item_key";

    private TagsView mView;
    private LoadingView mLoadingView;

    private TagsPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        mView = Mockito.mock(TagsView.class);
        Mockito.doNothing().when(mView).clearText();
        Mockito.doNothing().when(mView).setEmptyText(anyInt());
        Mockito.doNothing().when(mView).hideAllElements();
        Mockito.doNothing().when(mView).showTags(anyListOf(Tag.class));
        Mockito.doNothing().when(mView).showFirstVisibleItem(anyInt());
        Mockito.doNothing().when(mView).showClearButton();
        Mockito.doNothing().when(mView).hideClearButton();
        Mockito.doNothing().when(mView).notifyChanged();
        Mockito.doNothing().when(mView).showEmptyListView();
        Mockito.doNothing().when(mView).hideEmptyListView();

        mLoadingView = Mockito.mock(LoadingView.class);

        mPresenter = new TagsPresenter(MockUtils.mockContext(), MockUtils.mockLoaderManager(),
                mView, mLoadingView);

        RepositoryProvider.setRemoteRepository(new TestRemoteRepository());
        RepositoryProvider.setLocalRepository(new TestLocalRepository());
    }

    @Test
    public void testCreated() throws Exception {
        assertNotNull(mPresenter);
    }

    @Test
    public void testInitEmpty() throws Exception {
        mPresenter.init(null);

        Mockito.verify(mView).hideAllElements();
    }

    @Test
    public void testShowSavedTags() throws Exception {
        RepositoryProvider.setLocalRepository(new ContentLocalRepository(false));
        mPresenter.init(null);

        Mockito.verify(mView).showTags(anyListOf(Tag.class));
        Mockito.verify(mView).hideEmptyListView();
        Mockito.verify(mView).hideClearButton();
        Mockito.verify(mView).showFirstVisibleItem(0);
    }

    @Test
    public void testEmptyInputNoFavourite() throws Exception {
        mPresenter.init(null);
        mPresenter.onInput("");

        Mockito.verify(mView, times(2)).hideAllElements();
    }

    @Test
    public void testEmptyInputSavedFavourite() throws Exception {
        RepositoryProvider.setLocalRepository(new ContentLocalRepository(false));
        mPresenter.init(null);
        mPresenter.onInput("");

        Mockito.verify(mView, times(2)).showTags(anyListOf(Tag.class));
        Mockito.verify(mView, times(2)).hideEmptyListView();
        Mockito.verify(mView, times(2)).hideClearButton();
        Mockito.verify(mLoadingView).hideLoadingIndicator();
    }

    @Test
    public void testOnEmptyClick() throws Exception {
        mPresenter.init(null);
        mPresenter.onInput("a");
        mPresenter.onClearButtonClick();

        Mockito.verify(mView).clearText();
        Mockito.verify(mView, times(2)).hideAllElements();
    }

    @Test
    public void testLoadingShown() throws Exception {
        mPresenter.init(null);
        mPresenter.onInput("a");

        Mockito.verify(mLoadingView).showLoadingIndicator();
        Mockito.verify(mLoadingView).hideLoadingIndicator();
    }

    @Test
    public void testContentShown() throws Exception {
        RepositoryProvider.setRemoteRepository(new ContentRemoteRepository());
        mPresenter.init(null);
        mPresenter.onInput("a");

        Mockito.verify(mView).showTags(anyListOf(Tag.class));
    }

    @Test
    public void testFavouriteReplaced() throws Exception {
        RepositoryProvider.setLocalRepository(new ContentLocalRepository(false));
        RepositoryProvider.setRemoteRepository(new ContentRemoteRepository());
        mPresenter.init(null);
        mPresenter.onInput("a");

        Mockito.verify(mView, times(2)).showTags(anyListOf(Tag.class));
    }

    @Test
    public void testEmptyListShown() throws Exception {
        mPresenter.init(null);
        mPresenter.onInput("0");

        Mockito.verify(mView).showEmptyListView();
        Mockito.verify(mView, never()).showTags(anyListOf(Tag.class));
    }

    @Test
    public void testAddTagToFavourite() throws Exception {
        RepositoryProvider.setLocalRepository(new ContentLocalRepository(true));
        RepositoryProvider.setRemoteRepository(new ContentRemoteRepository());
        mPresenter.init(null);
        mPresenter.onInput("a");
        mPresenter.onFavouriteClick(1);

        Mockito.verify(mView).notifyChanged();
    }

    @Test
    public void testRemoveTagFromFavourite() throws Exception {
        RepositoryProvider.setLocalRepository(new ContentLocalRepository(false));
        mPresenter.init(null);
        mPresenter.onFavouriteClick(0);

        Mockito.verify(mView).notifyChanged();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testStateSaved() throws Exception {
        RepositoryProvider.setLocalRepository(new ContentLocalRepository(false));
        mPresenter.init(null);
        Bundle outState = Mockito.mock(Bundle.class);
        Mockito.doNothing().when(outState).putParcelableArrayList(anyString(), any(ArrayList.class));
        Mockito.doNothing().when(outState).putInt(anyString(), anyInt());

        mPresenter.onSaveInstanceState(outState, 1);
        Mockito.verify(outState).putInt(FIRST_VISIBLE_ITEM_KEY, 1);
        Mockito.verify(outState).putParcelableArrayList(eq(FOUND_TAGS_KEY), any(ArrayList.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testPositionRestored() throws Exception {
        ArrayList tags = new ArrayList();
        tags.add(new Tag("android", false));
        tags.add(new Tag("android-actionbar", false));
        tags.add(new Tag("android-testing", false));
        tags.add(new Tag("java", false));
        Bundle savedInstanceState = Mockito.mock(Bundle.class);
        Mockito.when(savedInstanceState.getParcelableArrayList(FOUND_TAGS_KEY)).thenReturn(tags);
        Mockito.when(savedInstanceState.getInt(FIRST_VISIBLE_ITEM_KEY)).thenReturn(2);

        mPresenter.init(savedInstanceState);
        Mockito.verify(mView).showFirstVisibleItem(2);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFoundTagsRestored() throws Exception {
        ArrayList tags = new ArrayList();
        tags.add(new Tag("android-actionbar", false));
        tags.add(new Tag("java", false));
        Bundle savedInstanceState = Mockito.mock(Bundle.class);
        Mockito.when(savedInstanceState.getParcelableArrayList(FOUND_TAGS_KEY)).thenReturn(tags);
        Mockito.when(savedInstanceState.getInt(FIRST_VISIBLE_ITEM_KEY)).thenReturn(1);

        mPresenter.init(savedInstanceState);
        Mockito.verify(mView).showTags(anyListOf(Tag.class));
        Mockito.verify(mView).hideEmptyListView();
        Mockito.verify(mView).showClearButton();
    }

    @SuppressWarnings("ConstantConditions")
    @After
    public void tearDown() throws Exception {
        RepositoryProvider.setRemoteRepository(null);
        RepositoryProvider.setLocalRepository(null);
    }

    private class ContentLocalRepository extends TestLocalRepository {

        private final boolean mUpdateResult;

        public ContentLocalRepository(boolean updateResult) {
            mUpdateResult = updateResult;
        }

        @NonNull
        @Override
        public Observable<List<String>> tags() {
            List<String> tags = new ArrayList<>();
            tags.add("android");
            tags.add("java");
            return Observable.just(tags);
        }

        @Override
        public boolean updateTag(@NonNull Tag tag) {
            return mUpdateResult;
        }
    }

    private class ContentRemoteRepository extends TestRemoteRepository {

        @NonNull
        @Override
        public Observable<List<Tag>> searchTags(@NonNull String search) {
            List<Tag> tags = new ArrayList<>();
            tags.add(new Tag("android", false));
            tags.add(new Tag("android-actionbar", false));
            tags.add(new Tag("android-testing", false));
            tags.add(new Tag("java", false));
            return Observable.just(tags);
        }
    }
}
