package ru.arturvasilov.stackexchangeclient.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * @author Artur Vasilov
 */
final class SplashLoadingAnimationCreator {

    private static final float START_ALPHA = 0.35f;
    private static final float END_ALPHA = 0.1f;

    private static final float START_SCALE = 0.8f;
    private static final float END_SCALE = 1.0f;

    private static final long FULL_EXPANSION_DURATION = 1000;
    private static final long FULL_CONSTRICTION_DURATION = 1000;

    private static final long FIRST_CIRCLE_TIME = 500;
    private static final long SECOND_CIRCLE_TIME = 700;
    private static final long THIRD_CIRCLE_TIME = 900;

    @NonNull
    public static Animator createCirclesAnimator(@NonNull View outer, @NonNull View middle, @NonNull View inner) {
        Animator fullExpansionAnimator = fullExpansionAnimator(outer, middle, inner);
        Animator fullConstrictionAnimator = fullConstrictionAnimator(outer, middle, inner);

        AnimatorSet animator = new AnimatorSet();
        animator.playSequentially(fullExpansionAnimator, fullConstrictionAnimator);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                outer.setVisibility(View.VISIBLE);
                middle.setVisibility(View.VISIBLE);
                inner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animation.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                outer.setVisibility(View.GONE);
                middle.setVisibility(View.GONE);
                inner.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Do nothing
            }
        });

        return animator;
    }

    @NonNull
    private static Animator fullExpansionAnimator(@NonNull View outer, @NonNull View middle, @NonNull View inner) {
        Animator expansionAnimator1 = createExpansionAnimator(outer, FIRST_CIRCLE_TIME);
        Animator expansionAnimator2 = createExpansionAnimator(middle, SECOND_CIRCLE_TIME);
        Animator expansionAnimator3 = createExpansionAnimator(inner, THIRD_CIRCLE_TIME);
        Animator time = createTimeAnimator(FULL_EXPANSION_DURATION);

        AnimatorSet fullExpansionAnimator = new AnimatorSet();
        fullExpansionAnimator.playTogether(expansionAnimator1, expansionAnimator2, expansionAnimator3, time);

        return fullExpansionAnimator;
    }

    @NonNull
    private static Animator fullConstrictionAnimator(@NonNull View outer, @NonNull View middle, @NonNull View inner) {
        Animator animOuter = createConstrictionAnimator(outer, FIRST_CIRCLE_TIME);
        Animator animMiddle = createConstrictionAnimator(middle, SECOND_CIRCLE_TIME);
        Animator animInner = createConstrictionAnimator(inner, THIRD_CIRCLE_TIME);
        Animator time = createTimeAnimator(FULL_CONSTRICTION_DURATION);

        AnimatorSet fullConstrictionAnimator = new AnimatorSet();
        fullConstrictionAnimator.playTogether(animOuter, animMiddle, animInner, time);

        return fullConstrictionAnimator;
    }

    @NonNull
    private static Animator createExpansionAnimator(@NonNull View view, long duration) {
        Animator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, START_ALPHA, END_ALPHA);
        alphaAnimator.setInterpolator(new LinearInterpolator());

        Animator scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, START_SCALE, END_SCALE);
        scaleXAnimator.setInterpolator(new DecelerateInterpolator());

        Animator scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, START_SCALE, END_SCALE);
        scaleYAnimator.setInterpolator(new DecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(duration);

        return animatorSet;
    }

    @NonNull
    private static Animator createConstrictionAnimator(@NonNull View view, long duration) {
        Animator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, END_ALPHA, START_ALPHA);
        alphaAnimator.setInterpolator(new LinearInterpolator());

        Animator scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, END_SCALE, START_SCALE);
        scaleXAnimator.setInterpolator(new DecelerateInterpolator());

        Animator scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, END_SCALE, START_SCALE);
        scaleYAnimator.setInterpolator(new DecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(duration);

        return animatorSet;
    }

    @NonNull
    private static Animator createTimeAnimator(long duration) {
        Animator timeAnimator = ObjectAnimator.ofInt(1);
        timeAnimator.setDuration(duration);
        return timeAnimator;
    }
}
