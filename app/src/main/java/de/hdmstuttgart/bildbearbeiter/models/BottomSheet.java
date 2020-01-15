package de.hdmstuttgart.bildbearbeiter.models;

import java.io.File;

import de.hdmstuttgart.bildbearbeiter.utilities.ImageFileHandler;

public class BottomSheet {
    private ImageFileHandler imageFileHandler;
    private File imageToSave;

    public BottomSheet(File file) {
        super();
        imageFileHandler = new ImageFileHandler(file, null);
    }
}
