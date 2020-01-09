package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

public class GreenBitmapFilter extends BitmapFilterBase {

    public GreenBitmapFilter(Bitmap sourceBitmap) {
        super(sourceBitmap);
    }

    @Override
    public Bitmap applyFilter() {
        for(int i = 0; i < sourceBitmap.getWidth(); i++){
            for(int j=0; j < sourceBitmap.getHeight(); j++){
                int p = sourceBitmap.getPixel(i, j);
                int r = 0;
                int g = Color.green(p) + 150;
                int b = 0;
                resultBitmap.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }
        return resultBitmap;
    }

    @Override
    public String getName() {
        return "Green";
    }
}
