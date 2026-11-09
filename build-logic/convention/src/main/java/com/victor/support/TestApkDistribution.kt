package com.victor.support

import com.android.build.gradle.AppExtension
import org.gradle.api.Project
import org.gradle.api.GradleException
import java.io.BufferedReader
import java.io.FileWriter
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2025-2035, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TestApkDistribution
 * Author: Victor
 * Date: 2026/1/11 9:15
 * Description: 测试apk分发
 * -----------------------------------------------------------------
 */

object TestApkDistribution {

    /**
     * 手动分发测试apk
     */
    fun Project.manualDistributeTestApk() {
        println("########################## manualDistributeTestApk ##########################")

        val type = UploadType.typeOf(UploadApkConfig.UPLOAD_CHANNEL_BUILD_TYPE)

        distributeTestApk(type)
    }

    /**
     * 自动分发测试apk
     */
    fun Project.autoDistributeTestApk(taskNames: List<String>? = null) {
        val supportTasks = UploadType.values().map { it.taskName }
        println("---------------------supportTasks：$supportTasks---------------------")
        if (taskNames?.any { it in supportTasks } == true) {
            println("---------------------buildFinished$taskNames-自动分发---------------------")
            val taskName = taskNames.firstOrNull()
            println("---------------------开始自动分发$taskName---------------------")
            val type = UploadType.taskNameOf(taskName)
            distributeTestApk(type)
        }
    }

    /**
     * 分发测试apk
     */
    fun Project.distributeTestApk(uploadType: UploadType) {
        generateBuildLog(uploadType)
        if (uploadType.reinforcer360) {
            reinforceApk(uploadType)
        } else {
            uploadApk(uploadType,false)
        }
    }

    fun Project.reinforceApk(uploadType: UploadType) {
        println("########################## reinforceApk start ##########################")
        val fileName = UploadApkConfig.getBuildApkFileName(uploadType.channel, uploadType.buildType,false)
        val apkPath = "${rootDir}/app/build/outputs/apk/${uploadType.channel}/${uploadType.buildType}/$fileName"
        val keystorePath = "${rootDir}/docs/ydj_keystore.jks"
        val reinforceOutPath = "${rootDir}/app/build/outputs/apk/${uploadType.channel}/${uploadType.buildType}"

        // 检查文件是否存在
        val apkFile = file(apkPath)
        if (!apkFile.exists()) {
            println("⚠️ 警告: apk 文件不存在: $apkPath")
            return
        }

        val command = listOf(
            "python",
            "${project.rootDir}/docs/reinforcer_360.py",
            apkPath,
            keystorePath,
            reinforceOutPath
        )
        // 执行Python脚本
        executePythonScript(command)

        println("✅ 360 apk 加固成功!")
        uploadApk(uploadType,true)
    }

    fun Project.uploadApk(uploadType: UploadType, reinforceApk: Boolean) {
        println("########################## uploadApk ##########################")
        if (UploadApkConfig.UPLOAD_TO_FIR) {
            uploadToFir(uploadType,reinforceApk)
        }
        if (UploadApkConfig.UPLOAD_TO_PGYER) {
            uploadToPgyer(uploadType,reinforceApk)
        }
    }

    /**
     * 上传到fir.im
     */
    fun Project.uploadToFir(uploadType: UploadType, reinforceApk: Boolean) {
        if (uploadType.firApiKey.isBlank()) {
            println("⚠️ 警告: task：${uploadType.taskName}暂未配置 firApiKey 无法上传apk到fir.im，若需上传请在UploadType中配置 firApiKey")
            return
        }
        val fileName = UploadApkConfig.getBuildApkFileName(uploadType.channel,uploadType.buildType,reinforceApk)
        val android = extensions.getByType(AppExtension::class.java)
        val bundleId = android.defaultConfig.applicationId
        val changeLog = getLastGitCommitInfo()
        val uploadLog = "${uploadType.channel}_${uploadType.buildType}_${AppBuildConfig.BUILD_NUMBER}_$changeLog"
        val iconPath = "${rootDir}/app/src/main/res/mipmap-xxhdpi/ic_launcher.png"
        val apkPath = "${rootDir}/app/build/outputs/apk/${uploadType.channel}/${uploadType.buildType}/$fileName"

        val pythonScript = "${rootDir}/docs/upToFir.py"

        // 检查文件是否存在
        val apkFile = file(apkPath)
        if (!apkFile.exists()) {
            println("⚠️ 警告: APK文件不存在: $apkPath")
            return
        }

        // 检查icon文件是否存在
        val iconFile = file(iconPath)
        if (!iconFile.exists()) {
            println("⚠️ 警告: Icon文件不存在: $iconPath")
            return
        }

        println("########################## upload to fir start ##########################")

        // 构建命令
        val command = listOf(
            "python",
            pythonScript,
            UploadApkConfig.FIR_UPLOAD_URL,
            AppBuildConfig.BUILD_NAME,
            bundleId,
            AppBuildConfig.VERSION_NAME,
            uploadType.firApiKey,
            iconPath,
            apkPath,
            "${AppBuildConfig.VERSION_CODE}",
            uploadLog
        )
        // 执行命令
        executePythonScript(command)

        println("✅ 上传到fir.im成功!")

        println("########################## upload to fir complete ##########################")
    }

    /**
     * 上传到蒲公英
     */
    fun Project.uploadToPgyer(uploadType: UploadType, reinforceApk: Boolean) {
        if (uploadType.pgyerApiKey.isBlank()) {
            println("⚠️ 警告: task：${uploadType.taskName}暂未配置 pgyerApiKey 无法上传apk到蒲公英，若需上传请在UploadType中配置 pgyerApiKey")
            return
        }
        val fileName = UploadApkConfig.getBuildApkFileName(uploadType.channel,uploadType.buildType,reinforceApk)
        val changeLog = getLastGitCommitInfo()
        val uploadLog = "${uploadType.channel}_${uploadType.buildType}_${AppBuildConfig.BUILD_NUMBER}_$changeLog"
        val apkPath = "${rootDir}/app/build/outputs/apk/${uploadType.channel}/${uploadType.buildType}/$fileName"

        println("########################## upload to pgyer start ##########################")

        // 检查apk文件是否存在
        val apkFile = file(apkPath)
        if (!apkFile.exists()) {
            println("⚠️ 警告: apk 文件不存在: $apkPath")
            return
        }
        val command = listOf(
            "python",
            "${project.rootDir}/docs/upToPgyer.py",
            apkPath,
            uploadType.pgyerApiKey,
            uploadLog
        )

        // 执行Python脚本
        executePythonScript(command)

        println("✅ 上传到蒲公英成功!")

        println("########################## upload to pgyer complete ##########################")
    }

    /**
     * 执行Python脚本
     */
    fun executePythonScript(command: List<String?>) {
        try {
            println("########################## executePythonScript start ##########################")
            val processBuilder = getUtf8ProcessBuilder(command)
            // 执行Python脚本
            val process  = processBuilder.start()

            // 读取输出
            val reader = BufferedReader(InputStreamReader(process.inputStream, Charsets.UTF_8))
            val output = StringBuilder()

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                output.appendLine(line)
                println(line)
            }

            println("executePythonScript-result = $output")

            // 等待进程完成
            val exitCode = process.waitFor()
            if (exitCode != 0) {
                val errorStream = process.errorStream
                val errorBytes = errorStream.readBytes()
                val errorMessage = String(errorBytes, Charsets.UTF_8)
                throw GradleException("executePythonScript failed (exitCode: $exitCode): $errorMessage")
            }
            println("########################## executePythonScript complete ##########################")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 生成构建日志
     */
    fun Project.generateBuildLog(uploadType: UploadType) {
        val changeLog = getLastGitCommitInfo()
        val fileName = UploadApkConfig.getBuildApkFileName(uploadType.channel,uploadType.buildType,false)
        val android = extensions.getByType(AppExtension::class.java)
        val bundleId = android.defaultConfig.applicationId
        val apkPath = "${rootDir}/app/build/outputs/apk/${uploadType.channel}/${uploadType.buildType}/$fileName"
        val logPath =  "${rootDir}/app/build/outputs/apk/${uploadType.channel}/${uploadType.buildType}/log.txt"

        val fw = FileWriter(logPath)
        val builder = StringBuilder()
        builder.append("[构建时间]=${getBuildTime()}")
        builder.append("\r\n")
        builder.append("[编译名称]=${AppBuildConfig.BUILD_NAME}")
        builder.append("\r\n")
        builder.append("[编译包名]=$bundleId")
        builder.append("\r\n")
        builder.append("[版本名称]=${AppBuildConfig.VERSION_NAME}")
        builder.append("\r\n")
        builder.append("[版本编号]=${AppBuildConfig.VERSION_CODE}")
        builder.append("\r\n")
        builder.append("[编译版本]=${AppBuildConfig.BUILD_NUMBER}")
        builder.append("\r\n")
        builder.append("[是否上传fir]=${UploadApkConfig.UPLOAD_TO_FIR}")
        builder.append("\r\n")
        builder.append("[是否上传pgyer]=${UploadApkConfig.UPLOAD_TO_PGYER}")
        builder.append("\r\n")
        builder.append("[上传渠道]=${uploadType.channel}")
        builder.append("\r\n")
        builder.append("[上传类型]=${uploadType.buildType}")
        builder.append("\r\n")
        builder.append("[上传路径]=$apkPath")
        builder.append("\r\n")
        builder.append("[git更新日志]=$changeLog")
        fw.write(builder.toString())
        fw.flush()
        fw.close()

        println(builder.toString())
    }

    /**
     * 获取最后一次提交的git信息
     */
    fun getLastGitCommitInfo(): Map<String, String> {
        val command = "git log --no-merges --pretty=format:%s__Author:%cn__Date:%cd__Email:%ce HEAD -1"
        return try {
            val process = Runtime.getRuntime().exec(command)
            val output = process.inputStream.bufferedReader().use { it.readText() }
            println("getLastGitCommitInfo-output = $output")
            val parts = output.split("__")
            return mapOf(
                "subject" to parts.getOrElse(0) { "" },
                "author" to parts.getOrElse(1) { "" }.removePrefix("Author:"),
                "date" to parts.getOrElse(2) { "" }.removePrefix("Date:"),
                "email" to parts.getOrElse(3) { "" }.removePrefix("Email:")
            )
        } catch (e: Exception) {
            e.printStackTrace()
            emptyMap()
        }
    }

    /**
     * 获取UTF-8编码的ProcessBuilder
     */
    fun getUtf8ProcessBuilder(cmd: List<String?>): ProcessBuilder {
        val processBuilder = ProcessBuilder(cmd)
        val env = processBuilder.environment()
        // 设置 Python 的输出编码为 UTF-8
        env["PYTHONIOENCODING"] = "utf-8"
        // 禁用 Python stdout 缓冲，确保 print() 立即输出到管道
        env["PYTHONUNBUFFERED"] = "1"
        return processBuilder
    }

    /**
     * 获取构建时间
     */
    fun getBuildTime(): String {
        return LocalDateTime.now(ZoneId.of("GMT+8"))
            .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))
    }

}