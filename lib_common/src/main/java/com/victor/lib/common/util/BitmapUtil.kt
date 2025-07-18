package com.victor.lib.common.util

import android.Manifest
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import com.victor.lib.common.module.PermissionHelper
import com.victor.lib.common.app.App
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BitmapUtil
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

object BitmapUtil {

    fun drawableToBitmap(drawable: Drawable): Bitmap? {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        )
        val canvas = Canvas(bitmap)

        //canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return bitmap
    }

    fun saveBitmap(
        bitmap: Bitmap?,
        dir: String?,
        name: String?,
        isShowPhotos: Boolean
    ): Boolean {
        if (!PermissionHelper.hasPermission(App.get(),Manifest.permission.WRITE_EXTERNAL_STORAGE)||
            !PermissionHelper.hasPermission(App.get(),Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Loger.e(javaClass.simpleName,"saveBitmap-has no permission to save")
            return false
        }

        val path = File(dir)
        if (!path.exists()) {
            path.mkdirs()
        }
        val file = File("$path/$name")
        if (file.exists()) {
            file.delete()
        }
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        } else {
            return true
        }
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(file)
            bitmap?.compress(
                Bitmap.CompressFormat.PNG, 100,
                fileOutputStream
            )
            fileOutputStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            try {
                fileOutputStream!!.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 其次把文件插入到系统图库
        if (isShowPhotos) {
            try {
                MediaStore.Images.Media.insertImage(
                    App.get().getContentResolver(),
                    file.absolutePath, name, null
                )
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            // 最后通知图库更新
            App.get().sendBroadcast(
                Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.parse("file://$file")
                )
            )
        }
        return true
    }

    fun getBlurBitMap(bm: Bitmap?, view: View): Bitmap? {
        val radius = 25
        val scaleFactor = 8f
        val overlay = Bitmap.createBitmap(
            (view.measuredWidth / scaleFactor).toInt(),
            (view.measuredHeight / scaleFactor).toInt(),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(overlay)
        canvas.translate(-view.left / scaleFactor, -view.top / scaleFactor)
        canvas.scale(1 / scaleFactor, 1 / scaleFactor)
        val paint = Paint()
        paint.flags = Paint.FILTER_BITMAP_FLAG
        canvas.drawBitmap(bm!!, 0f, 0f, paint)
        return overlay
    }

    /**
     * 将byte[]转换成Bitmap
     *
     * @param bytes
     * @param width
     * @param height
     * @return
     */
    fun getBitmapFromByte(
        bytes: ByteArray,
        width: Int,
        height: Int
    ): Bitmap? {
        val image =
            YuvImage(bytes, ImageFormat.NV21, width, height, null)
        val os = ByteArrayOutputStream(bytes.size)
        if (!image.compressToJpeg(Rect(0, 0, width, height), 100, os)) {
            return null
        }
        val tmp = os.toByteArray()
        return BitmapFactory.decodeByteArray(tmp, 0, tmp.size)
    }

    fun circleBitmap(source: Bitmap): Bitmap? {
        //获取Bitmap的宽度
        val width = source.width
        //以Bitmap的宽度值作为新的bitmap的宽高值。
        val bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
        //以此bitmap为基准，创建一个画布
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.isAntiAlias = true
        //在画布上画一个圆
        canvas.drawCircle(
            (width / 2).toFloat(),
            (width / 2).toFloat(),
            (width / 2).toFloat(),
            paint
        )

        //设置图片相交情况下的处理方式
        //setXfermode：设置当绘制的图像出现相交情况时候的处理方式的,它包含的常用模式有：
        //PorterDuff.Mode.SRC_IN 取两层图像交集部分,只显示上层图像
        //PorterDuff.Mode.DST_IN 取两层图像交集部分,只显示下层图像
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        //在画布上绘制bitmap
        canvas.drawBitmap(source, 0f, 0f, paint)
        return bitmap
    }

    fun bmpToByteArray (image: Bitmap,size: Int): ByteArray? {
        val out = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 85, out)
        val zoom = Math.sqrt(size * 1024 / out.toByteArray().size.toDouble()).toFloat()

        val matrix = Matrix()
        matrix.setScale(zoom, zoom)

        var result =
            Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true)

        out.reset()
        result.compress(Bitmap.CompressFormat.JPEG, 85, out)
        while (out.toByteArray().size > size * 1024) {
            matrix.setScale(0.9f, 0.9f)
            result = Bitmap.createBitmap(result, 0, 0, result.width, result.height, matrix, true)
            out.reset()
            result.compress(Bitmap.CompressFormat.JPEG, 85, out)
        }
        return out.toByteArray()
    }

    fun scaleBitmap (image: Bitmap,size: Int): Bitmap? {
        val out = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 85, out)
        val zoom = Math.sqrt(size * 1024 / out.toByteArray().size.toDouble()).toFloat()

        val matrix = Matrix()
        matrix.setScale(zoom, zoom)

        var result =
            Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true)

        out.reset()
        result.compress(Bitmap.CompressFormat.JPEG, 85, out)
        while (out.toByteArray().size > size * 1024) {
            matrix.setScale(0.9f, 0.9f)
            result = Bitmap.createBitmap(result, 0, 0, result.width, result.height, matrix, true)
            out.reset()
            result.compress(Bitmap.CompressFormat.JPEG, 85, out)
        }
        return result
    }

}