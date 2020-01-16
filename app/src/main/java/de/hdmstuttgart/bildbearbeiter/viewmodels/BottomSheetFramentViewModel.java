package de.hdmstuttgart.bildbearbeiter.viewmodels;

import androidx.lifecycle.ViewModel;

import java.io.File;

import de.hdmstuttgart.bildbearbeiter.models.BottomSheet;

/**
 * The Model for the Bottom Sheet
 */
public class BottomSheetFramentViewModel extends ViewModel {
    private BottomSheet model;

    /**
     * Instantiates a new BottomSheet model.
     *
     * @param file the file
     */
    public BottomSheetFramentViewModel(File file) {
        super();
        model = new BottomSheet(file);

    }
}
