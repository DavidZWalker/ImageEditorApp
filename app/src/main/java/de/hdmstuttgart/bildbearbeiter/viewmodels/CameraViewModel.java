package de.hdmstuttgart.bildbearbeiter.viewmodels;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import de.hdmstuttgart.bildbearbeiter.models.Camera;

/**
 * The type Camera view model.
 */
public class CameraViewModel extends ViewModel {

    private Camera model;

    /**
     * Instantiates a new Camera view model.
     * @param appFilesDir the app files directory
     */
    public CameraViewModel(File appFilesDir) {
        model = new Camera(appFilesDir);
    }

    /**
     * Saves the specified image to library
     * @param imageToSave the image to save
     * @return true if saving is successful
     */
    public boolean saveImageToLibrary(Bitmap imageToSave) {
        try {
            Random r = new Random();
            model.saveImageToLibrary(imageToSave, "captured_" + r.nextInt());
            return true;
        } catch (IOException e) {
            Log.e("Error", "Failed to save image to library", e);
            return false;
        }
    }

    /**
     * Creates a File for the captured image.
     * @return the created file
     */
    public File createCapturedImageFile() {
        return model.createCapturedImageFile();
    }

    /**
     * Gets captured bitmap taken by the camera.
     * @return the captured bitmap
     */
    public Bitmap getCapturedBitmap() {
        try {
            return model.getCapturedBitmap();
        } catch (IOException e) {
            Log.e("Error", "Failed to get captured bitmap", e);
            return null;
        }
    }

    /**
     * Sets image URI
     * @param imageUri the image URI
     */
    public void setImageUri(Uri imageUri) {
        model.setImageUri(imageUri);
    }

    /**
     * Gets image URI.
     * @return the image URI
     */
    public Uri getImageUri() {
        return model.getImageUri();
    }
}
