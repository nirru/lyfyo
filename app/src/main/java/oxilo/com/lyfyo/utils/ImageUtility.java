/*
 *   The MIT License (MIT)
 *
 *   Copyright (c) 2015 Shopify Inc.
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package oxilo.com.lyfyo.utils;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.net.URI;

public final class ImageUtility {

    private static final String LOG_TAG = ImageUtility.class.getSimpleName();
    /**
     * Return the URL of an appropriate file to display for the given product image. This method
     * examines the default URL for the image, and returns the URL for a resized version of that
     * image which has dimensions that are at least as large as the specified width and height.
     *
     * @param featuredImageUrl    URL of image src.
     * @param imageTargetWidthPx  Width of the view that will display the image, in pixels.
     * @param imageTargetHeightPx Height of the view that will display the image, in pixels.
     * @return A URL string, or null, if one could not be generated.
     */
    public static String getSizedImageUrl(final String featuredImageUrl, int imageTargetWidthPx, int imageTargetHeightPx) {
        if (featuredImageUrl == null) {
            return null;
        }

        // The usage of both Java's URI and Android's Uri classes is intentional. The former provides public
        // getters to retrieve the path component, but a verbose constructor to build a URI out of its
        // components. The latter is immutable but provides a Builder class with a cleaner way of
        // altering a single component.

        try {
            URI originalUri = new URI(featuredImageUrl);
            String path = originalUri.getPath();
            String suffixedPath;
            String sizeSuffix = getImageSuffixForDimensions(imageTargetWidthPx, imageTargetHeightPx);
            int extensionSeparatorPos = path.lastIndexOf('.');
            if (-1 == extensionSeparatorPos) {
                // Shopify should always store the images in the CDN with an extension, even if you upload
                // the file without one. But just in case.
                suffixedPath = path + sizeSuffix;
            } else {
                suffixedPath = path.substring(0, extensionSeparatorPos) + sizeSuffix + path.substring(extensionSeparatorPos);
            }

            return Uri.parse(featuredImageUrl).buildUpon().path(suffixedPath).toString();
        } catch (Exception e) {
            Log.v(LOG_TAG, "Getting sized image URL", e);
            return null;
        }
    }

    /**
     * Fetches and loads an image into an ImageView that has layout params with MATCH_PARENT as width and/or height. You must pass in non-zero values for
     * {@code parentWidth} and {@code parentHeight} so that an appropriately sized image can be fetched from the server.
     *
     * @param parentHeight        The height of the parent view in px.
     * @param parentWidth         The width of the parent view in px.
     * @param crop                Crop image if true.
     * @param imageLoader         ImageLoader to use.
     * @param imageSrc            The Url for the image.
     * @param imageView           The view to load the image into.
     * @param placeholderDrawable Image drawable to use as a placeholder.
     * @param errorDrawable       Image drawable to use on error.
     * @param callback            callback to call when image load completes.
     */
    public static void loadRemoteImageIntoViewWithoutSize(final Picasso imageLoader, String imageSrc, final ImageView imageView, int parentWidth, int parentHeight, boolean crop, Drawable placeholderDrawable, Drawable errorDrawable, Callback callback) {
        String imageUrl = getSizedImageUrl(imageSrc, parentWidth, parentHeight);
        RequestCreator c = imageLoader.load(imageUrl).fit();

        if (placeholderDrawable != null) {
            c.placeholder(placeholderDrawable);
        }

        if (errorDrawable != null) {
            c.error(errorDrawable);
        }

        if (crop) {
            c = c.centerCrop();
        } else {
            c = c.centerInside();
        }
        c.into(imageView, callback);
    }

    /**
     * Fetches and loads an image into an ImageView that has layout params with MATCH_PARENT as width and/or height. You must pass in non-zero values for
     * {@code parentWidth} and {@code parentHeight} so that an appropriately sized image can be fetched from the server.
     * @param imageLoader  ImageLoader to use.
     * @param imageSrc     The Url for the image.
     * @param imageView    The view to load the image into.
     * @param parentWidth  The width of the parent view in px.
     * @param parentHeight The height of the parent view in px.
     * @param crop         Crop image if true.
     * @param callback     callback to call when image load completes.
     */
    public static void loadRemoteImageIntoViewWithoutSize(final Picasso imageLoader, String imageSrc, final ImageView imageView, int parentWidth, int parentHeight, boolean crop, Callback callback) {
        loadRemoteImageIntoViewWithoutSize(imageLoader, imageSrc, imageView, parentWidth, parentHeight, crop, null, null, callback);
    }

    /**
     * Load image into an ImageView where the layout has a fixed size
     *
     * @param crop        Crop image if true.
     * @param imageLoader ImageLoader to use.
     * @param imageSrc    The Url for the image.
     * @param imageView   The view to load the image into.
     * @param callback    callback to call when image load completes.
     */
    public static void loadImageResourceIntoSizedView(final Picasso imageLoader, String imageSrc, final ImageView imageView, boolean crop, Callback callback) {
//        final int imageWidthPx = imageView.getLayoutParams().width;
//        final int imageHeightPx = imageView.getLayoutParams().height;
//        String imageUrl = getSizedImageUrl(imageSrc, imageWidthPx, imageHeightPx);
//        RequestCreator c = imageLoader.load(imageUrl).resize(imageWidthPx, imageHeightPx);
        RequestCreator c = imageLoader.load(imageSrc).fit();
        if (crop) {
            c = c.centerCrop();
        } else {
            c = c.centerInside();
        }
        c.into(imageView, callback);
    }

    public static void loadImageResourceIntoSizedView(final Picasso imageLoader, String imageSrc, final ImageView imageView,  Callback callback) {

        String imageUrl = imageSrc;
        RequestCreator c = imageLoader.load(imageUrl).fit();

        c = c.centerCrop();
        c.into(imageView, callback);
    }

    /**
     * See <a href="http://docs.shopify.com/themes/filters/product-img-url">Product_img_url</a> for details.
     *
     * @return The suffix for the image of the requested dimensions.
     * @param width  The view width, in pixels.
     * @param height The view height, in pixels.
     */
    public static String getImageSuffixForDimensions(int width, int height) {
        final int pixels = Math.max(width, height);
        if (pixels <= 16) {
            return "_pico";
        } else if (pixels <= 32) {
            return "_icon";
        } else if (pixels <= 50) {
            return "_thumb";
        } else if (pixels <= 100) {
            return "_small";
        } else if (pixels <= 160) {
            return "_compact";
        } else if (pixels <= 240) {
            return "_medium";
        } else if (pixels <= 480) {
            return "_large";
        } else if (pixels <= 600) {
            return "_grande";
        } else if (pixels <= 1024) {
            return "_1024x1024";
        } else {
            return "_2048x2048";
        }
    }

    public static String stripQueryFromUrl(String url) {
        if (url == null || !url.contains("?")) {
            return url;
        }
        return url.substring(0, url.lastIndexOf('?'));
    }


    private ImageUtility() {
    }
}
