package de.hdmstuttgart.bildbearbeiter.ui.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.lifecycle.ViewModel;

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
import utilities.ImageFileHandler;

public class FullscreenImageViewModel extends ViewModel {
    private Bitmap sourceImage;
    private List<IBitmapFilter> availableFilters;
    private File rootDir;

    public FullscreenImageViewModel(File appFilesDir) {
        super();
        rootDir = appFilesDir;
        availableFilters = new ArrayList<>();
        sourceImage = loadSourceImageFromFile();
        initAvailableFilters();
    }

    public List<IBitmapFilter> getAvailableFilters()
    {
        return availableFilters;
    }

    public Bitmap getSourceImage()
    {
        if (sourceImage == null)
        {
            sourceImage = loadSourceImageFromFile();
        }

        return sourceImage;
    }

    public Bitmap getTempBlackBitmap()
    {
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(0F, 0F, (float) width, (float) height, paint);
        return bitmap;
    }

    public boolean saveImageToLibrary(Bitmap imageToSave)
    {
        ImageFileHandler ifh = new ImageFileHandler(rootDir, ImageFileHandler.IMAGE_DIR_LIB);
        Random r = new Random();
        return ifh.saveImage(imageToSave, "filtered_" + r.nextInt());
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
