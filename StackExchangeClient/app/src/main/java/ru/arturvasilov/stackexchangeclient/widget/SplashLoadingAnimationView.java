package ru.arturvasilov.stackexchangeclient.widget;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import ru.arturvasilov.stackexchangeclient.R;

/**
 * @author Artur Vasilov
 */
public class SplashLoadingAnimationView extends FrameLayout {

    private View mOuterCircle;
    private View mMiddleCircle;
    private View mInnerCircle;

    private Animator mAnimator;

    public SplashLoadingAnimationView(Context context) {
        super(context);
        init();
    }

    public SplashLoadingAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SplashLoadingAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    private void init() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View v = layoutInflater.inflate(R.layout.splash_loading_animation_view, this, true);

        mOuterCircle = v.findViewById(R.id.outer_circle);
        mMiddleCircle = v.findViewById(R.id.middle_circle);
        mInnerCircle = v.findViewById(R.id.inner_circle);
    }

    public void startAnimation() {
        mAnimator = SplashLoadingAnimationCreator.createCirclesAnimator(mOuterCircle, mMiddleCircle, mInnerCircle);
        mAnimator.start();
    }

    public void stopAnimation() {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
    }
}
