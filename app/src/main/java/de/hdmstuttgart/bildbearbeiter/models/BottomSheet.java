package de.hdmstuttgart.bildbearbeiter.models;

import java.io.File;

import de.hdmstuttgart.bildbearbeiter.utilities.ImageFileHandler;

/**
 * Model for BottomSheet, it gives the Image to an {@link ImageFileHandler}
 */
public class BottomSheet {
    private ImageFileHandler imageFileHandler;
    private File imageToSave;


    /**
     * Instantiates a new Bottom sheet and gives the parameter to an {@link ImageFileHandler}
     *
     * @param file the file
     */
    public BottomSheet(File file) {
        super();
        imageFileHandler = new ImageFileHandler(file, null);
    }
}
