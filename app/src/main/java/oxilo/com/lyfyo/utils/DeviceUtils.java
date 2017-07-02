/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Shopify Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package oxilo.com.lyfyo.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class DeviceUtils {

    private int screenWidth;
    private int imageMaxAreaHeight;

    public static boolean isTablet(Resources res) {
        int screenLayoutMasked = res.getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        return screenLayoutMasked == Configuration.SCREENLAYOUT_SIZE_XLARGE || screenLayoutMasked == Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public DeviceUtils(Context mContext){
        windowManager(mContext);
    }

    public void windowManager(Context mContext){
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        if (DeviceUtils.isTablet(mContext.getResources())) {
            screenHeight = ((AppCompatActivity)mContext).getWindow().getAttributes().height;
            screenWidth =  ((AppCompatActivity)mContext).getWindow().getAttributes().width;
        }

        // Make sure the description area below the image is at least 40% of the screen height
        int minDetailsHeightInPx = Math.round(screenHeight * 0.4f);
        int minDetailsWidthInPx = Math.round(screenHeight * 0.4f);

        int maxHeightInPx = screenHeight - minDetailsHeightInPx ;
        int maxWidthInPx = screenWidth - minDetailsWidthInPx;
        imageMaxAreaHeight = Math.min(screenHeight, maxHeightInPx);
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getImageMaxAreaHeight() {
        return imageMaxAreaHeight;
    }

}
