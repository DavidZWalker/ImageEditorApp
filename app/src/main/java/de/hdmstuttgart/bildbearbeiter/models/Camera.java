package de.hdmstuttgart.bildbearbeiter.models;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import de.hdmstuttgart.bildbearbeiter.utilities.ImageFileHandler;

/**
 * The type Camera provides functionality for Camera Image handling.
 */
public class Camera {
    private Uri imageUri;
    private ImageFileHandler capturedImageFileHandler;
    private ImageFileHandler saveImageFileHandler;
    private String capturedImageTmpFileName = "capturedImage";

    /**
     * Instantiates a new Camera.
     *
     * @param appFilesDir the app files directory
     */
    public Camera(File appFilesDir) {
        capturedImageFileHandler = new ImageFileHandler(appFilesDir, ImageFileHandler.IMAGE_DIR_TMP);
        saveImageFileHandler = new ImageFileHandler(appFilesDir, ImageFileHandler.IMAGE_DIR_LIB);
    }

    /**
     * Saves image to the internal image library.
     *
     * @param imageToSave the image to save
     * @param fileName    the file name
     * @throws IOException if the image couldn't be saved.
     */
    public void saveImageToLibrary(Bitmap imageToSave, String fileName) throws IOException {
        Log.d("Camera", "Saving image to internal file: " + fileName);
        saveImageFileHandler.saveImage(imageToSave, fileName);
    }

    /**
     * Creates image file for the captured image.
     *
     * @return the file
     */
    public File createCapturedImageFile() {
        Log.d("Camera", "Creating a captured image file...");
        return capturedImageFileHandler.createFileWithName(capturedImageTmpFileName);
    }

    /**
     * Gets captured bitmap from {@link ImageFileHandler}
     *
     * @return the captured bitmap
     * @throws IOException if the picture couldn't be retrieved.
     */
    public Bitmap getCapturedBitmap() throws IOException {
        Log.d("Camera", "Retrieving the captured image...");
        return capturedImageFileHandler.getImage(capturedImageTmpFileName);
    }

    /**
     * Sets image uri.
     *
     * @param imageUri the image uri
     */
    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    /**
     * Gets image uri.
     *
     * @return the image uri
     */
    public Uri getImageUri() {
        return this.imageUri;
    }
}
