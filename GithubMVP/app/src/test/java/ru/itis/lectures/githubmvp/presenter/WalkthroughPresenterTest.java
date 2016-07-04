package ru.itis.lectures.githubmvp.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ru.itis.lectures.githubmvp.content.Benefit;
import ru.itis.lectures.githubmvp.content.Settings;
import ru.itis.lectures.githubmvp.rx.RxUtils;
import ru.itis.lectures.githubmvp.testutils.prefs.SharedPreferencesMapImpl;
import ru.itis.lectures.githubmvp.view.WalkthroughView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class WalkthroughPresenterTest {

    private Context mContext;

    private WalkthroughView mView;
    private WalkthroughPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        SharedPreferences preferences = new SharedPreferencesMapImpl();
        mContext = RxUtils.rxContext();
        when(mContext.getSharedPreferences(anyString(), anyInt())).thenReturn(preferences);

        mView = mock(WalkthroughView.class);
        doNothing().when(mView).showBenefit(anyInt(), anyBoolean());
        doNothing().when(mView).finishWalkthrough();
        doNothing().when(mView).startLogInActivity();
        mPresenter = new WalkthroughPresenter(mContext, mView);
    }

    @Test
    public void testCheckWalkthroughWasShown() throws Exception {
        Settings.saveWalkthroughPassed(mContext);

        mPresenter.dispatchCreated();
        verify(mView).startLogInActivity();
    }

    @Test
    public void testCheckWalkthroughWasntShown() throws Exception {
        mPresenter.dispatchCreated();
        verifyNoMoreInteractions(mView);
    }

    @Test
    public void testBenefitsCount() throws Exception {
        assertEquals(3, mPresenter.getBenefits().size());
    }

    @Test
    public void testBenefits() throws Exception {
        assertEquals(Benefit.WORK_TOGETHER, mPresenter.getBenefits().get(0));
        assertEquals(Benefit.CODE_HISTORY, mPresenter.getBenefits().get(1));
        assertEquals(Benefit.PUBLISH_SOURCE, mPresenter.getBenefits().get(2));
    }

    @Test
    public void testButtonClickNext() throws Exception {
        mPresenter.onButtonClick();
        verify(mView).showBenefit(1, false);
    }

    @Test
    public void testButtonClickFinish() throws Exception {
        mPresenter.onButtonClick();
        mPresenter.onButtonClick();
        mPresenter.onButtonClick();

        assertTrue(Settings.isWalkthroughPassed(mContext));
        verify(mView).finishWalkthrough();
    }

    @Test
    public void testWalkthroughClicksScenario() throws Exception {
        mPresenter.onButtonClick();
        verify(mView).showBenefit(1, false);

        mPresenter.onButtonClick();
        verify(mView).showBenefit(2, true);

        mPresenter.onButtonClick();
        verify(mView).finishWalkthrough();
    }

    @Test
    public void testWalkthroughSwipeScenario() throws Exception {
        mPresenter.onBenefitSelected(1);
        verify(mView).showBenefit(1, false);

        mPresenter.onBenefitSelected(2);
        verify(mView).showBenefit(2, true);

        mPresenter.onButtonClick();
        verify(mView).finishWalkthrough();
    }

    @Test
    public void testWalkthroughManySwipesAndClicksScenario() throws Exception {
        mPresenter.onBenefitSelected(1);
        verify(mView).showBenefit(1, false);

        mPresenter.onBenefitSelected(0);
        verify(mView).showBenefit(0, false);

        mPresenter.onBenefitSelected(1);
        verify(mView, times(2)).showBenefit(1, false);

        mPresenter.onButtonClick();
        verify(mView).showBenefit(2, true);

        mPresenter.onBenefitSelected(1);
        verify(mView, times(3)).showBenefit(1, false);

        mPresenter.onButtonClick();
        verify(mView, times(2)).showBenefit(2, true);

        mPresenter.onButtonClick();
        verify(mView).finishWalkthrough();
    }

}
