package ru.itis.lectures.githubmvp.view;

/**
 * @author Artur Vasilov
 */
public interface WalkthroughView {

    void showBenefit(int index, boolean isLastBenefit);

    void finishWalkthrough();

    void startLogInActivity();

}
