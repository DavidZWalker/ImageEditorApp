package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

public class BlueBitmapFilter extends BitmapFilterBase {

    public BlueBitmapFilter(Bitmap sourceBitmap) {
        super(sourceBitmap);
    }

    @Override
    public Bitmap applyFilter() {
        for(int i=0; i<sourceBitmap.getWidth(); i++){
            for(int j=0; j<sourceBitmap.getHeight(); j++){
                int p = sourceBitmap.getPixel(i, j);
                int r = 0;
                int g = 0;
                int b = Color.blue(p) + 150;
                resultBitmap.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }

        return resultBitmap;
    }

    @Override
    public String getName() {
        return "Blue Channel";
    }
}
