package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;

public class NoBitmapFilter extends BitmapFilterBase {

    public NoBitmapFilter(Bitmap sourceBitmap) {
        super(sourceBitmap);
    }

    @Override
    public Bitmap applyFilter() {
        return sourceBitmap;
    }

    @Override
    public String getName() {
        return "Original";
    }
}
