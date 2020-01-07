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
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r =  0;
                g =  g + 150;
                b =  0;
                alpha = 0;
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
