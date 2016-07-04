package ru.itis.lectures.githubmvp.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class EmptyRecyclerViewTest {

    @Test
    public void testEmptyView() throws Exception {
        TestAdapter adapter = spy(new TestAdapter(new ArrayList<>()));
        EmptyRecyclerView recyclerView = mock(EmptyRecyclerView.class);

        setupAdapter(adapter, recyclerView);
        setupRecycler(recyclerView, adapter);
        setupEmptyView(recyclerView, true);

        adapter.attachToRecyclerView(recyclerView);

        verify(recyclerView).checkIfEmpty();
        verify(recyclerView).showEmptyView();
    }

    @Test
    public void testItems() throws Exception {
        TestAdapter adapter = spy(new TestAdapter(values()));
        EmptyRecyclerView recyclerView = mock(EmptyRecyclerView.class);

        setupAdapter(adapter, recyclerView);
        setupRecycler(recyclerView, adapter);
        setupEmptyView(recyclerView, false);

        adapter.attachToRecyclerView(recyclerView);

        verify(recyclerView).checkIfEmpty();
        verify(recyclerView).showRecycler();
    }

    @Test
    public void testHandlesAdapterChanges() throws Exception {
        TestAdapter adapter = spy(new TestAdapter(new ArrayList<>()));
        EmptyRecyclerView recyclerView = mock(EmptyRecyclerView.class);

        setupAdapter(adapter, recyclerView);
        setupRecycler(recyclerView, adapter);

        adapter.attachToRecyclerView(recyclerView);

        verify(recyclerView).checkIfEmpty();
        verify(recyclerView).showEmptyView();

        reset(recyclerView);
        setupRecycler(recyclerView, adapter);

        adapter.setNewValues(values());
        verify(recyclerView).checkIfEmpty();
        verify(recyclerView).showRecycler();
    }

    private void setupEmptyView(@NonNull EmptyRecyclerView recyclerView, boolean isEmpty) {
        doCallRealMethod().when(recyclerView).showEmptyView();
        doCallRealMethod().when(recyclerView).showRecycler();

        View view = mock(View.class);
        //noinspection ResourceType
        doAnswer(new TestVisibilityAnswer(isEmpty ? View.VISIBLE : View.GONE)).when(view).setVisibility(anyInt());
        //noinspection ResourceType
        doAnswer(new TestVisibilityAnswer(isEmpty ? View.GONE : View.VISIBLE)).when(recyclerView).setVisibility(anyInt());
        recyclerView.setEmptyView(view);
    }

    private void setupRecycler(@NonNull EmptyRecyclerView recyclerView, @NonNull RecyclerView.Adapter adapter) {
        doCallRealMethod().when(recyclerView).checkIfEmpty();
        doNothing().when(recyclerView).showEmptyView();
        doNothing().when(recyclerView).showRecycler();
        doNothing().when(recyclerView).setAdapter(any(RecyclerView.Adapter.class));

        when(recyclerView.getAdapter()).thenReturn(adapter);
    }

    private void setupAdapter(TestAdapter adapter, final EmptyRecyclerView recyclerView) {
        doAnswer(invocationOnMock -> {
            recyclerView.checkIfEmpty();
            return new Object();
        }).when(adapter).refreshRecycler();
    }

    @NonNull
    private List<Integer> values() {
        List<Integer> values = new ArrayList<>();
        values.add(5);
        values.add(10);
        values.add(15);
        values.add(20);
        return values;
    }

    private class TestVisibilityAnswer implements Answer<Void> {

        private final int mVisibility;

        public TestVisibilityAnswer(int visibility) {
            mVisibility = visibility;
        }

        @Override
        public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
            int visibility = (int) invocationOnMock.getArguments()[0];
            assertEquals(mVisibility, visibility);
            return null;
        }
    }

    public class TestAdapter extends BaseAdapter<TestHolder, Integer> {

        public TestAdapter(@NonNull List<Integer> items) {
            super(items);
        }

        @Override
        public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TestHolder(parent);
        }

        @Override
        public void onBindViewHolder(TestHolder holder, int position) {
            super.onBindViewHolder(holder, position);
        }
    }

    public class TestHolder extends RecyclerView.ViewHolder {

        public TestHolder(View itemView) {
            super(itemView);
        }

    }

}
