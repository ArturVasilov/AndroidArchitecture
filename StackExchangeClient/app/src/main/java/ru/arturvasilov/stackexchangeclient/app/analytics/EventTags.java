package ru.arturvasilov.stackexchangeclient.app.analytics;

/**
 * @author Artur Vasilov
 */
public interface EventTags {

    String APP_STARTED = "app_started";

    String SCREEN_AUTH = "screen_auth";
    String AUTH_BUTTON_CLICKED = "auth_button_clicked";
    String SUCCESS_AUTH = "success_auth";

    String SCREEN_WALKTHROUGH = "screen_walkthrough";
    String WALKTHROUGH_BUTTON_CLICK = "walkthrough_button_click";
    String WALKTHROUGH_SWIPE = "walkthrough_button_click";
    String WALKTHROUGH_SELECTED_BENEFIT = "walkthrough_selected_benefit";
    String WALKTHROUGH_SPLASH = "walkthrough_error";
    String WALKTHROUGH_ERROR = "walkthrough_splash";

    String SCREEN_MAIN = "screen_main";
    String MAIN_TABS = "main_tabs";
    String MAIN_DRAWER_ITEM_SELECTED = "main_drawer_item_selected";

    String SCREEN_PROFILE = "screen_profile";
    String PROFILE_BADGE_CLICKED = "profile_badge_clicked";

    String SCREEN_ANSWERS = "screen_answers";
    String ANSWER_CLICKED = "answer_clicked";

    String SCREEN_TAGS = "screen_tags";
    String TAGS_ADD_FAVOURITE = "tags_add_favourite";
    String TAGS_REMOVE_FAVOURITE = "tags_remove_favourite";

    String SCREEN_QUESTION = "screen_question";

    String PUSH_NOTIFICATION = "push_notification";
}
