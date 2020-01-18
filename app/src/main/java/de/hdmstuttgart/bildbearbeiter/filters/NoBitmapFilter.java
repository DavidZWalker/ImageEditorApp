package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;

/**
 * The type No bitmap filter it will not transform the Bitmap into anything.
 */
public class NoBitmapFilter extends BitmapFilterBase {

    /**
     * Instantiates a new No bitmap filter.
     * @param sourceBitmap the source bitmap
     */
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
