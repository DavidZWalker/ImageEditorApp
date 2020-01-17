package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;

/**
 * The type Bitmap filter base. It provides a constructor to be used by every filter
 */
public abstract class BitmapFilterBase implements IBitmapFilter {

    /**
     *  with the applied filter.
     */
    protected Bitmap resultBitmap;
    /**
     * The Source bitmap without a filter.
     */
    protected Bitmap sourceBitmap;

    /**
     * Instantiates a new Bitmap
     * creates a Bitmap ready to be edited
     *
     * @param sourceBitmap the source bitmap
     */
    public BitmapFilterBase(Bitmap sourceBitmap) {
        this.sourceBitmap = sourceBitmap;
        resultBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), sourceBitmap.getConfig());
    }

    @Override
    public abstract Bitmap applyFilter();

    @Override
    public abstract String getName();
}
