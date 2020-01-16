package de.hdmstuttgart.bildbearbeiter.views;

import androidx.fragment.app.Fragment;

/**
 * A FragmentFactory which takes an index an creates a corresponding {@link Fragment}.
 */
public class FragmentFactory {

    public Fragment getFragment(int index) {
        switch (index) {
            case MainActivity.CAMERA_PAGE:
                return new CameraFragment();
            case MainActivity.SEARCH_PAGE:
                return new SearchFragment();
            case MainActivity.LIBRARY_PAGE:
            default:
                return new ImageLibraryFragment();
        }
    }
}