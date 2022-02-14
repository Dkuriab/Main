package com.onlineshop.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Utils using for downloading images from remote resources
 */
class ImageDownloader {

    /**
     * Downloads image by its url, sets it to passed [ImageView]
     *
     * @param imageURL link to image
     * @param imageView [ImageView] in which the image will be inserted
     */
    fun downloadImageToViewByURL(
        imageURL: String,
        imageView: ImageView
    ) {
        Picasso.get()
            .load(imageURL)
            .into(imageView)
    }
}
