package ru.itis.lectures.githubmvp.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class BaseAdapterTest {

    private List<Integer> mValues;

    @Before
    public void setUp() throws Exception {
        mValues = new ArrayList<>();
        mValues.add(5);
        mValues.add(10);
        mValues.add(15);
        mValues.add(20);
    }

    @Test
    public void testCount() throws Exception {
        BaseAdapter<TestHolder, Integer> adapter = new TestAdapter(mValues);
        assertEquals(4, adapter.getItemCount());
    }

    @Test
    public void testGet() throws Exception {
        BaseAdapter<TestHolder, Integer> adapter = new TestAdapter(mValues);
        assertEquals(15, adapter.getItem(2).intValue());
    }

    @Test
    public void testAttach() throws Exception {
        EmptyRecyclerView recyclerView = mock(EmptyRecyclerView.class);
        doNothing().when(recyclerView).setAdapter(any(RecyclerView.Adapter.class));

        BaseAdapter<TestHolder, Integer> adapter = spy(new TestAdapter(mValues));
        doNothing().when(adapter).refreshRecycler();
        adapter.attachToRecyclerView(recyclerView);

        verify(recyclerView).setAdapter(any(RecyclerView.Adapter.class));
    }

    @Test
    public void testAddItem() throws Exception {
        BaseAdapter<TestHolder, Integer> adapter = spy(new TestAdapter(mValues));
        doNothing().when(adapter).refreshRecycler();
        adapter.add(5);
        assertEquals(5, adapter.getItemCount());

        verify(adapter).refreshRecycler();
    }

    @Test
    public void testSetNewValues() throws Exception {
        BaseAdapter<TestHolder, Integer> adapter = spy(new TestAdapter(mValues));
        doNothing().when(adapter).refreshRecycler();

        List<Integer> values = new ArrayList<Integer>() {{
            add(10);
            add(17);
            add(19);
        }};

        adapter.setNewValues(values);

        assertEquals(3, adapter.getItemCount());
        verify(adapter).refreshRecycler();
    }

    @Test
    public void testClearItems() throws Exception {
        BaseAdapter<TestHolder, Integer> adapter = spy(new TestAdapter(mValues));
        doNothing().when(adapter).refreshRecycler();

        adapter.clear();

        assertEquals(0, adapter.getItemCount());
        verify(adapter).refreshRecycler();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnItemClickListener() throws Exception {
        BaseAdapter.OnItemClickListener listener = mock(BaseAdapter.OnItemClickListener.class);
        doNothing().when(listener).onItemClick(any());

        BaseAdapter<TestHolder, Integer> adapter = new TestAdapter(mValues);
        adapter.setOnItemClickListener(listener);

        View view = mock(View.class);
        doCallRealMethod().when(view).setTag(any());
        when(view.getTag()).thenReturn(2);

        doAnswer(invocation -> {
            View.OnClickListener onClickListener = (View.OnClickListener) invocation.getArguments()[0];
            onClickListener.onClick(view);
            verify(listener).onItemClick(15);
            return null;
        }).when(view).setOnClickListener(any(View.OnClickListener.class));
        doNothing().when(view).setTag(any());

        adapter.onBindViewHolder(new TestHolder(view), 2);
        verify(view).setTag(2);
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
