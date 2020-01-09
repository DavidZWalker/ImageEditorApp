package de.hdmstuttgart.bildbearbeiter.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;

public class VignetteBitmapFilter extends BitmapFilterBase {

    public VignetteBitmapFilter(Bitmap sourceBitmap) {
        super(sourceBitmap);
    }

    @Override
    public Bitmap applyFilter() {
        resultBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(),sourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        int rad;
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(sourceBitmap, 0, 0, new Paint());
        if(sourceBitmap.getWidth()<sourceBitmap.getHeight()){
            int o = (sourceBitmap.getHeight()*2)/100;
            rad = sourceBitmap.getHeight() - o*50/3;
        }else{
            int o = (sourceBitmap.getWidth()*2)/100;
            rad = sourceBitmap.getWidth() - o*50/3;
        }
        Rect rect = new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight());
        RectF rectf = new RectF(rect);
        int[] colors = new int[] { 0, 0, Color.BLACK };
        float[] pos = new float[] { 0.0f, 0.1f, 1.0f };
        Shader linGradLR = new RadialGradient(rect.centerX(), rect.centerY(),rad, colors, pos, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(linGradLR);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setAlpha(255);
        canvas.drawRect(rectf, paint);
        return resultBitmap;
    }

    @Override
    public String getName() {
        return "Vignette";
    }
}
