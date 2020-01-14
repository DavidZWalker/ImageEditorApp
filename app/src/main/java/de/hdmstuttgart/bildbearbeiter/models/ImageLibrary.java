package de.hdmstuttgart.bildbearbeiter.models;

import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import de.hdmstuttgart.bildbearbeiter.utilities.ImageFileHandler;

public class ImageLibrary {
    private ImageFileHandler imageFileHandler;
    private HashMap<File, Bitmap> loadedFiles = new HashMap<>();

    public ImageLibrary(File appFilesDir) {
        imageFileHandler = new ImageFileHandler(appFilesDir, ImageFileHandler.IMAGE_DIR_LIB);
    }

    public Bitmap loadImageFile(File file) throws IOException {
        Bitmap bmp = imageFileHandler.getImage(file.getName());
        loadedFiles.put(file, bmp);
        return bmp;
    }

    public List<File> getUnloadedImageFiles() {
        List<File> allFiles = new ArrayList<>(Arrays.asList(imageFileHandler.getImageFolder().listFiles()));
        return allFiles.stream()
                .filter(x -> !loadedFiles.keySet().contains(x))
                .collect(Collectors.toList());
    }

    public void removeImage(Bitmap imageToRemove) {
        for (File f : loadedFiles.keySet())
            if (loadedFiles.get(f).equals(imageToRemove)) {
                loadedFiles.remove(f);
                imageFileHandler.deleteFile(f);
            }
    }
}
