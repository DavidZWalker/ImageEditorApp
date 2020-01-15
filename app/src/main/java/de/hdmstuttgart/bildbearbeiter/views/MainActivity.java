package de.hdmstuttgart.bildbearbeiter.views;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import de.hdmstuttgart.bildbearbeiter.R;
import de.hdmstuttgart.bildbearbeiter.adapters.SectionsPagerAdapter;
import de.hdmstuttgart.bildbearbeiter.utilities.UIUtil;

public class MainActivity extends AppCompatActivity {

    public static final int CAMERA_PAGE = 1;
    public static final int LIBRARY_PAGE = 2;
    public static final int SEARCH_PAGE = 3;
    private static View activityRootView;

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
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                sectionsPagerAdapter.onNewTabSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    public static void showSnackbar(String message) {
        UIUtil.showShortSnackbar(activityRootView, message);
    }
}