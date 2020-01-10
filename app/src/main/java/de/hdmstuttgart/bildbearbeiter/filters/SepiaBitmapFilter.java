package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.stream.IntStream;

public class SepiaBitmapFilter extends BitmapFilterBase {

    public SepiaBitmapFilter(Bitmap sourceBitmap) {
        super(sourceBitmap);
    }

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

                            r = g = b = (r + g + b) / 3;

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
