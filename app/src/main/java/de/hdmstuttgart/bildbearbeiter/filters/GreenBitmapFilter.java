package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.stream.IntStream;

public class GreenBitmapFilter extends BitmapFilterBase {

    public GreenBitmapFilter(Bitmap sourceBitmap) {
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
                            int r = 0;
                            int g = Color.green(p) + 150;
                            int b = 0;
                            resultBitmap.setPixel(x, y, Color.argb(Color.alpha(p), r, g, b));
                        })
                );

        return resultBitmap;
    }

    @Override
    public String getName() {
        return "Green Channel";
    }
}
