package de.hdmstuttgart.bildbearbeiter.viewmodels;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdmstuttgart.bildbearbeiter.filters.IBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.models.ImageEditor;

/**
 * The model for the image editor.
 */
public class ImageEditorViewModel extends ViewModel {

    private ImageEditor model;

    /**
     * Instantiates a new Image editor view model.
     *
     * @param appFilesDir the app files directory
     */
    public ImageEditorViewModel(File appFilesDir) {
        super();
        model = new ImageEditor(appFilesDir);
    }

    /**
     * List of all currently available Filters
     *
     * @return the available filters
     */
    public List<IBitmapFilter> getAvailableFilters() {
        return model.getAvailableFilters();
    }

    /**
     * Loads image from source.
     *
     * @return the source image
     */
    public Bitmap getSourceImage() {
        try {
            return model.getSourceImage();
        } catch (IOException e) {
            Log.e("Error", "Failed to get source image.", e);
            return null;
        }
    }

    /**
     * Saves image to the in-app library.
     *
     * @param bitmapToSave the bitmap to save
     * @return the boolean
     */
    public boolean saveImageToLibrary(Bitmap bitmapToSave) {
        try {
            model.saveImageToLibrary(bitmapToSave);
            return true;
        } catch (IOException e) {
            Log.e("Error", "Failed to save image to library", e);
            return false;
        }
    }

    /**
     * Creates a temporary BlackBitmap which is displayed during the process of applying a filter to a Bitmap.
     *
     * @return temporary BlackBitmap
     */
    public Bitmap createTempBlackBitmap() {
        try {
            int width = model.getSourceImage().getWidth();
            int height = model.getSourceImage().getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            canvas.drawRect(0F, 0F, (float) width, (float) height, paint);
            return bitmap;
        } catch (IOException e) {
            Log.e("Error", "Failed to create temp bitmap", e);
            return null;
        }
    }
}
