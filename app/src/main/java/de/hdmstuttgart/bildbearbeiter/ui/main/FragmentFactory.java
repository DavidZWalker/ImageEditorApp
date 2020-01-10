package de.hdmstuttgart.bildbearbeiter.ui.main;

import androidx.fragment.app.Fragment;

import de.hdmstuttgart.bildbearbeiter.utilities.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentFactory {

    public Fragment getFragment(int index) {
        switch (index)
        {
            case Constants.CAMERA_PAGE:
                return new CameraFragment();
            case Constants.SEARCH_PAGE:
                return new SearchFragment();
            case Constants.LIBRARY_PAGE:
            default:
                return new LibraryFragment();
        }
    }
}