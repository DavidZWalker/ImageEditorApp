package de.hdmstuttgart.bildbearbeiter;

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
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented lib_test_saveImage, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SearchFragmentTests {
    @Rule
    public IntentsTestRule<MainActivity> activityActivityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void init(){
        activityActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void search_buttontext_beforesearch_correct() {
        onView(withId(R.id.view_pager)).perform(swipeLeft());
        onView(withId(R.id.buttonSearchPictures)).check(matches(withText(R.string.searchButtons)));
    }

    @Test
    public void search_buttontext_aftersearch_correct() {
        doSearch("Test");
        onView(withId(R.id.buttonSearchPictures)).check(matches(withText(R.string.searchingText)));
        onView(withId(R.id.progress_search)).check(matches(isDisplayed()));
    }

    @Test
    public void search_recyclerView_clickItem() throws InterruptedException {
        doSearch("Test");
        Thread.sleep(2000);
        onView(withId(R.id.recyclerSearch))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(ImageEditorActivity.class.getName()));
    }

    private void doSearch(String query) {
        onView(withId(R.id.view_pager)).perform(swipeLeft());
        onView(withId(R.id.editTextSearchPictures)).perform(typeText(query));
        onView(withId(R.id.buttonSearchPictures)).perform(click());
    }
}