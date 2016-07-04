package ru.itis.lectures.githubmvp.view;

/**
 * @author Artur Vasilov
 */
public interface LogInView extends LoadingView {

    void openRepositoriesScreen();

    void showLoginError();

    void showPasswordError();

}
