package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;

/**
 * The interface Bitmap filter.
 */
public interface IBitmapFilter {
    /**
     * This method is supposed to apply a Filter onto the entire Bitmap.
     * @return the bitmap after applying it with a filter
     */
    Bitmap applyFilter();

    /**
     * Gets the name of the filter
     * @return the name
     */
    String getName();
}
