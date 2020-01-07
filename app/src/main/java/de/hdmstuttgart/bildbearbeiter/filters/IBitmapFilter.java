package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;

public interface IBitmapFilter {
    Bitmap applyFilter();
    String getName();
}
