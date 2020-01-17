package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.stream.IntStream;

/**
 * The type Blue bitmap filter.
 */
public class BlueBitmapFilter extends BitmapFilterBase {

    /**
     * Instantiates a new Blue bitmap filter. The blue filter boosts the blue value of every filter and sets 0 to the other two.
     *
     * @param sourceBitmap the source bitmap
     */
    public BlueBitmapFilter(Bitmap sourceBitmap) {
        super(sourceBitmap);
    }

    /*
     *Every blue pixel has its value boosted by 150. The Other Pixels are set to 0.
     */
    @Override
    public Bitmap applyFilter() {
        IntStream.range(0, sourceBitmap.getWidth())
                .parallel()
                .forEach(x -> IntStream.range(0, sourceBitmap.getHeight())
                        .parallel()
                        .forEach(y -> {
                            int p = sourceBitmap.getPixel(x, y);
                            int r = 0;
                            int g = 0;
                            int b = Color.blue(p) + 150;
                            resultBitmap.setPixel(x, y, Color.argb(Color.alpha(p), r, g, b));
                        })
                );

        return resultBitmap;
    }

    @Override
    public String getName() {
        return "Blue Channel";
    }
}
