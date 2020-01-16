package de.hdmstuttgart.bildbearbeiter.views;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import de.hdmstuttgart.bildbearbeiter.R;
import de.hdmstuttgart.bildbearbeiter.adapters.SectionsPagerAdapter;
import de.hdmstuttgart.bildbearbeiter.utilities.UIUtil;

/**
 * The MainActivity, the App starts here.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The constant CAMERA_PAGE contains the internal index for the camera page.
     */
    public static final int CAMERA_PAGE = 1;
    /**
     * The constant LIBRARY_PAGE contains the internal index for the library page.
     */
    public static final int LIBRARY_PAGE = 2;
    /**
     * The constant SEARCH_PAGE contains the internal index for the search page.
     */
    public static final int SEARCH_PAGE = 3;
    private static View activityRootView;

    /*
     * Creates a SectionsPagerAdapter which returns a frament and loads them into the tab layout.
     * If a new page is selected, the SectionsPagerAdapter handles the action of showing a new page.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityRootView = findViewById(R.id.activityRoot);

        ViewPager viewPager = findViewById(R.id.view_pager);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, viewPager, getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        viewPager.post(() -> viewPager.setCurrentItem(LIBRARY_PAGE - 1));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                sectionsPagerAdapter.onNewTabSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * Shows a short snackbar.
     *
     * @param message the message
     */
    public static void showSnackbar(String message) {
        UIUtil.showShortSnackbar(activityRootView, message);
    }
}