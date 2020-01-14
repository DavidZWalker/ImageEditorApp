package de.hdmstuttgart.bildbearbeiter.ui.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.lifecycle.ViewModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdmstuttgart.bildbearbeiter.ImageEditor;
import de.hdmstuttgart.bildbearbeiter.filters.IBitmapFilter;

public class ImageEditorViewModel extends ViewModel {

    private ImageEditor model;

    public ImageEditorViewModel(File appFilesDir) {
        super();
        model = new ImageEditor(appFilesDir);
    }

    public List<IBitmapFilter> getAvailableFilters()
    {
        return model.getAvailableFilters();
    }

    public Bitmap getSourceImage()
    {
        try {
            return model.getSourceImage();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean saveImageToLibrary(Bitmap bitmapToSave) {
        try {
            model.saveImageToLibrary(bitmapToSave);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Bitmap createTempBlackBitmap()
    {
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
            e.printStackTrace();
            return null;
        }
    }
}
