package com.victor.lib.common.util

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.text.TextUtils
import androidx.annotation.IntDef
import com.victor.lib.common.app.App
import com.victor.lib.common.util.FileUtil.FileType.Companion.DOC
import com.victor.lib.common.util.FileUtil.FileType.Companion.EXCEL
import com.victor.lib.common.util.FileUtil.FileType.Companion.IMAGE
import com.victor.lib.common.util.FileUtil.FileType.Companion.OTHER
import com.victor.lib.common.util.FileUtil.FileType.Companion.PDF
import com.victor.lib.common.util.FileUtil.FileType.Companion.PPT
import com.victor.lib.common.util.FileUtil.FileType.Companion.TXT
import com.victor.lib.common.util.FileUtil.FileType.Companion.docRe
import com.victor.lib.common.util.FileUtil.FileType.Companion.excelRe
import com.victor.lib.common.util.FileUtil.FileType.Companion.imageRe
import com.victor.lib.common.util.FileUtil.FileType.Companion.pdfRe
import com.victor.lib.common.util.FileUtil.FileType.Companion.pptRe
import com.victor.lib.common.util.FileUtil.FileType.Companion.txtRe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FileUtil
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 文件操作工具类
 * -----------------------------------------------------------------
 */

object FileUtil {

    private const val TAG = "FileUtil"
    private const val ROOT_FOLDER = "hok"

    @Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE, AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
    @IntDef(TXT, PDF, IMAGE, DOC, EXCEL, OTHER,PPT)
    @Retention(AnnotationRetention.SOURCE)
    annotation class FileType {
        companion object {
            const val TXT = 1
            const val PDF = 2
            const val IMAGE = 3
            const val DOC = 4
            const val EXCEL = 5
            const val OTHER = 6
            const val PPT = 7
            const val imageRe = "(?:jpg|gif|png|jpeg|webp)$"
            const val pptRe = "(?:pptx|ppt)$"
            const val docRe = "(?:docx|doc|csv)$"
            const val pdfRe = "pdf$"
            const val txtRe = "txt$"
            const val excelRe = "(?:xls|xlsx|xlsm)\$"
        }
    }

    @FileType
    fun getFileTypeForUrl(url: String?): Int {
        val str = url?.lowercase(Locale.getDefault()) ?: ""
        return when {
            imageRe.toRegex().containsMatchIn(str) -> IMAGE
            txtRe.toRegex().containsMatchIn(str) -> TXT
            pptRe.toRegex().containsMatchIn(str) -> PPT
            pdfRe.toRegex().containsMatchIn(str) -> PDF
            docRe.toRegex().containsMatchIn(str) -> DOC
            excelRe.toRegex().containsMatchIn(str) -> EXCEL
            else -> OTHER
        }
    }

    fun getFileType(paramString: String): String {
        var str = ""
        if (TextUtils.isEmpty(paramString)) {
            return str
        }
        val i = paramString.lastIndexOf('.')
        if (i <= -1) {
            return str
        }
        str = paramString.substring(i + 1)
        return str
    }

    /**
     * 在指定的位置创建指定的文件
     *
     * @param filePath 完整的文件路径
     * @param mkdir    是否创建相关的文件夹
     * @throws Exception
     */
    @Throws(Exception::class)
    fun mkFile(filePath: String?, mkdir: Boolean) {
        var file: File? = File(filePath)
        file!!.parentFile.mkdirs()
        file.createNewFile()
        file = null
    }

    /**
     * 在指定的位置创建文件夹
     *
     * @param dirPath 文件夹路径
     * @return 若创建成功，则返回True；反之，则返回False
     */
    fun mkDir(dirPath: String?): Boolean {
        return File(dirPath).mkdirs()
    }

    /**
     * 删除指定的文件
     *
     * @param filePath 文件路径
     * @return 若删除成功，则返回True；反之，则返回False
     */
    fun delFile(filePath: String?): Boolean {
        return File(filePath).delete()
    }

    /**
     * 删除指定的文件夹
     *
     * @param dirPath 文件夹路径
     * @param delFile 文件夹中是否包含文件
     * @return 若删除成功，则返回True；反之，则返回False
     */
    fun delDir(dirPath: String?, delFile: Boolean): Boolean {
        return if (delFile) {
            val file = File(dirPath)
            if (file.isFile) {
                file.delete()
            } else if (file.isDirectory) {
                if (file.listFiles().size === 0) {
                    file.delete()
                } else {
                    val zfiles: Int = file.listFiles().size
                    val delfile = file.listFiles()
                    for (i in 0 until zfiles) {
                        if (delfile[i].isDirectory) {
                            delDir(delfile[i].absolutePath, true)
                        }
                        delfile[i].delete()
                    }
                    file.delete()
                }
            } else {
                false
            }
        } else {
            File(dirPath).delete()
        }
    }

    /**
     * 复制文件/文件夹 若要进行文件夹复制，请勿将目标文件夹置于源文件夹中
     *
     * @param source   源文件（夹）
     * @param target   目标文件（夹）
     * @param isFolder 若进行文件夹复制，则为True；反之为False
     * @throws Exception
     */
    @Throws(Exception::class)
    fun copy(
        source: String,
        target: String,
        isFolder: Boolean
    ) {
        if (isFolder) {
            File(target).mkdirs()
            val a = File(source)
            val file = a.list()
            var temp: File? = null
            for (i in file.indices) {
                temp = if (source.endsWith(File.separator)) {
                    File(source + file[i])
                } else {
                    File(source + File.separator + file[i])
                }
                if (temp.isFile) {
                    val input = FileInputStream(temp)
                    val output =
                        FileOutputStream(target + "/" + temp.name.toString())
                    val b = ByteArray(1024)
                    var len: Int
                    while (input.read(b).also { len = it } != -1) {
                        output.write(b, 0, len)
                    }
                    output.flush()
                    output.close()
                    input.close()
                }
                if (temp.isDirectory) {
                    copy(source + "/" + file[i], target + "/" + file[i], true)
                }
            }
        } else {
            var byteread = 0
            val oldfile = File(source)
            if (oldfile.exists()) {
                val inStream: InputStream = FileInputStream(source)
                val file = File(target)
                file.parentFile.mkdirs()
                file.createNewFile()
                val fs = FileOutputStream(file)
                val buffer = ByteArray(1024)
                while (inStream.read(buffer).also { byteread = it } != -1) {
                    fs.write(buffer, 0, byteread)
                }
                inStream.close()
                fs.close()
            }
        }
    }

    /**
     * 移动指定的文件（夹）到目标文件（夹）
     *
     * @param source   源文件（夹）
     * @param target   目标文件（夹）
     * @param isFolder 若为文件夹，则为True；反之为False
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun move(
        source: String,
        target: String,
        isFolder: Boolean
    ): Boolean {
        copy(source, target, isFolder)
        return if (isFolder) {
            delDir(source, true)
        } else {
            delFile(source)
        }
    }

    /**
     * 获取缓存文件夹
     *
     * @param context
     * @return
     */
    fun getDiskCacheDir(context: Context): String? {
        var cachePath: String? = "/sdcard"
        try {
            //isExternalStorageEmulated()设备的外存是否是用内存模拟的，是则返回true
            cachePath =
                if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageEmulated()) {
                    context.externalCacheDir!!.absolutePath
                } else {
                    context.cacheDir.absolutePath
                }
        } catch (e: Exception) {
        }
        return cachePath
    }

    fun getExRootFolder(): File? {
        val rootFolder =
            File(getRootDir() + File.separator + ROOT_FOLDER)
        if (!rootFolder.exists()) {
            rootFolder.mkdir()
        }
        return rootFolder
    }

    fun getRootDir(): String {
        return App.get().filesDir.path
    }

    /**
     * 获取缓存图片的目录
     *
     * @param context Context
     * @return 缓存图片的目录
     */
    fun getImageCacheDir(context: Context): String? {
        val file: File?
        file = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        } else {
            context.cacheDir
        }
        val path = file?.path + "/cache"
        val cachePath = File(path)
        if (!cachePath.exists()) cachePath.mkdir()
        return path
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    fun getFileByPath(filePath: String?): File? {
        return if (isSpace(filePath)) null else File(filePath)
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     *
     * @param s
     * @return
     */
    private fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }

    /**
     * 保存图片
     *
     * @param src      源图片
     * @param filePath 要保存到的文件路径
     * @param format   格式
     * @return `true`: 成功<br></br>`false`: 失败
     */
    fun save(
        src: Bitmap?,
        filePath: String?,
        format: Bitmap.CompressFormat?
    ): Boolean {
        return save(src, getFileByPath(filePath), format, false)
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return `true`: 存在或创建成功<br></br>`false`: 不存在或创建失败
     */
    fun createOrExistsDir(file: File?): Boolean {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return `true`: 存在或创建成功<br></br>`false`: 不存在或创建失败
     */
    fun createOrExistsFile(file: File?): Boolean {
        if (file == null) return false
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) return file.isFile
        return if (!createOrExistsDir(file.parentFile)) false else try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 保存图片
     *
     * @param src     源图片
     * @param file    要保存到的文件
     * @param format  格式
     * @param recycle 是否回收
     * @return `true`: 成功<br></br>`false`: 失败
     */
    fun save(
        src: Bitmap?,
        file: File?,
        format: Bitmap.CompressFormat?,
        recycle: Boolean
    ): Boolean {
        if (isEmptyBitmap(src) || !createOrExistsFile(file)) {
            return false
        }
        println(src?.width.toString() + ", " + src?.height)
        var os: OutputStream? = null
        var ret = false
        try {
            os = BufferedOutputStream(FileOutputStream(file))
            ret = format?.let { src?.compress(it, 100, os) }!!
            if (src != null) {
                if (recycle && !src.isRecycled) {
                    src.recycle()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            closeIO(os)
        }
        return ret
    }

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    fun closeIO(vararg closeables: Closeable?) {
        if (closeables == null) return
        try {
            for (closeable in closeables) {
                closeable?.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 判断bitmap对象是否为空
     *
     * @param src 源图片
     * @return `true`: 是<br></br>`false`: 否
     */
    private fun isEmptyBitmap(src: Bitmap?): Boolean {
        return src == null || src.width == 0 || src.height == 0
    }


    fun copyAssetsDataToCache(baseName: String,copyCompleted: ((filePath: String) -> Unit)) {
        CoroutineScope(Dispatchers.IO).launch {
            var outputStream: OutputStream? = null
            var inputStream: InputStream? = null
            val file = File(App.get().cacheDir, baseName)
            if (file.exists()) {
                Loger.e(TAG,"copyAssetsDataToCache-file exists path = ${file?.path}")
                CoroutineScope(Dispatchers.Main).launch {
                    file?.path?.let { copyCompleted.invoke(it) }
                }
                return@launch
            }
            try {
                outputStream = FileOutputStream(file)
                inputStream = App.get().assets.open(baseName)
                var len: Int
                val buffer = ByteArray(1024)
                while (inputStream.read(buffer).also { len = it } != -1) {
                    outputStream.write(buffer, 0, len)
                }
                CoroutineScope(Dispatchers.Main).launch {
                    file?.path?.let { copyCompleted.invoke(it) }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    inputStream?.close()
                    outputStream?.close()
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    CoroutineScope(Dispatchers.Main).launch {
                        file?.path?.let { copyCompleted.invoke(it) }
                    }
                }
            }
        }

    }

}