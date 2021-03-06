package de.hdmstuttgart.bildbearbeiter.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import de.hdmstuttgart.bildbearbeiter.R;
import de.hdmstuttgart.bildbearbeiter.views.CameraFragment;
import de.hdmstuttgart.bildbearbeiter.views.FragmentFactory;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;
    private ViewPager viewPager;
    private FragmentFactory fragmentFactory;
    private List<Fragment> loadedFragments;

    /**
     * @param context  context of the activity which wants to switch to the page
     * @param viewPager Generated code which allows swiping to switch between fragments.
     * @param fm the fragment manager
     */
    public SectionsPagerAdapter(Context context, ViewPager viewPager, FragmentManager fm) {
        super(fm);
        mContext = context;
        this.viewPager = viewPager;
        fragmentFactory = new FragmentFactory();
        loadedFragments = new ArrayList<>();
    }

    /*
     * getItem is called to instantiate the fragment for the given page.
     */
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragmentFactory.getFragment(position + 1);
        if (fragment instanceof CameraFragment) {
            CameraFragment cameraFragment = (CameraFragment) fragment;
            cameraFragment.setViewPager(viewPager);
        }
        loadedFragments.add(fragment);
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }

    /*
     * If the CameraFragment is requested, we immediately start the takePhoto() method which reacts when the user takes a photo through the android camera.
     */
    public void onNewTabSelected(int index) {
        Fragment fragment = loadedFragments.get(index);
        if (fragment instanceof CameraFragment) {
            CameraFragment cameraFragment = (CameraFragment) fragment;
            cameraFragment.takePhoto();
        }
    }
}