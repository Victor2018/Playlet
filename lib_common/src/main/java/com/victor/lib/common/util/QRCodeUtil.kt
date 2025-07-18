package com.victor.lib.common.util

import android.graphics.Bitmap
import android.graphics.Matrix
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.util.*


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: QRCodeUtil
 * Author: Victor
 * Date: 2022/8/8 17:34
 * Description: 
 * -----------------------------------------------------------------
 */

object QRCodeUtil {
    private var IMAGE_HALFWIDTH = 50

    /**
     * 生成二维码，默认大小为500*500
     *
     * @param text 需要生成二维码的文字、网址等
     * @return bitmap
     */
    fun createQRCode(text: String?): Bitmap? {
        return createQRCode(text, 500)
    }

    /**
     * 生成二维码
     *
     * @param text 文字或网址
     * @param size 生成二维码的大小
     * @return bitmap
     */
    fun createQRCode(text: String?, size: Int): Bitmap? {
        try {
            val hints: Hashtable<EncodeHintType, String?> = Hashtable()
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8")
            val bitMatrix = QRCodeWriter().encode(
                text,
                BarcodeFormat.QR_CODE, size, size, hints
            )
            val pixels = IntArray(size * size)
            for (y in 0 until size) {
                for (x in 0 until size) {
                    if (bitMatrix[x, y]) {
                        pixels[y * size + x] = -0x1000000
                    } else {
                        pixels[y * size + x] = -0x1
                    }
                }
            }
            val bitmap = Bitmap.createBitmap(
                size, size,
                Bitmap.Config.ARGB_8888
            )
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size)
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * bitmap的颜色代替黑色的二维码
     *
     * @param text
     * @param size
     * @param mBitmap
     * @return
     */
    fun createQRCodeWithLogo2(text: String?, size: Int, mBitmap: Bitmap): Bitmap? {
        var mBitmap = mBitmap
        try {
            IMAGE_HALFWIDTH = size / 10
            val hints: Hashtable<EncodeHintType, Any?> = Hashtable()
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8")
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H)
            val bitMatrix = QRCodeWriter().encode(
                text,
                BarcodeFormat.QR_CODE, size, size, hints
            )

            //将logo图片按martix设置的信息缩放
            mBitmap = Bitmap.createScaledBitmap(mBitmap, size, size, false)
            val pixels = IntArray(size * size)
            val color = -0x1
            for (y in 0 until size) {
                for (x in 0 until size) {
                    if (bitMatrix[x, y]) {
                        pixels[y * size + x] = mBitmap.getPixel(x, y)
                    } else {
                        pixels[y * size + x] = color
                    }
                }
            }
            val bitmap = Bitmap.createBitmap(
                size, size,
                Bitmap.Config.ARGB_8888
            )
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size)
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * bitmap作为底色
     *
     * @param text
     * @param size
     * @param mBitmap
     * @return
     */
    fun createQRCodeWithLogo3(text: String?, size: Int, mBitmap: Bitmap): Bitmap? {
        var mBitmap = mBitmap
        try {
            IMAGE_HALFWIDTH = size / 10
            val hints: Hashtable<EncodeHintType, Any?> = Hashtable()
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8")
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H)
            val bitMatrix = QRCodeWriter().encode(
                text,
                BarcodeFormat.QR_CODE, size, size, hints
            )

            //将logo图片按martix设置的信息缩放
            mBitmap = Bitmap.createScaledBitmap(mBitmap, size, size, false)
            val pixels = IntArray(size * size)
            val color = -0x6d8ca
            for (y in 0 until size) {
                for (x in 0 until size) {
                    if (bitMatrix[x, y]) {
                        pixels[y * size + x] = color
                    } else {
                        pixels[y * size + x] = mBitmap.getPixel(x, y) and 0x66ffffff
                    }
                }
            }
            val bitmap = Bitmap.createBitmap(
                size, size,
                Bitmap.Config.ARGB_8888
            )
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size)
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * 比方法2的颜色黑一些
     *
     * @param text
     * @param size
     * @param mBitmap
     * @return
     */
    fun createQRCodeWithLogo4(text: String?, size: Int, mBitmap: Bitmap): Bitmap? {
        var mBitmap = mBitmap
        try {
            IMAGE_HALFWIDTH = size / 10
            val hints: Hashtable<EncodeHintType, Any?> = Hashtable()
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8")
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H)
            val bitMatrix = QRCodeWriter().encode(
                text,
                BarcodeFormat.QR_CODE, size, size, hints
            )

            //将logo图片按martix设置的信息缩放
            mBitmap = Bitmap.createScaledBitmap(mBitmap, size, size, false)
            val pixels = IntArray(size * size)
            var flag = true
            for (y in 0 until size) {
                for (x in 0 until size) {
                    if (bitMatrix[x, y]) {
                        if (flag) {
                            flag = false
                            pixels[y * size + x] = -0x1000000
                        } else {
                            pixels[y * size + x] = mBitmap.getPixel(x, y)
                            flag = true
                        }
                    } else {
                        pixels[y * size + x] = -0x1
                    }
                }
            }
            val bitmap = Bitmap.createBitmap(
                size, size,
                Bitmap.Config.ARGB_8888
            )
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size)
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * 生成带logo的二维码
     * @param text
     * @param size
     * @param mBitmap
     * @return
     */
    fun createQRCodeWithLogo5(text: String?, size: Int, mBitmap: Bitmap): Bitmap? {
        var mBitmap = mBitmap
        try {
            IMAGE_HALFWIDTH = size / 10
            val hints: Hashtable<EncodeHintType, Any?> = Hashtable()
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8")
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H)
            hints.put(EncodeHintType.MARGIN, 0)//设置margin 为 0去掉二维码周围空白
            var bitMatrix = QRCodeWriter().encode(
                text,
                BarcodeFormat.QR_CODE, size, size, hints
            )

            //将logo图片按martix设置的信息缩放
            mBitmap = Bitmap.createScaledBitmap(mBitmap, size, size, false)
            val width = bitMatrix.width //矩阵高度
            val height = bitMatrix.height //矩阵宽度
            val halfW = width / 2
            val halfH = height / 2
            val m = Matrix()
            val sx = 2.toFloat() * IMAGE_HALFWIDTH / mBitmap.width
            val sy = (2.toFloat() * IMAGE_HALFWIDTH
                    / mBitmap.height)
            m.setScale(sx, sy)
            //设置缩放信息
            //将logo图片按martix设置的信息缩放
            mBitmap = Bitmap.createBitmap(
                mBitmap, 0, 0,
                mBitmap.width, mBitmap.height, m, false
            )
            val pixels = IntArray(size * size)
            for (y in 0 until size) {
                for (x in 0 until size) {
                    if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH && y > halfH - IMAGE_HALFWIDTH && y < halfH + IMAGE_HALFWIDTH) {
                        //该位置用于存放图片信息
                        //记录图片每个像素信息
                        pixels[y * width + x] = mBitmap.getPixel(
                            x - halfW
                                    + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH
                        )
                    } else {
                        if (bitMatrix[x, y]) {
                            pixels[y * size + x] = -0x1000000
                        } else {
                            pixels[y * size + x] = -0x1
                        }
                    }
                }
            }
            val bitmap = Bitmap.createBitmap(
                size, size,
                Bitmap.Config.ARGB_8888
            )
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size)
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * 修改三个顶角颜色的，带logo的二维码
     * @param text
     * @param size
     * @param mBitmap
     * @return
     */
    fun createQRCodeWithLogo6(text: String?, size: Int, mBitmap: Bitmap): Bitmap? {
        var mBitmap = mBitmap
        try {
            IMAGE_HALFWIDTH = size / 10
            val hints: Hashtable<EncodeHintType, Any?> = Hashtable()
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8")
            /*
                      * 设置容错级别，默认为ErrorCorrectionLevel.L
                      * 因为中间加入logo所以建议你把容错级别调至H,否则可能会出现识别不了
                      */hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H)
            val bitMatrix = QRCodeWriter().encode(
                text,
                BarcodeFormat.QR_CODE, size, size, hints
            )

            //将logo图片按martix设置的信息缩放
            mBitmap = Bitmap.createScaledBitmap(mBitmap, size, size, false)
            val width = bitMatrix.width //矩阵高度
            val height = bitMatrix.height //矩阵宽度
            val halfW = width / 2
            val halfH = height / 2
            val m = Matrix()
            val sx = 2.toFloat() * IMAGE_HALFWIDTH / mBitmap.width
            val sy = (2.toFloat() * IMAGE_HALFWIDTH
                    / mBitmap.height)
            m.setScale(sx, sy)
            //设置缩放信息
            //将logo图片按martix设置的信息缩放
            mBitmap = Bitmap.createBitmap(
                mBitmap, 0, 0,
                mBitmap.width, mBitmap.height, m, false
            )
            val pixels = IntArray(size * size)
            for (y in 0 until size) {
                for (x in 0 until size) {
                    if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH && y > halfH - IMAGE_HALFWIDTH && y < halfH + IMAGE_HALFWIDTH) {
                        //该位置用于存放图片信息
                        //记录图片每个像素信息
                        pixels[y * width + x] = mBitmap.getPixel(
                            x - halfW
                                    + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH
                        )
                    } else {
                        if (bitMatrix[x, y]) {
                            pixels[y * size + x] = -0xeeeeef
                            if (x < 115 && (y < 115 || y >= size - 115) || (y < 115 && x >= size - 115)) {
                                pixels[y * size + x] = -0x6d8ca
                            }
                        } else {
                            pixels[y * size + x] = -0x1
                        }
                    }
                }
            }
            val bitmap = Bitmap.createBitmap(
                size, size,
                Bitmap.Config.ARGB_8888
            )
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size)
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            return null
        }
    }

}