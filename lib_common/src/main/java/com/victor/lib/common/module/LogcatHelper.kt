package com.victor.lib.common.module

import com.victor.lib.common.app.App
import com.victor.lib.common.util.DateUtil
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LogcatHelper
 * Author: Victor
 * Date: 2024/04/23 19:31
 * Description: 
 * -----------------------------------------------------------------
 */

class LogcatHelper {
    private object Holder {
        val instance = LogcatHelper()
    }

    companion object {
        val instance: LogcatHelper by lazy { Holder.instance }
    }

    fun dumpLogcatToFile() {
        try {
            val date = DateUtil.getTodayDate("yyyy_MM_dd")
            val savePath = App.get().filesDir.path
            val filePath = savePath + File.separator + "hok_log_${date}.txt"

            // 创建用于输出的文件
            val fileOutputStream = FileOutputStream(filePath)

            // 创建Runtime进程
            val process = Runtime.getRuntime().exec("logcat -d")

            // 读取logcat的输出并写入文件
            val bufferedReader = BufferedReader(InputStreamReader(process.inputStream), 8192)
            val log = StringBuilder()
            var line: String? = ""
            while (bufferedReader.readLine().also { line = it } != null) {
                log.append(line)
            }

            var time = DateUtil.getTodayDate("yyyy-MM-dd HH:mm:ss")
            log.append("---------------------$time---------------------")

            // 将日志写入文件
            fileOutputStream.write(log.toString().toByteArray())

            // 关闭文件输出流
            fileOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}