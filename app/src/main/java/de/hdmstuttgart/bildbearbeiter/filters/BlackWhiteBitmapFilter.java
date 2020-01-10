package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.stream.IntStream;

public class BlackWhiteBitmapFilter extends BitmapFilterBase {

    public BlackWhiteBitmapFilter(Bitmap sourceBitmap) {
        super(sourceBitmap);
    }

    @Override
    public Bitmap applyFilter() {
        double contrast = Math.pow(1.5, 2);

        IntStream.range(0, sourceBitmap.getWidth())
                .parallel()
                .forEach(x -> IntStream.range(0, sourceBitmap.getHeight())
                        .parallel()
                        .forEach(y -> {
                            // get pixel color
                            int pixel = sourceBitmap.getPixel(x, y);
                            int A = Color.alpha(pixel);
                            // apply filter contrast for every channel R, G, B
                            int R = Color.red(pixel);
                            R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                            if (R < 0) {
                                R = 0;
                            } else if (R > 255) {
                                R = 255;
                            }

                            int G = Color.red(pixel);
                            G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                            if (G < 0) {
                                G = 0;
                            } else if (G > 255) {
                                G = 255;
                            }

                            int B = Color.red(pixel);
                            B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                            if (B < 0) {
                                B = 0;
                            } else if (B > 255) {
                                B = 255;
                            }

                            // set new pixel color to output bitmap
                            resultBitmap.setPixel(x, y, Color.argb(A, R, G, B));
                        }));
        return resultBitmap;
    }

    @Override
    public String getName() {
        return "B&W";
    }
}
