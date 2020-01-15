package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;

public abstract class BitmapFilterBase implements IBitmapFilter {

    protected Bitmap resultBitmap;
    protected Bitmap sourceBitmap;

    public BitmapFilterBase(Bitmap sourceBitmap) {
        this.sourceBitmap = sourceBitmap;
        resultBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), sourceBitmap.getConfig());
    }

    @Override
    public abstract Bitmap applyFilter();

    @Override
    public abstract String getName();
}
