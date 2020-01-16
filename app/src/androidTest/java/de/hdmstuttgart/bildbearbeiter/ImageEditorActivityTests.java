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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ImageEditorActivityTests {
    @Rule
    public IntentsTestRule<MainActivity> activityActivityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void init(){
        activityActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
        openImageEditor();
    }

    @Test
    public void imageEditor_open_isOpen() {
        intended(hasComponent(ImageEditorActivity.class.getName()));
    }

    @Test
    public void imageEditor_filters_correctAmount() {
        onView(withId(R.id.filterButtons)).check(matches(hasChildCount(7)));
    }

    private void openImageEditor() {
        onView(withId(R.id.imageRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }
}
