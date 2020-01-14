package de.hdmstuttgart.bildbearbeiter.ui.main;

import androidx.fragment.app.Fragment;

import de.hdmstuttgart.bildbearbeiter.MainActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentFactory {

    public Fragment getFragment(int index) {
        switch (index)
        {
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