package de.hdmstuttgart.bildbearbeiter.ui.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.lifecycle.ViewModel;

import java.io.File;
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
        return model.getSourceImage();
    }

    public boolean saveImageToLibrary(Bitmap bitmapToSave) {
        return model.saveImageToLibrary(bitmapToSave);
    }

    public Bitmap createTempBlackBitmap()
    {
        int width = model.getSourceImage().getWidth();
        int height = model.getSourceImage().getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(0F, 0F, (float) width, (float) height, paint);
        return bitmap;
    }
}
