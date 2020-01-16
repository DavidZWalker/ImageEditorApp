package de.hdmstuttgart.bildbearbeiter.models;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import de.hdmstuttgart.bildbearbeiter.utilities.ImageFileHandler;

/**
 * The type Image library handles everything about the Files corresponding to the images.
 */
public class ImageLibrary {
    private ImageFileHandler imageFileHandler;
    private HashMap<File, Bitmap> loadedFiles = new HashMap<>();

    /**
     * Instantiates a new Image library. and creates a {@link ImageFileHandler}
     *
     * @param appFilesDir the app files dir
     */
    public ImageLibrary(File appFilesDir) {
        imageFileHandler = new ImageFileHandler(appFilesDir, ImageFileHandler.IMAGE_DIR_LIB);
    }

    /**
     * Loads a Bitmap from file and buts it in loadedFiles.
     *
     * @param file the file to be loaded
     * @return the loaded bitmap
     * @throws IOException if loading is unsuccessful.
     */
    public Bitmap loadImageFile(File file) throws IOException {
        Log.d("ImageLib", "Loading image from file: " + file.getPath());
        Bitmap bmp = imageFileHandler.getImage(file.getName());
        loadedFiles.put(file, bmp);
        return bmp;
    }

    /**
     * Gets all Images which are currently not loaded.
     *
     * @return the unloaded image files
     */
    public List<File> getUnloadedImageFiles() {
        Log.d("ImageLib", "Getting images which are not loaded...");
        List<File> allFiles = new ArrayList<>(Arrays.asList(imageFileHandler.getImageFolder().listFiles()));
        return allFiles.stream()
                .filter(x -> !loadedFiles.keySet().contains(x))
                .collect(Collectors.toList());
    }

    /**
     * Removes image from loadedFiles and deletes it permanently.
     *
     * @param imageToRemove the image to remove
     */
    public void removeImage(Bitmap imageToRemove) {
        Log.d("ImageLib", "Attempting to remove image from library...");
        for (File f : loadedFiles.keySet())
            if (loadedFiles.get(f).equals(imageToRemove)) {
                Log.d("ImageLib", "Found image to remove! Removing...");
                loadedFiles.remove(f);
                imageFileHandler.deleteFile(f);
                return;
            }
    }
}
