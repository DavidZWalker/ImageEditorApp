package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.stream.IntStream;

/**
 * The type Sepia bitmap filter. It applies a reddish-brown color to the Bitmap.
 */
public class SepiaBitmapFilter extends BitmapFilterBase {

    /**
     * Instantiates a new Sepia bitmap filter.
     *
     * @param sourceBitmap the source bitmap
     */
    public SepiaBitmapFilter(Bitmap sourceBitmap) {
        super(sourceBitmap);
    }

    /*
     * This filter calculates the average of every color on a pixel and applies that value to said pixel.
     */
    @Override
    public Bitmap applyFilter() {
        int depth = 20;

        IntStream.range(0, sourceBitmap.getWidth())
                .parallel()
                .forEach(x -> IntStream.range(0, sourceBitmap.getHeight())
                        .parallel()
                        .forEach(y -> {
                            int r, g, b;
                            int c = sourceBitmap.getPixel(x, y);

                            r = Color.red(c);
                            g = Color.green(c);
                            b = Color.blue(c);

                            //generates the average value of current pixel
                            r = g = b = (r + g + b) / 3;

                            //checks that the value is not higher than 255, this is the max value for any pixel in the RGB spectrum
                            r = Math.min(r + (depth * 2), 255);
                            g = Math.min(g + depth, 255);

                            resultBitmap.setPixel(x, y, Color.rgb(r, g, b));
                        })
                );

        return resultBitmap;
    }

    @Override
    public String getName() {
        return "Sepia";
    }
}
