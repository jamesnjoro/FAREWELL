package com.example.groundzero.farewell;

import android.graphics.Bitmap;
import android.view.View;

public class Screenshot {
    public static Bitmap takeScreenShot(View view){
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return b;
    }

    public static Bitmap takeScreenShotRootView(View v){
        return takeScreenShot(v.getRootView());
    }
}
