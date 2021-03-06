package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.stream.IntStream;

/**
 * The type Red bitmap filter.
 */
public class RedBitmapFilter extends BitmapFilterBase {

    /**
     * Instantiates a new Red bitmap filter. The Red filter boosts the red value of every filter and sets 0 to the other two.
     * @param sourceBitmap the source bitmap
     */
    public RedBitmapFilter(Bitmap sourceBitmap) {
        super(sourceBitmap);
    }

    @Override
    public Bitmap applyFilter() {
        IntStream.range(0, sourceBitmap.getWidth())
                .parallel()
                .forEach(x -> IntStream.range(0, sourceBitmap.getHeight())
                        .parallel()
                        .forEach(y -> {
                            int p = sourceBitmap.getPixel(x, y);
                            int r = Color.red(p) + 150;
                            int g = 0;
                            int b = 0;
                            resultBitmap.setPixel(x, y, Color.argb(Color.alpha(p), r, g, b));
                        })
                );

        return resultBitmap;
    }

    @Override
    public String getName() {
        return "Red Channel";
    }
}
