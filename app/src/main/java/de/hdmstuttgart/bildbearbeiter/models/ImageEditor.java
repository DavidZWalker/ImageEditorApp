package de.hdmstuttgart.bildbearbeiter.models;

import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdmstuttgart.bildbearbeiter.filters.BlackWhiteBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.filters.BlueBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.filters.GreenBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.filters.IBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.filters.NoBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.filters.RedBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.filters.SepiaBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.filters.VignetteBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.utilities.ImageFileHandler;

/**
 * The type Image editor it manages a list of different Filters available.
 */
public class ImageEditor {

    private Bitmap sourceImage;
    private List<IBitmapFilter> availableFilters;
    private File rootDir;

    /**
     * Instantiates a new Image editor and loads the Image from source.
     *
     * @param appFilesDir the app files directory
     */
    public ImageEditor(File appFilesDir) {
        rootDir = appFilesDir;
        availableFilters = new ArrayList<>();

        try {
            sourceImage = loadSourceImageFromFile();
        } catch (IOException e) {
            e.printStackTrace();
            sourceImage = null;
        }

        initAvailableFilters();
    }

    /**
     * Creates an {@link ImageFileHandler} and saves the image with a random name.
     *
     * @param imageToSave the image to save
     * @throws IOException the io exception if saving is unsuccessful
     */
    public void saveImageToLibrary(Bitmap imageToSave) throws IOException {
        ImageFileHandler ifh = new ImageFileHandler(rootDir, ImageFileHandler.IMAGE_DIR_LIB);
        Random r = new Random();
        ifh.saveImage(imageToSave, "filtered_" + r.nextInt());
    }

    /**
     * Returns the source image if available, if not loads it from the File.
     *
     * @return the source image
     * @throws IOException the io exception
     */
    public Bitmap getSourceImage() throws IOException {
        return sourceImage != null ? sourceImage : loadSourceImageFromFile();
    }

    /**
     * Contains all Filters which a currently available in the app.
     *
     * @return the available filters
     */
    public List<IBitmapFilter> getAvailableFilters() {
        return availableFilters;
    }

    private Bitmap loadSourceImageFromFile() throws IOException {
        ImageFileHandler ifh = new ImageFileHandler(rootDir, ImageFileHandler.IMAGE_DIR_TMP);
        return ifh.getImage("tmpImage");
    }

    private void initAvailableFilters() {
        // ADD NEW FILTERS HERE!!!
        availableFilters.add(new NoBitmapFilter(sourceImage));
        availableFilters.add(new BlackWhiteBitmapFilter(sourceImage));
        availableFilters.add(new SepiaBitmapFilter(sourceImage));
        availableFilters.add(new VignetteBitmapFilter(sourceImage));
        availableFilters.add(new RedBitmapFilter(sourceImage));
        availableFilters.add(new GreenBitmapFilter(sourceImage));
        availableFilters.add(new BlueBitmapFilter(sourceImage));
    }
}
