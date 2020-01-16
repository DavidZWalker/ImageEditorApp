package de.hdmstuttgart.bildbearbeiter;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.hdmstuttgart.bildbearbeiter.views.ImageEditorActivity;
import de.hdmstuttgart.bildbearbeiter.views.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LibraryFragmentTests {
    @Rule
    public IntentsTestRule<MainActivity> activityActivityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void init(){
        activityActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void lib_test_saveImage() throws InterruptedException {
        doSearch("Test");
        Thread.sleep(2000);
        onView(withId(R.id.recyclerSearch))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.btn_saveImage)).perform(click());
        Thread.sleep(1000);
        Espresso.pressBack();
        intended(hasComponent(ImageEditorActivity.class.getName()));
    }

    @Test
    public void lib_clickImage_startsActivity() {
        onView(withId(R.id.imageRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(ImageEditorActivity.class.getName()));
    }

    @Test
    public void lib_longClickImage_bottomSheetDisplayed() {
        onView(withId(R.id.imageRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));
        onView(withId(R.id.fragment_bottom_sheet)).check(matches(isDisplayed()));
    }

    @Test
    public void lib_clickEditBottomSheet_opensActivity() {
        onView(withId(R.id.imageRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));
        onView(withId(R.id.llEdit)).perform(click());
    }

    @Test
    public void lib_clickSaveBottomSheet_closesBottomSheet() {
        onView(withId(R.id.imageRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));
        onView(withId(R.id.llSave)).perform(click());
        onView(withId(R.id.fragment_bottom_sheet)).check(doesNotExist());
    }

    @Test
    public void lib_clickDeleteBottomSheet_closesBottomSheet() {
        onView(withId(R.id.imageRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));
        onView(withId(R.id.llDelete)).perform(click());
        onView(withId(R.id.fragment_bottom_sheet)).check(doesNotExist());
    }

    @Test
    public void lib_clickBackBottomSheet_closesBottomSheet() {
        onView(withId(R.id.imageRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));
        Espresso.pressBack();
        onView(withId(R.id.fragment_bottom_sheet)).check(doesNotExist());
    }

    private void doSearch(String query) {
        onView(withId(R.id.view_pager)).perform(swipeLeft());
        onView(withId(R.id.editTextSearchPictures)).perform(typeText(query));
        onView(withId(R.id.buttonSearchPictures)).perform(click());
    }
}
