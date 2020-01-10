package de.hdmstuttgart.bildbearbeiter;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import de.hdmstuttgart.bildbearbeiter.ui.main.SectionsPagerAdapter;
import de.hdmstuttgart.bildbearbeiter.utilities.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, viewPager, getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        viewPager.post(() -> viewPager.setCurrentItem(Constants.LIBRARY_PAGE - 1));
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
}