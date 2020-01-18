package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;

/**
 * The type Vignette bitmap filter will create A Bitmap with a dark border.
 */
public class VignetteBitmapFilter extends BitmapFilterBase {

    /**
     * Instantiates a new Vignette bitmap filter.
     * @param sourceBitmap the source bitmap
     */
    public VignetteBitmapFilter(Bitmap sourceBitmap) {
        super(sourceBitmap);
    }

    @Override
    public Bitmap applyFilter() {
        resultBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        int rad;

        //this is used to draw things onto the Bitmap
        Canvas canvas = new Canvas(resultBitmap);

        //draws the Bitmap on the canvas
        canvas.drawBitmap(sourceBitmap, 0, 0, new Paint());

        //checks the dimensions and calculates how many pixels are in the outer 20%
        //rad is the radius of the vignette
        if (sourceBitmap.getWidth() < sourceBitmap.getHeight()) {
            int o = (sourceBitmap.getHeight() * 2) / 100;
            rad = sourceBitmap.getHeight() - o * 50 / 3;
        } else {
            int o = (sourceBitmap.getWidth() * 2) / 100;
            rad = sourceBitmap.getWidth() - o * 50 / 3;
        }

        //draws a rectangle with the dimensions of the bitmap
        Rect rect = new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight());

        //creates  a Rectangle
        RectF rectf = new RectF(rect);

        //this is the color used for the shadow effect
        int[] colors = new int[]{0, 0, Color.BLACK};

        //this will be the different stops of the Gradient
        float[] pos = new float[]{0.0f, 0.1f, 1.0f};

        //this creates a radial gradient fitting to the given bitmap
        Shader linGradLR = new RadialGradient(rect.centerX(), rect.centerY(), rad, colors, pos, Shader.TileMode.CLAMP);

        //Paint is used to apply the Gradient to the Bitmap
        Paint paint = new Paint();
        paint.setShader(linGradLR);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setAlpha(255);

        //gradient is applied
        canvas.drawRect(rectf, paint);
        return resultBitmap;
    }

    @Override
    public String getName() {
        return "Vignette";
    }
}
