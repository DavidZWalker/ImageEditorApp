package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

public class SepiaBitmapFilter extends BitmapFilterBase {

    public SepiaBitmapFilter(Bitmap sourceBitmap) {
        super(sourceBitmap);
    }

    @Override
    public Bitmap applyFilter() {
        int depth = 20;
        int r, g, b;
        for(int x = 0; x < sourceBitmap.getWidth(); x++) {
            for(int y = 0; y < sourceBitmap.getHeight(); y++) {
                int c = sourceBitmap.getPixel(x, y);

                r = Color.red(c);
                g = Color.green(c);
                b = Color.blue(c);

                int gry = (r + g + b) / 3;
                r = g = b = gry;

                r = r + (depth * 2);
                g = g + depth;

                if(r > 255) {
                    r = 255;
                }
                if(g > 255) {
                    g = 255;
                }
                resultBitmap.setPixel(x, y, Color.rgb(r, g, b));
            }
        }

        return resultBitmap;
    }

    @Override
    public String getName() {
        return "Sepia";
    }
}
