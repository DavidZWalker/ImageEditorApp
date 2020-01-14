package de.hdmstuttgart.bildbearbeiter;

import android.graphics.Bitmap;

import java.io.File;
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

public class ImageEditor {

    private Bitmap sourceImage;
    private List<IBitmapFilter> availableFilters;
    private File rootDir;

    public ImageEditor(File appFilesDir) {
        rootDir = appFilesDir;
        availableFilters = new ArrayList<>();
        sourceImage = loadSourceImageFromFile();
        initAvailableFilters();
    }

    public boolean saveImageToLibrary(Bitmap imageToSave)
    {
        ImageFileHandler ifh = new ImageFileHandler(rootDir, ImageFileHandler.IMAGE_DIR_LIB);
        Random r = new Random();
        return ifh.saveImage(imageToSave, "filtered_" + r.nextInt());
    }

    public Bitmap getSourceImage() {
        return sourceImage != null ? sourceImage : loadSourceImageFromFile();
    }

    public List<IBitmapFilter> getAvailableFilters() {
        return availableFilters;
    }

    private Bitmap loadSourceImageFromFile()
    {
        ImageFileHandler ifh = new ImageFileHandler(rootDir, ImageFileHandler.IMAGE_DIR_TMP);
        return ifh.getImage("tmpImage");
    }

    private void initAvailableFilters()
    {
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
