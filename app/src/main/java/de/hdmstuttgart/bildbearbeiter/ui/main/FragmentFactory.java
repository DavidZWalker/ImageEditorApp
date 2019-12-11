package de.hdmstuttgart.bildbearbeiter.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import de.hdmstuttgart.bildbearbeiter.R;
import utilities.Constants;

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