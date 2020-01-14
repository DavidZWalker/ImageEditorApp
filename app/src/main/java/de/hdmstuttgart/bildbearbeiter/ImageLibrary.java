package de.hdmstuttgart.bildbearbeiter;

import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.hdmstuttgart.bildbearbeiter.utilities.ImageFileHandler;

public class ImageLibrary {
    private ImageFileHandler imageFileHandler;
    private List<File> loadedFiles = new ArrayList<>();

    public ImageLibrary(File appFilesDir) {
        imageFileHandler = new ImageFileHandler(appFilesDir, ImageFileHandler.IMAGE_DIR_LIB);
    }

    public List<File> getUnloadedImageFiles() {
        List<File> allFiles = new ArrayList<>(Arrays.asList(imageFileHandler.getImageFolder().listFiles()));
        return allFiles.stream()
                .filter(x -> !loadedFiles.contains(x))
                .collect(Collectors.toList());
    }

    public Bitmap getBitmapFromFile(File file) throws IOException {
        loadedFiles.add(file);
        return imageFileHandler.getImage(file.getName());
    }
}
