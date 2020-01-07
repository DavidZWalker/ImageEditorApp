package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

public class BrightBitmapFilter extends BitmapFilterBase {

    public BrightBitmapFilter(Bitmap sourceBitmap) {
        super(sourceBitmap);
    }

    @Override
    public Bitmap applyFilter() {
        for(int i = 0; i < sourceBitmap.getWidth(); i++){
            for(int j = 0; j < sourceBitmap.getHeight(); j++){
                int p = sourceBitmap.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r = 100  +  r;
                g = 100  + g;
                b = 100  + b;
                alpha = 100 + alpha;
                resultBitmap.setPixel(i, j, Color.argb(alpha, r, g, b));
            }
        }

        return resultBitmap;
    }

    @Override
    public String getName() {
        return "Bright";
    }
}
