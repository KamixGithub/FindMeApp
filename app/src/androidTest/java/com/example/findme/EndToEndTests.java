package com.example.findme;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.findme.view.CompassView;
import com.example.findme.view.FriendlistView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EndToEndTests {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testAddFriendDisplayed() {
        onView(withId((R.id.buttonFriend))).perform(click());
        onView(withId(R.id.buttonShowAddFriend)).perform(click());
        onView(withId(R.id.etName)).check((matches((isDisplayed()))));
        onView(withId(R.id.etFriendCode)).check((matches((isDisplayed()))));
    }
    @Test
    public void testFriendlistDisplayed(){
        onView(withId((R.id.buttonFriend))).perform(click());
        onView(withId(R.id.linearLayoutFriends)).check(matches(isDisplayed()));
    }

    @Test
    public void testCompassViewDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.arrowIMG)).check(ViewAssertions.matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.focus)).check(ViewAssertions.matches(isDisplayed()));
    }
    @Test
    public void testCompassRotation() {
        activityScenarioRule.getScenario().onActivity( activity -> {
            activity.getCompassView().setArrowIMGRotation(45f);
            assertEquals(45f, activity.getCompassView().getArrowIMGRotation(), 0.01f);
        });
    }
    @Test
    public void testFocusText() {

        onView(ViewMatchers.withId(R.id.focus)).check(matches(withText("No User Selected")));

    }
    @Test
    public void testInitialFragmentIsCompassView() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            Fragment currentFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            assertThat(currentFragment.getClass(), equalTo(CompassView.class));
        });
    }

    @Test
    public void testFragmentReplacement() {

        activityScenarioRule.getScenario().onActivity(activity -> {
            Fragment currentFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            assertThat(currentFragment.getClass(), equalTo(CompassView.class));
        });

        onView(withId((R.id.buttonFriend))).perform(click());

        activityScenarioRule.getScenario().onActivity(activity -> {
            Fragment currentFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            assertThat(currentFragment.getClass(), equalTo(FriendlistView.class));
        });
        onView(withId((R.id.buttonFriend))).perform(click());
        activityScenarioRule.getScenario().onActivity(activity -> {
            Fragment currentFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            assertThat(currentFragment.getClass(), equalTo(CompassView.class));
        });
    }

    @Test
    public void testAddFriend() {
        String name ="Steffan";
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.getStorageController().clearSave();
        });
        onView(withId((R.id.buttonFriend))).perform(click());
        onView(withId(R.id.buttonShowAddFriend)).perform(click());
        onView(withId(R.id.etName)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.etFriendCode)).perform(typeText("12233"), closeSoftKeyboard());
        onView(withId(R.id.buttonAddFriend)).perform(click());


        onView(allOf(withId(R.id.linearLayoutFriends), isDisplayed()))
                .check(matches(hasDescendant(allOf(withText(name), isAssignableFrom(Button.class)))));

    }


}
