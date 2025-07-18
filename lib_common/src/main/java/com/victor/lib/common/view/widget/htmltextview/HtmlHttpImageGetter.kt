package com.victor.lib.common.view.widget.htmltextview

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.text.Html
import android.view.View
import android.widget.TextView
import com.victor.lib.common.util.Loger
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference
import java.net.URI
import java.net.URL


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HtmlHttpImageGetter
 * Author: Victor
 * Date: 2024/04/08 18:23
 * Description: 
 * -----------------------------------------------------------------
 */

class HtmlHttpImageGetter(var container: TextView?,var matchParentWidth: Boolean) : Html.ImageGetter {
    private var baseUri: URI? = null

    private var compressImage = false
    private var qualityImage = 50

    override fun getDrawable(source: String?): Drawable? {
        val urlDrawable = UrlDrawable()
        // get the actual source
        val asyncTask = ImageGetterAsyncTask(
            urlDrawable, this, container,
            matchParentWidth, compressImage, qualityImage
        )
        asyncTask.execute(source)

        // return reference to URLDrawable which will asynchronously load the image specified in the src tag
        return urlDrawable
    }

    /**
     * Static inner [AsyncTask] that keeps a [WeakReference] to the [UrlDrawable]
     * and [HtmlHttpImageGetter].
     *
     *
     * This way, if the AsyncTask has a longer life span than the UrlDrawable,
     * we won't leak the UrlDrawable or the HtmlRemoteImageGetter.
     */
    inner class ImageGetterAsyncTask(
        d: UrlDrawable, imageGetter: HtmlHttpImageGetter, container: View?,
        private val matchParentWidth: Boolean, compressImage: Boolean, qualityImage: Int
    ) :
        AsyncTask<String?, Void?, Drawable?>() {
        private val drawableReference: WeakReference<UrlDrawable>
        private val imageGetterReference: WeakReference<HtmlHttpImageGetter>
        private val containerReference: WeakReference<View?>
        private val resources: WeakReference<Resources?>
        private var source: String? = null

        init {
            drawableReference = WeakReference(d)
            imageGetterReference = WeakReference(imageGetter)
            containerReference = WeakReference(container)
            resources = WeakReference(container!!.resources)
        }

        override fun doInBackground(vararg params: String?): Drawable? {
            source = params[0]
            return if (resources.get() != null) {
                fetchDrawable(resources.get(), source)
            } else null
        }

        override fun onPostExecute(result: Drawable?) {
            if (result == null) {
                Loger.e("ImageGetterAsyncTask", "Drawable result is null! (source: $source)")
                return
            }
            val imageGetter = imageGetterReference.get() ?: return
            val urlDrawable = drawableReference.get() ?: return
            // set the correct bound according to the result from HTTP call

            var scale = getScale(result)

            urlDrawable.setBounds(
                0,
                0,
                (result.intrinsicWidth * scale).toInt(),
                (result.intrinsicHeight  * scale).toInt()
            )

            // change the reference of the current drawable to the result from the HTTP call
            urlDrawable.drawable = result

            // redraw the image by invalidating the container
            imageGetter.container?.invalidate()
            // re-set text to fix images overlapping text
            imageGetter.container?.text = imageGetter.container?.text
        }

        /**
         * Get the Drawable from URL
         */
        fun fetchDrawable(res: Resources?, urlString: String?): Drawable? {
            return try {
                val inputStream = fetch(urlString)
                val drawable = BitmapDrawable(res, inputStream)
                var scale = getScale(drawable)
                drawable.setBounds(
                    0,
                    0,
                    (drawable.intrinsicWidth * scale).toInt(),
                    (drawable.intrinsicHeight * scale).toInt()
                )
                drawable
            } catch (e: Exception) {
                null
            }
        }

        private fun getScale(drawable: Drawable): Float {
            val container = containerReference.get()
            if (!matchParentWidth || container == null) {
                return 1f
            }

            val maxWidth = container.measuredWidth.toFloat()

            if (maxWidth == 0f){
                return 1f
            }

            val originalDrawableWidth = drawable.intrinsicWidth.toFloat()
            return if (maxWidth < originalDrawableWidth) {
                originalDrawableWidth / maxWidth
            } else {
                maxWidth / originalDrawableWidth
            }
        }

        @Throws(IOException::class)
        private fun fetch(urlString: String?): InputStream? {
            val url: URL
            val imageGetter = imageGetterReference.get() ?: return null
            url = if (imageGetter.baseUri != null) {
                imageGetter.baseUri!!.resolve(urlString).toURL()
            } else {
                URI.create(urlString).toURL()
            }
            return url.content as InputStream
        }
    }

    @Suppress("deprecation")
    class UrlDrawable : BitmapDrawable() {
        var drawable: Drawable? = null
        override fun draw(canvas: Canvas) {
            // override the draw to facilitate refresh function later
            drawable?.draw(canvas)
        }
    }
}