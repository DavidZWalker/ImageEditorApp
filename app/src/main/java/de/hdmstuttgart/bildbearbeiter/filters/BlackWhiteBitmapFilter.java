package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.core.math.MathUtils;

import java.util.stream.IntStream;

/**
 * The type Black white bitmap filter.
 */
public class BlackWhiteBitmapFilter extends BitmapFilterBase {

    /**
     * Instantiates a new Black white bitmap filter.
     * @param sourceBitmap the source bitmap
     */
    public BlackWhiteBitmapFilter(Bitmap sourceBitmap) {
        super(sourceBitmap);
    }

    @Override
    public Bitmap applyFilter() {
        double contrast = 2;

        IntStream.range(0, sourceBitmap.getWidth())
                .parallel()
                .forEach(x -> IntStream.range(0, sourceBitmap.getHeight())
                        .parallel()
                        .forEach(y -> {
                            // get pixel color
                            int pixel = sourceBitmap.getPixel(x, y);

                            // apply filter contrast for every channel R, G, B and makes sure every pixel stays in the range from 0 to 255
                            int r = Color.red(pixel);
                            r = (int) MathUtils.clamp(((((r / 255.0) - 0.5) * contrast) + 0.5) * 255.0, 0, 255);

                            int g = Color.red(pixel);
                            g = (int) MathUtils.clamp(((((g / 255.0) - 0.5) * contrast) + 0.5) * 255.0, 0, 255);

                            int b = Color.red(pixel);
                            b = (int) MathUtils.clamp(((((b / 255.0) - 0.5) * contrast) + 0.5) * 255.0, 0, 255);

                            // set new pixel color to output bitmap
                            resultBitmap.setPixel(x, y, Color.argb(Color.alpha(pixel), r, g, b));
                        }));

        return resultBitmap;
    }

    @Override
    public String getName() {
        return "B&W";
    }
}
