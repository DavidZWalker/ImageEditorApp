package de.hdmstuttgart.bildbearbeiter.viewmodels;

import androidx.lifecycle.ViewModel;

import java.io.File;

import de.hdmstuttgart.bildbearbeiter.models.BottomSheet;

public class BottomSheetFramentViewModel extends ViewModel {
    private BottomSheet model;

    public BottomSheetFramentViewModel(File file) {
        super();
        model = new BottomSheet(file);

    }
}
