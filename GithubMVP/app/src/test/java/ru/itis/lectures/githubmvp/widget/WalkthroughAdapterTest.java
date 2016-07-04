package ru.itis.lectures.githubmvp.widget;

import android.app.Fragment;
import android.app.FragmentManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import ru.itis.lectures.githubmvp.content.Benefit;
import ru.itis.lectures.githubmvp.fragment.WalkthroughBenefitFragment;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * @author Artur Vasilov
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(WalkthroughBenefitFragment.class)
public class WalkthroughAdapterTest {

    @Before
    public void setUp() throws Exception {
        mockStatic(WalkthroughBenefitFragment.class);
        when(WalkthroughBenefitFragment.create(any(Benefit.class))).then(invocation -> new WalkthroughBenefitFragment());
    }

    @Test
    public void testItemsCount() throws Exception {
        FragmentManager manager = Mockito.mock(FragmentManager.class);
        List<Benefit> benefits = new ArrayList<Benefit>() {
            {
                add(Benefit.PUBLISH_SOURCE);
                add(Benefit.CODE_HISTORY);
            }
        };

        WalkthroughAdapter adapter = new WalkthroughAdapter(manager, benefits);
        assertEquals(2, adapter.getCount());

        benefits.add(Benefit.WORK_TOGETHER);
        benefits.add(Benefit.CODE_HISTORY);
        benefits.add(Benefit.WORK_TOGETHER);
        adapter = new WalkthroughAdapter(manager, benefits);
        assertEquals(5, adapter.getCount());
    }

    @Test
    public void testGetItem() throws Exception {
        FragmentManager manager = Mockito.mock(FragmentManager.class);
        List benefits = mock(List.class);
        when(benefits.size()).thenReturn(3);
        when(benefits.get(0)).thenReturn(Benefit.WORK_TOGETHER);
        when(benefits.get(1)).thenReturn(Benefit.CODE_HISTORY);
        when(benefits.get(2)).thenReturn(Benefit.PUBLISH_SOURCE);

        //noinspection unchecked
        WalkthroughAdapter adapter = new WalkthroughAdapter(manager, benefits);
        Fragment fragment = adapter.getItem(2);
        assertNotNull(fragment);
        verify(benefits).get(2);

        fragment = adapter.getItem(0);
        assertNotNull(fragment);
        verify(benefits).get(0);

        fragment = adapter.getItem(1);
        assertNotNull(fragment);
        verify(benefits).get(1);
    }
}
